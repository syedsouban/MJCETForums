package com.syedsauban.mjforums;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Syed on 13-11-2017.
 */

public class MyAnswersAdapter extends RecyclerView.Adapter<MyAnswersAdapter.RecyclerViewHolder>
    {

        private ArrayList<SimpleAnswer> arrayList=new ArrayList<SimpleAnswer>();
    public MyAnswersAdapter(ArrayList<SimpleAnswer> arrayList)
        {
            this.arrayList=arrayList;
        }
        @Override
        public MyAnswersAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_answers_list_item,parent,false);
        MyAnswersAdapter.RecyclerViewHolder recyclerViewHolder=new MyAnswersAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

        @Override
        public void onBindViewHolder(MyAnswersAdapter.RecyclerViewHolder holder, int position) {
        SimpleAnswer simpleQuestion=arrayList.get(position);
        holder.question.setText(simpleQuestion.getQuestion());
        holder.answer.setText(simpleQuestion.getAnswer());
    }

        @Override
        public int getItemCount() {
        return arrayList.size();
    }
        public static class RecyclerViewHolder extends RecyclerView.ViewHolder
        {
            TextView question,answer;
            public RecyclerViewHolder(View itemView) {
                super(itemView);

                question=(TextView)itemView.findViewById(R.id.myquestionn);
                answer=(TextView)itemView.findViewById(R.id.myanswer);

            }
        }
}
