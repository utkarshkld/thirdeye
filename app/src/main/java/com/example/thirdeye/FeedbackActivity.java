package com.example.thirdeye;

//import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FeedbackActivity extends AppCompatActivity {


    private boolean isData = false;
    private boolean flag = false;
    private long starting_time = 0;
    Button submit;
    private  Vibrator vibe;
    ImageButton backbtnfeedback;
    RadioButton x,x1,x2,x3,x11,x12,x13,x14,x21,x22,x23,x24;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        submit = findViewById(R.id.buttonsubmit);
        flag = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        trackAppInstallation(this,"Feedback activity");
        backbtnfeedback = findViewById(R.id.backbtnfeedback);
        x = findViewById(R.id.radia_id1);x1 = findViewById(R.id.radia_id2); x2 = findViewById(R.id.radia_id3);x3 = findViewById(R.id.radia_id4);
        x11 = findViewById(R.id.radia_id11);x12 = findViewById(R.id.radia_id12); x13 = findViewById(R.id.radia_id13);x14 = findViewById(R.id.radia_id14);
        x21 = findViewById(R.id.radia_id21);x22 = findViewById(R.id.radia_id22); x23 = findViewById(R.id.radia_id23);x24 = findViewById(R.id.radia_id24);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting Feedback...");
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        starting_time = System.currentTimeMillis();

        progressDialog.setCancelable(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                new InsertFeedbackAsyncTask().execute();
            }
        });

        backbtnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mongoClient.close();
                vibe.vibrate(50);
//                startActivity(new Intent(FeedbackActivity.this, appsettings.class));
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
//       mongoClient.close();
        Intent intent = new Intent(FeedbackActivity.this,appsettings.class);
        startActivity(intent);
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
    public class InsertFeedbackAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show ProgressDialog
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
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
            if(q1.length()>0 && q2.length()>0 && q3.length()>0){
                try {
                    // Add your data
                    UserPreferences userPreferences = new UserPreferences(FeedbackActivity.this);
                    isData = true;
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("https://zoblik.com/api/save_review.php");
                    JSONArray feedbackArray = new JSONArray();

                    // Add feedback objects to the array
                    feedbackArray.put(createFeedbackJSON("question1", q1));
                    feedbackArray.put(createFeedbackJSON("question 2", q2));
                    feedbackArray.put(createFeedbackJSON("question3", q3));

                    JSONObject json = new JSONObject();
                    String userId = userPreferences.getUserId();
                    json.put("user_id",userId);
                    json.put("feedback", feedbackArray);
//                    json.put("question1", q1);
//                    json.put("question2", q2);
//                    json.put("question3",q3);
                    // Set the JSON object as the entity of the request

                    StringEntity se = new StringEntity(json.toString());
                    httppost.setEntity(se);
//                    Log.d("feedback answer", json.toString());
                    // Set content type of the request body
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity httpEntity = response.getEntity();
                    InputStream is = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    reader.close();
                    Log.d("feedback abc",""+sb);
                    return sb.toString();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    flag = true;
                    Log.d("feedback answer", "doInBackground: "+e.toString());
                } catch (IOException e) {
                    flag = true;
                    // TODO Auto-generated catch block
                    Log.d("feedback answer", "doInBackground: "+e.toString());
                } catch (JSONException e) {
                    flag = true;
                    throw new RuntimeException(e);
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(!isData){
                Toast.makeText(FeedbackActivity.this, "Please Mark answers for all the questions ", Toast.LENGTH_SHORT).show();
            }else if(!flag){
                if(isNetworkAvailable()) {
                    Toast.makeText(FeedbackActivity.this, "Data submitted.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(FeedbackActivity.this, "Thanks for your feedback.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeedbackActivity.this,appsettings.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(FeedbackActivity.this,"No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
    public  JSONObject createFeedbackJSON(String feedbackText, String rating) throws JSONException {
        JSONObject feedbackJSON = new JSONObject();
        feedbackJSON.put("feedback_text", feedbackText);
        feedbackJSON.put("rating", rating);
        return feedbackJSON;
    }
    @Override
    public void onDestroy(){
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time-starting_time);
        userPreferences.addFeature(time,"Feedback");
        Log.d("Duration check",""+time+" "+userPreferences.getFeatureListAsJsonArray());
        super.onDestroy();
    }
}
