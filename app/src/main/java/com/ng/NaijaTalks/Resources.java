package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;

public class Resources extends AppCompatActivity {

    GridView mygrid, mygrid2;
    int publications [] = {R.drawable.pub1, R.drawable.pub2, R.drawable.pub1, R.drawable.pub2, R.drawable.pub1, R.drawable.pub1, R.drawable.pub2, R.drawable.pub1};
    String text [] = {"Vote Not Fight", "Osun Youth Agenda 1", "Osun Youth Agenda 2", "Vote Not Fight", "Stop VAWIP", "Vote Not Fight", "Osun Youth Agenda 1", "Osun Youth Agenda 2"};

    int brief [] = {R.drawable.pub1, R.drawable.pub2, R.drawable.pub1, R.drawable.pub2, R.drawable.pub1};
    String brieftxt [] = {"Policy Brief 101", "Policy Brief 201", "Osun Youth Agenda Policy Brief", "Vote Not Fight", "Stop VAWIP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mygrid = findViewById(R.id.mygrid);
        mygrid2 = findViewById(R.id.mygrid2);

        ResourcesAdapter  myAdapter=new ResourcesAdapter(this,publications,text);
        mygrid.setAdapter(myAdapter);

        ResourcesAdapter myAdapter2=new ResourcesAdapter(this,brief,brieftxt);
        mygrid2.setAdapter(myAdapter2);
    }
}