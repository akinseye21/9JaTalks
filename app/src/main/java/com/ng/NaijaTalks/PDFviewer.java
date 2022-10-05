package com.ng.NaijaTalks;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class PDFviewer extends AppCompatActivity {
    String myURL, title;
    WebView webView;
    TextView title_r;
    ImageView back;
    Button download;
    CircularProgressIndicator circularProgressIndicator;
//    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        myURL = i.getStringExtra("url");
        title = i.getStringExtra("title");

        download = findViewById(R.id.download);
        back = findViewById(R.id.back);
        circularProgressIndicator = findViewById(R.id.progress_circular);
        title_r = findViewById(R.id.title);
        title_r.setText(title);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                webView.clearCache(true);
            }
        });

        webView = findViewById(R.id.webview);
        webView.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewClient(){

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                super.onPageFinished(view, url);
                if (webView.getProgress() == 100) {
                    circularProgressIndicator.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
//                circularProgressIndicator.setVisibility(View.GONE);
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+myURL);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //permission
                ActivityCompat.requestPermissions(PDFviewer.this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PDFviewer.this);
                alertDialogBuilder.setMessage("Are you sure you want to download "+title+"?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myURL));
                                        String titles = URLUtil.guessFileName(myURL, null, null);
                                        request.setTitle(titles);
                                        request.setDescription("Downloading file, please wait...");
                                        String cookie = CookieManager.getInstance().getCookie(myURL);
                                        request.addRequestHeader("cookie", cookie);
                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        request.setDestinationInExternalFilesDir(getApplicationContext(), titles, "");

                                        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                        downloadManager.enqueue(request);

                                        Toast.makeText(PDFviewer.this, "Download started", Toast.LENGTH_LONG).show();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        alertDialog.dismiss();
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }
}