package com.ng.NaijaTalks.adapters;

import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

import static java.net.Proxy.Type.HTTP;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.ng.NaijaTalks.EventDetail;
import com.ng.NaijaTalks.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllEventsAdapter extends BaseAdapter {

    Context context;
    //    ArrayList<String> image;
    ArrayList<String> id;
    ArrayList<String> title;
    ArrayList<String> author;
    ArrayList<Integer> arr_post_id;
    ArrayList<String> arr_post_url;
    ArrayList<String> arr_source_url;
    ArrayList<String> arr_date;
    String email;
    LayoutInflater inflter;

    int ArrayLength, ArrayLength2;
    ArrayList<String> post_ID = new ArrayList<>();
    ArrayList<String> meta_Key = new ArrayList<>();
    ArrayList<String> meta_Value = new ArrayList<>();

    String startDate, startTimeHour, startTimeMinutes, endDate, endTimeHour, endTimeMinutes, startTimeAmPm, endTimeAmPm;
    public static final String EVENT_META = "https://9jatalks.org/mobile/json_get_event_meta.php";
//    public static final String ALL_MEDIA = "https://9jatalks.org/wp-json/wp/v2/media?per_page=100";

    public AllEventsAdapter(Context applicationContext, ArrayList<String> arr_source_url, ArrayList<String> arr_date, ArrayList<String> title, String email) {
        this.context = applicationContext;
//        this.image = image;
//        this.id = id;
        this.title = title;
        this.arr_date = arr_date;
//        this.author = author;
//        this.post_url = post_url;
        this.email = email;
        this.arr_source_url = arr_source_url;
//        this.arr_post_id = arr_post_id;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return title.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.event_item, null); // inflate the layout
        TextView event_title = (TextView) convertView.findViewById(R.id.event_title);
        TextView event_info = (TextView) convertView.findViewById(R.id.event_info);
        Button btn_title = (Button) convertView.findViewById(R.id.btn_title);
        Button btn_info = (Button) convertView.findViewById(R.id.btn_info);
        LinearLayout card = (LinearLayout) convertView.findViewById(R.id.card);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        TextView viewDetails = (TextView) convertView.findViewById(R.id.view);
        ImageView btnShare = (ImageView) convertView.findViewById(R.id.btnshare);
        LinearLayout social_share = (LinearLayout) convertView.findViewById(R.id.social_share);
        TextView close = (TextView) convertView.findViewById(R.id.close);
        ImageView facebook = convertView.findViewById(R.id.pic_facebook);
        ImageView twitter = convertView.findViewById(R.id.pic_twitter);
        ImageView gmail = convertView.findViewById(R.id.pic_gmail);
        ImageView instagram = convertView.findViewById(R.id.pic_instagram);
        ImageView logo = convertView.findViewById(R.id.logo);




        int[] rand_color = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
        R.color.purple_200, R.color.purple_500, R.color.purple_700, R.color.black,
        R.color.md_red_50, R.color.md_red_100, R.color.md_red_200, R.color.md_red_300,
                R.color.md_red_400, R.color.md_red_500, R.color.md_red_600, R.color.md_red_700,
        R.color.md_red_800, R.color.md_red_900, R.color.md_pink_50, R.color.md_pink_100,
        R.color.md_pink_200, R.color.md_pink_300, R.color.md_pink_400, R.color.md_pink_500,
        R.color.teal_700, R.color.teal_200, R.color.md_teal_50, R.color.md_teal_500};

        Random myRand = new Random();
        int randNum1 = myRand.nextInt(rand_color.length);
        int randNum2 = myRand.nextInt(rand_color.length);

        int[] rand_image = {R.drawable.hall1, R.drawable.hall2, R.drawable.hall3, R.drawable.hall4, R.drawable.hall5};

        int randImg = myRand.nextInt(rand_image.length);

//        System.out.println("Gotten ID = "+id);

//        if (Integer.parseInt(id.get(position)) == arr_post_id.get(position)){
//
//        }else{
//            imageView.setImageResource(rand_image[randImg]);
//        }

        Glide.with(context).load(arr_source_url.get(position)).into(imageView);


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                social_share.setVisibility(View.VISIBLE);

                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Join event on 9jaTalks\n"+title.get(position)+"\nhttps://9jaTalks.org");
                        emailIntent.setPackage("com.facebook.katana");
                        context.startActivity(Intent.createChooser(emailIntent, "Share"));
                    }
                });

                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("https://9jatalks.org"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Join event on 9jaTalks\n"+title.get(position)+"\nhttps://9jaTalks.org");
                        emailIntent.setPackage("com.twitter.android");
                        context.startActivity(Intent.createChooser(emailIntent, "Share"));
                    }
                });

                gmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("https://9jatalks.org"));
                        emailIntent.setType("text/plain");
