package com.syedsauban.mjforums;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter<StudentsRecyclerViewAdapter.StudentsViewHolder> adapter;
    ArrayList<Student> studentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_roll_recy);
        studentArrayList=new ArrayList<Student>();
        String[] studentNames=getResources().getStringArray(R.array.students);
        String[] studentRollNumbers=getResources().getStringArray(R.array.rollnumber_of_students);
        for(int i=0;i<studentNames.length;i++)
            studentArrayList.add(new Student(studentNames[i],studentRollNumbers[i]));
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        adapter=new StudentsRecyclerViewAdapter(studentArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
