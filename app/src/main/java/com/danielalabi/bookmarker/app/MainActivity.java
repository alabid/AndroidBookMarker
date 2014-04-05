package com.danielalabi.bookmarker.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
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

    public final static String EXTRA_MESSAGE = "com.danielalabi.bookmark.MESSAGE1";

    /** Called when the user clicks the Send button */
    public void openBookMark(View view, String s) {
        // Do something in response to button
        Intent intent = new Intent(this, BookMarkEditingActivity.class);

        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> listItems = new ArrayList<String>();
        listItems.add("goat");
        listItems.add("kudu");
        listItems.add("pig");
        listItems.add("moose");
        listItems.add("parrot fish");
        listItems.add("frogfish");
        listItems.add("blue tang");
        listItems.add("scorpion fish");
        listItems.add("spotted eagle ray");

        MainAdapter adapter = new MainAdapter(this, R.layout.list_item, listItems);
        ListView listView = (ListView)this.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        findViewById(R.id.create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        "About to create new bookmark",
                        Toast.LENGTH_SHORT).show();
            }
        });
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
        return super.onOptionsItemSelected(item);
    }

}
