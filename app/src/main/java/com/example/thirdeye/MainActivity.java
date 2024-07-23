package com.example.thirdeye;

//import static androidx.camera.core.impl.utils.ContextUtil.getApplicationContext;
//import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Vibrator vibe;
    public static int objdetfps;

    public static boolean deviceHasFlash = false;
    boolean resultflag = true;
    private GoogleApiClient apiClient;
    private long currtime = 0;
    public static HashMap<String, String> translationMap = new HashMap<>();
    private final int CREDENTIAL_PICKER_REQUEST = 120;
    private String currdirection;
    public static HashMap<String, String> languageMap = new HashMap<>();
    public static HashMap<String, Map<String, String>> langnamemap = new HashMap<>();
    private static final int SPEECH_REQUEST_CODE = 1;
    private long starting_time = 0;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 1;
    public static TextToSpeech textToSpeech;
    private static final int RESOLVE_HINT = 100;
    public static String speaklang;
    private boolean a;
    public static MicHandler shakeListener;
    CardView objectButton;
    private int i = 0;
    private CardView eyebtn;
    private CardView settingbtn;
    private String UserDeafultLanguage = Locale.getDefault().getLanguage();
    public static String output_lang = null;
    public static float speech_rate;
    public static boolean blindness;
    public static String input_lang;
    public static String trans_input;
    public static List<Settings> finalset;

    public static Map<String, List<String>> commandmap = new HashMap<>();
    public static SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private EditText fpsnumber;
    private PackageManager pm;
    private SpeechRecognizer sr;

    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];

    private ProgressDialog voiceDialog;

    //    private AlertDialog.Builder builderInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        objdetfps = 30;
//        trackAppInstallation(this);
        pm = getPackageManager();
        deviceHasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = Formatter.formatIpAddress(ipAddress);
        Log.d("ip adress", formatedIpAddress + " " + ipAddress);
//        fpsnumber = findViewById(R.id.fpsnumber);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {
            // Accelerometer sensor is available on this device
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            accelerometer = null;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
            // Magnetometer sensor is available on this device
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        } else {
            // Magnetometer sensor is not available on this device
            magnetometer = null;
        }


        resultflag = true;


        // Call the getPhoneNumber method when needed


        starting_time = System.currentTimeMillis();
//        initializeLanguageMap();
//        translationmap.initializetransMap();
        //Commands are Translated
        // Check and request RECORD_AUDIO permission
        checkRecordAudioPermission();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        requestPermission();
        eyebtn = findViewById(R.id.eyebtn);
        Log.d("hello_testing", UserDeafultLanguage);


        eyebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                speaktext(translationMap.get(speaklang + "_" + "opening magnifier"));
                Intent intent = new Intent(MainActivity.this, Magnifying.class);
                startActivity(intent);
            }
        });

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result;
                if (output_lang == null) {
                    result = textToSpeech.setLanguage(Locale.getDefault());
                } else {
                    result = textToSpeech.setLanguage(new Locale(output_lang));
                }
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language initialization errors here
                }
            } else {
                // Handle Text-to-Speech initialization error
            }
        });
        ImageView micButton = findViewById(R.id.micButton);
        // Initialize ShakeListener
        shakeListener = new MicHandler(this);
        a = false;
        shakeListener.setOnShakeListener(() -> {
            long temp = System.currentTimeMillis();
            if (temp - currtime >= 5000 && !a) {

                currtime = System.currentTimeMillis();
                a = true;
//                Toast.makeText(MainActivity.this, "Shake detected!", Toast.LENGTH_SHORT).show();
//                startRecording();
                micButton.performClick();

            }
        });

        objectButton = findViewById(R.id.objectbutton);
        CardView wordsButton = findViewById(R.id.wordsButton);
        CardView st = findViewById(R.id.btnspeechtotext);
        settingbtn = findViewById(R.id.settings);

        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);

                startVoiceRecognition();
                a = false;
            }
        });
        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                speaktext(translationMap.get(speaklang + "_" + "opening setting"));
                Intent intent = new Intent(MainActivity.this, appsettings.class);
                startActivity(intent);
            }
        });
        objectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                speaktext(translationMap.get(speaklang + "_" + "opening object"));
//                shakeListener.unregisterShakeListener();
                Intent intent = new Intent(MainActivity.this, Yolodetection.class);
                SharedPreferences sharedPreferences = getSharedPreferences("Output_lang", Context.MODE_PRIVATE);

                intent.putExtra("Output_lang", sharedPreferences.getString("langname", "en"));
