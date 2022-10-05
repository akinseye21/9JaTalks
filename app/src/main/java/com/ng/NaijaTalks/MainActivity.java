package com.ng.NaijaTalks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.navigation.NavigationView;
import com.ng.NaijaTalks.adapters.SliderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
//    private ViewPager mSlideViewPager;
//    private SliderAdapter sliderAdapter;
//    Timer timer;
    LinearLayout groups, experthangout, opportunities, resources, events;
    Dialog myDialog;
    ImageView menu;
    EditText edtemail, edtpassword;
    ImageView facebook, twitter, instagram, linkedin;
    CallbackManager callbackManager;
//    RelativeLayout social_rel;
    TextView username;
    CircleImageView imgview;
    LinearLayout logout;
    TextView about, tos;
//    String email, password;
//    ProgressBar progressBar;
//    TextView progressText;
//    CheckBox remember;


//    Integer[] imageId = {
//            R.drawable.img1,
//            R.drawable.img2,
//            R.drawable.img3,
//            R.drawable.img1};
//
//    public static final String LOGIN = "https://9jatalks.org/mobile/json_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        social_rel = findViewById(R.id.social_rel);
        username = findViewById(R.id.username);
        imgview = findViewById(R.id.imageview);
        logout = findViewById(R.id.facebook_logout);
        groups = findViewById(R.id.lin_groups);
        experthangout = findViewById(R.id.lin_experthangout);
        opportunities = findViewById(R.id.lin_opportunity);
        resources = findViewById(R.id.lin_resources);
        events = findViewById(R.id.lin_events);
        about = findViewById(R.id.about);
        tos = findViewById(R.id.tos);

        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Group2.class);
                i.putExtra("email", "default");
                startActivity(i);
            }
        });
        experthangout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ExpertHangout.class);
                i.putExtra("email", "default");
                startActivity(i);
            }
        });
        opportunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Opportunities.class);
                i.putExtra("email", "default");
                startActivity(i);
            }
        });
        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Resources.class);
                i.putExtra("email", "default");
                startActivity(i);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Events.class);
                i.putExtra("email", "default");
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AboutAndTOS.class);
                i.putExtra("from", "About 9jaTalks");
                i.putExtra("link", "https://9jatalks.org/about-us/");
                startActivity(i);
            }
        });
        tos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AboutAndTOS.class);
                i.putExtra("from", "9jaTalks Terms of Service");
                i.putExtra("link", "https://9jatalks.org/privacy-policy/");
                startActivity(i);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && accessToken.isExpired()==false){
            //already logged in
//            social_rel.setVisibility(View.VISIBLE);
            GraphRequest request = GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                            //Code
                            try {
                                String fullName = jsonObject.getString("name");
                                String url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                                username.setText(fullName);
                                Glide.with(getApplicationContext()).load(url).into(imgview);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }else{
            //stay in the page
        }
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
//                        Toast.makeText(getApplicationContext(), "Facebook Login successful", Toast.LENGTH_LONG).show();
//                        System.out.println("my output = "+loginResult);
//                        Intent i = new Intent(getApplicationContext(), Group2.class);
//                        i.putExtra("email", email);
//                        startActivity(i);

//                        social_rel.setVisibility(View.VISIBLE);
                        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                        //Code
                                        try {
                                            String fullName = jsonObject.getString("name");
                                            String url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                                            username.setText(fullName);
                                            Glide.with(getApplicationContext()).load(url).into(imgview);
                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
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


        facebook = findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
//                Intent i = new Intent(getApplicationContext(), Groups.class);
//                i.putExtra("email", email);
//                startActivity(i);
//                finish();
            }
        });
//        remember = findViewById(R.id.remember);
//        progressBar = findViewById(R.id.progressBar);
//        progressText = findViewById(R.id.progressText);
//        edtemail = findViewById(R.id.edtemail);
//        edtpassword = findViewById(R.id.edtpassword);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_right);
        menu = findViewById(R.id.menu);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        String sharedEmail = preferences.getString("email", "");
        String sharedPass = preferences.getString("password", "");

//        if(checkbox.equals("true")){
//            edtemail.setText(sharedEmail);
//            edtpassword.setText(sharedPass);
//            remember.setChecked(true);
//        }else if(checkbox.equals("false")){
//            edtemail.setText("");
//            edtpassword.setText("");
//            remember.setChecked(false);
//        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.home){
//                    Toast.makeText(Dashboard.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                    myDialog = new Dialog(MainActivity.this);
                    myDialog.setContentView(R.layout.custom_popu_register);
                    TextView close = myDialog.findViewById(R.id.close);
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
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                        }
                    });
                    // Setting dialogview
                    Window window = myDialog.getWindow();
                    window.setGravity(Gravity.CENTER);
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.setCanceledOnTouchOutside(true);
                    myDialog.show();

                }
                else if(id == R.id.groups){
//                    Toast.makeText(Dashboard.this, "Reviews Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Groups.class);
                    i.putExtra("email", "");
                    startActivity(i);
                }
                else if(id == R.id.resources){
//                    Toast.makeText(Dashboard.this, "Payments Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Resources.class);
                    i.putExtra("email", "");
                    startActivity(i);
                }
                else if(id == R.id.events){
//                    Toast.makeText(Dashboard.this, "Notifications Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Events.class);
                    i.putExtra("email", "");
                    startActivity(i);
                }
