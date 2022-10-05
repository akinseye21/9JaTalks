package com.ng.NaijaTalks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.ng.NaijaTalks.adapters.AllUserAdapter;
import com.ng.NaijaTalks.adapters.GroupAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Group2 extends AppCompatActivity {

    LinearLayout logout;
    TextView username;
    CircleImageView imgview;
    ImageView back;
    ProgressBar progressBar;
    TextView loadingtext;
    TextView login, register;
    String email;
    ListView listView;
    int ArrayLength;
    ArrayList<String> groupName = new ArrayList<>();
    ArrayList<String> groupID = new ArrayList<>();

    public static final String FACEBOOK_GROUPS = "https://9jatalks.org/mobile/json_get_facebook_groups.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group2);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progressBar);
        loadingtext = findViewById(R.id.loadingText);
        logout = findViewById(R.id.facebook_logout);
        username = findViewById(R.id.username);
        imgview = findViewById(R.id.imageview);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        email = i.getStringExtra("email");

        if (email.isEmpty()){
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }else if(!email.isEmpty()){
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
        }


//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        GraphRequest request = GraphRequest.newMeRequest(accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
//                        //Code
//                        try {
//                            String fullName = jsonObject.getString("name");
//                            String url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
//                            username.setText(fullName);
//                            Glide.with(getApplicationContext()).load(url).into(imgview);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//
//
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,picture.type(large)");
//        request.setParameters(parameters);
//        request.executeAsync();
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginManager.getInstance().logOut();
//                Intent i = new Intent(getApplicationContext(), Groups.class);
//                i.putExtra("email", email);
//                startActivity(i);
//                finish();
//            }
//        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FACEBOOK_GROUPS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);

                        progressBar.setVisibility(View.GONE);
                        loadingtext.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayLength = jsonArray.length();

                            System.out.println("Number of row = "+ArrayLength);

                            if(ArrayLength >= 1){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject section1 = jsonArray.getJSONObject(i);
                                    String group_id = section1.getString("group_id");
                                    String group_name = section1.getString("group_name");
//                                    String ufullname = section1.getString("fullname");

//                                    System.out.println("Username "+uname+"\nEmail "+uemail+"\nFullname "+ufullname);

                                    groupID.add(group_id);
                                    groupName.add(group_name);


                                    listView = findViewById(R.id.listview);
                                    GroupAdapter myAdapter=new GroupAdapter(getApplicationContext(),groupID,groupName,email);
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