//                if(!fpsnumber.getText().toString().isEmpty())
//                {
//                    objdetfps = Integer.parseInt(fpsnumber.getText().toString());
//                }

//                intent.putExtra("flash",false);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
        wordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                speaktext(translationMap.get(speaklang + "_" + "opening read text"));
                Intent intent = new Intent(MainActivity.this, TextSpeech.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                Log.d("check lang please ", "" + speaklang);
//                shakeListener.unregisterShakeListener();
                speaktext(translationMap.get(speaklang + "_" + "opening translate"));
                Intent intent = new Intent(MainActivity.this, LangTranslate.class);
                intent.putExtra("Translation lang", output_lang);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
        getAllSettings(this::onSettingsListLoaded);// loading all settings
        scheduleMidnightApiCall(this);
//        requestHint();
    }

    public void insertSingleTodo(String language, String inputlang, String trans_input, boolean blindness, float speech_rate) {
        Settings settings = new Settings(language, inputlang, trans_input, blindness, speech_rate);
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
        if (finalset == null || finalset.size() == 0) {
            speech_rate = 1f;
            output_lang = UserDeafultLanguage;
            SharedPreferences sharedPreferences = getSharedPreferences("Output_lang", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("langname", UserDeafultLanguage);
            editor.apply(); // Save changes
            blindness = false;
            input_lang = "English";
            trans_input = "en";
            insertSingleTodo(output_lang, input_lang, trans_input, blindness, speech_rate);
//            Toast.makeText(MainActivity.this,UserDeafultLanguage,Toast.LENGTH_LONG).show();
            Log.d("first time", "onCreate: " + output_lang);
        } else {
            Settings currset = finalset.get(0);
            speech_rate = currset.rate;
            SharedPreferences sharedPreferences = getSharedPreferences("Output_lang", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("langname", currset.output_speech);
            editor.apply(); // Save changes
            output_lang = currset.output_speech;
            blindness = currset.blindness;
            input_lang = currset.input_lang;
            trans_input = currset.trans_input;
        }
        textToSpeech.setLanguage(new Locale(output_lang));
        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            if (entry.getValue().equals(output_lang)) {
                speaklang = entry.getKey();
                break;
            }
        }
        Log.d("test", "onSettingsListLoaded: " + finalset);
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
    protected void onStart() {
//        int READINGRATE = 500000;
        shakeListener.registerShakeListener();
//        getAllSettings(this::onSettingsListLoaded);
        if (magnetometer != null) {
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(MainActivity.this, "Compass is not supported with your device", Toast.LENGTH_LONG).show();
        }
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result;
                if (output_lang == null) {
                    result = textToSpeech.setLanguage(Locale.getDefault());
                } else {
                    result = textToSpeech.setLanguage(new Locale(output_lang));
                }
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language initialization errors here
                }
            } else {
                // Handle Text-to-Speech initialization error
            }
        });
        super.onStart();
    }

    @Override
    protected void onResume() {
//        getAllSettings(this::onSettingsListLoaded);
        translationmap.initializetransMap();
        shakeListener.registerShakeListener();
        if (magnetometer != null) {
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(MainActivity.this, "Compass is not supported with your device", Toast.LENGTH_LONG).show();
        }
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result;
                if (output_lang == null) {
                    result = textToSpeech.setLanguage(Locale.getDefault());
                } else {
                    result = textToSpeech.setLanguage(new Locale(output_lang));
                }
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language initialization errors here
                }
            } else {
                // Handle Text-to-Speech initialization error
            }
        });
        super.onResume();

        // Start the shake listener when the activity is resumed

    }

    @Override
    protected void onRestart() {
        translationmap.initializetransMap();
        shakeListener.registerShakeListener();
//        getAllSettings(this::onSettingsListLoaded);
        super.onRestart();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        shakeListener.unregisterShakeListener();
        super.onPause();

        // Stop the shake listener when the activity is paused


    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        shakeListener.unregisterShakeListener();
        super.onStop();


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
        resultflag = false;
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Commands...");
        // Get the selected language code from the map
        String selectedSourceLanguage = output_lang;
        //Custom Voice Starts
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
//                Toast.makeText(MainActivity.this, "Ready for speech", Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog(speaklang == null ? "English" : speaklang);
                    }
                });

            }

            @Override
            public void onBeginningOfSpeech() {
//                Toast.makeText(MainActivity.this, "Speech started", Toast.LENGTH_SHORT).show();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });


            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // You can update UI here with rmsdB value if you want
                Log.d("checking decibels", "" + rmsdB);
                voiceDialog.setProgress(Math.max(0, (int) rmsdB));
            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
//                Toast.makeText(MainActivity.this, "Speech ended", Toast.LENGTH_SHORT).show();
//                voiceDialog.dismiss();
            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "No Text Detected", Toast.LENGTH_SHORT).show();
                voiceDialog.dismiss();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    String resultText = matches.get(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            voiceDialog.setMessage("Result : " + resultText);
                        }
                    });

