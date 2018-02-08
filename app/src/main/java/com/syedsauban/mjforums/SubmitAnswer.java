package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.richeditor.RichEditor;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SubmitAnswer extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    private EditText mEditor;
    TextView questionTV;
    TextView questionDetailsTV;
    ImageButton backButton;
    Button answerButton;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    SharedPreferences prefs;
    String UserId,key,UserName;

    String questionDetailsString;
    String questionString;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


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
        setContentView(R.layout.activity_submit_answer);
        backButton=(ImageButton) findViewById(R.id.backButton);
        answerButton=(Button)findViewById(R.id.AnswerButton);
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();
        prefs = getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
        UserId=prefs.getString("UserId","");
        key=prefs.getString("key","");
        UserName=prefs.getString("name","");

        questionTV=(TextView) findViewById(R.id.questionSA);
        questionDetailsTV=(TextView)findViewById(R.id.question_detailsSA);
        questionString= getIntent().getStringExtra("questionString");
        questionDetailsString= getIntent().getStringExtra("questionDetails");
        questionTV.setText(questionString);
        questionDetailsTV.setText(questionDetailsString);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitAnswer.super.onBackPressed();
            }
        });
        mEditor = (EditText) findViewById(R.id.editor);


        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    answerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String AnswerString=mEditor.getText().toString();
                            if(AnswerString!=null&&!AnswerString.equals(""))
                            {
                                String AskerKey=getIntent().getStringExtra("UserKey");
                                ArrayList<String> tags=getIntent().getStringArrayListExtra("tags");
                                String questionKey=getIntent().getStringExtra("questionKey");
                                String DeptNameAndYear=prefs.getString("deptNameAndYear","");

                                String NameOfAsker=getIntent().getStringExtra("NameOfAsker");

                                mReference.child("Questions").child(questionKey).child("hasAnswers").setValue(true);
                                Answer newAnswer=new Answer(new Date().getTime()*-1, questionString,questionDetailsString,NameOfAsker,AnswerString,UserName,key,UserId,DeptNameAndYear,tags,1);
                                mReference.child("Questions").child(questionKey).child("Answers").child(questionKey).setValue(newAnswer);

                                mReference.child("users").child(AskerKey).child("Questions").child(questionKey).child("hasAnswers").setValue(true);
                                mReference.child("users").child(AskerKey).child("Questions").child(questionKey).child("Answers").child(questionKey).child(UserName).setValue(newAnswer);

                                mReference.child("users").child(key).child("Answers").child(questionKey).setValue(newAnswer);
                                mReference.child("users").child(key).child("Answers").child(questionKey).child("numberOfUpvotes").setValue(0);

                                for(String tag:tags)
                                {
                                    mReference.child("Tags").child(tag).child("Questions").child(questionKey).child("hasAnswers").setValue(true);
                                    mReference.child("Tags").child(tag).child("Questions").child(questionKey).child("Answers").child(questionKey).setValue(newAnswer);
                                }
                                mReference.child("Answers").child(questionKey).child(UserName).setValue(newAnswer);
                                startActivity(new Intent(SubmitAnswer.this,launching.class));
                            }
                        }
                    });
                }
                else
                {
                    finish();
                }
            }
        };
    }
}
