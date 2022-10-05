package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Session;

import java.util.Properties;



public class Register extends AppCompatActivity {

    EditText username, email, password, fullname;
    Button complete;
    String gotName, gotEmail, gotPass, gotFullname;
    ProgressBar progressBar;

    public static final String REGISTER = "https://9jatalks.org/mobile/json_register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = findViewById(R.id.edtusername);
        email = findViewById(R.id.edtemail);
        password = findViewById(R.id.edtpassword);
        fullname = findViewById(R.id.edtfullname);
        complete = findViewById(R.id.btncomplete);
        progressBar = findViewById(R.id.progressBar);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                gotName = username.getText().toString().trim();
                gotEmail = email.getText().toString().trim();
                gotPass = password.getText().toString().trim();
                gotFullname = fullname.getText().toString().trim();
                //check for empty fields first
                if (gotName.isEmpty() || gotEmail.isEmpty() || gotFullname.isEmpty() || gotPass.isEmpty()){
                    username.setError("check inputs");
                    email.setError("check inputs");
                    password.setError("check inputs");
                    fullname.setError("check inputs");
                }else{
                    //send info to the DB
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER,
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
                                            String key = section1.getString("key");
                                            String reg_time = section1.getString("reg_time");
                                            String fullname = section1.getString("fullname");

                                            if(status.equals("successful")){


                                                Intent j = new Intent(getApplicationContext(), Activation.class);
                                                j.putExtra("email", mail);
                                                j.putExtra("username", gotName);
                                                j.putExtra("key", key);
                                                j.putExtra("reg_time", reg_time);
                                                j.putExtra("fullname", fullname);
                                                j.putExtra("password", gotPass);
                                                startActivity(j);
                                                
                                            }else{
                                                Toast.makeText(Register.this, "User already exist", Toast.LENGTH_SHORT).show();
                                            }

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
                            params.put("user_name", gotName);
                            params.put("user_email", gotEmail);
                            params.put("user_pass", gotPass);
                            params.put("display_name", gotFullname);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }



            }
        });

    }

    public void Back(View view) {
        onBackPressed();
    }
}