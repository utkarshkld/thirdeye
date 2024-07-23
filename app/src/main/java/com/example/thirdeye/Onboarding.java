package com.example.thirdeye;



import static com.example.thirdeye.OnboardingAdapter.languageSpinner;

import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;
import static com.example.thirdeye.OnboardingAdapter.speakText;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Onboarding extends AppCompatActivity {

    ViewPager viewPager;
    private Button button;
    public static boolean canSpeak = false;
    public static boolean inPauseSpeak = false;
    public static boolean say = false;
    public static TextToSpeech textToSpeech;
    private OnboardingAdapter pagerAdapter;
    private ProgressDialog progressDialog;
    public static ProgressDialog progressDialog2;
    private boolean blindness;
    private String input_lang,trans_input;
    private List<TranslatorOptions> optionslist;
    private Vibrator vibe;
    public static String output_lang="en";
    private float speech_rate;

    public static int width;
    private int pageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewPager = findViewById(R.id.viewPager);
        button = findViewById(R.id.buttonNextonboard);
//        textToSpeech = SplashScreen.textToSpeech;
//        textToSpeech.setSpeechRate(0.8f);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Downloading Language Models..."+"\n"+"This process may take time depending on internet speed");
        progressDialog2.setCancelable(false);
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        width = displayMetrics.widthPixels;
        vibe  = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        canSpeak = false;
        // Initialize ViewPager and Button
        pagerAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        pageCount = pagerAdapter.getCount();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Setting Language ....");
        initializetexttospeech();
//        UserPreferences userPreferences = new UserPreferences(this);
//        Location location = AnalyticsManager.getLocation(this);
//        String Country = "";
//        if(location!=null){
//            Country = AnalyticsManager.getCountry(this,location.getLatitude(),location.getLongitude());
//            Log.d("Country check",Country);
//        }
//
//        userPreferences.saveCountry(Country);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                initializetexttospeech();
//            }
//        }).start();
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Disables swipe gestures
            }
        });
        // Check if it's the first run of the app
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstTime = preferences.getString("isFirstRun", "");
        Log.d("checking first run",firstTime);
        if (firstTime.equals("")) {
            // If it's the first run, set the flag in SharedPreferences
//             Listen for page changes in ViewPager
            SharedPreferences preferencesDate = getSharedPreferences("installDate",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencesDate.edit();
            editor.putLong("TIME_MILLI_SEC", System.currentTimeMillis());
            editor.apply();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getAllSettings(Onboarding.this::onSettingsListLoaded);
                }
            }).start();
        } else {
            // If not the first run, directly start the main activity

            SharedPreferences preferencesDate = getSharedPreferences("installDate",MODE_PRIVATE);
            // remove this code for testing phase
            if(System.currentTimeMillis() - preferencesDate.getLong("TIME_MILLI_SEC",0) < 604800000){
                startMainActivity();
            }else{
                showOpenSettingsAlertDialog();
            }
//            startMainActivity();

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                int currentItem = viewPager.getCurrentItem();
                if(currentItem == 0){
                    // Add in database
//                    speech_rate = 1f;
//                    output_lang = OnboardingAdapter.languageMap.get(languageSpinner.getSelectedItem().toString());
//                    blindness = false;
//                    input_lang = "English";
//                    trans_input = "en";
//                    insertSingleTodo(output_lang,input_lang,trans_input,blindness,speech_rate);
//                    pagerAdapter = new OnboardingAdapter(Onboarding.this);
//                    viewPager.setAdapter(pagerAdapter);
                    downloadlanguage();
                }else{
                    if (currentItem < pageCount - 1) {

//                            say = true;

                        viewPager.setCurrentItem(currentItem + 1);
                    } else {
//                        say = true;
                        trackAppInstallation(Onboarding.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("isFirstRun", "Yes");
                        editor.apply();

                        startMainActivity();
                    }
                }
            }
        });
    }
    private void showOpenSettingsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Can't Open the App");
        builder.setMessage("Testing period of 7 days Ended...");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }
    public interface SettingsListCallback {
        void onSettingsListLoaded(List<Settings> settingsList);
    }
    public void onSettingsListLoaded(List<Settings> settingsList) {
        List<Settings> finalset = settingsList;
        for(Settings t : finalset){
            Log.d("Delete",t.toString());
            deleteATodo(t);
        }
    }
    public  void deleteATodo(Settings setting) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (setting != null) {
                    MyRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .delete(setting);
//                    Log.d("hmlo database", "run: todo has been deleted...");
                }

            }
        }).start();
    }
    public void getAllSettings(final Onboarding.SettingsListCallback callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Settings> todoList = MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .getAll();
                callback.onSettingsListLoaded(todoList);
                Log.d("hmlo database", "run: " + todoList.toString());
            }
        });
        thread.start();
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
        canSpeak = false;
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
    @Override
    public void onBackPressed(){
        canSpeak = false;
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onBackPressed();
    }
    @Override
    public void onPause(){
        canSpeak = false;
        inPauseSpeak = true;
//        Log.d("Inside the onPause",""+viewPager.getCurrentItem()+" "+"I am in Pause");
        if (textToSpeech != null) {
            textToSpeech.stop();
//            textToSpeech.shutdown();
        }
        super.onPause();
    }
    public void onStop(){
        canSpeak = false;
        Log.d("Inside the onPause",""+viewPager.getCurrentItem()+" "+"I am in Pause");
        if (textToSpeech != null) {
            textToSpeech.stop();
//            textToSpeech.shutdown();
        }
        super.onStop();
    }
    @Override
    public void onResume(){
        Log.d("Checking the current page Number",""+viewPager.getCurrentItem());
        if(viewPager.getCurrentItem() == 1){
//            initializetexttospeech();
            canSpeak = true;
            inPauseSpeak = false;
            Onboarding.textToSpeech.setLanguage(new Locale(Onboarding.output_lang));
            Log.d("Checking Instruction", ""+Onboarding.output_lang+" "+OnboardingAdapter.tx.getText().toString());
//            Onboarding.textToSpeech.speak(tx.getText().toString(),0, null, null);
            if( Onboarding.canSpeak && !inPauseSpeak) {
                speakText(OnboardingAdapter.tx.getText().toString(), 0);
            }
        }
        super.onResume();
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
            pagerAdapter = new OnboardingAdapter(Onboarding.this);
            canSpeak = true;
            say = true;
            viewPager.setAdapter(pagerAdapter);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            super.onPostExecute(unused);
        }
    }
    public void downloadlanguage(){
        List<String> languages = new ArrayList<>(Arrays.asList("en", "zh", "hi","ko","ja"));
        languages.add(OnboardingAdapter.languageMap.get(languageSpinner.getSelectedItem().toString()));
        optionslist = new ArrayList<>();
        progressDialog2.show();
        for(int i = 0;i<6;++i){
            TranslatorOptions options = new TranslatorOptions.Builder()
                    .setSourceLanguage("en")
                    .setTargetLanguage(languages.get(i))
                    .build();
            optionslist.add(options);
        }
        downloadModel(optionslist.get(0),0);
    }
    private void downloadModel(TranslatorOptions options,int i) {
        Translator translator = Translation.getClient(options);
        translator.downloadModelIfNeeded()
                .addOnSuccessListener(unused -> {
                    progressDialog2.setProgress((int)(((i+1)/6f)*100f));
                    if(i == 5) {
                        progressDialog2.dismiss();
                        speech_rate = 1f;
                        output_lang = OnboardingAdapter.languageMap.get(languageSpinner.getSelectedItem().toString());
                        textToSpeech.setLanguage(new Locale(output_lang));
                        blindness = false;
                        input_lang = "English";
                        trans_input = "en";
                        insertSingleTodo(output_lang,input_lang,trans_input,blindness,speech_rate);
                    }else{
                        downloadModel(optionslist.get(i+1),i+1);
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog2.dismiss();
                    Toast.makeText(Onboarding.this, "Language can't be loaded", Toast.LENGTH_SHORT).show();
                });
    }
    // Method to start the main activity
    private void startMainActivity() {

        Intent intent = new Intent(Onboarding.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent returning to it on back press
    }
//    public class TimePreference {
//
//        private static final String PREFS_NAME = "MyPrefs";
//        private static final String KEY_TIME_MILLIS = "stored_time_millis";
//
//        public  void saveTimeInMillis(Context context, long timeInMillis) {
//            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putLong(KEY_TIME_MILLIS, timeInMillis);
//            editor.apply(); // Save changes
//        }
}