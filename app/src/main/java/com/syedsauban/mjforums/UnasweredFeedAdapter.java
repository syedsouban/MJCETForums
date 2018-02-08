package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;

/**
 * Created by Syed on 04-11-2017.
 */

public class UnasweredFeedAdapter extends RecyclerView.Adapter<UnasweredFeedAdapter.RecyclerViewHolder>

{

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    SharedPreferences prefs;
    String userName;

    private ArrayList<Question> arrayList=new ArrayList<Question>();
    String QuestionKey;
    Context context;

    public UnasweredFeedAdapter(ArrayList<Question> arrayList, Context context)
    {

        this.context=context;
        this.arrayList=arrayList;
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();
        prefs = context.getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
    }

    @Override
    public UnasweredFeedAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unanswered_custom_view,parent,false);
        UnasweredFeedAdapter.RecyclerViewHolder recyclerViewHolder=new UnasweredFeedAdapter.RecyclerViewHolder(view,context);
        return recyclerViewHolder;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final UnasweredFeedAdapter.RecyclerViewHolder holder, final int position) {
        int count=0;

        userName=prefs.getString("name","");
        Question question=arrayList.get(position);
        String QuestionKeey;
        holder.question.setText(question.getQuestionString());
        holder.askedby.setText("Asked By "+question.getNameOfAsker());
        holder.questionDetails.setText(question.getQuestionDetails());

        QuestionKeey=question.getQuestionString();
        if(QuestionKeey.contains(".")||QuestionKeey.contains("#")||QuestionKeey.contains("$")||QuestionKeey
                .contains("[")||QuestionKeey.contains("]"))
        {
            QuestionKeey=QuestionKeey.replace("."," ");
            QuestionKeey=QuestionKeey.replace("#"," ");
            QuestionKeey=QuestionKeey.replace("$"," ");
            QuestionKeey=QuestionKeey.replace("["," ");
            QuestionKeey=QuestionKeey.replace("]"," ");
        }

        final String finalQuestionKeey = QuestionKeey;
        FirebaseDatabase.getInstance().getReference().child("Questions").
                child(finalQuestionKeey).child("Followers")
                .addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists())
                {
                    Log.v("onChildAdded",dataSnapshot.toString());
                    if(dataSnapshot.getValue().equals(userName))
                    {
                        Log.v("userFollowing",holder.question.getText().toString());
                        holder.FollowButton.setChecked(true);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Log.v("onChildRemoved",dataSnapshot.toString());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.FollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("FollowButtonClicked","Question: "+ finalQuestionKeey);

                String ToggleButtonState =((ToggleButton) v).getText().toString();
                if(ToggleButtonState.equals("Follow"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Questions")
                            .child(finalQuestionKeey).child("Followers")
                            .child(userName).removeValue();
                }
                else if(ToggleButtonState.equals("Following"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Questions")
                            .child(finalQuestionKeey).child("Followers")
                            .child(userName).setValue(userName);
                }
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Questions")
                .child(finalQuestionKeey).child("Followers").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        holder.numberOfFollowers.setText(dataSnapshot.getChildrenCount()+"");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        for(String tag:question.getTags())
        {
            holder.chipCloud.addChip(tag);
        }
                holder.answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question thisQuestion=arrayList.get(position);
                QuestionKey=thisQuestion.getQuestionString();
                if(QuestionKey.contains(".")||QuestionKey.contains("#")||QuestionKey.contains("$")||QuestionKey
                        .contains("[")||QuestionKey.contains("]"))
                {
                    QuestionKey=QuestionKey.replace("."," ");
                    QuestionKey=QuestionKey.replace("#"," ");
                    QuestionKey=QuestionKey.replace("$"," ");
                    QuestionKey=QuestionKey.replace("["," ");
                    QuestionKey=QuestionKey.replace("]"," ");
                }
                Log.d("MyTest",thisQuestion.getQuestionString()+" "+thisQuestion.getUserKey());
                String questionString =thisQuestion.getQuestionString();
                String questionDetails=thisQuestion.getQuestionDetails();
                String questionAskedBy=thisQuestion.getNameOfAsker();
                String UserKey=thisQuestion.getUserKey();
                ArrayList<String> tags=new ArrayList<String>();
                tags=thisQuestion.getTags();
                Intent intent=new Intent(context,SubmitAnswer.class);
                intent.putExtra("questionKey",QuestionKey);
                intent.putExtra("questionString",questionString);
                intent.putExtra("questionDetails",questionDetails);
                intent.putExtra("UserKey",UserKey);
                intent.putExtra("NameOfAsker",questionAskedBy);
                intent.putStringArrayListExtra("tags",tags);
                context.startActivity(intent);
            }
        });

//        DatabaseReference followersRef=mReference.child("Questions").child(QuestionKey).child("Followers");
//
//        final ChildEventListener followersListener=new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                if(dataSnapshot.exists())
//                {
//                    holder.numberOfFollowers.setText(dataSnapshot.getChildrenCount()+"");
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                if(dataSnapshot.exists())
//                {
//                    holder.numberOfFollowers.setText(dataSnapshot.getChildrenCount()+"");
//                }
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    holder.numberOfFollowers.setText(dataSnapshot.getChildrenCount()+"");
//                }
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//
//        ChildEventListener followersCHL=new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.d("ChildCallBacks","OnChildAddedCalled");
//                if(dataSnapshot.exists())
//                {
//                    for(DataSnapshot ds:dataSnapshot.getChildren())
//                    {
//                        if(ds.getValue().toString().equals(userName))
//                        {
//                            holder.FollowButton.setText("Following");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.d("ChildCallBacks","OnChildChangedCalled");
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d("ChildCallBacks","OnChildRemovedCalled");
//                if(dataSnapshot.exists())
//                {
//                    for(DataSnapshot ds:dataSnapshot.getChildren())
//                    {
//                        if(ds.getValue().toString().equals(userName))
//                        {
//                            holder.FollowButton.setText("Follow");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                Log.d("ChildCallBacks","OnChildMovedCalled");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mReference.child("Questions").child(QuestionKey).child("Followers").addChildEventListener(followersCHL);
//        followersRef.addChildEventListener(followersListener);
//
//
//
//        holder.FollowButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Button b=(Button)v;
//                String status=b.getText().toString();
//
//                String QuestionKey=question.getQuestionString();
//                if(status.equals("Follow"))
//                {
//                    b.setText("Following");
//
//                    mReference.child("Questions").child(QuestionKey).child("Followers").child(userName).setValue(userName);
//                }
//                else
//                {
//                    b.setText("Follow");
//                    mReference.child("Questions").child(QuestionKey).child("Followers").child(userName).removeValue();
//                }
//
//
//                }
//        });
//        for(String tag:question.getTags())
//        {
//            holder.chipCloud.addChip(tag);
//        }
//
//        holder.answerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Question thisQuestion=arrayList.get(position);
//                QuestionKey=thisQuestion.getQuestionString();
//                if(QuestionKey.contains(".")||QuestionKey.contains("#")||QuestionKey.contains("$")||QuestionKey
//                        .contains("[")||QuestionKey.contains("]"))
//                {
//                    QuestionKey=QuestionKey.replace("."," ");
//                    QuestionKey=QuestionKey.replace("#"," ");
//                    QuestionKey=QuestionKey.replace("$"," ");
//                    QuestionKey=QuestionKey.replace("["," ");
//                    QuestionKey=QuestionKey.replace("]"," ");
//                }
//                Log.d("MyTest",thisQuestion.getQuestionString()+" "+thisQuestion.getUserKey());
//                String questionString =thisQuestion.getQuestionString();
//                String questionDetails=thisQuestion.getQuestionDetails();
//                String questionAskedBy=thisQuestion.getNameOfAsker();
//                String UserKey=thisQuestion.getUserKey();
//                ArrayList<String> tags=new ArrayList<String>();
//                tags=thisQuestion.getTags();
//                Intent intent=new Intent(context,SubmitAnswer.class);
//                intent.putExtra("questionKey",QuestionKey);
//                intent.putExtra("questionString",questionString);
//                intent.putExtra("questionDetails",questionDetails);
//                intent.putExtra("UserKey",UserKey);
//                intent.putExtra("NameOfAsker",questionAskedBy);
//                intent.putStringArrayListExtra("tags",tags);
//                context.startActivity(intent);
//            }
//        });
//        followersRef.removeEventListener(followersListener);
//        followersRef.removeEventListener(followersCHL);
//        followersRef.removeEventListener(followersListener);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {


        Context context;
        TextView question,askedby,questionDetails,numberOfFollowers;
        FlexboxLayout flexbox;
        ChipCloud chipCloud;
        ImageButton answerButton;
        ToggleButton FollowButton;
        public RecyclerViewHolder(View itemView,Context context) {
            super(itemView);

            this.context=context;
            question=(TextView)itemView.findViewById(R.id.questionUA);
            askedby=(TextView)itemView.findViewById(R.id.question_asked_byUA);
            flexbox = (FlexboxLayout) itemView.findViewById(R.id.tagViewUA);
            FollowButton=(ToggleButton) itemView.findViewById(R.id.followButtonUA);
            answerButton=(ImageButton)itemView.findViewById(R.id.answerButtonUA);
            numberOfFollowers=(TextView)itemView.findViewById(R.id.numberOfFollowers);
//Create a new ChipCloud with a Context and ViewGroup:


            ChipCloudConfig config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.multi)
                    .checkedChipColor(Color.parseColor("#B1DDFF"))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#AED374"))
                    .uncheckedTextColor(Color.parseColor("#ffffff"))
                    .useInsetPadding(true);

            chipCloud = new ChipCloud(context, flexbox,config);

            questionDetails=(TextView)itemView.findViewById(R.id.question_detailsUA);

        }
    }
}

