package com.ng.NaijaTalks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    Integer[] slides_images = {
            R.drawable.img4,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1};

    String[] word1 = {"Join the club",
            "News Feed",
            "Expert Hangout",
            "Opportunities"};

    String[] word2 = {"Come yarn your mata",
            "News wey fresh like\ntoday bread",
            "You wanna chill with\nthe big boyz",
            "Sapa go collect\nwotowoto"};

    String[] word3 = {"Give your constructive opinions in discussion forums where \nyou can share photos, videos and articles",
            "Stay uo to date with trending news and instant updates",
            "Have a live experience with Naija top experts and celebrities",
            "With our extensive partnerships, come and engage and find\nmind-blowing opportunities"};

    @Override
    public int getCount() {
        return slides_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        RelativeLayout slideImage = view.findViewById(R.id.slide_relativeLayout);
        TextView text1 = view.findViewById(R.id.text1);
        TextView text2 = view.findViewById(R.id.text2);
        TextView text3 = view.findViewById(R.id.text3);
        Button register = view.findViewById(R.id.register);

        slideImage.setBackgroundResource(slides_images[position]);
        text1.setText(word1[position]);
        text2.setText(word2[position]);
        text3.setText(word3[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }

}
