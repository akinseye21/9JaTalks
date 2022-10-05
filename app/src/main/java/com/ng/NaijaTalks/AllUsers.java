package com.ng.NaijaTalks;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ng.NaijaTalks.adapters.AllUserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllUsers extends AppCompatActivity {

//    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
//    NavigationView navigationView;
    ImageView back;
    GridView listView;
    int ArrayLength;
    ProgressBar progressBar;
    TextView loadingText;
    String got_email;

//    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> username = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> fullname = new ArrayList<>();


    public static final String ALL_USERS = "https://9jatalks.org/mobile/json_get_users.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        got_email = i.getStringExtra("email");

        progressBar = findViewById(R.id.progressBar);
        loadingText = findViewById(R.id.loadingText);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_USERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);

                        progressBar.setVisibility(View.GONE);
                        loadingText.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength);

                            if(ArrayLength >= 1){
                                for(int i=ArrayLength-1; i>=0; i--){
                                    JSONObject section1 = jsonArray.getJSONObject(i);
                                    String uname = section1.getString("username");
                                    String uemail = section1.getString("email");
                                    String ufullname = section1.getString("fullname");

                                    System.out.println("Username "+uname+"\nEmail "+uemail+"\nFullname "+ufullname);

                                    username.add(uname);
                                    email.add(uemail);
                                    fullname.add(ufullname);


                                    listView = findViewById(R.id.listview);
                                    AllUserAdapter myAdapter=new AllUserAdapter(getApplicationContext(),username,email,fullname);
                                    listView.setAdapter(myAdapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}