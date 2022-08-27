package com.ng.NaijaTalks;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity implements AllMembersFeed.OnFragmentInteractionListener, MentionsFeed.OnFragmentInteractionListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView, navigationView_right;
    CircleImageView user;
    ImageView openMenu;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        email = i.getStringExtra("user_email");

        user = findViewById(R.id.user);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation);
        View hView = navigationView.getHeaderView(0);

        LinearLayout profile = hView.findViewById(R.id.profile);
        LinearLayout activity = hView.findViewById(R.id.activity);
        LinearLayout resources = hView.findViewById(R.id.resources);
        LinearLayout photos = hView.findViewById(R.id.photo);
        LinearLayout watch = hView.findViewById(R.id.watch);
        LinearLayout people = hView.findViewById(R.id.people);
        LinearLayout groups = hView.findViewById(R.id.groups);
        LinearLayout forum = hView.findViewById(R.id.forums);
        LinearLayout opportunities = hView.findViewById(R.id.opportunities);
        LinearLayout events = hView.findViewById(R.id.events);

        navigationView_right = findViewById(R.id.navigation_right);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                i.putExtra("email", email);
                startActivity(i);
            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                drawerLayout.close();
            }
        });
        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                Intent i = new Intent(getApplicationContext(), Resources.class);
                i.putExtra("email", email);
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
                i.putExtra("email", email);
                startActivity(i);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show activity page
                Intent i = new Intent(getApplicationContext(), Groups.class);
                i.putExtra("email", email);
                startActivity(i);
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
                Intent i = new Intent(getApplicationContext(), Opportunities.class);
                i.putExtra("email", email);
                startActivity(i);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Events.class);
                i.putExtra("email", email);
                startActivity(i);
            }
        });

        openMenu = findViewById(R.id.menu);
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        viewPager = findViewById(R.id.viewpager);
        addTabs(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AllMembersFeed(viewPager), "All members");
        adapter.addFrag(new MentionsFeed(viewPager), "Mentions");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {

    }
}