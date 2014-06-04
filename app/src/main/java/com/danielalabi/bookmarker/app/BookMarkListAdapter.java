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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class BookMarkListAdapter extends ArrayAdapter<String> {
    Context context;
    int resource;

    public BookMarkListAdapter(Context context, int resource, ArrayList<String> listItems) {
        super(context, resource, listItems);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        String item = this.getItem(position);
        final BookMark bookmark = BookMark.deserializeBookMark(item);
        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(bookmark.getName());


        final BookMarkListAdapter m  = this;

        // Because each list item contains UI list item targets, you should not override
        // onListItemClick. Instead, set a click listener for each target individually.

        LinearLayout l1 = (LinearLayout) convertView.findViewById(R.id.primary_target);
        if (l1 != null) {
            l1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BookMarkMainActivity) m.context).openURI(bookmark.getURI());
                    }
                });
        }

        LinearLayout l2 = (LinearLayout) convertView.findViewById(R.id.primary_target2);
        if (l2 != null) {
                l2.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((BookMarkMainActivity) m.context).openBookMark(bookmark);
                            }
                        });
        }

        ImageButton l3 = (ImageButton) convertView.findViewById(R.id.secondary_action);
        if (l3 != null) {
            l3.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((BookMarkMainActivity)m.context).deleteDialog(bookmark);
                        }
                    });
        }
        return convertView;
    }
}
