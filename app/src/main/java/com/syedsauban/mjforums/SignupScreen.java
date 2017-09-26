package com.syedsauban.mjforums;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignupScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        //testing

    }
    public void onLoginClicked(View view)
    {
        startActivity(new Intent(SignupScreen.this,LoginScreen.class));
    }
}
