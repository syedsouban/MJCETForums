package com.syedsauban.mjforums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Syed on 25-08-2017.
 */

public class Unanswered extends android.support.v4.app.Fragment {


    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    ArrayList<Question> questions;
    RecyclerView recyclerView;
    UnasweredFeedAdapter unasweredFeedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.unanswered,container,false);
        questions=new ArrayList<>();
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerview_unanswered);

        unasweredFeedAdapter=new UnasweredFeedAdapter(questions,getActivity());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(unasweredFeedAdapter);

        Query query=mReference.child("Questions").orderByChild("timestamp");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists())
                {
                    Question question = dataSnapshot.getValue(Question.class);
//                            if(!question.isHasAnswers())
                                questions.add(question);
                                unasweredFeedAdapter.notifyDataSetChanged();


//                    for(DataSnapshot ds:dataSnapshot.getChildren())
//                    {
//                        if(ds.exists())
//                        {
//                            Question question = ds.getValue(Question.class);
//                            if(!question.isHasAnswers())
//                                questions.add(question);
//                                unasweredFeedAdapter.notifyDataSetChanged();
//                        }
//                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }
}
