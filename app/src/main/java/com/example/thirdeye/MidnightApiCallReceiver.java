package com.example.thirdeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MidnightApiCallReceiver extends BroadcastReceiver {
    public Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MidnightApiCallReceiver", "Making API call at midnight");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // If connected, make the API call
            new ApiCallTask(context).execute();
        } else {
            // If not connected, register the ConnectivityReceiver to retry when internet is available
            context.getApplicationContext().registerReceiver(new ConnectivityReceiver(),
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

        // Make the API call in an AsyncTask
        new ApiCallTask(context).execute();
    }

    // Function to make Api call at midnight
    public static class ApiCallTask extends AsyncTask<Void, Void, String> {
        private Context context;

        public ApiCallTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                // Replace with your API URL
                URL url = new URL("https://zoblik.com/api/save_user_analytics.php");
                Log.d("Checking call made hello before", "hello hello");

                // Create JSON object with your data
                JSONObject eventData = new JSONObject();
                UserPreferences userPreferences = new UserPreferences(context);
                eventData.put("user_id", userPreferences.getUserId());
                eventData.put("data", userPreferences.getFeatureListAsJsonArray());  // Replace with your data

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                Log.d("checking Json analytics", "" + eventData.toString());
                conn.setDoOutput(true);

                // Write the JSON data to the output stream
                OutputStream os = conn.getOutputStream();
                os.write(eventData.toString().getBytes("UTF-8"));
                os.close();

                // Read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Checking call made hello after", "hello hello");
            if (result != null) {
                new UserPreferences(context).clearFeatureList();
                Log.d("ApiCallTask saving analytics", "API Response: " + result);
            } else {
                Log.d("ApiCallTask saving analytics", "Failed to make API call");
            }
        }
    }
}
