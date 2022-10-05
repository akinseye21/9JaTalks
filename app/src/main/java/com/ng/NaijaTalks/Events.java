package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ng.NaijaTalks.adapters.AllEventsAdapter;
import com.ng.NaijaTalks.adapters.AllJobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Events extends AppCompatActivity {

    ListView myList;
    int ArrayLength, ArrayLength2;
    ProgressBar progressBar;
    TextView loading;
    TextView login, register;
    String email;
    ImageView back;

    String post_id_from_events;

    ArrayList<String> arr_id = new ArrayList<>();
    ArrayList<String> arr_title = new ArrayList<>();
    ArrayList<String> arr_author = new ArrayList<>();
    ArrayList<String> arr_source_url = new ArrayList<>();
    ArrayList<Integer> arr_post_id = new ArrayList<>();
    ArrayList<String> arr_date = new ArrayList<>();
//    ArrayList<Integer> arr_title = new ArrayList<>();

    public static final String ALL_EVENTS = "https://9jatalks.org/mobile/json_get_events.php";
    public static final String ALL_MEDIA = "https://9jatalks.org/wp-json/wp/v2/media?per_page=100";
    public static final String MEC_EVENTS = "https://9jatalks.org/wp-json/wp/v2/mec-events?per_page=100";

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        email = i.getStringExtra("email");

        progressBar = findViewById(R.id.progressBar);
        loading = findViewById(R.id.loadingText);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (email.isEmpty()){
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }else if(!email.isEmpty()){
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
        }

        progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);


        //get events
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_EVENTS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("Response = "+response);
//
//                        progressBar.setVisibility(View.GONE);
//                        loading.setVisibility(View.GONE);
//
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            ArrayLength = jsonArray.length();
//
////                            System.out.println("Number of row = "+ArrayLength);
//
//                            if(ArrayLength >= 1){
//                                for(int i=ArrayLength-1; i>=0; i--){
//                                    JSONObject section1 = jsonArray.getJSONObject(i);
//                                    post_id_from_events = section1.getString("id");
//                                    String post_title = section1.getString("post_title");
//                                    String post_author = section1.getString("post_author");
//
//                                    System.out.println("id "+post_id_from_events+"\ntitle "+post_title+"\nauthor "+post_author);
//
//                                    arr_id.add(post_id_from_events);
//                                    arr_title.add(post_title);
//                                    arr_author.add(post_author);
//
//
//
//                                    //get media
//                                    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, ALL_MEDIA,
//                                            new Response.Listener<String>() {
//                                                @Override
//                                                public void onResponse(String response) {
//                                                    System.out.println("My Response = "+response);
//
//                                                    try {
//                                                        JSONArray jsonArray = new JSONArray(response);
//                                                        ArrayLength2 = jsonArray.length();
//
//                                                        System.out.println("Number of row = "+ArrayLength2);
//
//                                                        for(int i=0; i<ArrayLength2; i++){
//                                                            JSONObject section1 = jsonArray.getJSONObject(i);
//                                                            int post_id = section1.getInt("post");
//                                                            String source_url = section1.getString("source_url");
//
//
//                                                            arr_source_url.add(source_url);
//                                                            arr_post_id.add(post_id);
//
//                                                            myList = findViewById(R.id.mylist);
//                                                            AllEventsAdapter myAdapter=new AllEventsAdapter(Events.this,arr_id,arr_title,arr_author, email, arr_source_url, arr_post_id);
//                                                            myList.setAdapter(myAdapter);
//
////                                if (Integer.parseInt(id.get(position)) == post_id){
////                                    Glide.with(context).load(source_url).into(imageView);
////                                }else{
////                                    imageView.setImageResource(rand_image[randImg]);
////                                }
//
//                                                        }
//
//                                                    } catch (JSONException e) {
//                                                        e.printStackTrace();
//                                                    }
//
//                                                }
//                                            },
//                                            new Response.ErrorListener() {
//                                                @Override
//                                                public void onErrorResponse(VolleyError error) {
//
//                                                }
//                                            }){
//                                        @Override
//                                        protected Map<String, String> getParams(){
//                                            Map<String, String> params = new HashMap<>();
//                                            return params;
//                                        }
//                                    };
//                                    RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
//                                    requestQueue2.add(stringRequest2);
//
//
//
//
//
//
//
//                                }
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<>();
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);


        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, MEC_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("My Response = "+response);

                        progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength2 = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength2);

                            for(int i=0; i<ArrayLength2; i++){
                                JSONObject section1 = jsonArray.getJSONObject(i);
//                                int event_id = section1.getInt("id");
                                String image_url = section1.getString("featured_image_src");
                                String date = section1.getString("date");
                                String cat_title = section1.getString("title");
//                                int mec_category = section1.getInt("mec_category");

                                JSONObject section2 = new JSONObject(cat_title);
                                String title = section2.getString("rendered");



                                arr_source_url.add(image_url);
                                arr_date.add(date);
                                arr_title.add(title);
//                                arr_post_id.add(event_id);

                                myList = findViewById(R.id.mylist);
                                AllEventsAdapter myAdapter=new AllEventsAdapter(Events.this,arr_source_url, arr_date, arr_title, email);
                                myList.setAdapter(myAdapter);


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

    }
}