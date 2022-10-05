package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.ng.NaijaTalks.adapters.AllEventsAdapter;
import com.ng.NaijaTalks.adapters.ExpertHangoutAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpertHangout extends AppCompatActivity {
    ListView myList;
    int ArrayLength, ArrayLength2;
    ProgressBar progressBar;
    TextView loading;
    TextView login, register;
    String email;
    ImageView back;

    ArrayList<String> arr_id = new ArrayList<>();
    ArrayList<String> arr_title = new ArrayList<>();
    ArrayList<String> arr_author = new ArrayList<>();
    ArrayList<String> arr_source_url = new ArrayList<>();
    ArrayList<Integer> arr_post_id = new ArrayList<>();
    ArrayList<String> arr_link = new ArrayList<>();

    public static final String MEC_EVENTS = "https://9jatalks.org/wp-json/wp/v2/mec-events?per_page=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_hangout);
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
//                                String date = section1.getString("date");
                                String cat_title = section1.getString("title");
                                String link = section1.getString("link");
                                String mec_category = section1.getString("mec_category");

                                JSONObject section2 = new JSONObject(cat_title);
                                String title = section2.getString("rendered");

                                JSONArray arr = new JSONArray(mec_category);
                                if(arr.length() == 1){
                                    if ((int)arr.get(0) == 110){
                                        arr_source_url.add(image_url);
                                        arr_title.add(title);
                                        arr_link.add(link);
                                    }
                                    else{
                                        //do nothing
                                    }
                                }

                                myList = findViewById(R.id.mylist);
                                ExpertHangoutAdapter myAdapter=new ExpertHangoutAdapter(ExpertHangout.this,arr_source_url, arr_title, arr_link, email);
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