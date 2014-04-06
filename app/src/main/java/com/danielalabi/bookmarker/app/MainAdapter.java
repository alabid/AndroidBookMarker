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
import android.widget.Toast;

import java.util.ArrayList;


public class MainAdapter extends ArrayAdapter<String> {
    Context c;
    int resource;
    public MainAdapter(Context context, int resource, ArrayList<String> listItems) {
        super(context, resource, listItems);
        this.c = context;
        this.resource = resource;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }
        if (convertView != null) {
            String item = this.getItem(position);
            if (item.length() > 0) {
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                String[] kv = item.split("\\|");
                textView.setText(kv[0]);
            }

        }
        final MainAdapter m  = this;
        // Because the list item contains multiple touch targets, you should not override
        // onListItemClick. Instead, set a click listener for each target individually.

        LinearLayout l1 = (LinearLayout) convertView.findViewById(R.id.primary_target);
        if (l1 != null) {
            l1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),
                                "Item clicked for " +m.getItem(position),
                                Toast.LENGTH_SHORT).show();
                        String[] info = m.getItem(position).split("\\|");
                        if (info.length == 2) {
                            ((MainActivity)m.c).openURI(view, info[1]);
                        }
                    }
                });
        }

        LinearLayout l2 = (LinearLayout) convertView.findViewById(R.id.primary_target2);
        if (l2 != null) {
                l2.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(view.getContext(),
                                        "Item clicked for " + m.getItem(position),
                                        Toast.LENGTH_SHORT).show();
                                ((MainActivity) m.c).openBookMark(view, m.getItem(position));
                            }
                        });
        }

        ImageButton l3 = (ImageButton) convertView.findViewById(R.id.secondary_action);
        if (l3 != null) {
            l3.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(),
                                    "Trash clicked for " + m.getItem(position),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return convertView;
    }
}
