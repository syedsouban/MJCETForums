package com.syedsauban.mjforums;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturedQuestions extends Fragment {


    public FeaturedQuestions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView= inflater.inflate(R.layout.fragment_featured_questions, container, false);




    return rootView;
    }


}
