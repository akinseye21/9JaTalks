package com.ng.NaijaTalks;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ng.NaijaTalks.adapters.ResourcesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Resources extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView, navigationView_right;
    ImageView back;
    ProgressBar progressBar;

    ListView listView;
    int ArrayLength, ArrayLength2;


    //category array
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Integer> arr_category_id = new ArrayList<>();

    //post array
    ArrayList<Integer> arr_post_id = new ArrayList<>();
    ArrayList<String> arr_post_date = new ArrayList<>();
    ArrayList<String> arr_post_title = new ArrayList<>();
    ArrayList<String> arr_post_content = new ArrayList<>();
    ArrayList<String> arr_post_image_link = new ArrayList<>();
    ArrayList<Integer> arr_post_category_id = new ArrayList<>();

    public static final String ALL_CATEGORY = "https://9jatalks.org/wp-json/wp/v2/categories";
    public static final String ALL_POSTS = "https://9jatalks.org/wp-json/wp/v2/posts?per_page=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listview);
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
                drawerLayout.close();
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
                Intent i = new Intent(getApplicationContext(), Groups.class);
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
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        progressBar.setVisibility(View.VISIBLE);
        //get the post in each category
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, ALL_POSTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);

                        progressBar.setVisibility(View.GONE);
//                        loadingText.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength2 = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength2);

                            if(ArrayLength2 >= 1){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject section1 = jsonArray.getJSONObject(i);
                                    int post_id = section1.getInt("id");
                                    String post_date = section1.getString("date");
                                    String title = section1.getString("title");
                                    String content = section1.getString("content");

                                    JSONObject tit = new JSONObject(title);
                                    String post_title = tit.getString("rendered");

                                    JSONObject tit2 = new JSONObject(content);
                                    String post_content = tit2.getString("rendered");

                                    String image_link = section1.getString("featured_media_src_url");
                                    String categories = section1.getString("categories");

                                    JSONArray cat = new JSONArray(categories);
                                    int category_id = cat.getInt(0);


                                    arr_post_id.add(post_id);
                                    arr_post_date.add(post_date);
                                    arr_post_title.add(post_title);
                                    arr_post_content.add(post_content);
                                    arr_post_image_link.add(image_link);
                                    arr_post_category_id.add(category_id);

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
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue2.add(stringRequest2);




        //get the categories
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);

                        progressBar.setVisibility(View.GONE);
//                        loadingText.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength);

                            if(ArrayLength >= 1){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject section1 = jsonArray.getJSONObject(i);
                                    String category_name = section1.getString("name");
                                    int category_id = section1.getInt("id");

                                    if (!category_name.equals("Uncategorized")){
                                        //don't add to the category array
                                        categories.add(category_name);
                                        arr_category_id.add(category_id);
                                    }

                                    listView = findViewById(R.id.listview);
                                    ResourcesAdapter myAdapter=new ResourcesAdapter(getApplicationContext(),categories,arr_category_id,arr_post_id,arr_post_date,arr_post_title,arr_post_content,arr_post_image_link,arr_post_category_id);
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