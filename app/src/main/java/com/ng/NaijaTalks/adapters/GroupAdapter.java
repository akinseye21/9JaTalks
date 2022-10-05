package com.ng.NaijaTalks.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ng.NaijaTalks.FacebookPage;
import com.ng.NaijaTalks.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> groupID;
    ArrayList<String> groupName;
    String email;
    LayoutInflater inflter;

    public GroupAdapter(Context applicationContext, ArrayList<String> groupID, ArrayList<String> groupName, String email) {
        this.context = applicationContext;
        this.groupID = groupID;
        this.groupName = groupName;
        this.email = email;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return groupID.size();
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
        RelativeLayout bannerl = (RelativeLayout) convertView.findViewById(R.id.banner);
        TextView groupNameT = (TextView) convertView.findViewById(R.id.groupName);
        LinearLayout join = (LinearLayout) convertView.findViewById(R.id.join);

        groupNameT.setText(groupName.get(position));
        bannerl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FacebookPage.class);
                //send the groupID to the page
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("groupID", groupID.get(position));
                i.putExtra("groupName", groupName.get(position));
                i.putExtra("email", email);
                context.startActivity(i);
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FacebookPage.class);
                //send the groupID to the page
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("groupID", groupID.get(position));
                i.putExtra("groupName", groupName.get(position));
                i.putExtra("email", email);
                context.startActivity(i);
            }
        });


        return convertView;
    }
}
