package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserProfile extends AppCompatActivity {

    TextView userName;
    ProfilePictureView ProfilePic;

    SharedPreferences prefs;
    String UserId,key;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    Button myQuestions;
    Button myAnswers;
    URL imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
        UserId=prefs.getString("UserId","");
        key=prefs.getString("key","");
        setContentView(R.layout.activity_user_profile);
        ProfilePic=(ProfilePictureView) findViewById(R.id.profilePic);
        userName=(TextView)findViewById(R.id.name);
        myQuestions=(Button)findViewById(R.id.button2);
        myAnswers=(Button)findViewById(R.id.button3);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        dRef = firebaseDatabase.getReference("users");

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
                if(user != null) {
                    for (UserInfo userinfo : mAuth.getCurrentUser().getProviderData()) {

                        if (userinfo.getProviderId().equals("facebook.com")) {
                           ProfilePic.setProfileId(UserId);
                           userName.setText(user.getDisplayName());

                        } else if (userinfo.getProviderId().equals("google.com")
                                || userinfo.getProviderId().equals("gmail.com")) {

                        } else {

                            Query query = dRef
                                    .orderByChild("email")
                                    .equalTo(user.getEmail());

                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        User user = postSnapshot.getValue(User.class);
                                        userName.setText(user.getName());
                                        TextView tv5=(TextView)findViewById(R.id.textView5);
                                        String deptAndYear=user.getDeptName()+" "+user.getYear();
                                        tv5.setText(deptAndYear);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    myQuestions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(UserProfile.this,MyQuestions.class));
                        }
                    });
                    myAnswers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(UserProfile.this,MyAnswers.class));

                        }
                    });
                }
            }
        };
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

}

