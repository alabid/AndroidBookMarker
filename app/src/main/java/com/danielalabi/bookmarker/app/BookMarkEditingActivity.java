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
    public final static String BOOK_MARK_MESSAGE_OLD = "com.danielalabi.bookmark.BOOK_MARK_MESSAGE_OLD";
    public final static String BOOK_MARK_MESSAGE_NEW = "com.danielalabi.bookmark.BOOK_MARK_MESSAGE_NEW";

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
            currentURI = current[1];
            t2.setText(currentURI);
        }
    }


    /** Called when the user clicks the Send button */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void saveBookMark(View view) {
        EditText e1 = (EditText) findViewById(R.id.editTextName);
        String newName = (String) e1.getText().toString();

        EditText e2 = (EditText) findViewById(R.id.editTextURI);
        String newURI = (String) e2.getText().toString();

        if (newName.length() == 0 || newURI.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "BookMark name or URI cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = getParentActivityIntent();
            intent.putExtra(BOOK_MARK_MESSAGE_OLD, currentName + "|" + currentURI);
            if (!newURI.startsWith("http://") || newURI.startsWith("https://")) {
                newURI = "http://" + newURI;
            }
            intent.putExtra(BOOK_MARK_MESSAGE_NEW, newName + "|" + newURI);
            navigateUpTo(intent);
        }
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

        return super.onOptionsItemSelected(item);
    }

}
