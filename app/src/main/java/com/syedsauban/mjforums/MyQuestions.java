package com.syedsauban.mjforums;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyQuestions extends AppCompatActivity {

    DatabaseReference mReference;
    MyQuestionsAdapter myQuestionsAdapter;
    ArrayList<SimpleQuestion> simpleQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.myQuestionsView);
        simpleQuestions=new ArrayList<>();
        myQuestionsAdapter= new MyQuestionsAdapter(simpleQuestions);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(myQuestionsAdapter);
        mReference= FirebaseDatabase.getInstance().getReference();
        mReference.child("users").child(getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE)
        .getString("key","")).child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        Log.v("MyQuestions",ds.getKey());
                        Question question=ds.getValue(Question.class);
                        String questionString=ds.getKey();
                        String questionDetails=question.getQuestionDetails();
                        Log.v("QuestionDetails",questionDetails);
                        SimpleQuestion simpleQuestion=new SimpleQuestion(questionString,questionDetails);
                        simpleQuestions.add(simpleQuestion);
                        myQuestionsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
