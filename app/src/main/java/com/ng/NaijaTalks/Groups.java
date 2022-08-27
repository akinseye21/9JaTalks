package com.ng.NaijaTalks;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.ng.NaijaTalks.adapters.GroupAdapter;

import java.util.ArrayList;

public class Groups extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ImageView openMenu;
    ListView listView;
    int ArrayLength;
    ProgressBar progressBar;
    TextView loadingText;

    //    ArrayList<String> images = new ArrayList<>();
    ArrayList<Integer> banner = new ArrayList<Integer>();
    ArrayList<String> groupName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation);
        View hView = navigationView.getHeaderView(0);
        LinearLayout activity = hView.findViewById(R.id.activity);
        LinearLayout resources = hView.findViewById(R.id.resources);
        LinearLayout photos = hView.findViewById(R.id.photo);
        LinearLayout watch = hView.findViewById(R.id.watch);
        LinearLayout people = hView.findViewById(R.id.people);
        LinearLayout groups = hView.findViewById(R.id.groups);
        LinearLayout forum = hView.findViewById(R.id.forums);
        LinearLayout opportunities = hView.findViewById(R.id.opportunities);

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(i);
            }
        });
        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                Intent i = new Intent(getApplicationContext(), Resources.class);
                startActivity(i);
            }
        });
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                Intent i = new Intent(getApplicationContext(), AllUsers.class);
                startActivity(i);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                drawerLayout.close();
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
            }
        });
        opportunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
            }
        });

        openMenu = findViewById(R.id.menu);
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        banner.add(R.drawable.group_header);
        banner.add(R.drawable.pub2);
        banner.add(R.drawable.pub1);
        banner.add(R.drawable.group_header);

        groupName.add("Election Observation");
        groupName.add("Voter Education");
        groupName.add("Vote Not Fight");
        groupName.add("Stop Violence Against Women");

        listView = findViewById(R.id.listview);
        GroupAdapter myAdapter=new GroupAdapter(getApplicationContext(),banner,groupName);
        listView.setAdapter(myAdapter);
    }
}