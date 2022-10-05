package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activation extends AppCompatActivity {

    String email, username, key, reg_time, fullname, password;
    EditText code;
    Button complete;
    ProgressBar progressBar;

    public static final String ACTIVATION = "https://9jatalks.org/mobile/json_register_complete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        email = i.getStringExtra("email");
        username = i.getStringExtra("username");
        key = i.getStringExtra("key");
        reg_time = i.getStringExtra("reg_time");
        fullname = i.getStringExtra("fullname");
        password = i.getStringExtra("password");

        code = findViewById(R.id.code);
        code.setText(key);
        progressBar = findViewById(R.id.progressBar);
        complete = findViewById(R.id.btncomplete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                //send values to the db Users
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ACTIVATION,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("Response = "+response);

                                progressBar.setVisibility(View.GONE);
//                                progressText.setVisibility(View.GONE);

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject section1 = jsonArray.getJSONObject(i);
                                        String status = section1.getString("status");
                                        String mail = section1.getString("email");
//                                        String key = section1.getString("key");
//                                        String reg_time = section1.getString("reg_time");
                                        String fullname = section1.getString("fullname");
                                        String username = section1.getString("username");

                                        if(status.equals("activation successful")){
                                            Intent j = new Intent(getApplicationContext(), Dashboard.class);
                                            j.putExtra("user_email", mail);
                                            j.putExtra("username", username);
//                                            j.putExtra("key", key);
//                                            j.putExtra("reg_time", reg_time);
                                            j.putExtra("display_name", fullname);
//                                            j.putExtra("password", gotPass);
                                            startActivity(j);
                                        }
                                        else if(status.equals("user have been activated before")){
                                            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getApplicationContext(), "Network connectivity problem, please try again", Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("user_name", username);
                        params.put("user_email", email);
                        params.put("user_pass", password);
                        params.put("display_name", fullname);
                        params.put("user_key", key);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

    }

    public void Back(View view) {
        onBackPressed();
    }
}