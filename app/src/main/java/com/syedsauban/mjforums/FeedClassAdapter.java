package com.syedsauban.mjforums;

/**
 * Created by Ajaaz on 9/29/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
public class FeedClassAdapter extends RecyclerView.Adapter<FeedClassAdapter.RecyclerViewHolder>
{
    private ArrayList<FeedClass> arrayList=new ArrayList<FeedClass>();
    public FeedClassAdapter(ArrayList<FeedClass> arrayList)
    {
        this.arrayList=arrayList;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        FeedClass feedClass=arrayList.get(position);
        holder.question.setText(feedClass.getQuestion());
        holder.askedby.setText(feedClass.getAskedby());
        holder.answeredby.setText(feedClass.getAnsweredby());
        holder.answeredbyCred.setText(feedClass.getAnsweredbyCred());
        holder.answerPreview.setText(feedClass.getAnswerPreview());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageButton upvoteButton;
        ImageView answeredbyProfpic;
        TextView answer,follow,comment,readmore;
        TextView question,askedby,answeredbyCred,answeredby,answerPreview;
        public RecyclerViewHolder(View itemView) {
            super(itemView);


            question=(TextView)itemView.findViewById(R.id.question);
            askedby=(TextView)itemView.findViewById(R.id.askedBy);
            answeredby=(TextView)itemView.findViewById(R.id.answeredbyname);
            answeredbyCred=(TextView)itemView.findViewById(R.id.answerCredential);
            answerPreview=(TextView)itemView.findViewById(R.id.answerpreview);
            answeredbyProfpic=(ImageView)itemView.findViewById(R.id.answerprofile);
        }
    }
}