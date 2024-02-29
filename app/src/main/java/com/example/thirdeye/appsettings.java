package com.example.thirdeye;

import static android.provider.Telephony.TextBasedSmsColumns.STATUS;
import static java.util.Arrays.stream;
import java.util.Calendar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import androidx.appcompat.widget.SwitchCompat;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateRemoteModel;

import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class appsettings extends AppCompatActivity {

    private SeekBar seekBarSpeechRate;
    private MicHandler shakeListener;
    Calendar calendar;
    ProgressDialog progressDialog2;
    private Spinner spinnerDefaultLanguage;
    private SwitchCompat switchPartiallyBlind;
    private LinearLayout llsettings;
    private Button applybtn;
    private TextToSpeech textToSpeech;
    private Button cancelbtn;
    private HashMap<String, String> languageMap = new HashMap<>();
    private HashMap<Integer, String> WeekDays = new HashMap<>();
    private ProgressDialog progressDialog;
    private String ouptutlang = MainActivity.output_lang;
    private boolean blindness = MainActivity.blindness;
    private float rate = MainActivity.speech_rate;
    private String inputlang = MainActivity.input_lang;
    private String trans_input = MainActivity.trans_input;
    private boolean alreadydownloaded = false;
    private ImageView backbtn;
    private Button feedbackBtn;
    private boolean buttonClickable = true;
    private String speaklang;
    private boolean toret = false;

    private List<Settings> finalset;
    private static final int SPEECH_REQUEST_CODE = 1;
    private List<String> languages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Initialize views
        TextView textSpeechRate = findViewById(R.id.textSpeechRate);
        TextView textDefaultLanguage = findViewById(R.id.textDefaultLanguage);
        TextView textPartiallyBlind = findViewById(R.id.textPartiallyBlind);
//        spinnerinputlang = findViewById(R.id.spinnerDefaultinputlanguage);
//        spinnertranslanguage = findViewById(R.id.spinnertransinputlanguage);
        seekBarSpeechRate = findViewById(R.id.sliderSpeechRate);
        spinnerDefaultLanguage = findViewById(R.id.spinnerDefaultLanguage);
        switchPartiallyBlind = findViewById(R.id.partallyblindswitch);
        llsettings = findViewById(R.id.llsettings);
        backbtn = findViewById(R.id.backbtn_);
        applybtn = findViewById(R.id.buttonApply);
        feedbackBtn = findViewById(R.id.buttonfeedback);
//        cancelbtn = findViewById(R.id.buttonCancel);
        initializetexttospeech();
        getAllSettings(this::onSettingsListLoaded);
        initializelanguageMap();
        for( Map.Entry<String, String> entry : languageMap.entrySet()){
            if(entry.getValue().equals(ouptutlang)){
                speaklang = entry.getKey();
                break;
            }
        }

        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(appsettings.this, FeedbackActivity.class));
            }
        });

        seekBarSpeechRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            boolean check = true;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // This method is called when the progress of the seek bar changes
                    calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    textToSpeech.speak("Today is " + WeekDays.get(dayOfWeek), TextToSpeech.QUEUE_FLUSH, null, null);
                    textToSpeech.setSpeechRate((progress / 100.0f) * 2.0f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This method is called when the user starts touching the seek bar

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // This method is called when the user stops touching the seek bar
                    calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    int currprogg = seekBarSpeechRate.getProgress();
                    textToSpeech.speak("Today is " + WeekDays.get(dayOfWeek), TextToSpeech.QUEUE_FLUSH, null, null);
                    textToSpeech.setSpeechRate((currprogg / 100.0f) * 2.0f);
            }
        });
        shakeListener = new MicHandler(this);
        shakeListener.setOnShakeListener(() -> {
            Toast.makeText(appsettings.this, "Shake detected!", Toast.LENGTH_SHORT).show();
            startVoiceRecognition();
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appsettings.this,MainActivity.class);
                startActivity(intent);

            }
        });
        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickable) {

                    downloadLanguage(languageMap.get(spinnerDefaultLanguage.getSelectedItem().toString()));
                    final ProgressDialog progressDialog = new ProgressDialog(appsettings.this);
                    progressDialog.setMessage("Applying all the changes...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    applybtn.setEnabled(false); // Disable the button
                    buttonClickable = false;
    //                    applyProgressDialog();


                    // Re-enable the button after 1 second
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            applybtn.setEnabled(true);
                            progressDialog.dismiss();
                            buttonClickable = true;
                        }
                    }, 1000); // 1 second
                }
            }

        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        Log.d("getting widht of ll",""+width+"pixels");

        languages = new ArrayList<>(languageMap.values());
        List<String> keys = new ArrayList<>(languageMap.keySet());


        List<String> textDetList = new ArrayList<>(Arrays.asList("English", "Hindi","Marathi","Japanese","Korean"));

        ArrayAdapter<String> text_det_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, textDetList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,keys);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        text_det_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//        spinnertranslanguage.setDropDownVerticalOffset(dpToPx(26));