//                else  if(id == R.id.news){
////                    Toast.makeText(Dashboard.this, "Settings Selected", Toast.LENGTH_SHORT).show();
//                }
                else if(id == R.id.opportunities){
//                    Toast.makeText(Dashboard.this, "Privacy Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Opportunities.class);
                    i.putExtra("email", "");
                    startActivity(i);
                }
//                else if(id == R.id.hangout){
////                    Toast.makeText(Dashboard.this, "Privacy Selected", Toast.LENGTH_SHORT).show();
//                }

                return true;
            }
        });

//        mSlideViewPager = findViewById(R.id.slideViewPager);
//        sliderAdapter = new SliderAdapter(this);
//        mSlideViewPager.setAdapter(sliderAdapter);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        //adding timer for the images
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mSlideViewPager.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mSlideViewPager.setCurrentItem((mSlideViewPager.getCurrentItem()+1)%imageId.length);
//
//                    }
//                });
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 9000, 9000);

    }

//    @Override
//    protected void onDestroy() {
//        timer.cancel();
//        super.onDestroy();
//    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

//    public void Register(View view) {
//        Intent i = new Intent(MainActivity.this, Register.class);
//        startActivity(i);
//    }

//    public void login(View view) {
//        myDialog = new Dialog(MainActivity.this);
//        myDialog.setContentView(R.layout.custom_popup_login);
//        TextView close = myDialog.findViewById(R.id.close);
//        EditText edtemail1 = myDialog.findViewById(R.id.edtemail);
//        EditText edtpassword1 = myDialog.findViewById(R.id.edtpassword);
//        CheckBox rememberme = myDialog.findViewById(R.id.remember);
//        ProgressBar myProgress = myDialog.findViewById(R.id.progressBar);
//        TextView loadingText = myDialog.findViewById(R.id.loadingtext);
//        TextView lostpassword = myDialog.findViewById(R.id.lostpassword);
//        Button login = myDialog.findViewById(R.id.login);
//        TextView createaccount = myDialog.findViewById(R.id.createaccount);
//
//        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//        String checkbox = preferences.getString("remember", "");
//        String sharedEmail = preferences.getString("email", "");
//        String sharedPass = preferences.getString("password", "");
//
//        if(checkbox.equals("true")){
//            edtemail1.setText(sharedEmail);
//            edtpassword1.setText(sharedPass);
//            rememberme.setChecked(true);
//        }else if(checkbox.equals("false")){
//            edtemail1.setText("");
//            edtpassword1.setText("");
//            rememberme.setChecked(false);
//        }
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        createaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), Register.class);
//                startActivity(i);
//            }
//        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String myEmail = edtemail1.getText().toString().trim();
//                String myPassword = edtpassword1.getText().toString().trim();
//
//                if (myEmail.isEmpty() || !myEmail.contains("@")){
//                    edtemail1.setError("Wrong email");
//                }else{
//                    if(myPassword.isEmpty()){
//                        edtpassword1.setError("Wrong password");
//                    }else{
//
//                        myProgress.setVisibility(View.VISIBLE);
//                        loadingText.setVisibility(View.VISIBLE);
//
//                        //send email and password to db
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        myProgress.setVisibility(View.GONE);
//                                        loadingText.setVisibility(View.GONE);
//
//                                        if(rememberme.isChecked()){
//                                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = preferences.edit();
//                                            editor.putString("remember", "true");
//                                            editor.putString("email", email);
//                                            editor.putString("password", password);
//                                            editor.commit();
//                                        }else if(!rememberme.isChecked()){
//                                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = preferences.edit();
//                                            editor.putString("remember", "false");
//                                            editor.putString("email", "");
//                                            editor.putString("password", "");
//                                            editor.commit();
//                                        }
//
//                                        try {
//                                            JSONArray jsonArray = new JSONArray(response);
//                                            for(int i=0; i<jsonArray.length(); i++){
//                                                JSONObject section1 = jsonArray.getJSONObject(i);
//                                                String id = section1.getString("id");
//                                                String user_login = section1.getString("user_login");
//                                                String user_nickname = section1.getString("user_nicename");
//                                                String user_email = section1.getString("user_email");
//                                                String user_registered = section1.getString("user_registered");
//                                                String display_name = section1.getString("display_name");
//
//                                                System.out.println("{id: "+id+"\nlogin: "+user_login+"\nemail: "+user_email+"\nregistered: "+user_registered);
//
//                                                Intent j = new Intent(getApplicationContext(), Dashboard.class);
//                                                j.putExtra("user_id", id);
//                                                j.putExtra("user_nickname", user_nickname);
//                                                j.putExtra("user_email", user_email);
//                                                j.putExtra("user_registered", user_registered);
//                                                j.putExtra("display_name", display_name);
//                                                startActivity(j);
//                                            }
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//
//                                            try {
//                                                JSONObject obj = new JSONObject(response);
//                                                String status = obj.getString("status");
//
//                                                if (status.equals("Login failed")){
//                                                    Toast.makeText(getApplicationContext(), status+" please try again", Toast.LENGTH_LONG).show();
//                                                }
//                                            } catch (JSONException ex) {
//                                                ex.printStackTrace();
//                                            }
//                                        }
//
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(getApplicationContext(), "Network connectivity problem, please try again", Toast.LENGTH_LONG).show();
//                                    }
//                                }){
//                            @Override
//                            protected Map<String, String> getParams(){
//                                Map<String, String> params = new HashMap<>();
//                                params.put("user_email", myEmail);
//                                params.put("user_pass", myPassword);
//                                return params;
//                            }
//                        };
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                        requestQueue.add(stringRequest);
//
//                    }
//                }
//            }
//        });
//
//        // Setting dialogview
//        Window window = myDialog.getWindow();
//        window.setGravity(Gravity.CENTER);
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.setCanceledOnTouchOutside(true);
//        myDialog.show();
//    }

