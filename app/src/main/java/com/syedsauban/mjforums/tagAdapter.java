package com.syedsauban.mjforums;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Syed on 31-10-2017.
 */

public class tagAdapter extends ArrayAdapter {

    public tagAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int[] RandomColorList=new int[]{
                ContextCompat.getColor(getContext(),R.color.red_A700),
                ContextCompat.getColor(getContext(),R.color.purple_A700),
                ContextCompat.getColor(getContext(),R.color.purple_A400),
                ContextCompat.getColor(getContext(),R.color.light_blue_A700),
                ContextCompat.getColor(getContext(),R.color.light_blue_A400),
                ContextCompat.getColor(getContext(),R.color.green_A700),
                ContextCompat.getColor(getContext(),R.color.green_A400),
                ContextCompat.getColor(getContext(),R.color.deep_orange_400),
                ContextCompat.getColor(getContext(),R.color.pink_400),
                ContextCompat.getColor(getContext(),R.color.purple_A700),
                ContextCompat.getColor(getContext(),R.color.indigo_A700),
                ContextCompat.getColor(getContext(),R.color.indigo_200),
                ContextCompat.getColor(getContext(),R.color.cyan_A700),
        };
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.tag_element,parent,false);
        }


        Random random=new Random();

        int randomNumber=random.nextInt(RandomColorList.length);
        String tagName = getItem(position).toString();
        TextView textView=(TextView)convertView.findViewById(R.id.tag_tv);
        textView.setText(tagName);
        TextView textView1=(TextView)convertView.findViewById(R.id.initialTV);
        GradientDrawable tagCircle = (GradientDrawable) textView1.getBackground();
        tagCircle.setColor(RandomColorList[randomNumber]);
        if (tagName != null) {
            textView1.setText(tagName.charAt(0)+"");
        }
        return convertView;
    }
}
