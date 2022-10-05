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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ng.NaijaTalks.adapters.AllEventsAdapter;
import com.ng.NaijaTalks.adapters.AllJobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Opportunities extends AppCompatActivity {

    ImageView back;
    ListView myList;
    int ArrayLength;
    ProgressBar progressBar;
    TextView login, register;
    String email;

    ArrayList<String> arr_job_title = new ArrayList<>();
    ArrayList<String> arr_job_content = new ArrayList<>();
    ArrayList<String> arr_job_location = new ArrayList<>();
    ArrayList<String> arr_application_link = new ArrayList<>();
    ArrayList<String> arr_company_name = new ArrayList<>();
    ArrayList<String> arr_company_website = new ArrayList<>();
    ArrayList<Integer> arr_featured_image = new ArrayList<>();

    public static final String JOBS = "https://9jatalks.org/wp-json/wp/v2/job-listings?per_page=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        email = i.getStringExtra("email");

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        myList = findViewById(R.id.listview_job);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        if (email.isEmpty()){
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }else if(!email.isEmpty()){
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JOBS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);

                        progressBar.setVisibility(View.GONE);
//                        loading.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength);

                            if(ArrayLength >= 1){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject section1 = jsonArray.getJSONObject(i);
                                    String titled = section1.getString("title");
                                    String contents = section1.getString("content");
                                    int featured_media = section1.getInt("featured_media");
                                    String meta = section1.getString("meta");

                                    JSONObject section2 = new JSONObject(titled);
                                    String title = section2.getString("rendered");

                                    JSONObject section3 = new JSONObject(contents);
                                    String content = section3.getString("rendered");

                                    JSONObject section4 = new JSONObject(meta);
                                    String job_location = section4.getString("_job_location");
                                    String application_link = section4.getString("_application");
                                    String company_name = section4.getString("_company_name");
                                    String company_website = section4.getString("_company_website");

                                    arr_job_title.add(title);
                                    arr_job_content.add(content);
                                    arr_job_location.add(job_location);
                                    arr_application_link.add(application_link);
                                    arr_company_name.add(company_name);
                                    arr_company_website.add(company_website);
                                    arr_featured_image.add(featured_media);

                                    AllJobsAdapter myAdapter=new AllJobsAdapter(getApplicationContext(),arr_job_title,arr_job_content,arr_job_location,arr_application_link,arr_company_name,arr_company_website,arr_featured_image, email);
                                    myList.setAdapter(myAdapter);
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