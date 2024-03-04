package com.example.thirdeye;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    public static Vibrator vibe;
    public static HashMap<String, String> translationMap = new HashMap<>();
    private String currdirection;
    public static HashMap<String,String> languageMap = new HashMap<>();
    public static HashMap<String,Map<String,String>> langnamemap = new HashMap<>();
    private static final int SPEECH_REQUEST_CODE = 1;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 1;
    public static TextToSpeech textToSpeech;
    public static String speaklang;
    private MicHandler shakeListener;
    private int i = 0;
    private ImageView eyebtn,settingbtn;
    private String UserDeafultLanguage = Locale.getDefault().getLanguage();
    public static String output_lang = null;
    public static float speech_rate;
    public static boolean blindness;
    public static String input_lang;
    public static String trans_input;
    public static List<Settings> finalset;

    public static Map<String, List<String>> commandmap = new HashMap<>();
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        initializeLanguageMap();
        translationmap.initializetransMap();
        //Commands are Translated
        // Check and request RECORD_AUDIO permission
        checkRecordAudioPermission();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        requestPermission();
        eyebtn = findViewById(R.id.eyebtn);
        Log.d("hello_testing",UserDeafultLanguage);
        eyebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Magnifying.class);
                startActivity(intent);
            }
        });

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result;
                if(output_lang == null) {
                    result = textToSpeech.setLanguage(Locale.getDefault());
                }
                else{
                    result = textToSpeech.setLanguage(new Locale(output_lang));
                }

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
        settingbtn = findViewById(R.id.settings);
        micButton.setOnClickListener(v -> startVoiceRecognition());
        settingbtn.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("flash",false);
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
                intent.putExtra("Translation lang",output_lang);
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
            speech_rate = 1f;
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
//    public  void deleteATodo(Settings setting) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                if (setting != null) {
//                    MyRoomDatabase.getInstance(getApplicationContext())
//                            .userDao()
//                            .delete(setting);
//
//                    Log.d("hmlo database", "run: todo has been deleted...");
//                }
//
//            }
//        }).start();
//    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        // Start the shake listener when the activity is resumed
        shakeListener.registerShakeListener();
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the text you want to translate.");
        // Get the selected language code from the map
        String selectedSourceLanguage = output_lang;
        if (selectedSourceLanguage != null) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedSourceLanguage);
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Speech recognition is not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRecording() {
        ImageView micButton = findViewById(R.id.micButton);
        micButton.performClick();
    }
    private boolean partialmatch(String text, String pattern,int n,int m){
        int dp[][]=new int[2][m+1];
        int res=0;

        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=m;j++)
            {
                if(text.charAt(i-1)==pattern.charAt(j-1))
                {
                    dp[i%2][j]=dp[(i-1)%2][j-1]+1;
                    if(dp[i%2][j]>res)
                        res=dp[i%2][j];
                }
                else dp[i%2][j]=0;
            }
        }
        int t = n;
        if(res > 0.6f*t){
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0).toLowerCase();
                List<String> wordList = Arrays.asList(spokenText.split("\\s+"));