//    public void login_dashboard(View view) {
//
//        email = edtemail.getText().toString().trim();
//        password = edtpassword.getText().toString().trim();
//
//        if (email.isEmpty() || !email.contains("@")){
//            edtemail.setError("Wrong email");
//        }else{
//            if(password.isEmpty()){
//                edtpassword.setError("Wrong password");
//            }else{
//
//                progressBar.setVisibility(View.VISIBLE);
//                progressText.setVisibility(View.VISIBLE);
//
//                //send email and password to db
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                System.out.println("Response = "+response);
//
//                                progressBar.setVisibility(View.GONE);
//                                progressText.setVisibility(View.GONE);
//
//                                if(remember.isChecked()){
//                                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = preferences.edit();
//                                    editor.putString("remember", "true");
//                                    editor.putString("email", email);
//                                    editor.putString("password", password);
//                                    editor.commit();
//                                }else if(!remember.isChecked()){
//                                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = preferences.edit();
//                                    editor.putString("remember", "false");
//                                    editor.putString("email", "");
//                                    editor.putString("password", "");
//                                    editor.commit();
//                                }
//
//                                try {
//                                    JSONArray jsonArray = new JSONArray(response);
//                                        for(int i=0; i<jsonArray.length(); i++){
//                                            JSONObject section1 = jsonArray.getJSONObject(i);
//                                            String id = section1.getString("id");
//                                            String user_login = section1.getString("user_login");
//                                            String user_nickname = section1.getString("user_nicename");
//                                            String user_email = section1.getString("user_email");
//                                            String user_registered = section1.getString("user_registered");
//                                            String display_name = section1.getString("display_name");
//
//                                            System.out.println("{id: "+id+"\nlogin: "+user_login+"\nemail: "+user_email+"\nregistered: "+user_registered);
//
//
//
//                                            Intent j = new Intent(getApplicationContext(), Dashboard.class);
//                                            j.putExtra("user_id", id);
//                                            j.putExtra("user_nickname", user_nickname);
//                                            j.putExtra("user_email", user_email);
//                                            j.putExtra("user_registered", user_registered);
//                                            j.putExtra("display_name", display_name);
//                                            startActivity(j);
//                                        }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//
//                                    try {
//                                        JSONObject obj = new JSONObject(response);
//                                        String status = obj.getString("status");
//
//                                        if (status.equals("Login failed")){
//                                            Toast.makeText(getApplicationContext(), status+" please try again", Toast.LENGTH_LONG).show();
//                                        }
//                                    } catch (JSONException ex) {
//                                        ex.printStackTrace();
//                                    }
//                                }
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getApplicationContext(), "Network connectivity problem, please try again", Toast.LENGTH_LONG).show();
//                            }
//                        }){
//                    @Override
//                    protected Map<String, String> getParams(){
//                        Map<String, String> params = new HashMap<>();
//                        params.put("user_email", email);
//                        params.put("user_pass", password);
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//
//            }
//        }
//
//
//
//    }

}