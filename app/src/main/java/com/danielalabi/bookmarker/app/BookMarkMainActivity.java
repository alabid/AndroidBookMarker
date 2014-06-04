package com.danielalabi.bookmarker.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookMarkMainActivity extends ListActivity {

    public final static String BOOK_MARK_MESSAGE = "com.danielalabi.bookmark.BOOK_MARK_MESSAGE";
    public final static String BOOK_MARK_COUNT = "com.danielalabi.bookmark.item.count";
    public final static String BOOK_MARK_ITEM_STRING = "com.danielalabi.bookmark.item.";

    BookMarkListAdapter adapter;
    ArrayList<String> listItems;
    BookMarkMainActivity thisActivity;
    boolean notEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (listItems == null) {
            listItems = getData();
        }

        getIntentFromBookMarkEditingActivity();
        adapter = getAdapterListItem(R.layout.list_item);

        thisActivity = this;
        findViewById(R.id.create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thisActivity.openEmptyBookMark();

            }
        });

        notEditing = false;
    }


    public void getIntentFromBookMarkEditingActivity() {
        Intent intent = getIntent();
        String extra1 = intent.getStringExtra(BookMarkEditingActivity.BOOK_MARK_MESSAGE_OLD);
        String extra2 = intent.getStringExtra(BookMarkEditingActivity.BOOK_MARK_MESSAGE_NEW);
        if (extra1 != null && extra2 != null) {
            int index = 0;

            if (listItems.contains(extra1)) {
                index = listItems.indexOf(extra1);
                listItems.remove(extra1);
            }

            listItems.add(index, extra2);
            commitData();
        }
    }

    public BookMarkListAdapter getAdapterListItem(int resource) {
        BookMarkListAdapter adapter = new BookMarkListAdapter(this, resource, listItems);
        ListView listView = (ListView)this.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        return adapter;
    }

    public ArrayList<String> getData() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int itemCount = prefs.getInt(BOOK_MARK_COUNT, 0);

        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < itemCount; i++) {
            String item = prefs.getString(BOOK_MARK_ITEM_STRING + i, null);
            if (item != null) {
                data.add(item);
            }
        }
        return data;
    }

    public void commitData() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(BOOK_MARK_COUNT, listItems.size());

        for (int i = 0; i < listItems.size(); i++) {
            editor.putString(BOOK_MARK_ITEM_STRING + i, listItems.get(i));
        }
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        if (!notEditing) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        commitData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        commitData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commitData();
    }

    public void deleteDialog(final BookMark bookmark) {
        final String toDelete = bookmark.serializeBookMark();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete the '" + bookmark.getName() + "' BookMark?");

        final BookMarkMainActivity m = this;

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (listItems.contains(toDelete)) {
                    listItems.remove(toDelete);
                }
                String name = bookmark.getName();
                Toast.makeText(getApplicationContext(),
                               "Just deleted BookMark '" + name + "'",
                               Toast.LENGTH_SHORT).show();
                m.commitData();
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
         });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void openURI(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void openEmptyBookMark() {
        openBookMark(new BookMark());
    }

    public void openBookMark(BookMark bookmark) {
        // Do something in response to button
        Intent intent = new Intent(this, BookMarkEditingActivity.class);
        intent.putExtra(BOOK_MARK_MESSAGE, bookmark.serializeBookMark());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem mi = menu.getItem(0);

        if(notEditing) {

            mi.setTitle("Done");
            mi.setIcon(android.R.drawable.ic_menu_upload);
            adapter = getAdapterListItem(R.layout.list_item_info);
            notEditing = false;
        }
        else{

            mi.setTitle("Edit");
            mi.setIcon(android.R.drawable.ic_menu_edit);
            adapter = getAdapterListItem(R.layout.list_item);
            notEditing = true;
        }

        mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(menu);
    }

}
