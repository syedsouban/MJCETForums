package com.syedsauban.mjforums;

/**
 * Created by Syed on 28-08-2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomTabLayout extends TabLayout {
    Context mContext;
    private Typeface mTypeface;

    public CustomTabLayout(Context context) {

        super(context);
        mContext=context;
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private void init() {
        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/tabfont_bold.ttf");
    }
    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    @Override
    public void addTab(Tab tab,int p,boolean isS) {
        super.addTab(tab,p,isS);

        ViewGroup mainView = (ViewGroup) getChildAt(0);

        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());
        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                ((TextView) tabViewChild).setTextSize(pxFromDp(30,mContext));
            }
        }
    }

}