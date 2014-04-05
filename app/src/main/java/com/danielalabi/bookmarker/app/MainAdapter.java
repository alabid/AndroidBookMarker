/**
 * Created by Jeff Ondich on 4/4/14.
 * For CS 342, Carleton College
 */

package com.danielalabi.bookmarker.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainAdapter extends ArrayAdapter<String> {
    Context c;
    public MainAdapter(Context context, int resource, ArrayList<String> listItems) {
        super(context, resource, listItems);
        this.c = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.text1);
            textView.setText(this.getItem(position));

        }
        final MainAdapter m  = this;
        // Because the list item contains multiple touch targets, you should not override
        // onListItemClick. Instead, set a click listener for each target individually.

        convertView.findViewById(R.id.primary_target).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),
                                "Item clicked for " +m.getItem(position),
                                Toast.LENGTH_SHORT).show();
                        ((MainActivity)m.c).openBookMark(view, m.getItem(position));
                    }
                });

        convertView.findViewById(R.id.secondary_action).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),
                                "Trash clicked for " + m.getItem(position),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        return convertView;
    }
}
