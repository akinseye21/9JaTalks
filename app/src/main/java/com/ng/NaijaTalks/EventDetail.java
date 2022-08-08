package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetail extends AppCompatActivity {
    TextView date;
    TextView start_time_hour;
    TextView start_time_minute;
    TextView start_time_ampm;
    TextView end_time_hour;
    TextView end_time_minute;
    TextView end_time_ampm;
    TextView event_title;

    String passed_date;
    String passed_start_time_hour;
    String passed_start_time_minute;
    String passed_start_time_ampm;
    String passed_end_time_hour;
    String passed_end_time_minute;
    String passed_end_time_ampm;
    String passed_end_date;
    String passed_event_title;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        passed_date = i.getStringExtra("startDate");
        passed_start_time_hour = i.getStringExtra("startTimeHour");
        passed_start_time_minute = i.getStringExtra("startTimeMinutes");
        passed_end_time_ampm = i.getStringExtra("endTimeAmPm");
        passed_start_time_ampm = i.getStringExtra("startTimeAmPm");
        passed_end_time_hour = i.getStringExtra("endTimeHour");
        passed_end_time_minute = i.getStringExtra("endTimeMinutes");
        passed_end_date = i.getStringExtra("endDate");  //check if this is equal to start date
        passed_event_title = i.getStringExtra("eventTitle");

        date = findViewById(R.id.date);
        start_time_hour = findViewById(R.id.start_time_hour);
        start_time_minute = findViewById(R.id.start_time_minutes);
        start_time_ampm = findViewById(R.id.start_time_ampm);
        end_time_hour = findViewById(R.id.end_time_hour);
        end_time_minute = findViewById(R.id.end_time_minutes);
        end_time_ampm = findViewById(R.id.end_time_ampm);
        event_title = findViewById(R.id.event_title);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        date.setText(passed_date);
        start_time_hour.setText(passed_start_time_hour);
        start_time_minute.setText(passed_start_time_minute);
        start_time_ampm.setText(passed_start_time_ampm);
        end_time_hour.setText(passed_end_time_hour);
        end_time_minute.setText(passed_end_time_minute);
        end_time_ampm.setText(passed_end_time_ampm);
        event_title.setText(passed_event_title);

    }
}