package com.ng.NaijaTalks.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ng.NaijaTalks.HangoutRegistration;
import com.ng.NaijaTalks.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExpertHangoutAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> arr_source_url;
    ArrayList<String> arr_title;
    ArrayList<String> arr_link;
    String email;
    LayoutInflater inflter;

    public ExpertHangoutAdapter(Context applicationContext, ArrayList<String> arr_source_url, ArrayList<String> arr_title, ArrayList<String> arr_link, String email) {
        this.context = applicationContext;
        this.arr_source_url = arr_source_url;
        this.arr_title = arr_title;
        this.arr_link = arr_link;
        this.email = email;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return arr_title.size();
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflter.inflate(R.layout.expert_hangout_item, null); // inflate the layout

        CircleImageView image = convertView.findViewById(R.id.image);
        TextView title = convertView.findViewById(R.id.hangout_title);
        TextView excerpt = convertView.findViewById(R.id.hangout_excerpt);
        LinearLayout register = convertView.findViewById(R.id.register);


        Glide.with(context).load(arr_source_url.get(i)).into(image);
        title.setText(arr_title.get(i));
        excerpt.setText(arr_title.get(i));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create new activity to load url in webview
                Intent j = new Intent(context, HangoutRegistration.class);
                j.putExtra("title", arr_title.get(i));
                j.putExtra("link", arr_link.get(i));
                context.startActivity(j);


//                Uri uri = Uri.parse(arr_link.get(i));
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                // Create and start the chooser
//                Intent chooser = Intent.createChooser(intent, "Open with");
//                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
