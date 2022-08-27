package com.ng.NaijaTalks.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.material.snackbar.Snackbar;
import com.ng.NaijaTalks.EventDetail;
import com.ng.NaijaTalks.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    LayoutInflater inflter;

    int ArrayLength;
    ArrayList<String> post_ID = new ArrayList<>();
    ArrayList<String> meta_Key = new ArrayList<>();
    ArrayList<String> meta_Value = new ArrayList<>();

    String startDate, startTimeHour, startTimeMinutes, endDate, endTimeHour, endTimeMinutes, startTimeAmPm, endTimeAmPm;
    public static final String EVENT_META = "https://9jatalks.org/mobile/json_get_event_meta.php";

    public AllEventsAdapter(Context applicationContext, ArrayList<String> id, ArrayList<String> title, ArrayList<String> author) {
        this.context = applicationContext;
//        this.image = image;
        this.id = id;
        this.title = title;
        this.author = author;
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

        imageView.setImageResource(rand_image[randImg]);
//        imageView.setBackgroundResource(rand_image[randImg]);
        btn_title.setBackgroundResource(rand_color[randNum1]);
        btn_info.setBackgroundResource(rand_color[randNum2]);
        event_title.setText(title.get(position));
        event_info.setText(title.get(position));

        //send the event_ID to the the table Talks_postmeta
        //send the response to the next activity

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                social_share.setVisibility(View.VISIBLE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                social_share.setVisibility(View.GONE);
            }
        });
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Loading... Please wait";
                int duration = Snackbar.LENGTH_LONG;
                showSnackbar(view, message, duration);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, EVENT_META,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("Response = "+response);


                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    ArrayLength = jsonArray.length();

                                    System.out.println("Number of row = "+ArrayLength);

                                    if(ArrayLength >= 1){
                                        for(int i=0; i<jsonArray.length(); i++){
                                            JSONObject section1 = jsonArray.getJSONObject(i);
                                            String post_id = section1.getString("post_id");
                                            String meta_key = section1.getString("meta_key");
                                            String meta_value = section1.getString("meta_value");

//                                            System.out.println("post_id "+post_id+"\nmeta_key "+meta_key+"\nmeta_value "+meta_value);

                                            if(meta_key.equals("mec_start_date")){
                                                startDate = meta_value;
                                            }
                                            if(meta_key.equals("mec_start_time_hour")){
                                                startTimeHour = meta_value;
                                            }
                                            if(meta_key.equals("mec_start_time_minutes")){
                                                startTimeMinutes = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_date")){
                                                endDate = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_time_hour")){
                                                endTimeHour = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_time_minutes")){
                                                endTimeMinutes = meta_value;
                                            }
                                            if(meta_key.equals("mec_start_time_ampm")){
                                                startTimeAmPm = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_time_ampm")){
                                                endTimeAmPm = meta_value;
                                            }

                                        }

//                                        Toast.makeText(context, "Start Date: "+startDate+"\n" +
//                                                "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
//                                                "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
//                                                "End Date: "+endDate, Toast.LENGTH_LONG).show();


                                    }

                                    System.out.println("Start Date: "+startDate+"\n" +
                                            "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
                                            "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
                                            "End Date: "+endDate);

                                    Intent intent = new Intent(context, EventDetail.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("startDate", startDate);
                                    intent.putExtra("startTimeHour", startTimeHour);
                                    intent.putExtra("startTimeMinutes", startTimeMinutes);
                                    intent.putExtra("endDate", endDate);
                                    intent.putExtra("endTimeHour", endTimeHour);
                                    intent.putExtra("endTimeMinutes", endTimeMinutes);
                                    intent.putExtra("startTimeAmPm", startTimeAmPm);
                                    intent.putExtra("endTimeAmPm", endTimeAmPm);
                                    intent.putExtra("eventTitle", title.get(position));
                                    context.startActivity(intent);

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
                        params.put("post_id", id.get(position));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "ID = "+id.get(position), Toast.LENGTH_LONG).show();
                String message = "Loading... Please wait";
                int duration = Snackbar.LENGTH_LONG;
                showSnackbar(view, message, duration);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, EVENT_META,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("Response = "+response);


                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    ArrayLength = jsonArray.length();

                                    System.out.println("Number of row = "+ArrayLength);

                                    if(ArrayLength >= 1){
                                        for(int i=0; i<jsonArray.length(); i++){
                                            JSONObject section1 = jsonArray.getJSONObject(i);
                                            String post_id = section1.getString("post_id");
                                            String meta_key = section1.getString("meta_key");
                                            String meta_value = section1.getString("meta_value");

//                                            System.out.println("post_id "+post_id+"\nmeta_key "+meta_key+"\nmeta_value "+meta_value);

                                            if(meta_key.equals("mec_start_date")){
                                                startDate = meta_value;
                                            }
                                            if(meta_key.equals("mec_start_time_hour")){
                                                startTimeHour = meta_value;
                                            }
                                            if(meta_key.equals("mec_start_time_minutes")){
                                                startTimeMinutes = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_date")){
                                                endDate = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_time_hour")){
                                                endTimeHour = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_time_minutes")){
                                                endTimeMinutes = meta_value;
                                            }
                                            if(meta_key.equals("mec_start_time_ampm")){
                                                startTimeAmPm = meta_value;
                                            }
                                            if(meta_key.equals("mec_end_time_ampm")){
                                                endTimeAmPm = meta_value;
                                            }

                                        }

//                                        Toast.makeText(context, "Start Date: "+startDate+"\n" +
//                                                "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
//                                                "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
//                                                "End Date: "+endDate, Toast.LENGTH_LONG).show();


                                    }

                                    System.out.println("Start Date: "+startDate+"\n" +
                                            "Start Time: "+startTimeHour+":"+startTimeMinutes+" "+startTimeAmPm+"\n" +
                                            "End Time: "+endTimeHour+":"+endTimeMinutes+" "+endTimeAmPm+"\n" +
                                            "End Date: "+endDate);

                                    Intent intent = new Intent(context, EventDetail.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("startDate", startDate);
                                    intent.putExtra("startTimeHour", startTimeHour);
                                    intent.putExtra("startTimeMinutes", startTimeMinutes);
                                    intent.putExtra("endDate", endDate);
                                    intent.putExtra("endTimeHour", endTimeHour);
                                    intent.putExtra("endTimeMinutes", endTimeMinutes);
                                    intent.putExtra("startTimeAmPm", startTimeAmPm);
                                    intent.putExtra("endTimeAmPm", endTimeAmPm);
                                    intent.putExtra("eventTitle", title.get(position));
                                    context.startActivity(intent);

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
                        params.put("post_id", id.get(position));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });

        return convertView;

    }


    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
}
