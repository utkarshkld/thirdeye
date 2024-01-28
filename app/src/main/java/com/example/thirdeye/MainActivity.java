package com.example.thirdeye;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static Vibrator vibe;
    public static HashMap<String, String> translationMap = new HashMap<>();
    public static HashMap<String,String> languageMap = new HashMap<>();
    private static final int SPEECH_REQUEST_CODE = 1;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 1;
    public static TextToSpeech textToSpeech;
    public static String speaklang;
    private MicHandler shakeListener;
    private int i = 0;
    private ImageView eyebtn;
    private String UserDeafultLanguage = Locale.getDefault().getLanguage();
    public static String output_lang;
    public static float speech_rate;
    public static boolean blindness;
    public static String input_lang;
    public static String trans_input;
    public static List<Settings> finalset;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initializeLanguageMap();
        translationmap.initializetransMap();
        //Commands are Translated
        // Check and request RECORD_AUDIO permission
        checkRecordAudioPermission();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        requestPermission();
        eyebtn = findViewById(R.id.eyebtn);
        Log.d("hello_testing",UserDeafultLanguage);


        // Initialize Text-to-Speech
        // we
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.getDefault());

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language initialization errors here
                }
            } else {
                // Handle Text-to-Speech initialization error
            }
        });

        // Initialize ShakeListener
        shakeListener = new MicHandler(this);
        shakeListener.setOnShakeListener(() -> {
            Toast.makeText(MainActivity.this, "Shake detected!", Toast.LENGTH_SHORT).show();
            startRecording();
        });
        ImageView micButton = findViewById(R.id.micButton);
        CardView objectButton = findViewById(R.id.objectbutton);
        CardView wordsButton = findViewById(R.id.wordsButton);
        CardView st = findViewById(R.id.btnspeechtotext);
        micButton.setOnClickListener(v -> startVoiceRecognition());
        eyebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(translationMap.get(speaklang+"_"+"opening setting"),TextToSpeech.QUEUE_FLUSH, null, null);
                Intent intent = new Intent(MainActivity.this,appsettings.class);
                startActivity(intent);
            }
        });
        objectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibe.vibrate(50);
                textToSpeech.speak(translationMap.get(speaklang+"_"+"opening object"),TextToSpeech.QUEUE_FLUSH, null, null);
                Intent intent = new Intent(MainActivity.this, cMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
        wordsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vibe.vibrate(50);
                    textToSpeech.speak(translationMap.get(speaklang+"_"+"opening read text"),TextToSpeech.QUEUE_FLUSH, null, null);
                    Intent intent = new Intent(MainActivity.this, TextSpeech.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                }
        });
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                textToSpeech.speak(translationMap.get(speaklang+"_"+"opening translate"),TextToSpeech.QUEUE_FLUSH, null, null);
                Intent intent = new Intent(MainActivity.this, LangTranslate.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }



        });
        getAllSettings(this::onSettingsListLoaded);// loading all settings




    }
    public void insertSingleTodo(String language,String inputlang,String trans_input,boolean blindness,float speech_rate) {
        Settings settings = new Settings(language,inputlang,trans_input,blindness,speech_rate);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
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
    }
    public interface SettingsListCallback {
        void onSettingsListLoaded(List<Settings> settingsList);
    }

    public void onSettingsListLoaded(List<Settings> settingsList) {
        finalset = settingsList;
        if(finalset == null || finalset.size() == 0){
            speech_rate = 0.8f;
            output_lang = UserDeafultLanguage;
            blindness = false;
            input_lang = "English";
            trans_input = "en";
            insertSingleTodo(output_lang,input_lang,trans_input,blindness,speech_rate);
//            Toast.makeText(MainActivity.this,UserDeafultLanguage,Toast.LENGTH_LONG).show();
            Log.d("first time", "onCreate: "+output_lang);
        }else{
                Settings currset = finalset.get(0);
                speech_rate = currset.rate;
                output_lang = currset.output_speech;
                blindness = currset.blindness;
                input_lang = currset.input_lang;
                trans_input =currset.trans_input;
        }
        textToSpeech.setLanguage(new Locale(output_lang));
        for( Map.Entry<String, String> entry : languageMap.entrySet()){
            if(entry.getValue().equals(output_lang)){
                speaklang = entry.getKey();
                break;
            }
        }
        Log.d("test", "onSettingsListLoaded: "+finalset);
    }

    public void getAllSettings(final SettingsListCallback callback) {
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

    public  void deleteATodo(Settings setting) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (setting != null) {
                    MyRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .delete(setting);

                    Log.d("hmlo database", "run: todo has been deleted...");
                }

            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start the shake listener when the activity is resumed
        shakeListener.registerShakeListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the shake listener when the activity is paused
        shakeListener.unregisterShakeListener();
    }
    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);

        }
    }
    private void checkRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO_PERMISSION_CODE
            );
        }
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a command...");

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Speech recognition is not available on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRecording() {
        ImageView micButton = findViewById(R.id.micButton);
        micButton.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0).toLowerCase();

                if (spokenText.contains("object")) {
                    textToSpeech.speak(translationMap.get(speaklang+"_"+"opening object"),TextToSpeech.QUEUE_FLUSH, null, null);
                    Intent intent = new Intent(MainActivity.this, cMainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

                } else if (spokenText.contains("read")) {
                    textToSpeech.speak(translationMap.get(speaklang+"_"+"opening read text"),TextToSpeech.QUEUE_FLUSH, null, null);
                    Intent intent = new Intent(MainActivity.this, TextSpeech.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

                } else if (spokenText.contains("translate")) {
                    textToSpeech.speak(translationMap.get(speaklang+"_"+"opening translate"),TextToSpeech.QUEUE_FLUSH, null, null);
                    Intent intent = new Intent(MainActivity.this, LangTranslate.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

                }else if(spokenText.contains("setting")){
                    textToSpeech.speak(translationMap.get(speaklang+"_"+"opening setting"),TextToSpeech.QUEUE_FLUSH, null, null);
                    Intent intent = new Intent(MainActivity.this, appsettings.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Command not recognized", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void initializeLanguageMap() {
        // Add languages and their Locale codes to the HashMap
        languageMap.put("Afrikaans", "af");
        //languageMap.put("Albanian", "sq");
        languageMap.put("Arabic", "ar");
        languageMap.put("Bengali", "bn");
        languageMap.put("Bulgarian", "bg");
        //languageMap.put("Catalan", "ca");
        languageMap.put("Chinese", "zh");
        //languageMap.put("Croatian", "hr");
        languageMap.put("Czech", "cs");
        languageMap.put("Danish", "da");
        languageMap.put("Dutch", "nl");
        languageMap.put("English", "en");
        languageMap.put("Finnish", "fi");
        languageMap.put("French", "fr");
        languageMap.put("Galician", "gl");
        //languageMap.put("Georgian", "ka");
        languageMap.put("German", "de");
        languageMap.put("Greek", "el");
        languageMap.put("Gujarati", "gu");
        //languageMap.put("Haitian", "ht");
        //languageMap.put("Hebrew", "he");
        languageMap.put("Hindi", "hi");
        languageMap.put("Hungarian", "hu");
        languageMap.put("Icelandic", "is");
        languageMap.put("Indonesian", "id");
        languageMap.put("Italian", "it");
        languageMap.put("Japanese", "ja");
        languageMap.put("Kannada", "kn");
        languageMap.put("Korean", "ko");
        languageMap.put("Latvian", "lv");
        languageMap.put("Lithuanian", "lt");
        //languageMap.put("Macedonian", "mk");
        languageMap.put("Malay", "ms");
        languageMap.put("Malayalam", "ml");
        //languageMap.put("Maltese", "mt");
        languageMap.put("Marathi", "mr");
        languageMap.put("Norwegian", "no");
        languageMap.put("Polish", "pl");
        languageMap.put("Portuguese", "pt");
        languageMap.put("Romanian", "ro");
        languageMap.put("Russian", "ru");
        languageMap.put("Slovak", "sk");
        //languageMap.put("Slovenian", "sl");
        languageMap.put("Spanish", "es");
        //languageMap.put("Swahili", "sw");
        languageMap.put("Swedish", "sv");
        //languageMap.put("Tagalog", "tl");
        languageMap.put("Tamil", "ta");
        languageMap.put("Telugu", "te");
        languageMap.put("Thai", "th");
        languageMap.put("Turkish", "tr");
        languageMap.put("Ukrainian", "uk");
        languageMap.put("Urdu", "ur");
        languageMap.put("Vietnamese", "vi");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        shakeListener.onDestroy();
    }
}