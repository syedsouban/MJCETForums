package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeptAndYearActivity extends AppCompatActivity {

    Spinner deptNameSpinner,yearSpinner;
    String deptName,yearName;

    SharedPreferences prefs;
    String UserId,key;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    startActivity(new Intent(DeptAndYearActivity.this,LoginScreen.class));
                    // User is signed out
                    Log.d("MainActivity", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        prefs = getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
        UserId=prefs.getString("UserId","");
        key=prefs.getString("key","");


        setContentView(R.layout.activity_dept_and_year);

        FirebaseDatabase.getInstance().getReference().child("users").child(key).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                String name=dataSnapshot.child("name").getValue().toString();
                                prefs.edit().putString("name",name).apply();
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        deptNameSpinner=(Spinner)findViewById(R.id.department);
        yearSpinner=(Spinner)findViewById(R.id.year);
        ArrayList<String> deptNames=new ArrayList<>();
        deptNames.add("CSE");
        deptNames.add("ECE");
        deptNames.add("Mech");
        deptNames.add("Civil");
        deptNames.add("EEE");
        deptNames.add("IT");
        deptNames.add("EIE");
        deptNames.add("Prod");
        deptNames.add("Other");

        ArrayAdapter<String> deptAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,deptNames);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptNameSpinner.setAdapter(deptAdapter);

        deptNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptName=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DeptAndYearActivity.this, "Please select one of the choices", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> yearList=new ArrayList<>();
        yearList.add("I Year");
        yearList.add("II Year");
        yearList.add("III Year");
        yearList.add("IV Year");
        yearList.add("Alumni");
        yearList.add("Other");

        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearName=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DeptAndYearActivity.this, "Please select one of the choices", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onContinueClicked(View v)
    {
        if(yearName!=null&&deptName!=null)
        {
            FirebaseDatabase.getInstance().getReference().child("users").child(key).
                    child("deptName").setValue(deptName);
            FirebaseDatabase.getInstance().getReference().child("users").child(key).
                    child("year").setValue(yearName);
            FirebaseDatabase.getInstance().getReference().child("users").child(key).
                    child("isDAYset").setValue("true");
            prefs.edit().putString("deptNameAndYear",deptName+" "+yearName).apply();
            startActivity(new Intent(this,launching.class));

        }
    }
}