//                TranslatorOptions options = new TranslatorOptions.Builder()
//                    .setSourceLanguage(output_lang)
//                    .setTargetLanguage("en")
//                    .build();
//            Translator translator = Translation.getClient(options);
                    resultflag = true;
//                        ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String spokenText = resultText.toLowerCase();
//                        Log.d("Just Spoken",""+spokenText);
//            translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
////                                    myRunnable.stop();
//                    Task<String> result = translator.translate(spokenText).addOnSuccessListener(new OnSuccessListener<String>() {
//                        @Override
//                        public void onSuccess(String s) {
//                            Log.d("Translated String",""+s);
//                            s = s.toLowerCase();
//                            if(s.contains("objec")){
//                                Intent intent = new Intent(MainActivity.this,Yolodetection.class);
//                                startActivity(intent);
//                            }else if(s.contains("translat")){
//                                Intent intent = new Intent(MainActivity.this,LangTranslate.class);
//                                startActivity(intent);
//                            }else if(s.contains("magnifier") || s.contains("magnify")){
//                                Intent intent = new Intent(MainActivity.this,Magnifying.class);
//                                startActivity(intent);
//                            }else if(s.contains("setting")){
//                                Intent intent = new Intent(MainActivity.this,appsettings.class);
//                                startActivity(intent);
//                            }else if(s.contains("read")){
//                                Intent intent = new Intent(MainActivity.this,TextSpeech.class);
//                                startActivity(intent);
//                            }
//                            // Make the TextView visible
//
////                                            textToSpeech.setLanguage(new Locale(Objects.requireNonNull(languageMap.get(sourceLanguageSpinner.getSelectedItem()))));
//
//
//
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
////                                    myRunnable.stop();
//
//
//                }
//            });

                    if (matches != null && !matches.isEmpty()) {
//                String spokenText = matches.get(0).toLowerCase();
                        List<String> wordList = Arrays.asList(spokenText.split("\\s+"));
//                Log.d("checking input cmd",wordList.toString());

                        boolean ismatched = false;
                        //we got the string seperated by space
                        if (!ismatched) {                   // for object

                            for (String str : Objects.requireNonNull(commandmap.get("object"))) {
                                int n = str.length();
                                for (String str2 : wordList) {
                                    int m = str2.length();
                                    if (partialmatch(str2, str, m, n, 0.7f)) {
//                                Log.d("here called",""+1);
//                                speaktext(translationMap.get(speaklang + "_" + "opening object"));
//                                Intent intent = new Intent(MainActivity.this, Yolodetection.class);
////                                if(!fpsnumber.getText().toString().isEmpty())
////                                {
////                                    objdetfps = Integer.parseInt(fpsnumber.getText().toString());
////                                }
//                                startActivity(intent);
                                        objectButton.performClick();
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                        ismatched = true;
                                        break;
                                    }
                                }
                                if (ismatched) {
                                    break;
                                }
                            }
                        }
                        if (!ismatched) {
                            // for object

                            for (String str : Objects.requireNonNull(commandmap.get("read"))) {
                                int n = str.length();
                                for (String str2 : wordList) {
                                    int m = str2.length();
//                            Log.d("Checking Strings",""+str+" "+str2+" "+partialmatch(str2, str, m, n,0.7f));
                                    if (partialmatch(str2, str, m, n, 0.7f)) {
                                        Log.d("Checking read text", "" + str + " " + str2);
                                        speaktext(translationMap.get(speaklang + "_" + "opening read text"));
                                        Log.d("here called", "" + 2);
                                        Intent intent = new Intent(MainActivity.this, TextSpeech.class);
                                        startActivity(intent);
                                        ;
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                        ismatched = true;
                                        break;
                                    }
                                }
                                if (ismatched) {
                                    break;
                                }
                            }
                        }
                        if (!ismatched) {
                            // for object
                            for (String str : Objects.requireNonNull(commandmap.get("translate"))) {
                                int n = str.length();
                                for (String str2 : wordList) {
                                    int m = str2.length();
                                    if (partialmatch(str2, str, m, n, 0.7f)) {
                                        ismatched = true;
                                        Log.d("here called", "" + 3);
                                        break;
                                    }
                                }
                                if (ismatched) {
                                    break;
                                }
                            }

                            if (ismatched) {
                                // before starting we have itertate over the map for detecting langugages
                                String defaultlang = speaklang;
                                Log.d("am i null", "" + defaultlang + " " + langnamemap.get(speaklang));

                                for (Map.Entry<String, String> entry : Objects.requireNonNull(langnamemap.get(speaklang)).entrySet()) {
                                    String str = entry.getKey();

                                    int n = str.length();
                                    for (String currword : wordList) {
                                        int m = currword.length();
                                        if (partialmatch(str.toLowerCase(), currword, n, m, 0.7f)) {
                                            defaultlang = langnamemap.get(speaklang).get(str);
                                            break;
                                        }
                                    }
                                }
//                            Log.d("Checking language i am called ", "onActivityResult: " + defaultlang);
                                speaktext(translationMap.get(speaklang + "_" + "opening translate"));
                                Intent intent = new Intent(MainActivity.this, LangTranslate.class);
                                intent.putExtra("Translation lang", languageMap.get(defaultlang));
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                startActivity(intent);
                            }
                        }
                        if (!ismatched) {
                            for (String str : Objects.requireNonNull(commandmap.get("magnifier"))) {
                                int n = str.length();
                                for (String str2 : wordList) {
                                    int m = str2.length();
                                    if (partialmatch(str2, str, m, n, 0.7f)) {
                                        Log.d("here called", "" + 6);
                                        Log.d("Check Strings 2", str2 + " " + str);
                                        speaktext(translationMap.get(speaklang + "_" + "opening magnifier"));
                                        Intent intent = new Intent(MainActivity.this, Magnifying.class);
                                        startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                        ismatched = true;
                                        break;
                                    }
                                }
                                if (ismatched) {
                                    break;
                                }
                            }
                        }
                        if (!ismatched) {
                            for (String str : Objects.requireNonNull(commandmap.get("settings"))) {
                                int n = str.length();
                                for (String str2 : wordList) {
                                    int m = str2.length();
                                    if (partialmatch(str2, str, m, n, 0.7f)) {
                                        Log.d("Checking stringshello 2", "" + str + " " + str2);
                                        Log.d("here called", "" + 4);
                                        speaktext(translationMap.get(speaklang + "_" + "opening setting"));
                                        Intent intent = new Intent(MainActivity.this, appsettings.class);
                                        startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                        ismatched = true;
                                        break;
                                    }
                                }
                                if (ismatched) {
                                    break;
                                }
                            }
                        }
                        if (!ismatched) {
                            for (String str : Objects.requireNonNull(commandmap.get("direction"))) {
                                int n = str.length();
                                for (String str2 : wordList) {
                                    int m = str2.length();
                                    if (partialmatch(str2, str, m, n, 0.7f)) {
                                        ismatched = true;
                                        Log.d("here called", "" + 5);
                                        Log.d("Direction Test", "" + translationMap.get(speaklang + "_" + currdirection));

                                        textToSpeech.speak(translationMap.get(speaklang + "_" + currdirection), TextToSpeech.QUEUE_FLUSH, null, null);

//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // Speak direction
//                                        speaktext(translationMap.get(speaklang + "_" + currdirection));
//                                        Log.d("Direction Test", "" + translationMap.get(speaklang + "_" + currdirection));
//
//                                        // Assuming MyAsyncTask is the AsyncTask instance
//                                    }
//                                }, 1000);

//                                    sensorManager.unregisterListener(this);

                                        break;
                                    }
                                }

                                if (ismatched) {
                                    break;
                                }
                            }
                        }

                        if (!ismatched) {
                            Toast.makeText(MainActivity.this, "Command not recognized", Toast.LENGTH_SHORT).show();
                        }
                    }


