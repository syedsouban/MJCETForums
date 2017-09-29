package com.syedsauban.mjforums;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Syed on 25-08-2017.
 */

public class Newest extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.newest, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewinfeed);
        ArrayList<FeedClass> a1 = new ArrayList<FeedClass>();
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        FeedClassAdapter adapter = new FeedClassAdapter(a1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
