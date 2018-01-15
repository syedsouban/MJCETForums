package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {
CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText emailEditText,passwordEditText;
    Button login;

    String isDAYset;

    String key;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    SharedPreferences prefs;

    FirebaseUser user;
    String UserId;
    String userName;
    ProgressBar progressBar;

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
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        login=(Button)findViewById(R.id.signup_button);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        prefs = getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.d("Debug","Email: "+user.getEmail());
                    DatabaseReference myRefUsers = mReference.child("users");
                    Query query = myRefUsers
                            .orderByChild("email")
                            .equalTo(user.getEmail());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    User user = postSnapshot.getValue(User.class);
                                    key = postSnapshot.getKey();
                                    Log.d("Debug","key: "+key);
                                }
                                    if(key!=null)
                                    {
                                        prefs.edit().putString("key","").apply();
                                        prefs.edit().putString("key",key).apply();
                                        mReference.child("users").child(key)
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists())
                                                {
                                                    userName=dataSnapshot.child("name").getValue().toString();
                                                    Log.d("userName",userName);
                                                    if (userName != null) {
                                                        prefs.edit().putString("name","").apply();
                                                        prefs.edit().putString("name",userName).apply();
                                                    } else {
                                                        Toast.makeText(LoginScreen.this, "UserName is null", Toast.LENGTH_SHORT).show();
                                                    }
                                                    Log.d("userNameFromPref",prefs.getString("name","not found"));
//                                                    Toast.makeText(LoginScreen.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                                                    isDAYset=dataSnapshot.child("isDAYset").getValue().toString();
                                                    Log.d("Debug","isDAYset: "+isDAYset);
                                                    if(isDAYset.equals("false"))
                                                    {
                                                        Intent intent= new Intent(LoginScreen.this,DeptAndYearActivity.class);
//                                                        intent.putExtra("key",key);
                                                        startActivity(intent);
//                                                        System.exit(0);
                                                    }
                                                    else if(isDAYset.equals("true"))
                                                    {
                                                        String deptName=dataSnapshot.child("deptName").getValue().toString();
                                                        String year=dataSnapshot.child("year").getValue().toString();
                                                        prefs.edit().putString("deptNameAndYear", "").apply();
                                                        prefs.edit().putString("deptNameAndYear",deptName+" "+year).apply();
                                                        for (UserInfo userinfo: mAuth.getCurrentUser().getProviderData()) {
                                                            if (userinfo.getProviderId().equals("facebook.com")) {
                                                                Toast.makeText(LoginScreen.this, "User is signed in with Facebook", Toast.LENGTH_SHORT).show();
                                                                if (isDAYset != null && isDAYset.equals("true")) {
                                                                    Intent intent=new Intent(LoginScreen.this, launching.class);
                                                                    startActivity(intent);
                                                                    break;
//                                                                    System.exit(0);
                                                                }

                                                            }
                                                            else if(userinfo.getProviderId().equals("google.com")
                                                                    ||userinfo.getProviderId().equals("gmail.com"))
                                                            {

                                                            }
                                                            else
                                                            {
//                                                                if(user.isEmailVerified())
//                                                                {
                                                                    if(isDAYset!=null&&isDAYset.equals("true")) {
                                                                        Intent intent=new Intent(LoginScreen.this, launching.class);
//                                    intent.putExtra("key",key);
                                                                        startActivity(intent);
                                                                        break;
//                                                                        System.exit(0);
                                                                    }

//                                                                }
//                                                                else
//                                                                    Toast.makeText(LoginScreen.this, "Your Email is not verified", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    Toast.makeText(LoginScreen.this, key, Toast.LENGTH_SHORT).show();

                                }

                            else
                            {

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





                    // User is signed in
//                    Log.d("LoginScreen", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    progressBar.setVisibility(View.GONE);

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
                emailEditText.setText("");
                passwordEditText.setText("");
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
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                UserId=loginResult.getAccessToken().getUserId();
                prefs.edit().putString("UserId","" +
                        "").apply();
                prefs.edit().putString("UserId",UserId).apply();
                handleFacebookAccessToken(loginResult.getAccessToken());
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
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("LoginScreen", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference dRef=FirebaseDatabase.getInstance().getReference("users");
                            Query query = dRef
                                    .orderByChild("email")
                                    .equalTo(mAuth.getCurrentUser().getEmail());
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {
                                        Toast.makeText(LoginScreen.this, "Old User", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginScreen.this, "New user", Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("users");
                                        String userId=myRef.push().getKey();
                                        User newUser=new User(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                        myRef.child(userId).setValue(newUser);
                                        myRef.child(userId).child("isDAYset").setValue("false");

                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginScreen", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginScreen.this, "Successfully Authenticated", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginScreen.this,launching.class);

//                            intent.putExtra("")
//                            intent.putExtra("UserId",UserId);
                            if(isDAYset!=null&&isDAYset.equals("true")) {
                                startActivity(intent);
                            }
                        } else {
                            Log.w("LoginScreen", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
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

