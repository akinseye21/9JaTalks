package com.ng.NaijaTalks;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.navigation.NavigationView;
import com.ng.NaijaTalks.adapters.GroupAdapter;

import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;

public class Groups extends AppCompatActivity {

//    DrawerLayout drawerLayout;
//    ActionBarDrawerToggle actionBarDrawerToggle;
//    NavigationView navigationView;
    ImageView back;
    ListView listView;
    int ArrayLength;
    ProgressBar progressBar;
    TextView loadingText;

    //    ArrayList<String> images = new ArrayList<>();
    ArrayList<Integer> banner = new ArrayList<Integer>();
    ArrayList<String> groupName = new ArrayList<>();

    CallbackManager callbackManager;
    LinearLayout login;
    TextView logintxt, registertxt;
    String email;
//    LoginButton loginButton;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login = findViewById(R.id.facebook_login);
        logintxt = findViewById(R.id.login);
        registertxt = findViewById(R.id.register);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        email = i.getStringExtra("email");

        if(email.isEmpty()){
            logintxt.setVisibility(View.VISIBLE);
            registertxt.setVisibility(View.VISIBLE);
        }else if(!email.isEmpty()){
            logintxt.setVisibility(View.GONE);
            registertxt.setVisibility(View.GONE);
        }

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && accessToken.isExpired()==false){
            //already logged in
            Intent k = new Intent(Groups.this, Group2.class);
            k.putExtra("email", email);
            startActivity(k);
            finish();
        }else{
            //stay in the page
        }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
//                        Toast.makeText(getApplicationContext(), "Facebook Login successful", Toast.LENGTH_LONG).show();
                        System.out.println("my output = "+loginResult);
                        Intent i = new Intent(getApplicationContext(), Group2.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
//                        System.out.println("my output = "+loginResult);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(), "Error code "+exception, Toast.LENGTH_LONG).show();
                    }
                });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Groups.this, Arrays.asList("public_profile"));
            }
        });

//        drawerLayout = findViewById(R.id.drawer_layout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
//        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        navigationView = findViewById(R.id.navigation);
//        View hView = navigationView.getHeaderView(0);
//        LinearLayout activity = hView.findViewById(R.id.activity);
//        LinearLayout resources = hView.findViewById(R.id.resources);
//        LinearLayout photos = hView.findViewById(R.id.photo);
//        LinearLayout watch = hView.findViewById(R.id.watch);
//        LinearLayout people = hView.findViewById(R.id.people);
//        LinearLayout groups = hView.findViewById(R.id.groups);
//        LinearLayout forum = hView.findViewById(R.id.forums);
//        LinearLayout opportunities = hView.findViewById(R.id.opportunities);

//        activity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//                Intent i = new Intent(getApplicationContext(), Dashboard.class);
//                i.putExtra("email", email);
//                startActivity(i);
//            }
//        });
//        resources.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//                Intent i = new Intent(getApplicationContext(), Resources.class);
//                startActivity(i);
//            }
//        });
//        photos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//            }
//        });
//        watch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//            }
//        });
//        people.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//                Intent i = new Intent(getApplicationContext(), AllUsers.class);
//                startActivity(i);
//            }
//        });
//        groups.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//                drawerLayout.close();
//            }
//        });
//        forum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//            }
//        });
//        opportunities.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //show activity page
//            }
//        });

//        openMenu = findViewById(R.id.menu);
//        openMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

//        banner.add(R.drawable.group_header);
//        banner.add(R.drawable.pub2);
//        banner.add(R.drawable.pub1);
//        banner.add(R.drawable.group_header);
//
//        groupName.add("Election Observation");
//        groupName.add("Voter Education");
//        groupName.add("Vote Not Fight");
//        groupName.add("Stop Violence Against Women");

//        listView = findViewById(R.id.listview);
//        GroupAdapter myAdapter=new GroupAdapter(getApplicationContext(),banner,groupName);
//        listView.setAdapter(myAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}