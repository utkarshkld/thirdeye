package com.example.thirdeye;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_FEATURE_LIST = "featureList";

    private static final String KEY_COUNTRY = "Country";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public String getCountry(){
        return sharedPreferences.getString(KEY_COUNTRY,"");
    }
    public void saveCountry(String country){
        editor.putString(KEY_COUNTRY,country);
        editor.apply();
    }

    // Method to save the user ID
    public void saveUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.apply(); // or commit() if you prefer synchronous saving
    }

    // Method to retrieve the user ID
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null); // Default value is null
    }

    // Method to clear the user ID
    public void clearUserId() {
        editor.remove(KEY_USER_ID);
        editor.apply();
    }

    // Method to add a feature to the list
    public void addFeature(String featureDuration, String featureName) {
        JSONArray featureList = getFeatureListAsJsonArray();
        JSONObject newFeature = new JSONObject();
        try {
            newFeature.put("feature_duration", featureDuration);
            newFeature.put("feature_name", featureName);
            featureList.put(newFeature);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.putString(KEY_FEATURE_LIST, featureList.toString());
        editor.apply();
    }

    // Method to retrieve the feature list as a JSONArray
    public JSONArray getFeatureListAsJsonArray() {
        String jsonString = sharedPreferences.getString(KEY_FEATURE_LIST, null);
        JSONArray jsonArray = new JSONArray();
        if (jsonString != null) {
            try {
                jsonArray = new JSONArray(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    // Method to retrieve the feature list as strings
    public List<String[]> getFeatureList() {
        JSONArray jsonArray = getFeatureListAsJsonArray();
        List<String[]> featureList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String featureDuration = jsonObject.getString("feature_duration");
                String featureName = jsonObject.getString("feature_name");
                featureList.add(new String[]{featureDuration, featureName});
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return featureList;
    }

    // Method to clear the feature list
    public void clearFeatureList() {
        editor.remove(KEY_FEATURE_LIST);
        editor.apply();
    }

    public String convertMillisToMinutesSeconds(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