//                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jan@example.com"}); // recipients
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "9jaTalks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Join event on 9jaTalks\n"+title.get(position)+"\nhttps://9jaTalks.org");
                        emailIntent.setPackage("com.google.android.gm");
                        context.startActivity(Intent.createChooser(emailIntent, "Send mail"));
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri file = Uri.parse("android.resource://com.ng.NaijaTalks/"+R.drawable.logo2);
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM,file);
                        shareIntent.putExtra(Intent.EXTRA_TITLE, "Join event on 9jaTalks\n"+title.get(position)+"\nhttps://9jaTalks.org");
                        shareIntent.setPackage("com.instagram.android");
                        context.startActivity(shareIntent);
                    }
                });
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                social_share.setVisibility(View.GONE);
            }
        });





//        imageView.setImageResource(rand_image[randImg]);

        btn_title.setBackgroundResource(rand_color[randNum1]);
        btn_info.setBackgroundResource(rand_color[randNum2]);
        event_title.setText(title.get(position));
        event_info.setText(title.get(position));

        //send the event_ID to the the table Talks_postmeta
        //send the response to the next activity

        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("date", arr_date.get(position));
                intent.putExtra("eventTitle", title.get(position));
                intent.putExtra("imgUrl", arr_source_url.get(position));
                intent.putExtra("email", email);
                context.startActivity(intent);
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("date", arr_date.get(position));
                intent.putExtra("eventTitle", title.get(position));
                intent.putExtra("imgUrl", arr_source_url.get(position));
                intent.putExtra("email", email);
                context.startActivity(intent);
            }
        });


