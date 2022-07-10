package com.ng.NaijaTalks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResourcesAdapter extends BaseAdapter {
    Context context;
    int publications[];
    String text[];
    LayoutInflater inflter;

    public ResourcesAdapter(Context applicationContext, int[] pub, String[] txt) {
        this.context = applicationContext;
        this.publications = pub;
        this.text = txt;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return publications.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.grid_view_item, null); // inflate the layout
        TextView textView = (TextView) convertView.findViewById(R.id.tx);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
        textView.setText(text[position]);
        imageView.setImageResource(publications[position]);
        return convertView;

    }
}
