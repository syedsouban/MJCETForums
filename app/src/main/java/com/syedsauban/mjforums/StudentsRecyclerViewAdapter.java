package com.syedsauban.mjforums;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Syed on 28-09-2017.
 */

public class StudentsRecyclerViewAdapter extends RecyclerView.Adapter<StudentsRecyclerViewAdapter.StudentsViewHolder> {

    ArrayList<Student> studentArrayList;
    StudentsRecyclerViewAdapter(ArrayList<Student> studentArrayList)
    {
        this.studentArrayList=studentArrayList;
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    @Override
    public StudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new StudentsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StudentsViewHolder holder, int position) {

        holder.RollNumberTV.setText(studentArrayList.get(position).RollNumber);
        holder.NameTV.setText(studentArrayList.get(position).Name);
    }

    class StudentsViewHolder extends RecyclerView.ViewHolder

    {
        TextView NameTV;
        TextView RollNumberTV;
        StudentsViewHolder(View view)
        {
            super(view);
            NameTV=(TextView)view.findViewById(R.id.nametv);
            RollNumberTV=(TextView)view.findViewById(R.id.rolltv);
        }

    }
}
