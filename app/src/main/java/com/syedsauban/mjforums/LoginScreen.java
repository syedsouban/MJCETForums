package com.syedsauban.mjforums;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText emailEditText,passwordEditText;
    Button login;
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
        setContentView(R.layout.login_screen);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        login=(Button)findViewById(R.id.signup_button);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if(user.isEmailVerified())
                    {
                     startActivity(new Intent(LoginScreen.this,launching.class));
                    }
                    else
                        Toast.makeText(LoginScreen.this, "Your Email is not verified", Toast.LENGTH_SHORT).show();
                    // User is signed in
//                    Log.d("LoginScreen", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
//                    startActivity(new Intent(LoginScreen.this,MainActivity.class));
                    // User is signed out
//                    Log.d("LoginScreen", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                if (email != null && password != null) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginScreen.this, "Logged In successfully, Checking for email verification...", Toast.LENGTH_SHORT).show();
                                            }
                                            if (!task.isSuccessful()) {
//                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                                Toast.makeText(LoginScreen.this, R.string.auth_failed,
                                                        Toast.LENGTH_SHORT).show();
                                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                                Toast.makeText(LoginScreen.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            // ...
                                        }
                                    });
            }
            }
        });


        callbackManager=CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Intent test=new Intent(LoginScreen.this,Test.class);

                test.putExtra("AccessToken",loginResult.getAccessToken()) ;
                startActivity(test);
                finish();
                Toast.makeText(LoginScreen.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginScreen.this,"Cancelled",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginScreen.this,"ErrorOccured",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onSignUpClicked(View view)
    {
        startActivity(new Intent(LoginScreen.this,SignupScreen.class));
    }
    }

