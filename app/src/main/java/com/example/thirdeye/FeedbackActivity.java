package com.example.thirdeye;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FeedbackActivity extends AppCompatActivity {




    Button submit;
    ImageView backbtnfeedback;
//    MongoClient mongoClient;
//    MongoDatabase database;
    RadioButton x,x1,x2,x3,x11,x12,x13,x14,x21,x22,x23,x24;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        submit = findViewById(R.id.buttonsubmit);
        backbtnfeedback = findViewById(R.id.backbtnfeedback);
        x = findViewById(R.id.radia_id1);x1 = findViewById(R.id.radia_id2); x2 = findViewById(R.id.radia_id3);x3 = findViewById(R.id.radia_id4);
        x11 = findViewById(R.id.radia_id11);x12 = findViewById(R.id.radia_id12); x13 = findViewById(R.id.radia_id13);x14 = findViewById(R.id.radia_id14);
        x21 = findViewById(R.id.radia_id21);x22 = findViewById(R.id.radia_id22); x23 = findViewById(R.id.radia_id23);x24 = findViewById(R.id.radia_id24);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InsertFeedbackAsyncTask().execute();
            }
        });

        backbtnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mongoClient.close();

                startActivity(new Intent(FeedbackActivity.this, appsettings.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
//       mongoClient.close();
       super.onBackPressed();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    public class InsertFeedbackAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String q1 = "", q2 = "",q3 = "";

            if (x.isChecked()) {
                q1 = x.getText().toString();
            } else if (x1.isChecked()) {
                q1 = x1.getText().toString();
            } else if (x2.isChecked()) {
                q1 = x2.getText().toString();
            } else if (x3.isChecked()) {
                q1 = x3.getText().toString();
            }

            if (x11.isChecked()) {
                q2 = x11.getText().toString();
            } else if (x12.isChecked()) {
                q2 = x12.getText().toString();
            } else if (x13.isChecked()) {
                q2 = x13.getText().toString();
            } else if (x14.isChecked()) {
                q2 = x14.getText().toString();
            }
            if (x21.isChecked()) {
                q3 = x21.getText().toString();
            } else if (x22.isChecked()) {
                q3 = x22.getText().toString();
            } else if (x23.isChecked()) {
                q3 = x23.getText().toString();
            } else if (x24.isChecked()) {
                q3 = x24.getText().toString();
            }
            Log.d("check feedback", q1 + " " + q2+" "+q3);



                try {
                    // Add your data
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("https://answers-td8w.onrender.com/add");

                    JSONObject json = new JSONObject();
                    json.put("question1", q1);
                    json.put("question2", q2);
                    json.put("question3",q3);

                    // Set the JSON object as the entity of the request
                    StringEntity se = new StringEntity(json.toString());
                    httppost.setEntity(se);
                    Log.d("feedback answer", json.toString());
                    // Set content type of the request body
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    // Execute HTTP Post Request

                    HttpResponse response = httpclient.execute(httppost);
//                    Log.d("feedback answer", response.toString());




                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    Log.d("feedback answer", "doInBackground: "+e.toString());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d("feedback answer", "doInBackground: "+e.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isNetworkAvailable()) {
                Toast.makeText(FeedbackActivity.this, "Data submitted.", Toast.LENGTH_SHORT).show();
                Toast.makeText(FeedbackActivity.this, "Thanks for your feedback.", Toast.LENGTH_SHORT).show();
            }else{

                    Toast.makeText(FeedbackActivity.this,"No Internet Connection", Toast.LENGTH_LONG).show();


            }

        }
    }

}
// hX69wWc61x8uE6Rq