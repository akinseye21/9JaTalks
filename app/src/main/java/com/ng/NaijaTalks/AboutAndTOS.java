package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class AboutAndTOS extends AppCompatActivity {

    ImageView back;
    TextView title;
    WebView webView;
    CircularProgressIndicator circularProgressIndicator;

    String myTitle,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_and_tos);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        myTitle = i.getStringExtra("from");
        link = i.getStringExtra("link");


        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        webView = findViewById(R.id.webview);
        circularProgressIndicator = findViewById(R.id.progress_circular);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title.setText(myTitle);
        webView.setWebViewClient(new WebViewClient(){

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                super.onPageFinished(view, url);
                if (webView.getProgress() == 100) {
                    circularProgressIndicator.setVisibility(View.GONE);
                }
//                circularProgressIndicator.setVisibility(View.GONE);
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(link);


    }
}