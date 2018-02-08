package com.syedsauban.mjforums;

/**
 * Created by Syed on 13-01-2018.
 */

public class ViewTag {
    String tag;
    int position;
    public ViewTag(String tag,int position)
    {
        this.position=position;
        this.tag=tag;
    }

    public int getPosition() {
        return position;
    }

    public String getTag() {
        return tag;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
