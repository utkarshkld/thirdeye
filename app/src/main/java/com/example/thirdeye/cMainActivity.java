package com.example.thirdeye;

import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;
import static com.example.thirdeye.MainActivity.finalset;
import static com.example.thirdeye.MainActivity.output_lang;
import static com.example.thirdeye.ObjectDetectorHelper.TAG;
import static com.example.thirdeye.ObjectDetectorHelper.textToSpeech;

import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.thirdeye.databinding.CheckMainBinding;
import com.example.thirdeye.fragments.CameraFragment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class cMainActivity extends AppCompatActivity {

    private CheckMainBinding activityMainBinding;
    private  Vibrator vibe;
    public static Map<String, Long> objectResolver = new HashMap<>();
    public static HashMap<String, String> languageMap = new HashMap<>();
    public static boolean partialblind = MainActivity.blindness;
    private MainViewModel viewModel;
    public static boolean isPlay = true;
    public static boolean hidescreen = false;
    public static FragmentContainerView fragmentContainerView;
    private ImageButton backbtn;
    private Button setfpsbtn;
    public static boolean candetect = true;
    private Button exitbtn;
    public static boolean flash = false;
    public static long detectime=0;
    public static int screenWidth , screenHeight;
    private EditText fpsText;
    public static int setfps;
    private TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        flash = intent.getBooleanExtra("flash",false);
        setfps = intent.getIntExtra("FPS",15);
        activityMainBinding = CheckMainBinding.inflate(getLayoutInflater());
        partialblind = MainActivity.blindness;
        cMainActivity.isPlay = true;
        setContentView(activityMainBinding.getRoot());
        fragmentContainerView = findViewById(R.id.fragment);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        trackAppInstallation(this,"Object Detection");
        hidescreen = false;
        screenHeight = fragmentContainerView.getLayoutParams().height;
        screenWidth = fragmentContainerView.getLayoutParams().width;
        Log.d("Checking screen size",screenHeight +" "+screenWidth);
        Log.d("Checking Partial Blind", ""+partialblind);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        // Initialize ViewModel using ViewModelProvider
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        backbtn = findViewById(R.id.backbtn_);
        exitbtn = findViewById(R.id.exitbtn);
        textView = findViewById(R.id.fpscount);
        Log.d("Fps checker","Current Fps : "+setfps);
        textView.setText("FPS : "+setfps);
        fragmentContainerView = findViewById(R.id.fragment);
        initializeTextToSpeech();
        setfpsbtn = findViewById(R.id.setFpsbtn);
        fpsText = findViewById(R.id.setFps);
        initializeLanguageMap();
        setfpsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isPlay = false;
                if(textToSpeech!=null) {
                    textToSpeech.shutdown();
                }
                finish();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Introduce a 100ms delay
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // Handle the interruption in some way, or ignore it
                            e.printStackTrace();
                        }

                        // Now create and start the intent after the delay
                        MainActivity.objdetfps = Integer.parseInt(fpsText.getText().toString());
                        Intent intent = new Intent(cMainActivity.this, cMainActivity.class);
                        startActivity(intent);
                    }
                }).start();


//                onBackPressed();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                isPlay = false;
                if(textToSpeech!=null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
//                Intent intent = new Intent(cMainActivity.this, MainActivity.class);
//                startActivity(intent);
//                onBackPressed();
            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = false;
                vibe.vibrate(50);
                if(textToSpeech!=null) {
                   textToSpeech.stop();
                   textToSpeech.shutdown();
                }
                Intent intent = new Intent(cMainActivity.this, MainActivity.class);
                startActivity(intent);
//                onBackPressed();
            }
        });


    }
    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(cMainActivity.this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result;
                if(output_lang.equals("id")){
                    result = textToSpeech.setLanguage(new Locale("in"));
                }else if(output_lang.equals("no")){
                    result = textToSpeech.setLanguage(new Locale("nb"));
                }
                else {
                    result = textToSpeech.setLanguage(new Locale(output_lang));
                }
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "TextToSpeech: Language not supported.");
                }else{

                    // Set up utterance progress listener
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            // Set your flag to true when speech starts isWordspeaking = true;
                        }
                        @Override
                        public void onDone(String utteranceId) {
                            // Set your flag to false when speech is completed

                        }

                        @Override
                        public void onError(String utteranceId) {
                            // Handle errors if needed
                        }
                    });

                }
            } else {

            }
        }, "com.google.android.tts");
        textToSpeech.setSpeechRate(1.2f);
    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void initializeLanguageMap() {
        // Add languages and their Locale codes to the HashMap
        languageMap.put("bn", "Bengali");
        languageMap.put("hi", "Hindi");
        languageMap.put("gu", "Gujarati");
        languageMap.put("en", "English");
        languageMap.put("kn", "Kannada");
        languageMap.put("mr", "Marathi");
        languageMap.put("ta", "Tamil");
        languageMap.put("te", "Telugu");
        languageMap.put("ur", "Urdu");
        languageMap.put("ml", "Malayalam");

        languageMap.put("af", "Afrikaans");
        languageMap.put("ar", "Arabic");

        languageMap.put("bg", "Bulgarian");
//        languageMap.put("ca", "Catalan");
        languageMap.put("cs", "Czech");

        languageMap.put("da", "Danish");
        languageMap.put("de", "German");
        languageMap.put("el", "Greek");

        languageMap.put("es", "Spanish");

        languageMap.put("fi", "Finnish");
        languageMap.put("fr", "French");

        languageMap.put("gl", "Galician");//note
//        languageMap.put("he", "Hebrew");
//        languageMap.put("hr", "Croatian");
//        languageMap.put("ht", "Haitian");
        languageMap.put("hu", "Hungarian");
        languageMap.put("id", "Indonesian");//in
        languageMap.put("is", "Icelandic");
        languageMap.put("it", "Italian");
        languageMap.put("ja", "Japanese");
//        languageMap.put("ka", "Georgian");
        languageMap.put("ko", "Korean");
        languageMap.put("lt", "Lithuanian");
        languageMap.put("lv", "Latvian");
//        languageMap.put("mk", "Macedonian");
        languageMap.put("ms", "Malay");
//        languageMap.put("mt", "Maltese");
        languageMap.put("nl", "Dutch");
        languageMap.put("no", "Norwegian");//nb
        languageMap.put("pl", "Polish");
        languageMap.put("pt", "Portuguese");
        languageMap.put("ro", "Romanian");
        languageMap.put("ru", "Russian");
        languageMap.put("sk", "Slovak");
//        languageMap.put("sl", "Slovenian");
//        languageMap.put("sq", "Albanian");
        languageMap.put("sv", "Swedish");
//        languageMap.put("sw", "Swahili");
        languageMap.put("th", "Thai");
//        languageMap.put("tl", "Tagalog");
        languageMap.put("tr", "Turkish");
        languageMap.put("uk", "Ukrainian");
        languageMap.put("vi", "Vietnamese");
        languageMap.put("zh", "Chinese");
        // Add more languages as needed
    }
    @Override
    protected void onPause() {
        backbtn.performClick();
        super.onPause();
    }
    @Override
    public void onBackPressed() {
//        isPlay = false;
        if(textToSpeech!=null) {

            textToSpeech.shutdown();
        }
        Intent intent = new Intent(cMainActivity.this, MainActivity.class);
        startActivity(intent);

        super.onBackPressed();
    }
}
