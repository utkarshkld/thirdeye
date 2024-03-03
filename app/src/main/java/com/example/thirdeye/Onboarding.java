package com.example.thirdeye;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;
import static com.example.thirdeye.OnboardingAdapter.languageSpinner;
import static com.example.thirdeye.appsettings.dpToPx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.thirdeye.appsettings;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdeye.MainActivity;
import com.example.thirdeye.OnboardingAdapter;
import com.example.thirdeye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class Onboarding extends AppCompatActivity {

    ViewPager viewPager;
    Button button;
    public static TextToSpeech textToSpeech;

    private OnboardingAdapter pagerAdapter;
    private ProgressDialog progressDialog;
    private boolean blindness;
    private String input_lang,trans_input;
    public static String output_lang="en";
    private float speech_rate;

    public static int width;
    private int pageCount;
    private boolean open = false;
//    public static List<String> languages;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.viewPager);
        button = findViewById(R.id.buttonNextonboard);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        // Initialize ViewPager and Button
        pagerAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        pageCount = pagerAdapter.getCount();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Setting Language ....");
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Disables swipe gestures
            }
        });
        // Check if it's the first run of the app
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstTime = preferences.getString("isFirstRun", "");

        if (firstTime.equals("")) {
            // If it's the first run, set the flag in SharedPreferences


//             Listen for page changes in ViewPager

        } else {
            // If not the first run, directly start the main activity
            startMainActivity();
        }

    initializetexttospeech();
        // Button click listener to navigate through onboarding screens
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if(currentItem == 0){
                    // Add in database
                    speech_rate = 1f;
                    output_lang = OnboardingAdapter.languageMap.get(languageSpinner.getSelectedItem().toString());
                    blindness = false;
                    input_lang = "English";
                    trans_input = "en";
                    insertSingleTodo(output_lang,input_lang,trans_input,blindness,speech_rate);
                    pagerAdapter = new OnboardingAdapter(Onboarding.this);
                    viewPager.setAdapter(pagerAdapter);

                }else{
                    if (currentItem < pageCount - 1) {
                        viewPager.setCurrentItem(currentItem + 1);
                    } else {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("isFirstRun", "Yes");
                        editor.apply();
                        startMainActivity();
                    }
                }
            }
        });

    }
    public void initializetexttospeech(){
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("en"));
                } else {
                    Toast.makeText(Onboarding.this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT).show();
                }
            }
        },"com.google.android.tts");
        textToSpeech.setSpeechRate(0.8f);
    }
    @Override
    public void onDestroy(){
        textToSpeech.shutdown();
        super.onPause();
    }
    public void insertSingleTodo(String language,String inputlang,String trans_input,boolean blindness,float speech_rate) {
        Settings settings = new Settings(language,inputlang,trans_input,blindness,speech_rate);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        progressDialog.show();
        insertAsyncTask.execute(settings);
    }
    class InsertAsyncTask extends AsyncTask<Settings, Void, Void> {
        @Override
        protected Void doInBackground(Settings... settings) {

            MyRoomDatabase.getInstance(getApplicationContext())
                    .userDao()
                    .insert(settings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            super.onPostExecute(unused);

        }
    }

    // Method to start the main activity
    private void startMainActivity() {
        Intent intent = new Intent(Onboarding.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent returning to it on back press
    }
}