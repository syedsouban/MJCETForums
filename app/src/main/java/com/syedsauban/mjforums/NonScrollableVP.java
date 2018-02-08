package com.syedsauban.mjforums;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Syed on 11-11-2017.
 */

public class NonScrollableVP extends ViewPager {
    public NonScrollableVP(Context context) {
        super(context);
    }
    public NonScrollableVP(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
    @Override public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages return false;
        return false;
    }
}