package com.syedsauban.mjforums;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Syed on 27-08-2017.
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, String fontName) {
        super(context);
        init(fontName);
    }


    public void init(String fontName) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/"+fontName+".ttf");
        setTypeface(tf ,1);
    }
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/tabfont.ttf");
        setTypeface(tf ,1);
    }
}
