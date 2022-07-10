package com.ng.NaijaTalks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    Timer timer;
    Dialog myDialog;
    ImageView menu;


    Integer[] imageId = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1};

    String[] word1 = {"Join the club",
            "News Feed",
            "Expert Hangout",
            "Opportunities"};

    String[] word2 = {"Come yarn your mata",
            "News wey fresh like\ntoday bread",
            "You wanna chill with\nthe big boyz",
            "Sapa go collect\nwotowoto"};

    String[] word3 = {"Give your constructive opinions in discussion forums where \nyou can share photos, videos and articles",
            "Stay uo to date with trending news and instant updates",
            "Have a live experience with Naija top experts and celebrities",
            "With our extensive partnerships, come and engage and find\nmind-blowing opportunities"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_right);
        menu = findViewById(R.id.menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.home){
//                    Toast.makeText(Dashboard.this, "Profile Selected", Toast.LENGTH_SHORT).show();

                }
                else if(id == R.id.groups){
//                    Toast.makeText(Dashboard.this, "Reviews Selected", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.resources){
//                    Toast.makeText(Dashboard.this, "Payments Selected", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.events){
//                    Toast.makeText(Dashboard.this, "Notifications Selected", Toast.LENGTH_SHORT).show();
                }
                else  if(id == R.id.news){
//                    Toast.makeText(Dashboard.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.opportunities){
//                    Toast.makeText(Dashboard.this, "Privacy Selected", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.hangout){
//                    Toast.makeText(Dashboard.this, "Privacy Selected", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        mSlideViewPager = findViewById(R.id.slideViewPager);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });



        //adding timer for the images
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mSlideViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        mSlideViewPager.setCurrentItem((mSlideViewPager.getCurrentItem()+1)%imageId.length);

                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 9000, 9000);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

    public void Register(View view) {
        Intent i = new Intent(MainActivity.this, Register.class);
        startActivity(i);
    }

    public void login(View view) {
        myDialog = new Dialog(MainActivity.this);
        myDialog.setContentView(R.layout.custom_popup_login);
        TextView close = myDialog.findViewById(R.id.close);
        EditText edtemail = myDialog.findViewById(R.id.edtemail);
        EditText edtpassword = myDialog.findViewById(R.id.edtpassword);
        CheckBox rememberme = myDialog.findViewById(R.id.remember);
        TextView lostpassword = myDialog.findViewById(R.id.lostpassword);
        Button login = myDialog.findViewById(R.id.login);
        TextView createaccount = myDialog.findViewById(R.id.createaccount);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

        // Setting dialogview
        Window window = myDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.show();
    }

    public void login_dashboard(View view) {
        Intent i = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(i);
    }
}