//                    Toast.makeText(MainActivity.this, "Results: " + resultText, Toast.LENGTH_SHORT).show();
                }
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        voiceDialog.dismiss();
                    }
                };
                handler.postDelayed(runnable, 1500);
//                voiceDialog.dismiss();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {


            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });


        if (selectedSourceLanguage != null) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedSourceLanguage);
        }
        sr.startListening(intent);
        //Custom Voice ends

// Google
//        try {
//            startActivityForResult(intent, SPEECH_REQUEST_CODE);
//
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(this, "Speech recognition is not supported on this device.", Toast.LENGTH_SHORT).show();
//        }
        // Google Voice

    }

    private void showAlertDialog(String language) {
//         builderInput = new AlertDialog.Builder(this);
//
//        // Inflate custom layout for the dialog
//        // Note: You can use a layout file if needed
//        // builder.setView(R.layout.custom_dialog_layout);
//
//        // Set dialog title and message
//        builderInput.setTitle("Spoken Text")
//                .setMessage("Language: " +language);


        // Create and show the dialog
        voiceDialog = new ProgressDialog(this);
//        voiceDialog.setMax(10);
//        voiceDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        voiceDialog.setTitle("Language : " + language);
        voiceDialog.setMessage("Listening...");
        voiceDialog.setCancelable(true);
        voiceDialog.setOnCancelListener(dialog -> stopSpeechRecognition());

        voiceDialog.show();

    }

    private void stopSpeechRecognition() {
        if (sr != null) {
            sr.stopListening();
            sr.cancel();
            sr.destroy();
            sr = null;
        }
    }

    private void startRecording() {
        ImageView micButton = findViewById(R.id.micButton);
        micButton.performClick();
    }

    private boolean partialmatch(String text, String pattern, int n, int m, float threshold) {
        int dp[][] = new int[2][m + 1];
        int res = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text.charAt(i - 1) == pattern.charAt(j - 1)) {
                    dp[i % 2][j] = dp[(i - 1) % 2][j - 1] + 1;
                    if (dp[i % 2][j] > res)
                        res = dp[i % 2][j];
//                    ++res;
                } else dp[i % 2][j] = 0;
            }
        }
        int t = n;
