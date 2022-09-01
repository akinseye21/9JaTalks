package com.ng.NaijaTalks.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ng.NaijaTalks.EventDetail;
import com.ng.NaijaTalks.JobViewPage;
import com.ng.NaijaTalks.R;
import com.ng.NaijaTalks.ResourcesDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllJobsAdapter extends BaseAdapter {

    Context context;
    //    ArrayList<String> image;
    ArrayList<String> arr_job_title;
    ArrayList<String> arr_job_content;
    ArrayList<String> arr_job_location;
    ArrayList<String> arr_application_link;
    ArrayList<String> arr_company_name;
    ArrayList<String> arr_company_website;
    ArrayList<Integer> arr_featured_image;
    LayoutInflater inflter;

    int ArrayLength;

    public static final String MEDIA = "https://9jatalks.org/wp-json/wp/v2/media";

    public AllJobsAdapter(Context applicationContext, ArrayList<String> arr_job_title, ArrayList<String> arr_job_content, ArrayList<String> arr_job_location, ArrayList<String> arr_application_link, ArrayList<String> arr_company_name, ArrayList<String> arr_company_website, ArrayList<Integer> arr_featured_image) {
        this.context = applicationContext;
//        this.image = image;
        this.arr_job_title = arr_job_title;
        this.arr_job_content = arr_job_content;
        this.arr_job_location = arr_job_location;
        this.arr_application_link = arr_application_link;
        this.arr_company_name = arr_company_name;
        this.arr_company_website = arr_company_website;
        this.arr_featured_image = arr_featured_image;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return arr_job_title.size();
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflter.inflate(R.layout.jobs_item, null);
        LinearLayout card = convertView.findViewById(R.id.card);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        TextView titl = (TextView) convertView.findViewById(R.id.position);
        TextView location = (TextView) convertView.findViewById(R.id.location);
        TextView organisation = (TextView) convertView.findViewById(R.id.organisation);
        TextView programType = (TextView) convertView.findViewById(R.id.program_type);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MEDIA+"/"+arr_featured_image.get(i),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String guid = jsonObject.getString("guid");

                            JSONObject section2 = new JSONObject(guid);
                            String link = section2.getString("rendered");

                            Glide.with(context).load(link).into(imageView);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                titl.setText(Html.fromHtml(arr_job_title.get(i), Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                titl.setText(Html.fromHtml(arr_job_title.get(i)));
                            }
                            location.setText(arr_job_location.get(i));
                            organisation.setText(arr_company_name.get(i));

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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, JobViewPage.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("content", arr_job_content.get(i));
                in.putExtra("title", arr_job_title.get(i));
                in.putExtra("link", arr_featured_image.get(i));
                context.startActivity(in);
            }
        });

        return convertView;
    }
}
