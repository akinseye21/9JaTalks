package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JobViewPage extends AppCompatActivity {

    ImageView back, logo;
    String content;
    String title;
    Integer link;
    TextView txt_content, txt_title;
    ProgressBar progressBar;

    public static final String MEDIA = "https://9jatalks.org/wp-json/wp/v2/media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        content = i.getStringExtra("content");
        title = i.getStringExtra("title");
        link = i.getIntExtra("link", 0);

        progressBar = findViewById(R.id.progressBar);
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_content = findViewById(R.id.content);
        txt_title = findViewById(R.id.title);

//        Glide.with(getApplicationContext()).load(link).into(logo);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txt_title.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
            txt_content.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        } else {
            txt_title.setText(Html.fromHtml(title));
            txt_content.setText(Html.fromHtml(content));
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MEDIA+"/"+link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String guid = jsonObject.getString("guid");

                            JSONObject section2 = new JSONObject(guid);
                            String links = section2.getString("rendered");

                            Glide.with(getApplicationContext()).load(links).into(logo);

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