//                Log.d("checking input cmd",wordList.toString());
                Log.d("object cmd",commandmap.get("object").toString());
                Log.d("traslate cmd",commandmap.get("translate").toString());
                Log.d("read cmd",commandmap.get("read").toString());
                boolean ismatched = false;
                //we got the string seperated by space
                if(!ismatched){                   // for object

                    for(String str : Objects.requireNonNull(commandmap.get("object"))){
                        int n = str.length();
                        for(String str2 : wordList) {
                            int m = str2.length();
                            if (partialmatch(str2,str,m, n)) {
                                textToSpeech.speak(translationMap.get(speaklang+"_"+"opening object"),TextToSpeech.QUEUE_FLUSH, null, null);
                                Intent intent = new Intent(MainActivity.this, cMainActivity.class);
                                startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                ismatched = true;
                                break;
                            }
                        }
                        if(ismatched){
                            break;
                        }
                    }
                }
                if(!ismatched){
                    // for object

                    for(String str : Objects.requireNonNull(commandmap.get("read"))){
                        int n = str.length();
                        for(String str2 : wordList) {
                            int m = str2.length();
                            if (partialmatch(str2,str,m, n)) {
                                Log.d("Checking read text",""+str+" "+str2);
                                textToSpeech.speak(translationMap.get(speaklang+"_"+"opening read text"),TextToSpeech.QUEUE_FLUSH, null, null);
                                Intent intent = new Intent(MainActivity.this, TextSpeech.class);
                                startActivity(intent);;
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                ismatched = true;
                                break;
                            }
                        }
                        if(ismatched){
                            break;
                        }
                    }
                }
                if(!ismatched){
                    // for object
                    for(String str : Objects.requireNonNull(commandmap.get("translate"))){
                        int n = str.length();
                        for(String str2 : wordList) {
                            int m = str2.length();
                            if (partialmatch(str2,str,m, n)) {
                                ismatched = true;
                                break;
                            }
                        }
                        if(ismatched){
                            break;
                        }
                    }

                    if(ismatched){
                        // before starting we have itertate over the map for detecting langugages
                        String defaultlang = speaklang;

                        for(Map.Entry<String, String> entry : Objects.requireNonNull(langnamemap.get(speaklang)).entrySet()){
                            String str = entry.getKey();

                            int n =str.length();
                            for(String currword : wordList){
                                int m = currword.length();
                                if (partialmatch(str.toLowerCase(),currword,n,m)) {
                                    defaultlang = langnamemap.get(speaklang).get(str);
                                    break;
                                }
                            }
                        }
                        Log.d("Checking language ", "onActivityResult: "+defaultlang);
                        textToSpeech.speak(translationMap.get(speaklang+"_"+"opening translate")+" "+defaultlang,TextToSpeech.QUEUE_FLUSH, null, null);
                        Intent intent = new Intent(MainActivity.this, LangTranslate.class);
                        intent.putExtra("Translation lang",languageMap.get(defaultlang));
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                        startActivity(intent);
                    }
                }
                if(!ismatched){
                    for(String str : Objects.requireNonNull(commandmap.get("settings"))){
                        int n = str.length();
                        for(String str2 : wordList) {
                            int m = str2.length();
                            if (partialmatch(str2,str,m, n)) {
                                textToSpeech.speak(translationMap.get(speaklang+"_"+"opening setting"),TextToSpeech.QUEUE_FLUSH, null, null);
                                Intent intent = new Intent(MainActivity.this, appsettings.class);
                                startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                ismatched = true;
                                break;
                            }
                        }
                        if(ismatched){
                            break;
                        }
                    }
                }
                if(!ismatched) {
                    for (String str : Objects.requireNonNull(commandmap.get("direction"))) {
                        int n = str.length();
                        for (String str2 : wordList) {
                            int m = str2.length();
                            if (partialmatch(str2,str, m, n)) {
                                ismatched = true;
                                Log.d("Direction Test", ""+translationMap.get(speaklang+"_"+currdirection));
                                textToSpeech.speak(translationMap.get(speaklang+"_"+currdirection),TextToSpeech.QUEUE_FLUSH,null,null);
                                break;
                            }
                        }
                        if (ismatched) {
                            break;
                        }
                    }
                }
                if(!ismatched){
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
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, accelerometerData, 0, accelerometerData.length);
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, magnetometerData, 0, magnetometerData.length);
        }
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        float azimuthInDegrees = (float) Math.toDegrees(orientation[0]);
        azimuthInDegrees = (azimuthInDegrees + 360) % 360;
        String compassDirection = getCompassDirection(azimuthInDegrees);
        currdirection = compassDirection;
        Log.d("Compass Direction", ""+compassDirection);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing Eat Five Star
    }

    private String getCompassDirection(float azimuth) {
        if (azimuth >= 337.5 || azimuth < 22.5) {
            return "North";
        } else if (azimuth >= 22.5 && azimuth < 67.5) {
            return "North-East";
        } else if (azimuth >= 67.5 && azimuth < 112.5) {
            return "East";
        } else if (azimuth >= 112.5 && azimuth < 157.5) {
            return "South-East";
        } else if (azimuth >= 157.5 && azimuth < 202.5) {
            return "South";
        } else if (azimuth >= 202.5 && azimuth < 247.5) {
            return "South-West";
        } else if (azimuth >= 247.5 && azimuth < 292.5) {
            return "West";
        } else {
            return "North-West";
        }
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