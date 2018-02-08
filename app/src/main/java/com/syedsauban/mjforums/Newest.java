package com.syedsauban.mjforums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Syed on 25-08-2017.
 */

public class Newest extends android.support.v4.app.Fragment {

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    ArrayList<Answer> answers;
    RecyclerView recyclerView;
    FeedClassAdapter feedClassAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.newest, container, false);
        answers=new ArrayList<>();
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerviewinfeed);

        feedClassAdapter=new FeedClassAdapter(answers,getActivity());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(feedClassAdapter);

        Query query=mReference.child("Answers");




        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        if(ds.exists())
                        {
//                            Answer displayAnswer=new Answer();
//                            Answer answer=new Answer();
                            Log.v("dsInString",ds.toString());
                            Log.v("dsInString2",ds.getChildren().iterator().next().child("nameOfAsker").toString());
                            long max=0;
                            long numberOfUpvotes=0;
//                            max=(Integer) ds.getChildren().iterator().next().child("numberOfUpvotes")
//                                    .getValue();
                            Answer answer = new Answer();
                            Answer displayAnswer=new Answer();
                            String displayAnswerString="empty";
                            for(DataSnapshot answerChild: ds.getChildren()) {
                                if (answerChild.exists()) {
                                    numberOfUpvotes = answerChild.child("Upvoters").getChildrenCount();
                                    Log.v("NumberOfUpvotes",answerChild.child("Upvoters").getChildrenCount()+"");
                                    Log.v("NewestDS", answerChild.getValue(Answer.class).getAnswerString()+ "");
                                    if (max <= numberOfUpvotes) {
                                        max = numberOfUpvotes;
                                        displayAnswerString = answerChild.child("answerString")
                                                .getValue().toString();
                                        displayAnswer=answerChild.getValue(Answer.class);
                                    }
                                }
                                Log.v("InAnsWB,max",displayAnswer.getAnswerWrittenBy()+","+max);
                            }

                        Log.v("OutAnsWB,max",displayAnswer.getAnswerWrittenBy()+","+max);

                            answers.add(displayAnswer);
                            feedClassAdapter.notifyDataSetChanged();



                            //
//     if(max<numberOfUpvotes)
//                                {
//                                    max=numberOfUpvotes;
//                                    displayAnswer= answerChild.getValue(Answer.class);
//
//                                }
//                            }
//                            Log.v("DisplayAnswer",displayAnswer.getAnswerString());
//                                answers.add(displayAnswer);
//                                feedClassAdapter.notifyDataSetChanged();
//
//////                            Answer displayAnswer=new Answer();
//////                            int max=0;
//////                            int numberOfUpvotes;
////
////                            Answer answer=ds.getChildren().iterator().next().getValue(Answer.class);
////
////                            max=answer.getNumberOfUpvotes();
////                            while (ds.getChildren().iterator().hasNext())
////                            {
////                                answer=ds.getChildren().iterator().next().getValue(Answer.class);
////                                numberOfUpvotes=answer.getNumberOfUpvotes();
////                                if(max<numberOfUpvotes)
////                                {
////                                    max=numberOfUpvotes;
////                                    displayAnswer= answer;
////                                }
////                            }
//                            Log.v("NumberOfUpvotes",max+"");
////                            if (ds.getChildrenCount()==1)
////                            {
//                                Log.d("Testting",ds.getChildren().iterator().next().getValue(Answer.class).getAnswerString());
////                                Answer answer = ds.getChildren().iterator().next().getValue(Answer.class);
//                                answers.add(displayAnswer);
//
//                                feedClassAdapter.notifyDataSetChanged();
//                            }
//                            else
//                            {
//                                Answer answer = ds.getChildren().iterator().next().getValue(Answer.class);
//                                Answer noAnswer=new Answer(answer.getTimestamp(),answer.getQuestionString(),answer.getQuestionDetailsString(),answer.getNameOfAsker(),answer.getTags());
//                                answers.add(noAnswer);
//                                feedClassAdapter.notifyDataSetChanged();
//                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}

