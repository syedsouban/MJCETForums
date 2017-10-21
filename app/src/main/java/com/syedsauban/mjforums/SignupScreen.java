package com.syedsauban.mjforums;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import static android.R.id.message;

public class SignupScreen extends AppCompatActivity {
    EditText usernameEditText,emailEditText,passwordEditText;
    Button signUpButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String username,email,password;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        usernameEditText=(EditText)findViewById(R.id.usernameEditText);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        signUpButton=(Button)findViewById(R.id.signup_button);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    if (!user.isEmailVerified()) {
                        Log.v("SignupScreen", "User is not verified..Sending email verification...");
                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupScreen.this, "Verify your Email and then Sign In", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            startActivity(new Intent(SignupScreen.this, LoginScreen.class));

                                        } else
                                            Log.v("SignupScreen", "Unable to send Email");
                                    }
                                });
                    }

                    else

                        startActivity(new Intent(SignupScreen.this,launching.class));
                    } else {
                        // User is signed out
                        Log.d("Auth", "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameEditText.getText().toString().trim();
                email=emailEditText.getText().toString().trim();

                password=passwordEditText.getText().toString().trim();

                OnCompleteListener<AuthResult> onCompleteListener=

                         new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignupScreen.this, "Signup Successfull", Toast.LENGTH_SHORT).show();
                        }
                        if (!task.isSuccessful()) {
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            Toast.makeText(SignupScreen.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupScreen.this,onCompleteListener);
            }
        });
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

    public void onLoginClicked(View view)
    {
        startActivity(new Intent(SignupScreen.this,LoginScreen.class));
    }
}
