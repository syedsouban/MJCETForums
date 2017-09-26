package com.syedsauban.mjforums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syedsauban.mjforums.R;

/**1
 * Created by Syed on 26-08-2017.
 */

public class Bookmarks extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmarks,container,false);
    }
}
