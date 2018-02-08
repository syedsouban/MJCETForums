package com.syedsauban.mjforums;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Syed on 13-11-2017.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyQuestionsAdapter extends RecyclerView.Adapter<MyQuestionsAdapter.RecyclerViewHolder>
{

    private ArrayList<SimpleQuestion> arrayList=new ArrayList<SimpleQuestion>();
    public MyQuestionsAdapter(ArrayList<SimpleQuestion> arrayList)
    {
        this.arrayList=arrayList;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.my_questions_list_item,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        SimpleQuestion simpleQuestion=arrayList.get(position);
        holder.question.setText(simpleQuestion.getQuestion());
        holder.questionDetails.setText(simpleQuestion.getQuestionDetails());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView question,questionDetails;
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            question=(TextView)itemView.findViewById(R.id.myquestionn);
            questionDetails=(TextView)itemView.findViewById(R.id.myquestiondetails);

        }
    }
}