//        Log.d("")
        Log.d("Checking Strings", "" + res + " " + threshold * t + " " + text + " " + pattern);
        if (res > threshold * t) {
            return true;
        }
        return false;
    }

    private void speaktext(String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        shakeListener.registerShakeListener();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK && data != null) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    String phoneNumber = credential.getId(); // Process the phone number string
                    // Use the phone number as needed

                }
            }
        }
        // Google default Voice Input Output here
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {

                resultflag = true;
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String spokenText = matches.get(0).toLowerCase();
                Log.d("Just Spoken", "" + spokenText);


                if (matches != null && !matches.isEmpty()) {

                    List<String> wordList = Arrays.asList(spokenText.split("\\s+"));


                    boolean ismatched = false;
                    //we got the string seperated by space
                    if (!ismatched) {                   // for object

                        for (String str : Objects.requireNonNull(commandmap.get("object"))) {
                            int n = str.length();
                            for (String str2 : wordList) {
                                int m = str2.length();
                                if (partialmatch(str2, str, m, n, 0.7f)) {
//
                                    objectButton.performClick();

                                    ismatched = true;
                                    break;
                                }
                            }
                            if (ismatched) {
                                break;
                            }
                        }
                    }
                    if (!ismatched) {


                        for (String str : Objects.requireNonNull(commandmap.get("read"))) {
                            int n = str.length();
                            for (String str2 : wordList) {
                                int m = str2.length();

                                if (partialmatch(str2, str, m, n, 0.7f)) {
                                    Log.d("Checking read text", "" + str + " " + str2);
                                    speaktext(translationMap.get(speaklang + "_" + "opening read text"));
                                    Log.d("here called", "" + 2);
                                    Intent intent = new Intent(MainActivity.this, TextSpeech.class);
                                    startActivity(intent);
                                    ;

                                    ismatched = true;
                                    break;
                                }
                            }
                            if (ismatched) {
                                break;
                            }
                        }
                    }
                    if (!ismatched) {
                        // for object
                        for (String str : Objects.requireNonNull(commandmap.get("translate"))) {
                            int n = str.length();
                            for (String str2 : wordList) {
                                int m = str2.length();
                                if (partialmatch(str2, str, m, n, 0.7f)) {
                                    ismatched = true;
                                    Log.d("here called", "" + 3);
                                    break;
                                }
                            }
                            if (ismatched) {
                                break;
                            }
                        }

                        if (ismatched) {
                            // before starting we have itertate over the map for detecting langugages
                            String defaultlang = speaklang;
                            Log.d("am i null", "" + defaultlang + " " + langnamemap.get(speaklang));

                            for (Map.Entry<String, String> entry : Objects.requireNonNull(langnamemap.get(speaklang)).entrySet()) {
                                String str = entry.getKey();

                                int n = str.length();
                                for (String currword : wordList) {
                                    int m = currword.length();
                                    if (partialmatch(str.toLowerCase(), currword, n, m, 0.7f)) {
                                        defaultlang = langnamemap.get(speaklang).get(str);
                                        break;
                                    }
                                }
                            }

                            speaktext(translationMap.get(speaklang + "_" + "opening translate"));
                            Intent intent = new Intent(MainActivity.this, LangTranslate.class);
                            intent.putExtra("Translation lang", languageMap.get(defaultlang));
                            startActivity(intent);
                        }
                    }
                    if (!ismatched) {
                        for (String str : Objects.requireNonNull(commandmap.get("magnifier"))) {
                            int n = str.length();
                            for (String str2 : wordList) {
                                int m = str2.length();
                                if (partialmatch(str2, str, m, n, 0.7f)) {
                                    Log.d("here called", "" + 6);
                                    Log.d("Check Strings 2", str2 + " " + str);
                                    speaktext(translationMap.get(speaklang + "_" + "opening magnifier"));
                                    Intent intent = new Intent(MainActivity.this, Magnifying.class);
                                    startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                    ismatched = true;
                                    break;
                                }
                            }
                            if (ismatched) {
                                break;
                            }
                        }
                    }
                    if (!ismatched) {
                        for (String str : Objects.requireNonNull(commandmap.get("settings"))) {
                            int n = str.length();
                            for (String str2 : wordList) {
                                int m = str2.length();
                                if (partialmatch(str2, str, m, n, 0.7f)) {
                                    Log.d("Checking stringshello 2", "" + str + " " + str2);
                                    Log.d("here called", "" + 4);
                                    speaktext(translationMap.get(speaklang + "_" + "opening setting"));
                                    Intent intent = new Intent(MainActivity.this, appsettings.class);
                                    startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                                    ismatched = true;
                                    break;
                                }
                            }
                            if (ismatched) {
                                break;
                            }
                        }
                    }
                    if (!ismatched) {
                        for (String str : Objects.requireNonNull(commandmap.get("direction"))) {
                            int n = str.length();
                            for (String str2 : wordList) {
                                int m = str2.length();
                                if (partialmatch(str2, str, m, n, 0.7f)) {
                                    ismatched = true;
                                    Log.d("here called", "" + 5);
                                    Log.d("Direction Test", "" + translationMap.get(speaklang + "_" + currdirection));

                                    textToSpeech.speak(translationMap.get(speaklang + "_" + currdirection), TextToSpeech.QUEUE_FLUSH, null, null);

                                    break;
                                }
                            }

                            if (ismatched) {
                                break;
                            }
                        }


                    }

                    if (!ismatched) {
                        Toast.makeText(this, "Command not recognized", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
//                Toast.makeText(MainActivity.this,"Hellp ",Toast.LENGTH_LONG).show();
            }
//
        }
    }

    // Handles the changes of Compass Direction
    @Override
    public void onSensorChanged(SensorEvent event) {

//        if(temptime-compasstime < 1000){
//            return;
//        }

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
        Log.d("Compass Direction", "" + compassDirection);
    }

    @Override
    public void onBackPressed() {


        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        sensorManager.unregisterListener(this);
        shakeListener.unregisterShakeListener();
        shakeListener.onDestroy();

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Generalization of Compass degrees
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

        sensorManager.unregisterListener(this);
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        shakeListener.unregisterShakeListener();
        shakeListener.onDestroy();
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time - starting_time);
        userPreferences.addFeature(time, "Home Page");
        Log.d("Duration check", "" + time + " " + userPreferences.getFeatureListAsJsonArray());
        super.onDestroy();

    }

    // Scheduling the Midnight API CALL
    public static void scheduleMidnightApiCall(Context context) {
        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an intent to trigger the BroadcastReceiver
        Intent intent = new Intent(context, MidnightApiCallReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to start at midnight
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // If the time is in the past, add one day
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Set a repeating alarm to trigger every day at midnight
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d("MainActivity", "Midnight API call scheduled");

    }
}