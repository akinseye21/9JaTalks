package com.ng.NaijaTalks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ng.NaijaTalks.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> banner;
    ArrayList<String> groupName;
    LayoutInflater inflter;

    public GroupAdapter(Context applicationContext, ArrayList<Integer> banner, ArrayList<String> groupName) {
        this.context = applicationContext;
//        this.image = image;
        this.banner = banner;
        this.groupName = groupName;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return banner.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.group_view_item, null); // inflate the layout
        LinearLayout bannerl = (LinearLayout) convertView.findViewById(R.id.banner);
        TextView groupNameT = (TextView) convertView.findViewById(R.id.groupName);
        LinearLayout join = (LinearLayout) convertView.findViewById(R.id.join);

        bannerl.setBackgroundResource(banner.get(position));
        groupNameT.setText(groupName.get(position));
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getItem(position);
            }
        });


        return convertView;
    }
}
