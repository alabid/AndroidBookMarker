package com.danielalabi.bookmarker.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookMarkEditingActivity extends Activity {
    String currentName;
    String currentURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String[] current = intent.getStringExtra(MainActivity.BOOK_MARK_MESSAGE).split("\\|");
        currentName = current[0];

        setContentView(R.layout.activity_book_mark_editing);
        EditText t1 = (EditText) findViewById(R.id.editTextName);
        EditText t2 = (EditText) findViewById(R.id.editTextURI);
        t1.setText(currentName);
        if (current.length == 2) {
            t2.setText(current[1]);
        }
    }


    /** Called when the user clicks the Send button */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void saveBookMark(View view) {
        // save and update list of items here
        /*
        TextView textView = (TextView) findViewById(R.id.name_show);
        EditText e = (EditText) findViewById(R.id.editTextName);
        current = (String) e.getText().toString();

        Toast.makeText(view.getContext(),
                "Updated " + current,
                Toast.LENGTH_SHORT).show();
        */
        // for now, just close
        navigateUpTo(getParentActivityIntent());
        // navigateUpFromSameTask(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_mark_editing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.edit) {
            // set content view to everything editable
            // also do this when
            // Create the text view
            setContentView(R.layout.activity_book_mark_editing);
            EditText e = (EditText) findViewById(R.id.editTextName);
            e.setTextSize(40);
            e.setText(currentName);
        }
        return super.onOptionsItemSelected(item);
    }

}
