package com.syedsauban.mjforums;

/**
 * Created by Ajaaz on 9/29/2017.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newest);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerviewinfeed);
        ArrayList<FeedClass> a1 = new ArrayList<FeedClass>();
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        FeedClassAdapter adapter = new FeedClassAdapter(a1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}