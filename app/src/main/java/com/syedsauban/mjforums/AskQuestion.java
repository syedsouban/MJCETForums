package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipDeletedListener;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AskQuestion extends AppCompatActivity {
    EditText Question,QuestionDetails;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;


    public static int counter=0;
    FlexboxLayout flexbox;
    ChipCloud chipCloud;

    Button addTagButton;

    DatabaseReference myRef;

    ImageButton AskQuestionButton;

    EditText editText;

    ChipCloudConfig config;

    ArrayList<String> tagslist;
    SharedPreferences prefs;


    String UserName;
    String UserEmail;

    ArrayList<String> tags;

    String key;

    tagAdapter tagAdapter;

    ListView listView;
    FirebaseDatabase database;


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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ask_question);


        tags=new ArrayList<>();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        flexbox = (FlexboxLayout) findViewById(R.id.flexbox);

//Create a new ChipCloud with a Context and ViewGroup:


        config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#80c7ff"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#80c7ff"))
                .uncheckedTextColor(Color.parseColor("#ffffff"))
                .useInsetPadding(true).showClose(Color.parseColor("#ffffff"),500);

        chipCloud = new ChipCloud(this, flexbox,config);

        listView=(ListView)findViewById(R.id.listview);

        editText=(EditText)findViewById(R.id.edittext);

        database = FirebaseDatabase.getInstance();
        myRef=FirebaseDatabase.getInstance().getReference();

        String a="firsttime";
        DatabaseReference tagsReference =myRef.child("Tags");
        String[] arr=new String[]{"CSE","ECE","Mech Engg","Civil Engg","EIE","EEE","Prod Engg","IT","CSI","E-Cell","IEEE","Robotics Club"
        ,"ACM","TRM","EWB","CIE","Competitive Exams","Major Projects","Mini Projects","Startups","Internships","Jobs","Programming",
                "Life Advice","Web Development","App Development","Career Guidance",
                "Placements","Higher Studies","Student Bodies","Academics","Book recommendations",
        "Exam Preparation","First year"};
        if(a=="firsttime") {
            for (int i = 0; i < arr.length; i++)
                tagsReference.child(arr[i]).child("Tag Name").setValue(arr[i]);
        }

        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(dataSnapshot.exists()) {
                        String tag = (String)ds.child("Tag Name").getValue();
                        Log.v("Tags", tag);
                        tagslist.add(tag);
//                        tagslist.add(tag.getTagName());
                    }
                }
                tagAdapter=new tagAdapter(AskQuestion.this,tagslist);
                listView.setAdapter(tagAdapter);
//                listView.setAdapter(new tagAdapter(AskQuestion.this, tagslist));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.child("Tags").addValueEventListener(listener);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tagAdapter.getFilter().filter(s.toString());
                tagAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AskQuestionButton=(ImageButton)findViewById(R.id.AnswerButton);
//        addTagButton=(Button)findViewById(R.id.addTagButton);

        tagslist=new ArrayList<>();

        prefs = getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
        mAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    UserEmail=user.getEmail();
                    AskQuestionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onQuestionAsked();
                        }
                    });
                }
                else
                {
                    finish();
                }
            }
        };


        Question=(EditText)findViewById(R.id.question);
        QuestionDetails=(EditText)findViewById(R.id.question_details);

//        AskQuestionButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    v.performClick();
//                }
//            }
//        });
//        addTagButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final LinearLayout addTagLayout =(LinearLayout) LayoutInflater.from(AskQuestion.this).inflate(R.layout.add_a_new_tag,null);
//                AlertDialog.Builder builder = new AlertDialog.Builder(AskQuestion.this);
//                builder.setView(addTagLayout);
//                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        EditText tagNameEditText=(EditText) addTagLayout.findViewById(R.id.tag_name);
//                        EditText tagDescriptionEditText=(EditText)addTagLayout.findViewById(R.id.tag_desc);
//                        DatabaseReference tagsReference =myRef.child("Tags");
//                        Tag newTag=new Tag(tagNameEditText.getText().toString(),tagDescriptionEditText.getText().toString());
//                        tagsReference.child(tagNameEditText.getText().toString()).setValue(newTag);
//                    }
//                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog =builder.create();
////                dialog.show();
//                EditText editText=(EditText)findViewById(R.id.edittext);
//                com.cunoraz.tagview.Tag tag=new com.cunoraz.tagview.Tag(editText.getText().toString());
//                tag.isDeletable=true;
//                tag.layoutColor= ContextCompat.getColor(AskQuestion.this,R.color.colorAccent);
//                tag.tagTextColor=ContextCompat.getColor(AskQuestion.this,R.color.white);
//                tagView.addTag(tag);
//            }
//        });
//        chipsInput.setFilterableList(tagslist);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tag=(String)parent.getAdapter().getItem(position);
                if(tags.contains(tag))
                {
                    Toast.makeText(AskQuestion.this, "Tag already added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (counter <= 4) {
                        tags.add(tag);
                        chipCloud.addChip(tag);
                        counter++;
                    } else
                        Toast.makeText(AskQuestion.this, "A question cannot have more than 5 tags", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chipCloud.setDeleteListener(new ChipDeletedListener() {
            @Override
            public void chipDeleted(int i, String s) {
                counter--;
                tags.remove(i);
            }
        });

    }
    public void onQuestionAsked()
    {
        counter=0;
        String QuestionString =Question.getText().toString();
        String QuestionDetailsString=QuestionDetails.getText().toString();
//        if(UserEmail!=null)
//        {
            ArrayList<String> tagNames=new ArrayList<>();
//
//            DatabaseReference myRefUsers = myRef.child("users");
//            Query query = myRefUsers
//                    .orderByChild("email")
//                    .equalTo(UserEmail);
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {



//                    if(dataSnapshot.exists())
//                    {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            User user = postSnapshot.getValue(User.class);
//                            key=postSnapshot.getKey();
//                            UserName=user.getName();
//
//                            Toast.makeText(AskQuestion.this, key, Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                    else
//                    {
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
            String QuestionKey=QuestionString;
            if(QuestionString.contains(".")||QuestionString.contains("#")||QuestionString.contains("$")||QuestionString
                    .contains("[")||QuestionString.contains("]"))
            {
                QuestionKey=QuestionKey.replace("."," ");
                QuestionKey=QuestionKey.replace("#"," ");
                QuestionKey=QuestionKey.replace("$"," ");
                QuestionKey=QuestionKey.replace("["," ");
                QuestionKey=QuestionKey.replace("]"," ");
            }
            key=prefs.getString("key","");
            UserName=prefs.getString("name","");

            com.syedsauban.mjforums.Question NewQuestion=new Question(QuestionString,UserEmail,UserName,QuestionDetailsString,tags,new Date().getTime()*-1,key);
            if(key!=null) {
                if(!tags.isEmpty())
                {
                    myRef.child("Questions").child(QuestionKey).setValue(NewQuestion);
                    myRef.child("users").child(key).child("Questions").child(QuestionKey).setValue(NewQuestion);
                    for (String tag : tags) {
                        myRef.child("Tags").child(tag).child("Questions").child(QuestionKey).setValue(NewQuestion);
                    }
                    startActivity(new Intent(AskQuestion.this, launching.class));
                }
                else
                {
                    Toast.makeText(this, "Please select one or more tags!", Toast.LENGTH_SHORT).show();
                }
            }
        }
//        else {
//            Toast.makeText(this, "User email is null", Toast.LENGTH_SHORT).show();
//        }
    }