//        spinnerDefaultLanguage.setDropDownVerticalOffset(dpToPx(26));
        spinnerDefaultLanguage.setDropDownHorizontalOffset(dpToPx(-10));
//        spinnerinputlang.setDropDownHorizontalOffset(dpToPx(-10));
//        spinnertranslanguage.setDropDownHorizontalOffset(dpToPx(-10));
//        spinnertranslanguage.setDropDownWidth(width-dpToPx(40));
//        spinnerinputlang.setDropDownWidth(width-dpToPx(40));
        spinnerDefaultLanguage.setDropDownWidth(width-dpToPx(40));
        spinnerDefaultLanguage.setAdapter(adapter);
//        spinnertranslanguage.setAdapter(adapter);
//        spinnerinputlang.setAdapter(text_det_adapter);
        spinnerDefaultLanguage.setSelection(languages.indexOf(ouptutlang));
//        spinnertranslanguage.setSelection(languages.indexOf(trans_input)); // this is for translation model have to update the database
//        spinnerinputlang.setSelection(textDetList.indexOf(inputlang));
        switchPartiallyBlind.setChecked(blindness);
        seekBarSpeechRate.setProgress((int)(rate*100/3.0f));
        // Setup Switch for Partially Blind
    }
    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the text you want to translate.");

        // Get the selected language code from the map
        String selectedSourceLanguage = languageMap.get(spinnerDefaultLanguage.getSelectedItem().toString());

        if (selectedSourceLanguage != null) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedSourceLanguage);
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Speech recognition is not supported on this device.", Toast.LENGTH_SHORT).show();
        }
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
        int t = Math.min(m,n);
        if(res > 0.6f*t){
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0).toLowerCase();
                Log.d("inside mic intent",spokenText+" "+speaklang+ouptutlang);
                //here i will make the shake listener for changing the language
                List<String> wordList = Arrays.asList(spokenText.split("\\s+"));
                boolean islanguage = false;
                for(String str : wordList){
                    int n = str.length();
                    for(String str2 : Objects.requireNonNull(MainActivity.commandmap.get("Language"))){
                        int m = str2.length();
                        if(partialmatch(str,str2,n,m)){
                            islanguage = true;
                            break;
                        }
                    }
                    if(islanguage){
                        break;
                    }
                }
                // islanguage is found now search for language
                boolean islangdetected = false;
                String defaultlang = spinnerDefaultLanguage.getSelectedItem().toString();
                Log.d("inside mic intent",defaultlang);
                for(Map.Entry<String, String> entry : Objects.requireNonNull(MainActivity.langnamemap.get(defaultlang)).entrySet()){
                    String str = entry.getKey();

                    int n =str.length();
                    for(String currword : wordList){
                        int m = currword.length();
                        if (partialmatch(str.toLowerCase(),currword,n,m)) {
                            defaultlang = Objects.requireNonNull(MainActivity.langnamemap.get(defaultlang)).get(str);
                            islangdetected = true;
                            break;
                        }
                    }
                    if(islangdetected)break;
                }
                if(islangdetected && islanguage){
                    spinnerDefaultLanguage.setSelection(languages.indexOf(languageMap.get(defaultlang)));
                    applybtn.performClick();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    public void initializetexttospeech(){
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("en"));
                } else {
                    Toast.makeText(appsettings.this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT).show();
                }
            }
        },"com.google.android.tts");
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
    private void applyProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Applying all the changes...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1500);
    }


    public void downloadLanguage(String language) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        toret = false;
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage("en")
                .setTargetLanguage(language)
                .build();
        RemoteModelManager modelManager = RemoteModelManager.getInstance();
        modelManager.getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(translateModels -> {
                    Settings st = finalset.get(0);
                    boolean alreadyDownloaded = false;
                    for (TranslateRemoteModel model : translateModels) {
                        String storedLang = model.getLanguage();
                        Log.d("languagemodels", "downloadlanguage: "+storedLang);
                        if (storedLang.equals(language)) {
                            alreadyDownloaded = true;
                            break;
                        }
                    }
                    if (alreadyDownloaded) {
                        toret = true;
                    } else if(networkInfo == null || !networkInfo.isConnected()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(appsettings.this);
                        builder.setMessage("No internet connection. Please check your connection and try again.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    } else {
                        toret = true;
                        runOnUiThread(() -> showProgressDialog()); // Show progress dialog on main UI thread
                        downloadModel(options,progressDialog);

                    }
                    Log.d("checking before","I should be before the returning");
                     new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            // Implementation of query (executed on a background thread)



                            if (st != null) {
                                MyRoomDatabase.getInstance(getApplicationContext())
                                        .userDao()
                                        .delete(st);
                                Log.d("hmlo database", "run: todo has been deleted..."+st);
                            }

                            if(toret) {
                                st.output_speech = languageMap.get(spinnerDefaultLanguage.getSelectedItem().toString());
                            }
//                            st.trans_input = languageMap.get(spinnertranslanguage.getSelectedItem().toString());
//                            st.input_lang = spinnerinputlang.getSelectedItem().toString();
                            st.rate = (seekBarSpeechRate.getProgress() / 100.0f) * 2.0f;
                            st.blindness = switchPartiallyBlind.isChecked();
                            MainActivity.input_lang = st.input_lang;
                            MainActivity.output_lang = st.output_speech;
                            MainActivity.blindness = st.blindness;
                            MainActivity.speech_rate = st.rate;
                            MainActivity.trans_input = st.trans_input;
                            for( Map.Entry<String, String> entry : languageMap.entrySet()){
                                if(entry.getValue().equals(st.output_speech)){
                                    MainActivity.speaklang = entry.getKey();
                                    break;
                                }
                            }
                            MainActivity.textToSpeech.setLanguage(new Locale(st.output_speech));
                            insertSingleTodo(st);
                            return null;

                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            // Update UI or perform any post-execution tasks here

                        }
                    }.execute();

                    ; // Callback indicating completion

                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(appsettings.this, "Failed to retrieve downloaded models.", Toast.LENGTH_SHORT).show();
                     // Callback indicating failure
                });
        Log.d("returning", "downloadlanguage: "+toret);
    }


    private void showProgressDialog() {
        progressDialog = new ProgressDialog(appsettings.this);
        progressDialog.setMessage("Downloading the translation model...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    private void downloadModel(TranslatorOptions options, ProgressDialog progressDialog) {
        Translator translator = Translation.getClient(options);

        translator.downloadModelIfNeeded()
                .addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    // Model downloaded successfully, proceed with translation
                    // You can add your translation logic here
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    // Model download failed, handle the failure
                    Toast.makeText(appsettings.this, "Language can't be loaded", Toast.LENGTH_SHORT).show();
                });
    }
    public void insertSingleTodo(Settings settings) {
//        Settings settings = new Settings(language,blindness,speech_rate);
        appsettings.InsertAsyncTask insertAsyncTask = new appsettings.InsertAsyncTask();
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
        Log.d("test", "onSettingsListLoaded: "+finalset);
    }

    public void getAllSettings(final MainActivity.SettingsListCallback callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Settings> todoList = MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .getAll();
                callback.onSettingsListLoaded(todoList);

            }
        });
        thread.start();
    }
    private void initializelanguageMap(){
        WeekDays.put(1, "Sunday");
        WeekDays.put(2, "Monday");
        WeekDays.put(3, "Tuesday");
        WeekDays.put(4, "Wednesday");
        WeekDays.put(5, "Thursday");
        WeekDays.put(6, "Friday");
        WeekDays.put(7, "Saturday");
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
}