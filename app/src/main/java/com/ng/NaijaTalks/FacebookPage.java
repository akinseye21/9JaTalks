package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class FacebookPage extends AppCompatActivity {
    String groupID, groupName, email;
    TextView title;
    ImageView back;
    WebView webView;
    Dialog myDialog;
    CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        groupID = i.getStringExtra("groupID");
        groupName = i.getStringExtra("groupName");
        email = i.getStringExtra("email");

        circularProgressIndicator = findViewById(R.id.progress_circular);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title = findViewById(R.id.title);
        title.setText(groupName);

        if (email.isEmpty()){
            myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.custom_popu_register);
            TextView close = myDialog.findViewById(R.id.close);
            Button login = myDialog.findViewById(R.id.login);
            TextView createaccount = myDialog.findViewById(R.id.createaccount);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myDialog.dismiss();
                }
            });
            createaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Register.class);
                    startActivity(i);
                }
            });
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });
            // Setting dialogview
            Window window = myDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.setCanceledOnTouchOutside(false);
            myDialog.show();
        }else if(!email.isEmpty()){
            webView = findViewById(R.id.webview);
            WebSettings webSettings = webView.getSettings();
            webView.setWebViewClient(new WebViewClient(){

                public void onPageFinished(WebView view, String url) {
                    // do your stuff here
                    super.onPageFinished(view, url);
                    if (webView.getProgress() == 100) {
                        circularProgressIndicator.setVisibility(View.GONE);
//                    webView.setVisibility(View.VISIBLE);
                    }
//                circularProgressIndicator.setVisibility(View.GONE);
                }
            });
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl("https://facebook.com/"+groupID);
        }




    }
}