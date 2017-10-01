package com.syedsauban.mjforums;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
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
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "To spot gravitational waves directly for the first time ever, scientists had to measure a distance change 1,000 times smaller than the width of a proton.  Researchers with the Laser Interferometer Gravitational-Wave Observatory (LIGO) announced today (Feb. 11) that they had made history's first direct detection of gravitational waves, enig", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("What is the best thing that you read today?", "naja naja", "diche", "I read this news in LinkedIn today, and this was posted by Mr.Vivek Agarwal, CFO & VP finance, Jindal Steel & Power Ltd."
                +"On my way I found a piece of paper tied to an electric pole, with a small note on it. I was curious to know what was written, hence went closer and read it. I lost Rs. 50 somewhere on the road. If any of you fi" + "nd it, please give it to me at this address. My vision isn't great so please help. " +
                "I was to follow that address and found an old hut in shatters with an older woman sitting outside. She was frail and asked who is it, following my footsteps. I said I came by this way, found Rs. 50 on the road so wanted to handover to you. She began crying on hearing this. She said my dear I have had atleast 30-40 people come over and give me Rs. 50 " +
                "saying they found it on the road. I didn't write that note; I cannot even see properly nor do i know to read and write I said its OK amma you keep it. She asked me to tear that note off on my way back. I walked back with a million thoughts as to who could have written that note? She would have asked everyone to tear that note but none did. I mentally thanked " +
                "that person and realised that we just have to feel the need to help, there are many ways to do.He/she just wanted to help this old woman. " +
                "Someone stopped me and asked, Bro, can you help me with this address? I found a 50 rupees note, want to handover.","Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "To spot gravitational waves directly for the first time ever, scientists had to measure a distance change 1,000 times smaller than the width of a proton.  Researchers with the Laser Interferometer Gravitational-Wave Observatory (LIGO) announced today (Feb. 11) that they had made history's first direct detection of gravitational waves, enig", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        a1.add(new FeedClass("who is hod", "Ajaz", "Souban", "Our hod is Moiz Qayser", "Intern at Chronology", R.drawable.answer_profile));
        FeedClassAdapter adapter = new FeedClassAdapter(a1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}

