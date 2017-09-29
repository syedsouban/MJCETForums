package com.syedsauban.mjforums;

/**
 * Created by Ajaaz on 9/29/2017.
 */
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newest);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerviewinfeed);
        ArrayList<FeedClass> a1 = new ArrayList<FeedClass>();
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "To spot gravitational waves directly for the first time ever, scientists had to measure a distance change 1,000 times smaller than the width of a proton.  Researchers with the Laser Interferometer Gravitational-Wave Observatory (LIGO) announced today (Feb. 11) that they had made history's first direct detection of gravitational waves, enig", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "To spot gravitational waves directly for the first time ever, scientists had to measure a distance change 1,000 times smaller than the width of a proton.  Researchers with the Laser Interferometer Gravitational-Wave Observatory (LIGO) announced today (Feb. 11) that they had made history's first direct detection of gravitational waves, enig", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "To spot gravitational waves directly for the first time ever, scientists had to measure a distance change 1,000 times smaller than the width of a proton.  Researchers with the Laser Interferometer Gravitational-Wave Observatory (LIGO) announced today (Feb. 11) that they had made history's first direct detection of gravitational waves, enig", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        FeedClassAdapter adapter = new FeedClassAdapter(a1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}