package com.syedsauban.mjforums;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAnswers extends AppCompatActivity {

    DatabaseReference mReference;
    MyAnswersAdapter myAnswersAdapter;
    ArrayList<SimpleAnswer> simpleAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answers);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.myAnswersView);
        simpleAnswers=new ArrayList<>();
        myAnswersAdapter= new MyAnswersAdapter(simpleAnswers);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(myAnswersAdapter);
        mReference= FirebaseDatabase.getInstance().getReference();



        mReference.child("users").child(getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE)
                .getString("key","")).child("Answers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        Answer answer=ds.getValue(Answer.class);
                        String questionString=answer.getQuestionString();
                        String answerString=answer.getAnswerString();
                        SimpleAnswer simpleAnswer=new SimpleAnswer(questionString,answerString);
                        simpleAnswers.add(simpleAnswer);
                        myAnswersAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
