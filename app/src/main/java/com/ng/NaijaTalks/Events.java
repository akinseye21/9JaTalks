package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Events extends AppCompatActivity {

    ListView myList;
    int ArrayLength;
    ProgressBar progressBar;
    TextView loading;

    ArrayList<String> arr_id = new ArrayList<>();
    ArrayList<String> arr_title = new ArrayList<>();
    ArrayList<String> arr_author = new ArrayList<>();

    public static final String ALL_EVENTS = "https://9jatalks.org/mobile/json_get_events.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progressBar);
        loading = findViewById(R.id.loadingText);

        progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);

                        progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength);

                            if(ArrayLength >= 1){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject section1 = jsonArray.getJSONObject(i);
                                    String id = section1.getString("id");
                                    String post_title = section1.getString("post_title");
                                    String post_author = section1.getString("post_author");

                                    System.out.println("id "+id+"\ntitle "+post_title+"\nauthor "+post_author);

                                    arr_id.add(id);
                                    arr_title.add(post_title);
                                    arr_author.add(post_author);


                                    myList = findViewById(R.id.mylist);
                                    AllEventsAdapter  myAdapter=new AllEventsAdapter(getApplicationContext(),arr_id,arr_title,arr_author);
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