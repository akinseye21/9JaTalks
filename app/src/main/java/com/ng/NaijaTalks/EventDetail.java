package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.EventLogTags;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;

public class EventDetail extends AppCompatActivity {
    TextView date;
//    TextView start_time_hour;
//    TextView start_time_minute;
//    TextView start_time_ampm;
//    TextView end_time_hour;
//    TextView end_time_minute;
//    TextView end_time_ampm;
    TextView event_title;

    String passed_date;
    String start_time_hour;
    String start_time_minute;
//    String passed_start_time_ampm;
//    String passed_end_time_hour;
//    String passed_end_time_minute;
//    String passed_end_time_ampm;
//    String passed_end_date;
    String imgUrl;
    String passed_event_title;
    String email;
    String year, month,day;

    Button addCalendar;

    Dialog myDialog;

    ArrayList<String> iterate = new ArrayList<>();

    ImageView back;
    ImageView image;
    ImageView facebook, twitter, gmail, instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
//        passed_date = i.getStringExtra("startDate");
//        passed_start_time_hour = i.getStringExtra("startTimeHour");
//        passed_start_time_minute = i.getStringExtra("startTimeMinutes");
//        passed_end_time_ampm = i.getStringExtra("endTimeAmPm");
//        passed_start_time_ampm = i.getStringExtra("startTimeAmPm");
//        passed_end_time_hour = i.getStringExtra("endTimeHour");
//        passed_end_time_minute = i.getStringExtra("endTimeMinutes");
//        passed_end_date = i.getStringExtra("endDate");  //check if this is equal to start date
        passed_date = i.getStringExtra("date");
        passed_event_title = i.getStringExtra("eventTitle");
        email = i.getStringExtra("email");
        imgUrl = i.getStringExtra("imgUrl");

        date = findViewById(R.id.date);
//        start_time_hour = findViewById(R.id.start_time_hour);
//        start_time_minute = findViewById(R.id.start_time_minutes);
//        start_time_ampm = findViewById(R.id.start_time_ampm);
//        end_time_hour = findViewById(R.id.end_time_hour);
//        end_time_minute = findViewById(R.id.end_time_minutes);
//        end_time_ampm = findViewById(R.id.end_time_ampm);
        event_title = findViewById(R.id.event_title);
        back = findViewById(R.id.back);
        image = findViewById(R.id.image);
        addCalendar = findViewById(R.id.add_calendar);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        gmail = findViewById(R.id.gmail);
        instagram = findViewById(R.id.instagram);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        Glide.with(EventDetail.this).load(imgUrl).into(image);

        if(email.isEmpty()){
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

//            start_time_hour.setText(passed_start_time_hour);
//            start_time_minute.setText(passed_start_time_minute);
//            start_time_ampm.setText(passed_start_time_ampm);
//            end_time_hour.setText(passed_end_time_hour);
//            end_time_minute.setText(passed_end_time_minute);
//            end_time_ampm.setText(passed_end_time_ampm);
            event_title.setText(passed_event_title);
            Glide.with(EventDetail.this).load(imgUrl).into(image);


            int date_len = passed_date.length();
            for(int j=0; j<date_len; j++){
                iterate.add(String.valueOf(passed_date.charAt(j)));
            }
            year = iterate.get(0)+iterate.get(1)+iterate.get(2)+iterate.get(3);
            month = iterate.get(5)+iterate.get(6);
            day = iterate.get(8)+iterate.get(9);
            start_time_hour = iterate.get(11)+iterate.get(12);
            start_time_minute = iterate.get(14)+iterate.get(15);

            date.setText(year+"-"+month+"-"+day);

            addCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Event is on January 23, 2021 -- from 7:30 AM to 10:30 AM.
                    Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day), Integer.parseInt(start_time_hour), Integer.parseInt(start_time_minute));
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day), Integer.parseInt(start_time_hour)+1, Integer.parseInt(start_time_minute));
                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                    calendarIntent.putExtra(CalendarContract.Events.TITLE, passed_event_title);
                    calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "");
                    startActivity(calendarIntent);
                }
            });
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Join event on 9jaTalks\n"+passed_event_title+"\nhttps://9jaTalks.org");
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
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Join event on 9jaTalks\n"+passed_event_title+"\nhttps://9jaTalks.org");
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
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Join event on 9jaTalks\n"+passed_event_title+"\nhttps://9jaTalks.org");
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
                    shareIntent.putExtra(Intent.EXTRA_TITLE, "Join event on 9jaTalks\n"+passed_event_title+"\nhttps://9jaTalks.org");
                    shareIntent.setPackage("com.instagram.android");
                    startActivity(shareIntent);
                }
            });
        }

    }
}