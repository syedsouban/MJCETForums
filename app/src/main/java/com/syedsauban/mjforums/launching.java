package com.syedsauban.mjforums;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class launching extends AppCompatActivity {

    ImageView thumbsUp;
    ImageView thumbsDown;

    Toast toast;

    public static int  flag=0;

    DrawerLayout drawerLayout;
    ImageView navigationMenuIcon;
    FirebaseUser user;


    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    FloatingActionButton fab;

    ProfilePictureView NavImage;

    ProfilePictureView profilePicLaunching;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView Name, DeptAndYear;

    String imageUrlString;
    URL imageUrl;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    NavigationView navigation;
    SharedPreferences prefs;
    String UserId,key;
    EditText questionEditText;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_launching);


        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();
        prefs = getSharedPreferences("com.syedsauban.mjforums", Context.MODE_PRIVATE);
        UserId=prefs.getString("UserId","");
        key=prefs.getString("key","");

        navigationMenuIcon = (ImageView) findViewById(R.id.nav_menu_icon);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        navigationMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigation = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigation.getHeaderView(0);
        Name = (TextView) header.findViewById(R.id.name);
        DeptAndYear = (TextView) header.findViewById(R.id.deptandyear);
        NavImage=(ProfilePictureView)header.findViewById(R.id.profilePicNavHeader);
        NavImage.setProfileId(UserId);
        profilePicLaunching =(ProfilePictureView)findViewById(R.id.profilePicLaunching);
        profilePicLaunching.setProfileId(UserId);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.my_profile:
                        Intent intent=new Intent(launching.this, UserProfile.class);
//                        intent.putExtra("UserId",prefs.getString("UserId",""));
                        startActivity(intent);
                        break;
                    case R.id.help:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;

                    case R.id.terms_and_conditions:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.logout:
                        if (mAuth.getCurrentUser() != null) {
                            for (UserInfo userinfo : mAuth.getCurrentUser().getProviderData()) {
                                if (userinfo.getProviderId().equals("facebook.com")) {
                                    prefs.edit().putString("key","").apply();
                                    prefs.edit().putString("UserId","").apply();
                                    prefs.edit().putString("name","").apply();
                                    prefs.edit().putString("deptNameAndYear","").apply();
                                    mAuth.signOut();
                                    LoginManager.getInstance().logOut();
                                    finish();
                                    break;
                                } else if (userinfo.getProviderId().equals("google.com")
                                        || userinfo.getProviderId().equals("gmail.com")) {

                                } else {
                                    prefs.edit().putString("key","").apply();
                                    prefs.edit().putString("UserId","").apply();
                                    prefs.edit().putString("name","").apply();
                                    prefs.edit().putString("deptNameAndYear","").apply();
                                    mAuth.signOut();
                                    finish();
                                }
                            }
                        }


                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(launching.this,AskQuestion.class);
//                intent.putExtra("UserId",getIntent().getStringExtra("UserId"));
                startActivity(intent);
            }
        });





        mAuth = FirebaseAuth.getInstance();
        questionEditText=(EditText)findViewById(R.id.question_editText);
        questionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(launching.this,AskQuestion.class);
//                intent.putExtra("UserId",getIntent().getStringExtra("UserId"));
                startActivity(intent);
            }
        });





        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();

                if (user != null) {
                    mReference.child("users").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                if (dataSnapshot.hasChild("deptName") && dataSnapshot.hasChild("year")) {
                                    String deptName = dataSnapshot.child("deptName").getValue().toString();
                                    String year = dataSnapshot.child("year").getValue().toString();

                                    DeptAndYear.setText(deptName + " " + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    for (UserInfo userinfo : mAuth.getCurrentUser().getProviderData()) {

                        if (userinfo.getProviderId().equals("facebook.com")) {
                            Name.setText(user.getDisplayName());
                        }
                        else if (userinfo.getProviderId().equals("google.com")
                                || userinfo.getProviderId().equals("gmail.com")) {

                        }
                        else
                        {

                        }

                    }

                }
                else
                {
                    finish();
                }
            }
        };

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the
        // activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        CustomTabLayout tabLayout = (CustomTabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if (toolbar.getChildAt(i) instanceof ImageButton) {
                toolbar.getChildAt(i).setScaleX(0.5f);
                toolbar.getChildAt(i).setScaleY(0.5f);
            }
        }


    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    Newest newest = new Newest();
                    return newest;
                case 1:
                    return new Bookmarks();
                case 2:
                    return new ByTopic();
                case 3:
                    return new Unaswered();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Newest";
                case 1:
                    return "Featured";
                case 2:
                    return "By Topic";
                case 3:
                    return "Unanswered";
            }
            return null;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(flag==2)
            {
                if(toast!=null) {
                    if (toast.getView().getWindowVisibility() == View.VISIBLE) {
//                        if(android.os.Build.VERSION.SDK_INT >= 21)
//                        {
//                            finishAndRemoveTask();
//                            System.exit(0);
//                        }
//                        else
//                        {
//                            finishAffinity();
//                            System.exit(0);
//                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                }
                flag=0;
            }
            else {
                flag=2;
                toast=Toast.makeText(this, "Press back again to exit activity", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    public void onEditTextClicked()
    {

    }

    class ImageLoader extends AsyncTask<URL, Void, Bitmap> {
        ImageView ProfilePic;
        Bitmap bmp = null;

        ImageLoader(ImageView ProfilePic) {
            this.ProfilePic = ProfilePic;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL imageUrl = params[0];

            if (imageUrl != null) {
                try {
                    bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                } catch (IOException e) {
                }
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bmp != null)
                ProfilePic.setImageBitmap(bmp);
        }
    }
}
