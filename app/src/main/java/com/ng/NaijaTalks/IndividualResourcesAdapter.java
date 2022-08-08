package com.ng.NaijaTalks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class IndividualResourcesAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> date;
    ArrayList<String> title;
    ArrayList<String> image;
    ArrayList<String> content;
    ArrayList<Integer> category_id;
    LayoutInflater inflter;

    public IndividualResourcesAdapter(Context applicationContext, ArrayList<String> date, ArrayList<String> title, ArrayList<String> image, ArrayList<String> content, ArrayList<Integer> category_id) {
        this.context = applicationContext;
        this.date = date;
        this.title = title;
        this.image = image;
        this.content = content;
        this.category_id = category_id;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return date.size();
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
        TextView mtitle = (TextView) convertView.findViewById(R.id.tx);
        TextView mdate = (TextView) convertView.findViewById(R.id.date);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        RelativeLayout go = (RelativeLayout) convertView.findViewById(R.id.go);


        mtitle.setText(title.get(position));
        mdate.setText(date.get(position));
        Glide.with(context).load(image.get(position)).into(img);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send the details to the next page
                //title, image, content and date
                Intent intent = new Intent(context, ResourcesDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", title.get(position));
                intent.putExtra("date", date.get(position));
                intent.putExtra("image", image.get(position));
                intent.putExtra("category_id", category_id.get(position));
                context.startActivity(intent);

            }
        });

        return convertView;

    }

}