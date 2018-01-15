package com.syedsauban.mjforums;

import java.util.ArrayList;

/**
 * Created by Syed on 30-10-2017.
 */

public class Tag {
    private String tagName,tagDescription;
    Tag(String tagName,String tagDescription)
    {
        this.tagName=tagName;
        this.tagDescription=tagDescription;
    }
    Tag()
    {
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public String getTagName() {
        return tagName;
    }
}