//        viewDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String message = "Loading... Please wait";
//                int duration = Snackbar.LENGTH_LONG;
//                showSnackbar(view, message, duration);
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, EVENT_META,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                System.out.println("Response = "+response);
//
//
//                                try {
//                                    JSONArray jsonArray = new JSONArray(response);
//                                    ArrayLength = jsonArray.length();
//
//                                    System.out.println("Number of row = "+ArrayLength);
//
//                                    if(ArrayLength >= 1){
//                                        for(int i=0; i<jsonArray.length(); i++){
//                                            JSONObject section1 = jsonArray.getJSONObject(i);
//                                            String post_id = section1.getString("post_id");
//                                            String meta_key = section1.getString("meta_key");
//                                            String meta_value = section1.getString("meta_value");
//
////                                            System.out.println("post_id "+post_id+"\nmeta_key "+meta_key+"\nmeta_value "+meta_value);
//
//                                            if(meta_key.equals("mec_start_date")){
//                                                startDate = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_start_time_hour")){
//                                                startTimeHour = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_start_time_minutes")){
//                                                startTimeMinutes = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_date")){
//                                                endDate = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_time_hour")){
//                                                endTimeHour = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_time_minutes")){
//                                                endTimeMinutes = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_start_time_ampm")){
//                                                startTimeAmPm = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_time_ampm")){
//                                                endTimeAmPm = meta_value;
//                                            }
//
//                                        }
//
////                                        Toast.makeText(context, "Start Date: "+startDate+"\n" +
////                                                "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
////                                                "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
////                                                "End Date: "+endDate, Toast.LENGTH_LONG).show();
//
//
//                                    }
//
//                                    System.out.println("Start Date: "+startDate+"\n" +
//                                            "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
//                                            "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
//                                            "End Date: "+endDate);
//
//                                    Intent intent = new Intent(context, EventDetail.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.putExtra("startDate", startDate);
//                                    intent.putExtra("startTimeHour", startTimeHour);
//                                    intent.putExtra("startTimeMinutes", startTimeMinutes);
//                                    intent.putExtra("endDate", endDate);
//                                    intent.putExtra("endTimeHour", endTimeHour);
//                                    intent.putExtra("endTimeMinutes", endTimeMinutes);
//                                    intent.putExtra("startTimeAmPm", startTimeAmPm);
//                                    intent.putExtra("endTimeAmPm", endTimeAmPm);
//                                    intent.putExtra("eventTitle", title.get(position));
//                                    intent.putExtra("imgUrl", arr_source_url.get(position));
//                                    intent.putExtra("email", email);
//                                    context.startActivity(intent);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }){
//                    @Override
//                    protected Map<String, String> getParams(){
//                        Map<String, String> params = new HashMap<>();
//                        params.put("post_id", id.get(position));
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                requestQueue.add(stringRequest);
//            }
//        });
//        card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(context, "ID = "+id.get(position), Toast.LENGTH_LONG).show();
//                String message = "Loading... Please wait";
//                int duration = Snackbar.LENGTH_LONG;
//                showSnackbar(view, message, duration);
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, EVENT_META,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                System.out.println("Response = "+response);
//
//
//                                try {
//                                    JSONArray jsonArray = new JSONArray(response);
//                                    ArrayLength = jsonArray.length();
//
//                                    System.out.println("Number of row = "+ArrayLength);
//
//                                    if(ArrayLength >= 1){
//                                        for(int i=0; i<jsonArray.length(); i++){
//                                            JSONObject section1 = jsonArray.getJSONObject(i);
//                                            String post_id = section1.getString("post_id");
//                                            String meta_key = section1.getString("meta_key");
//                                            String meta_value = section1.getString("meta_value");
//
////                                            System.out.println("post_id "+post_id+"\nmeta_key "+meta_key+"\nmeta_value "+meta_value);
//
//                                            if(meta_key.equals("mec_start_date")){
//                                                startDate = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_start_time_hour")){
//                                                startTimeHour = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_start_time_minutes")){
//                                                startTimeMinutes = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_date")){
//                                                endDate = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_time_hour")){
//                                                endTimeHour = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_time_minutes")){
//                                                endTimeMinutes = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_start_time_ampm")){
//                                                startTimeAmPm = meta_value;
//                                            }
//                                            if(meta_key.equals("mec_end_time_ampm")){
//                                                endTimeAmPm = meta_value;
//                                            }
//
//                                        }
//
////                                        Toast.makeText(context, "Start Date: "+startDate+"\n" +
////                                                "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
////                                                "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
////                                                "End Date: "+endDate, Toast.LENGTH_LONG).show();
//
//
//                                    }
//
//                                    System.out.println("Start Date: "+startDate+"\n" +
//                                            "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
//                                            "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
//                                            "End Date: "+endDate);
//
//                                    Intent intent = new Intent(context, EventDetail.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.putExtra("startDate", startDate);
//                                    intent.putExtra("startTimeHour", startTimeHour);
//                                    intent.putExtra("startTimeMinutes", startTimeMinutes);
//                                    intent.putExtra("endDate", endDate);
//                                    intent.putExtra("endTimeHour", endTimeHour);
//                                    intent.putExtra("endTimeMinutes", endTimeMinutes);
//                                    intent.putExtra("startTimeAmPm", startTimeAmPm);
//                                    intent.putExtra("endTimeAmPm", endTimeAmPm);
//                                    intent.putExtra("eventTitle", title.get(position));
//                                    intent.putExtra("imgUrl", arr_source_url.get(position));
//                                    intent.putExtra("email", email);
//                                    context.startActivity(intent);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }){
//                    @Override
//                    protected Map<String, String> getParams(){
//                        Map<String, String> params = new HashMap<>();
//                        params.put("post_id", id.get(position));
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                requestQueue.add(stringRequest);
//
//            }
//        });

        return convertView;

    }


    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
