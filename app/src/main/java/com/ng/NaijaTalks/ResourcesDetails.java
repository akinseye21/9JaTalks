package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

public class ResourcesDetails extends AppCompatActivity {

    String title, image, date;
    int category_id;
    TextView resources_name, dated, resource_name;
    ImageView imageView;
    VideoView videoView;
    TextView downloadDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_details);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        image = i.getStringExtra("image");
        date = i.getStringExtra("date");
        category_id = i.getIntExtra("category_id", 0);

        resources_name = findViewById(R.id.resources_name);
        dated = findViewById(R.id.date);
        resource_name = findViewById(R.id.resource_name);
        imageView = findViewById(R.id.image);
        videoView = findViewById(R.id.videoView);
        downloadDocument = findViewById(R.id.downloadDocument);

        if (category_id == 97){
            imageView.setVisibility(View.VISIBLE);
            downloadDocument.setVisibility(View.GONE);
        }else{
            imageView.setVisibility(View.GONE);
        }

        resources_name.setText(title);
        resource_name.setText(title);
        dated.setText(date);
        Glide.with(ResourcesDetails.this).load(image).into(imageView);



    }
}