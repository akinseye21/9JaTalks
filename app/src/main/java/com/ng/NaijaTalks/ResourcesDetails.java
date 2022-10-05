package com.ng.NaijaTalks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourcesDetails extends AppCompatActivity {

    String title, image, date, content;
    int category_id;
    TextView resources_name, dated, resource_name;
    ImageView back,imageView;
//    VideoView videoView;
    WebView webView;
    TextView downloadDocument;
    ProgressBar progressBar;
    RelativeLayout rel;
    TextView login, register;
    String email;

    ImageView facebook, twitter, gmail, instagram;


    String api_key = "AIzaSyAZqaGcGeIOsSiFuzKxVQbmSg_4i0h4IQs";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_details);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        image = i.getStringExtra("image");
        date = i.getStringExtra("date");
        content = i.getStringExtra("content");
        email = i.getStringExtra("email");
        category_id = i.getIntExtra("category_id", 0);

        rel = findViewById(R.id.rel);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webview);
        resources_name = findViewById(R.id.resources_name);
        dated = findViewById(R.id.date);
        resource_name = findViewById(R.id.resource_name);
        back = findViewById(R.id.back);
        imageView = findViewById(R.id.image);
        downloadDocument = findViewById(R.id.downloadDocument);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        gmail = findViewById(R.id.gmail);
        instagram = findViewById(R.id.instagram);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                webView.clearCache(true);
            }
        });

        if(email.isEmpty()){
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }else{
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
        }

        List<String> list = new ArrayList<>();
        String regex = "\\b((?:https?|ftp|file):" + "//[-a-zA-Z0-9+&@#/%?="
                + "~_|!:, .;]*[-a-zA-Z0-9+" + "&@#/%=~_|])";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(content);

        if (category_id == 97 || category_id == 113 || category_id == 100){
            imageView.setVisibility(View.GONE);
            downloadDocument.setVisibility(View.GONE);
//            videoView.setVisibility(View.VISIBLE);

            while (m.find()) {
                list.add(content.substring(
                        m.start(0), m.end(0)));
            }
            // IF there no URL present
            if (list.size() == 0) {
                System.out.println("-1");
                Toast.makeText(ResourcesDetails.this, "No video file", Toast.LENGTH_LONG).show();
                webView.setVisibility(View.GONE);
            }
            for (String url : list) {
//            String my_url = url;
//            Toast.makeText(ResourcesDetails.this, "my url ="+url, Toast.LENGTH_LONG).show();
                System.out.println("my url = "+url);
                // Set up the media controller widget and attach it to the video view.
//                MediaController controller = new MediaController(this);
////                videoView.setMediaPlayer(controller);
//                videoView.setMediaController(controller);
//                controller.setAnchorView(videoView);
//                webView.setVisibility(View.GONE);
                webView.setWebViewClient(new WebViewClient(){

                    public void onPageFinished(WebView view, String url) {
                        // do your stuff here
                        super.onPageFinished(view, url);
                        if (webView.getProgress() == 100) {
                            progressBar.setVisibility(View.GONE);
                            webView.setVisibility(View.VISIBLE);
                        }
//                circularProgressIndicator.setVisibility(View.GONE);
                    }
                });
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.loadUrl(url);

                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Watch \""+title+"\" on 9jaTalks\n"+url);
                        emailIntent.setPackage("com.facebook.katana");
                        startActivity(Intent.createChooser(emailIntent, "Share"));
                    }
                });

                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("https://9jatalks.org"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Watch \""+title+"\" on 9jaTalks\n"+url);
                        emailIntent.setPackage("com.twitter.android");
                        startActivity(Intent.createChooser(emailIntent, "Share"));
                    }
                });

                gmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("https://9jatalks.org"));
                        emailIntent.setType("text/plain");
//                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jan@example.com"}); // recipients
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Watch \""+title+"\" on 9jaTalks\n"+url);
                        emailIntent.setPackage("com.google.android.gm");
                        startActivity(Intent.createChooser(emailIntent, "Send mail"));
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri file = Uri.parse("android.resource://com.ng.NaijaTalks/"+R.drawable.logo2);
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM,file);
                        shareIntent.putExtra(Intent.EXTRA_TITLE, "Watch \""+title+"\" on 9jaTalks\n"+url);
                        shareIntent.setPackage("com.instagram.android");
                        startActivity(shareIntent);
                    }
                });

//                Uri uri = Uri.parse(url);
//                //set videoview to load url
//                videoView.setVideoURI(uri); //the string of the URL mentioned above
//                videoView.requestFocus();
//                videoView.start();
            }
        }else{
            rel.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            downloadDocument.setVisibility(View.VISIBLE);

            while (m.find()) {
                list.add(content.substring(
                        m.start(0), m.end(0)));
            }
            // IF there no URL present
            if (list.size() == 0) {
                System.out.println("-1");
                Toast.makeText(ResourcesDetails.this, "No document to view", Toast.LENGTH_LONG).show();
//            downloadDocument.setVisibility(View.GONE);
            }
            for (String url : list) {
//            String my_url = url;
//            Toast.makeText(ResourcesDetails.this, "my url ="+url, Toast.LENGTH_LONG).show();
                System.out.println("my url = "+url);

                downloadDocument.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //goto view document page
                        Intent i = new Intent(getApplicationContext(), PDFviewer.class);
                        i.putExtra("url", url);
                        i.putExtra("category_id", category_id);
                        i.putExtra("title", title);
                        startActivity(i);
                    }
                });




            }
        }

        resources_name.setText(title);
        resource_name.setText(title);
        dated.setText(date);
        Glide.with(ResourcesDetails.this).load(image).into(imageView);











    }

//    public String extractURL(String content){
//        // Creating an empty ArrayList
//        List<String> list = new ArrayList<>();
//
//        // Regular Expression to extract
//        // URL from the string
//        String regex = "\\b((?:https?|ftp|file):" + "//[-a-zA-Z0-9+&@#/%?="
//                + "~_|!:, .;]*[-a-zA-Z0-9+" + "&@#/%=~_|])";
//
//        // Compile the Regular Expression
//        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//
//        // Find the match between string
//        // and the regular expression
//        Matcher m = p.matcher(content);
//        // Find the next subsequence of
//        // the input subsequence that
//        // find the pattern
//        while (m.find()) {
//
//            // Find the substring from the
//            // first index of match result
//            // to the last index of match
//            // result and add in the list
//            list.add(content.substring(
//                    m.start(0), m.end(0)));
//        }
//        // IF there no URL present
//        if (list.size() == 0) {
//            System.out.println("-1");
//            return regex;
//        }
//
//        // Print all the URLs stored
//        for (String url : list) {
////            String my_url = url;
//            System.out.println("My url= "+url);
//            Toast.makeText(ResourcesDetails.this, "my url ="+url, Toast.LENGTH_LONG).show();
//            return url;
//        }
//
//        return regex;
//    }
}