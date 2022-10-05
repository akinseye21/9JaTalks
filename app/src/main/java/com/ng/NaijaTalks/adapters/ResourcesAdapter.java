package com.ng.NaijaTalks.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ng.NaijaTalks.CategoryView;
import com.ng.NaijaTalks.Dashboard;
import com.ng.NaijaTalks.MainActivity;
import com.ng.NaijaTalks.R;
import com.ng.NaijaTalks.Register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResourcesAdapter extends BaseAdapter {

    Context context;
    //category array
    ArrayList<String> categories;
    ArrayList<Integer> arr_category_id;

    //post array
    ArrayList<Integer> arr_post_id;
    ArrayList<String> arr_post_date;
    ArrayList<String> arr_post_title;
    ArrayList<String> arr_post_content;
    ArrayList<String> arr_post_image_link;
    ArrayList<Integer> arr_post_category_id;
    String email;
    LayoutInflater inflter;
    Dialog myDialog;

    public ResourcesAdapter(Context applicationContext, ArrayList<String> categories, ArrayList<Integer> arr_category_id,
                            ArrayList<Integer> arr_post_id, ArrayList<String> arr_post_date, ArrayList<String> arr_post_title, ArrayList<String> arr_post_content,
                            ArrayList<String> arr_post_image_link, ArrayList<Integer> arr_post_category_id, String email) {
        this.context = applicationContext;
        this.categories = categories;
        this.arr_category_id = arr_category_id;
        this.arr_post_id = arr_post_id;
        this.arr_post_date = arr_post_date;
        this.arr_post_title = arr_post_title;
        this.arr_post_content = arr_post_content;
        this.arr_post_image_link = arr_post_image_link;
        this.arr_post_category_id = arr_post_category_id;
        this.email = email;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categories.size();
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

        convertView = inflter.inflate(R.layout.resources_view, null); // inflate the layout
        TextView categoryName = (TextView) convertView.findViewById(R.id.category_name);
        Button btn_title = (Button) convertView.findViewById(R.id.btn_title);
        LinearLayout show = (LinearLayout) convertView.findViewById(R.id.show);
//        TextView txt1 = (TextView) convertView.findViewById(R.id.tx1);
//        TextView txt2 = (TextView) convertView.findViewById(R.id.tx2);
//        TextView txt3 = (TextView) convertView.findViewById(R.id.tx3);
//
//        ImageView img1 = (ImageView) convertView.findViewById(R.id.img1);
//        ImageView img2 = (ImageView) convertView.findViewById(R.id.img2);
//        ImageView img3 = (ImageView) convertView.findViewById(R.id.img3);
//
//        RelativeLayout rel1 = (RelativeLayout) convertView.findViewById(R.id.rel1);
//        RelativeLayout rel2 = (RelativeLayout) convertView.findViewById(R.id.rel2);
//        RelativeLayout rel3 = (RelativeLayout) convertView.findViewById(R.id.rel3);

        int[] rand_color = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
                R.color.purple_200, R.color.purple_500, R.color.purple_700, R.color.black,
                R.color.md_red_50, R.color.md_red_100, R.color.md_red_200, R.color.md_red_300,
                R.color.md_red_400, R.color.md_red_500, R.color.md_red_600, R.color.md_red_700,
                R.color.md_red_800, R.color.md_red_900, R.color.md_pink_50, R.color.md_pink_100,
                R.color.md_pink_200, R.color.md_pink_300, R.color.md_pink_400, R.color.md_pink_500,
                R.color.teal_700, R.color.teal_200, R.color.md_teal_50, R.color.md_teal_500};

        Random myRand = new Random();
        int randNum1 = myRand.nextInt(rand_color.length);
//        btn_title.setBackgroundResource(rand_color[randNum1]);
//        btn_title.setBackgroundResource(rand_color[randNum1]);
        categoryName.setText(categories.get(position));
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putStringArrayListExtra("categories", categories);
                intent.putIntegerArrayListExtra("category_id", arr_category_id);
                intent.putIntegerArrayListExtra("post_id", arr_post_id);
                intent.putStringArrayListExtra("post_date", arr_post_date);
                intent.putStringArrayListExtra("post_title", arr_post_title);
                intent.putStringArrayListExtra("post_content", arr_post_content);
                intent.putStringArrayListExtra("post_image_link", arr_post_image_link);
                intent.putIntegerArrayListExtra("post_category_id", arr_post_category_id);
                intent.putExtra("clicked_category_id", categories.get(position));
                intent.putExtra("clicked_id", arr_category_id.get(position));
                intent.putExtra("email", email);
                context.startActivity(intent);
            }
        });
        return convertView;

    }

    public ResourcesAdapter(Context context) {
        super();
        this.context = context;
    }

}