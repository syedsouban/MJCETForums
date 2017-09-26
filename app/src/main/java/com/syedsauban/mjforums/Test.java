package com.syedsauban.mjforums;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONObject;

import java.net.URL;

public class Test extends AppCompatActivity {
    public class NetAsyncTask extends AsyncTask<URL,Void,Bitmap> {


        @Override
        protected Bitmap doInBackground(URL... params) {
            Bitmap bitmap=null;
            try {
                bitmap= BitmapFactory.decodeStream(params[0].openConnection().getInputStream());
            }
            catch(Exception e)
            {
                Toast.makeText(Test.this,e.toString(),Toast.LENGTH_LONG).show();
                Log.v("AsyncTask",e.toString());
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            ImageView imageView=(ImageView)findViewById(R.id.profile_pic);
            imageView.setImageBitmap(bitmap);
        }
    }
    ImageView profilePic;
    TextView userName;
    String name;
    Bitmap ProfilePic;
    NetAsyncTask newTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        profilePic=(ImageView)findViewById(R.id.profile_pic);
        userName=(TextView)findViewById(R.id.name);
        newTask=new NetAsyncTask();
        Bundle bundle=new Bundle();
        bundle.putString("fields","name,picture,id");
        AccessToken ak=getIntent().getParcelableExtra("AccessToken");
        GraphRequest request= new GraphRequest(ak, "me", bundle, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted( GraphResponse response) {
                        try
                        {
                            Toast.makeText(Test.this,"response is: "+response.getRawResponse(),Toast.LENGTH_LONG).show();
                            JSONObject JSONResponse = new JSONObject(response.getRawResponse());
                            name=JSONResponse.getString("name");
                            String id;
                            id=JSONResponse.getString("id");
                            userName.setText(name);
                            URL url=new URL("http://graph.facebook.com/"+id+"/picture?width=150&height=150");
                            profilePic.setImageBitmap(newTask.execute(url).get());
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(Test.this,"some exception occured: "+e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        request.executeAsync();


    }

}
