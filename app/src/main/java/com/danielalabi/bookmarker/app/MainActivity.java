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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    public final static String BOOK_MARK_MESSAGE = "com.danielalabi.bookmark.BOOK_MARK_MESSAGE";
    public final static String BOOK_MARK_COUNT = "com.danielalabi.bookmark.item.count";
    public final static String BOOK_MARK_ITEM_STRING = "com.danielalabi.bookmark.item.";

    MainAdapter adapter;
    ArrayList<String> listItems;
    boolean doneEditing;

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<String>();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int itemCount = prefs.getInt(BOOK_MARK_COUNT, 0);
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

    }

    public void deleteDialog(final String toDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this BookMark?");
        final MainActivity m = this;
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (listItems.contains(toDelete)) {
                    listItems.remove(toDelete);
                }
                String[] kv = toDelete.split("\\|");
                String name = kv[0];
                Toast.makeText(getApplicationContext(),
                        "Just deleted BookMark '" + name + "'",
                        Toast.LENGTH_SHORT).show();
                m.commitData();
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
       });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (listItems == null) {
            listItems = getData();
        }

        // check if intent is not null, bla bla
        Intent intent = getIntent();
        if (intent != null) {
            String extra1 = intent.getStringExtra(BookMarkEditingActivity.BOOK_MARK_MESSAGE_OLD);
            String extra2 = intent.getStringExtra(BookMarkEditingActivity.BOOK_MARK_MESSAGE_NEW);
            if (extra1 != null && extra1.length() > 0 &&
                extra2 != null && extra2.length() > 0) {
                int index = 0;

                if (listItems.contains(extra1)) {
                    System.out.println("listItems contains:"+extra1);
                    index = listItems.indexOf(extra1);
                    listItems.remove(extra1);
                }

                listItems.add(index, extra2);
            }
        }
        commitData();

        adapter = new MainAdapter(this, R.layout.list_item, listItems);
        ListView listView = (ListView)this.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        findViewById(R.id.create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();

                ((MainActivity)c).openBookMark(view, "");
            }
        });
        doneEditing = false;
    }

    public void openURI(View view, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /** Called when the user clicks the Send button */
    public void openBookMark(View view, String s) {
        // Do something in response to button
        Intent intent = new Intent(this, BookMarkEditingActivity.class);

        intent.putExtra(BOOK_MARK_MESSAGE, s);

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
        if(doneEditing){
            mi.setTitle("done"); //.setIcon(android.R.drawable.ic_menu_dR.drawable.ic_content_remove);
            mi.setIcon(android.R.drawable.ic_menu_upload);
            adapter = new MainAdapter(this, R.layout.list_item_info, listItems);
            ListView listView = (ListView)this.findViewById(android.R.id.list);
            listView.setAdapter(adapter);
            doneEditing = false;
        }
        else{
            mi.setTitle("edit");
            mi.setIcon(android.R.drawable.ic_menu_edit);
            adapter = new MainAdapter(this, R.layout.list_item, listItems);
            ListView listView = (ListView)this.findViewById(android.R.id.list);
            listView.setAdapter(adapter);
            doneEditing = true;
        }

        mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(menu);
    }

}
