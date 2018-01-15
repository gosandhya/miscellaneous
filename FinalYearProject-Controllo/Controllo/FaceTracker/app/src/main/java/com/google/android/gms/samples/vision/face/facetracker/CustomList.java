package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] menuitemss;
    private final Integer[] menuiconss;


    public CustomList(Activity context,
                      String[] menuitemss, String[] category, Integer[] menuiconss) {
        super(context, R.layout.listview_layout, menuitemss);
        this.context = context;
        this.menuitemss = menuitemss;
        this.menuiconss = menuiconss;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview_layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.place);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.preference);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        txtTitle.setText(menuitemss[position]);
        txtTitle1.setText(menuitemss[position]);
        imageView.setImageResource(menuiconss[position]);
        return rowView;
    }
}

