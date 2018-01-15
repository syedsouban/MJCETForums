package com.syedsauban.mjforums;

/**
 * Created by Ajaaz on 9/29/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;

import static android.view.View.GONE;

public class FeedClassAdapter extends RecyclerView.Adapter<FeedClassAdapter.RecyclerViewHolder>
{
    static int counter=1;
    Context context;
    DatabaseReference mReference;
    String QuestionKey;
    SharedPreferences prefs;
    String userName;
    private ArrayList<Answer> arrayList=new ArrayList<Answer>();
    public FeedClassAdapter(ArrayList<Answer> arrayList,Context context)
    {
        this.context=context;
        this.arrayList=arrayList;
        prefs = context.getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view,context);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final Answer feedClass=arrayList.get(position);
        userName=prefs.getString("name","");

        QuestionKey=feedClass.getQuestionString();
        if(QuestionKey!=null)
            if(QuestionKey.contains(".")||QuestionKey.contains("#")||QuestionKey.contains("$")||QuestionKey
                    .contains("[")||QuestionKey.contains("]"))
            {
                QuestionKey=QuestionKey.replace("."," ");
                QuestionKey=QuestionKey.replace("#"," ");
                QuestionKey=QuestionKey.replace("$"," ");
                QuestionKey=QuestionKey.replace("["," ");
                QuestionKey=QuestionKey.replace("]"," ");
            }
        holder.question.setText(feedClass.getQuestionString());
        holder.askedby.setText(feedClass.getNameOfAsker());
            FirebaseDatabase.getInstance().getReference().child("Answers").child(QuestionKey).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            long numberOfAnswers=dataSnapshot.getChildrenCount();
                            if(numberOfAnswers==1)
                            {
                                holder.wrapper.setVisibility(View.VISIBLE);
                                holder.numberOfAnswers.setVisibility(GONE);

                                holder.answeredby.setText(feedClass.getAnswerWrittenBy());
                                holder.answeredbyCred.setText(feedClass.getDeptAndYear());
                                holder.answerPreview.setText(feedClass.getAnswerString());
                                holder.profilePictureView.setProfileId(feedClass.getUserId());
                            }
                            else
                            {
                                holder.wrapper.setVisibility(View.GONE);
                                holder.numberOfAnswers.setVisibility(View.VISIBLE);
                                holder.numberOfAnswers.setText(numberOfAnswers+" Answers");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );







        for(String tag:feedClass.getTags())
        {
            holder.chipCloud.addChip(tag);
        }
        mReference=FirebaseDatabase.getInstance().getReference();

        mReference.child("Questions").child(QuestionKey).child("Followers").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    holder.numberOfFollowers.setText(dataSnapshot.getChildrenCount()+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        holder.FollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b=(Button)v;
                String status=b.getText().toString();
                if(status.equals("Follow"))
                {
                    b.setText("Following");

                    mReference.child("Questions").child(QuestionKey).child("Followers").child(userName).setValue(userName);
                }
                else
                {
                    b.setText("Follow");
                    mReference.child("Questions").child(QuestionKey).child("Followers").child(userName).removeValue();
                }
                mReference.child("Questions").child(QuestionKey).child("Followers").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            holder.numberOfFollowers.setText(dataSnapshot.getChildrenCount()+"");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        mReference.child("Questions").child(QuestionKey).child("Followers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("ChildCallBacks","OnChildAddedCalled");
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        if(ds.getValue().toString().equals(userName))
                        {
                            holder.FollowButton.setText("Following");
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("ChildCallBacks","OnChildChangedCalled");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("ChildCallBacks","OnChildRemovedCalled");
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        if(ds.getValue().toString().equals(userName))
                        {
                            holder.FollowButton.setText("Follow");
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("ChildCallBacks","OnChildMovedCalled");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.UpVoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(counter%2==0) {
                    ((ImageButton) v).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.upvoted));

                }
                    else
                    ((ImageButton) v).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.upvoted));
                counter++;
            }
        });

        holder.answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer thisQuestion=arrayList.get(position);
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
                String questionDetails=thisQuestion.getQuestionDetailsString();
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

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        com.cunoraz.tagview.TagView tagView;
        ImageButton upvoteButton;
        Context context;
        ImageView answeredbyProfpic;
        TextView answer,follow,comment,readmore;
        FlexboxLayout flexbox;
        ChipCloud chipCloud;
        ProfilePictureView profilePictureView;
        TextView question,askedby,answeredbyCred,answeredby,answerPreview;
        RelativeLayout wrapper;
        Button numberOfAnswers;
        Button FollowButton;
        ImageButton UpVoteButton;
        ImageButton answerButton;
        TextView numberOfFollowers;
        public RecyclerViewHolder(View itemView,Context context) {
            super(itemView);
            this.context=context;
            UpVoteButton=(ImageButton)itemView.findViewById(R.id.thumbs_up);
            profilePictureView=(ProfilePictureView)itemView.findViewById(R.id.profilePicCustomView);
            question=(TextView)itemView.findViewById(R.id.question);
            askedby=(TextView)itemView.findViewById(R.id.deptandyear);
            flexbox = (FlexboxLayout) itemView.findViewById(R.id.tagViewNW);
            answeredby=(TextView)itemView.findViewById(R.id.answeredbyname);
            answeredbyCred=(TextView)itemView.findViewById(R.id.answerCredential);
            answerPreview=(TextView)itemView.findViewById(R.id.answerpreview);
            wrapper=(RelativeLayout)itemView.findViewById(R.id.wrapper);
            numberOfAnswers=(Button)itemView.findViewById(R.id.numberOfAnswers);
            FollowButton=(Button)itemView.findViewById(R.id.followButtonUA);
            answerButton=(ImageButton)itemView.findViewById(R.id.answerButtonUA);
            numberOfFollowers=(TextView)itemView.findViewById(R.id.numberOfFollowers);


            ChipCloudConfig config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.multi)
                    .checkedChipColor(Color.parseColor("#008FFE"))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#008FFE"))
                    .uncheckedTextColor(Color.parseColor("#ffffff"))
                    .useInsetPadding(true);

            chipCloud = new ChipCloud(context, flexbox,config);

        }
    }
}