package com.danielalabi.bookmarker.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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
    MainAdapter adapter;
    ArrayList<String> listItems;
    boolean doneEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItems = new ArrayList<String>();
        listItems.add("Google|http://google.com");
        listItems.add("Facebook|http://facebook.com");
        listItems.add("alabidan|http://alabidan.me");
        listItems.add("nytimes|http://nytimes");
        listItems.add("cnn|http://cnn.com");
        listItems.add("fox news|http://foxnews.com");
        listItems.add("huffington post|http://huffingtonpost.com");
        listItems.add("aljazeera|http://aljazeera.com");
        listItems.add("break.com|http://break.com");

        adapter = new MainAdapter(this, R.layout.list_item, listItems);
        ListView listView = (ListView)this.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        findViewById(R.id.create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Toast.makeText(c,
                        "About to create new bookmark",
                        Toast.LENGTH_SHORT).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }
        */
        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("on prepare called!");
        MenuItem mi = menu.getItem(0);
        if(doneEditing){
            mi.setTitle("done"); //.setIcon(android.R.drawable.ic_menu_dR.drawable.ic_content_remove);
            mi.setIcon(android.R.drawable.ic_menu_upload);
            /*MenuItem mi = menu.add("New Item");
            mi.setIcon(R.drawable.ic_location_web_site);
            canAddItem = false;
            */
            adapter = new MainAdapter(this, R.layout.list_item_info, listItems);
            ListView listView = (ListView)this.findViewById(android.R.id.list);
            listView.setAdapter(adapter);
            doneEditing = false;
        }
        else{
            mi.setTitle("edit");
            mi.setIcon(android.R.drawable.ic_menu_edit);
            /*menu.getItem(0).setIcon(R.drawable.ic_content_new);
            canAddItem = true;
            */
            adapter = new MainAdapter(this, R.layout.list_item, listItems);
            ListView listView = (ListView)this.findViewById(android.R.id.list);
            listView.setAdapter(adapter);
            doneEditing = true;
        }

        mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(menu);
    }

}
