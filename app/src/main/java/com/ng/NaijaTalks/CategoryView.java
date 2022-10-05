package com.ng.NaijaTalks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ng.NaijaTalks.adapters.IndividualResourcesAdapter;

import java.util.ArrayList;

public class CategoryView extends AppCompatActivity {

    ImageView back;
    TextView category_view, nopost;
    GridView gridView;

    //category array
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Integer> arr_category_id = new ArrayList<>();

    //post array
    ArrayList<Integer> arr_post_id = new ArrayList<>();
    ArrayList<String> arr_post_date = new ArrayList<>();
    ArrayList<String> arr_post_title = new ArrayList<>();
    ArrayList<String> arr_post_content = new ArrayList<>();
    ArrayList<String> arr_post_image_link = new ArrayList<>();
    ArrayList<Integer> arr_post_category_id = new ArrayList<>();

    //dom array
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayList<Integer> category_id = new ArrayList<>();
    String clicked_category_id;
    String email;
    Dialog myDialog;
    TextView login, register;
    int clicked_id;

    int Array_length_category;
    int Array_length_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        gridView = findViewById(R.id.gridview);
        nopost = findViewById(R.id.nopost);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        Intent i = getIntent();
        categories = i.getStringArrayListExtra("categories");
        arr_category_id = i.getIntegerArrayListExtra("category_id");
        arr_post_id = i.getIntegerArrayListExtra("post_id");
        arr_post_date = i.getStringArrayListExtra("post_date");
        arr_post_title = i.getStringArrayListExtra("post_title");
        arr_post_content = i.getStringArrayListExtra("post_content");
        arr_post_image_link = i.getStringArrayListExtra("post_image_link");
        arr_post_category_id = i.getIntegerArrayListExtra("post_category_id");
        clicked_category_id = i.getStringExtra("clicked_category_id");
        clicked_id = i.getIntExtra("clicked_id", 0);
        email = i.getStringExtra("email");

        category_view = findViewById(R.id.category_name);
        category_view.setText(clicked_category_id);

        System.out.println("Categories= "+categories+"\nCategory_id= "+arr_category_id+"\nPost_id= "+arr_post_id+
                "\nPost_date= "+arr_post_date+"\nPost_title= "+arr_post_title+"\nPost_content= "+arr_post_content+"\nImage_link= "+arr_post_image_link+
                "\nPost_category_id= "+arr_post_category_id+"\nClicked_category_id= "+clicked_category_id+"\nClicked_id= "+clicked_id);

        //populate listview
        Array_length_category = arr_category_id.size();
        Array_length_post = arr_post_id.size();

        if(email.isEmpty()){
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);

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
        }
        else{
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);

            for(int j=0; j<Array_length_post; j++){
                if(arr_post_category_id.get(j) == clicked_id){
                    String post_date = arr_post_date.get(j);
                    String post_title = arr_post_title.get(j);
                    String post_image = arr_post_image_link.get(j);
                    String post_content = arr_post_content.get(j);
                    int post_category_id = arr_post_category_id.get(j);

                    date.add(post_date);
                    title.add(post_title);
                    image.add(post_image);
                    content.add(post_content);
//                category_id.add(post_category_id);
                    category_id.add(clicked_id);
                }
            }

            if(date.size() == 0){
                nopost.setVisibility(View.VISIBLE);
            }else{
                IndividualResourcesAdapter myAdapter=new IndividualResourcesAdapter(getApplicationContext(),date,title,image,content,category_id, email);
                gridView.setAdapter(myAdapter);
            }
        }

    }
}