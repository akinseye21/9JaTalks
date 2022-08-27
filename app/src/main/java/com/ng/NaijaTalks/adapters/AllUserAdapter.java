package com.ng.NaijaTalks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ng.NaijaTalks.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserAdapter extends BaseAdapter {

    Context context;
//    ArrayList<String> image;
    ArrayList<String> username;
    ArrayList<String> email;
    ArrayList<String> fullname;
    LayoutInflater inflter;

    public AllUserAdapter(Context applicationContext, ArrayList<String> username, ArrayList<String> email, ArrayList<String> fullname) {
        this.context = applicationContext;
//        this.image = image;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return username.size();
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

        convertView = inflter.inflate(R.layout.user_view_item, null); // inflate the layout
        TextView uname = (TextView) convertView.findViewById(R.id.uname);
        TextView uemail = (TextView) convertView.findViewById(R.id.uemail);
        CircleImageView imageView = (CircleImageView) convertView.findViewById(R.id.img);

        uname.setText(username.get(position));
        uemail.setText(email.get(position));

        return convertView;

    }

}
