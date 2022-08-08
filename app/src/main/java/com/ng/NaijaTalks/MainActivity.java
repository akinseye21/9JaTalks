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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
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
    EditText edtemail, edtpassword;
    String email, password;
    ProgressBar progressBar;
    TextView progressText;


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

    public static final String LOGIN = "https://9jatalks.org/mobile/json_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
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
                    Intent i = new Intent(getApplicationContext(), Resources.class);
                    startActivity(i);
                }
                else if(id == R.id.events){
//                    Toast.makeText(Dashboard.this, "Notifications Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Events.class);
                    startActivity(i);
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

        email = edtemail.getText().toString().trim();
        password = edtpassword.getText().toString().trim();

        if (email.isEmpty() || !email.contains("@")){
            edtemail.setError("Wrong email");
        }else{
            if(password.isEmpty()){
                edtpassword.setError("Wrong password");
            }else{

                progressBar.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.VISIBLE);

                //send email and password to db
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("Response = "+response);

                                progressBar.setVisibility(View.GONE);
                                progressText.setVisibility(View.GONE);

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                        for(int i=0; i<jsonArray.length(); i++){
                                            JSONObject section1 = jsonArray.getJSONObject(i);
                                            String id = section1.getString("id");
                                            String user_login = section1.getString("user_login");
                                            String user_nickname = section1.getString("user_nicename");
                                            String user_email = section1.getString("user_email");
                                            String user_registered = section1.getString("user_registered");
                                            String display_name = section1.getString("display_name");

                                            System.out.println("{id: "+id+"\nlogin: "+user_login+"\nemail: "+user_email+"\nregistered: "+user_registered);

                                            Intent j = new Intent(getApplicationContext(), Dashboard.class);
                                            j.putExtra("user_id", id);
                                            j.putExtra("user_nickname", user_nickname);
                                            j.putExtra("user_email", user_email);
                                            j.putExtra("user_registered", user_registered);
                                            j.putExtra("display_name", display_name);
                                            startActivity(j);
                                        }

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        String status = obj.getString("status");

                                        if (status.equals("Login failed")){
                                            Toast.makeText(getApplicationContext(), status+" please try again", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Network connectivity problem, please try again", Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("user_email", email);
                        params.put("user_pass", password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }
        }



    }

}