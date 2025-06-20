package com.example.thirdeye;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class appsettings extends AppCompatActivity {

    private Vibrator vibe;
    private SeekBar seekBarSpeechRate;
    private boolean isChanged = false;
    // This variable is set true when only the setting is been changed
    //When the setting is saved it will set again false
    private long touchtim = 0;
    private long starting_time = 0;
    private boolean isAlert = false;
    private boolean isSaveClicked = false;
    // this variable sets true when user saves the setting

    private MicHandler shakeListener;
    Calendar calendar;


    private Spinner spinnerDefaultLanguage;
    private SwitchCompat switchPartiallyBlind;

    private Button applybtn;
    private TextToSpeech textToSpeech;

    private HashMap<String, String> languageMap = new HashMap<>();
    private HashMap<Integer, String> WeekDays = new HashMap<>();
    private ProgressDialog progressDialog, progressDialog2;
    private boolean isfirstime = false;
    private String ouptutlang = MainActivity.output_lang;
    private boolean blindness = MainActivity.blindness;
    private float rate = MainActivity.speech_rate;
    private ImageButton backbtn;
    private Button aboutbtn;
    private Button feedbackBtn;
    private boolean buttonClickable = true;
    private String speaklang;
    private boolean toret = false;


    private List<Settings> finalset;
    private static final int SPEECH_REQUEST_CODE = 1;
    private int width;
    private List<String> languages = new ArrayList<>();
    public static Map<String, String> languageLocalMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        seekBarSpeechRate = findViewById(R.id.sliderSpeechRate);
        spinnerDefaultLanguage = findViewById(R.id.spinnerDefaultLanguage);
        isSaveClicked = false;
        isChanged = false;
        starting_time = System.currentTimeMillis();
        switchPartiallyBlind = findViewById(R.id.partallyblindswitch);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        backbtn = findViewById(R.id.backbtn_);
        applybtn = findViewById(R.id.buttonApply);
        aboutbtn = findViewById(R.id.aboutbtn);
        feedbackBtn = findViewById(R.id.buttonfeedback);
        progressDialog2 = new ProgressDialog(appsettings.this);
        progressDialog2.setMessage("Applying all the changes...");
        progressDialog2.setCancelable(false);
        initializelanguageMap();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initializetexttospeech();
        getAllSettings(this::onSettingsListLoaded);
        // Getting the language in text format
        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            if (entry.getValue().equals(ouptutlang)) {
                speaklang = entry.getKey();
                break;
            }
        }
        // Initialising on click in about button
        aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appsettings.this,About.class);
                startActivity(intent);
            }
        });
        // Initialising the on click feedback button
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);

                startActivity(new Intent(appsettings.this, FeedbackActivity.class));
            }
        });
        //Initialising the  speech rate seekbar
        seekBarSpeechRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // This method is called when the progress of the seek bar changes

                isChanged = true;
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
                isChanged = true;
                textToSpeech.speak("Today is " + WeekDays.get(dayOfWeek), TextToSpeech.QUEUE_FLUSH, null, null);
                textToSpeech.setSpeechRate((currprogg / 100.0f) * 2.0f);
            }
        });

        // This implements the shake listner
//        shakeListener = new MicHandler(this);
//        shakeListener.setOnShakeListener(() -> {
//            Toast.makeText(appsettings.this, "Shake detected!", Toast.LENGTH_SHORT).show();
//            startVoiceRecognition();
//        });
        // Back Button Initialisation
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
//                Intent intent = new Intent(appsettings.this,MainActivity.class);
//                startActivity(intent);
                onBackPressed();
            }
        });
        // Apply Button Initialisation
        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (buttonClickable) {
                    vibe.vibrate(50);
                    downloadLanguage(languageMap.get(spinnerDefaultLanguage.getSelectedItem().toString()), isAlert);
                    progressDialog2.show();
//                    applybtn.setEnabled(false); // Disable the button
                    isSaveClicked = true;
                    buttonClickable = false;
                    isChanged = false;
                    // This handler is called only
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog2.dismiss();
                            buttonClickable = true;
                            if (isAlert) {
                                appsettings.super.onBackPressed();
                            }

                        }
                    }, 1000); // 1 second
                }
            }

        });
        // Setting LanguageSpinners
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        languages = new ArrayList<>(languageMap.values());
        List<String> keys = new ArrayList<>(languageMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, keys);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinnerDefaultLanguage.setDropDownHorizontalOffset(dpToPx(-10));
        spinnerDefaultLanguage.setDropDownWidth(width - dpToPx(40));
        spinnerDefaultLanguage.setAdapter(adapter);
        spinnerDefaultLanguage.setSelection(languages.indexOf(ouptutlang));
        switchPartiallyBlind.setChecked(blindness);
        seekBarSpeechRate.setProgress((int) (rate * 100.0f / 2.0f));
        // Setting spinner onTouchListener
        spinnerDefaultLanguage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    long curr = System.currentTimeMillis();
                    if (curr - touchtim >= 800) {
                        touchtim = curr;
                        showPopupWindow(spinnerDefaultLanguage, adapter);
                        return true;
                    }


                }
                return true;
            }
        });
        spinnerDefaultLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text
                if (!isfirstime) {
                    isfirstime = true;
                    isChanged = false;
                    return;
                }
                isChanged = true;
                String selectedText = parent.getItemAtPosition(position).toString();
                textToSpeech.speak(selectedText, TextToSpeech.QUEUE_FLUSH, null, null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Setting Up the switch
        switchPartiallyBlind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the spoken state based on the switch state change
                isChanged = true;
                setSpokenState(isChecked);
            }
        });


    }

    // This function is used Check the state and make TTS speak about the state of Partial Blind Switch
    private void setSpokenState(boolean isChecked) {
        String state = isChecked ? "Partial Blind on" : "Partial Blind off";
        textToSpeech.speak(state, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    // This function shows up the spinner DropDown
    private void showPopupWindow(Spinner spinnerDefaultLanguage, ArrayAdapter<String> adapter) {
        // Create a ListView for the PopupWindow
        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        int selectedPosition = spinnerDefaultLanguage.getSelectedItemPosition();
        listView.setSelection(selectedPosition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listView.setVerticalScrollbarThumbDrawable(getResources().getDrawable(R.drawable.scrollbar));
        }
        // Create the PopupWindow
        PopupWindow popupWindow = new PopupWindow(listView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ; // Set background color
        popupWindow.setOutsideTouchable(true);
        // Allow the popup window to be dismissed when touched outside
        // setting the dimensions of the code
        popupWindow.setHeight(dpToPx(400));
        popupWindow.setWidth(width - dpToPx(40));
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
        Drawable drawable = getResources().getDrawable(R.drawable.popupbackground);
// Set the drawable as the background for the PopupWindow
        popupWindow.setBackgroundDrawable(drawable);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item of the Spinner
                spinnerDefaultLanguage.setSelection(position);
                // Dismiss the PopupWindow
                popupWindow.dismiss();
            }
        });
        // Show the PopupWindow below the Spinner
        popupWindow.showAsDropDown(spinnerDefaultLanguage, dpToPx(-10), 0);
    }

    // Used to Invoke Voice recognition
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

    // Compares two string how much they match
    private boolean partialmatch(String text, String pattern, int n, int m) {
        int dp[][] = new int[2][m + 1];
        int res = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text.charAt(i - 1) == pattern.charAt(j - 1)) {
                    dp[i % 2][j] = dp[(i - 1) % 2][j - 1] + 1;
                    if (dp[i % 2][j] > res)
                        res = dp[i % 2][j];
                } else dp[i % 2][j] = 0;
            }
        }
        int t = Math.min(m, n);
        if (res > 0.8f * t) {
            return true;
        }
        return false;
    }

    // After the Default (Google) speech input is completed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0).toLowerCase();
                Log.d("inside mic intent", spokenText + " " + speaklang + ouptutlang);
                List<String> wordList = Arrays.asList(spokenText.split("\\s+"));
                boolean islanguage = false;
                for (String str : wordList) {
                    int n = str.length();
                    for (String str2 : Objects.requireNonNull(MainActivity.commandmap.get("Language"))) {
                        int m = str2.length();
                        if (partialmatch(str, str2, n, m)) {
                            islanguage = true;
                            break;
                        }
                    }
                    if (islanguage) {
                        break;
                    }
                }
                // islanguage is found now search for language
                boolean islangdetected = false;
                String defaultlang = spinnerDefaultLanguage.getSelectedItem().toString();
                Log.d("inside mic intent", defaultlang);
                for (Map.Entry<String, String> entry : Objects.requireNonNull(MainActivity.langnamemap.get(defaultlang)).entrySet()) {
                    String str = entry.getKey();
                    int n = str.length();
                    for (String currword : wordList) {
                        int m = currword.length();
                        if (partialmatch(str.toLowerCase(), currword, n, m)) {
                            defaultlang = Objects.requireNonNull(MainActivity.langnamemap.get(defaultlang)).get(str);
                            islangdetected = true;
                            break;
                        }
                    }
                    if (islangdetected) break;
                }
                if (islangdetected && islanguage) {
                    isChanged = true;
                    spinnerDefaultLanguage.setSelection(languages.indexOf(languageMap.get(defaultlang)));
                    applybtn.performClick();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    // Initialization of text to speech
    public void initializetexttospeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("en"));
                } else {
                    Toast.makeText(appsettings.this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT).show();
                }
            }
        }, "com.google.android.tts");
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    // Called whent a language is to be downloaded
    public void downloadLanguage(String language, boolean fromAlert) {
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
                        Log.d("languagemodels", "downloadlanguage: " + storedLang);
                        if (storedLang.equals(language)) {
                            alreadyDownloaded = true;
                            break;
                        }
                    }
                    if (alreadyDownloaded) {
                        toret = true;
                    } else if (networkInfo == null || !networkInfo.isConnected()) {
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
                        downloadModel(options, progressDialog);

                    }
                    // Function Calls to store the current settings
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            // Implementation of query (executed on a background thread)


                            if (st != null) {
                                MyRoomDatabase.getInstance(getApplicationContext())
                                        .userDao()
                                        .delete(st);
                                Log.d("hmlo database", "run: todo has been deleted..." + st);
                            }

                            if (toret) {
                                st.output_speech = languageMap.get(spinnerDefaultLanguage.getSelectedItem().toString());
                            }
//                            st.trans_input = languageMap.get(spinnertranslanguage.getSelectedItem().toString());
//                            st.input_lang = spinnerinputlang.getSelectedItem().toString();
                            st.rate = (seekBarSpeechRate.getProgress() / 100.0f) * 2.0f;
                            st.blindness = switchPartiallyBlind.isChecked();
                            MainActivity.input_lang = st.input_lang;
                            MainActivity.output_lang = st.output_speech;
                            SharedPreferences sharedPreferences = getSharedPreferences("Output_lang", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("langname", st.output_speech);
                            editor.apply();
                            MainActivity.blindness = st.blindness;
                            MainActivity.speech_rate = st.rate;
                            MainActivity.trans_input = st.trans_input;
                            for (Map.Entry<String, String> entry : languageMap.entrySet()) {
                                if (entry.getValue().equals(st.output_speech)) {
                                    MainActivity.speaklang = entry.getKey();
                                    break;
                                }
                            }
                            MainActivity.textToSpeech.setLanguage(new Locale(st.output_speech));
                            insertSingleTodo(st);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    progressDialog2.dismiss();
                                }
                            });


                            return null;

                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {

//                            }
                        }
                    }.execute();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(appsettings.this, "Failed to retrieve downloaded models.", Toast.LENGTH_SHORT).show();
                    // Callback indicating failure
                });
        Log.d("returning", "downloadlanguage: " + toret);
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

    @Override
    public void onResume() {
//        shakeListener.registerShakeListener();
        super.onResume();
    }

    @Override
    public void onPause() {
//        shakeListener.unregisterShakeListener();
        super.onPause();
    }

    // insert settings
    public void insertSingleTodo(Settings settings) {

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

    public void onSettingsListLoaded(List<Settings> settingsList) {
        finalset = settingsList;
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

    @Override
    public void onBackPressed() {


        if (isSaveClicked) {
            isAlert = false;
            isChanged = false;
            appsettings.super.onBackPressed();
            return;

        }
        if (!isChanged) {
            isAlert = false;
            isChanged = false;
            appsettings.super.onBackPressed();
            return;
        }
        // If the current changes made is not saved then call this
        new AlertDialog.Builder(this)
                .setMessage("Do you want to save changes ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        isAlert = true;
                        applybtn.performClick();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        isAlert = false;

                        appsettings.super.onBackPressed();
                    }
                })
                .show();

        ;
    }

    // Initialization of Maps for language and weekdays
    private void initializelanguageMap() {
        WeekDays.put(1, "Sunday");
        WeekDays.put(2, "Monday");
        WeekDays.put(3, "Tuesday");
        WeekDays.put(4, "Wednesday");
        WeekDays.put(5, "Thursday");
        WeekDays.put(6, "Friday");
        WeekDays.put(7, "Saturday");
        UserPreferences userPreferences = new UserPreferences(this);

        String country = userPreferences.getCountry();
        if (!Objects.equals(country, "India")) {
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
//
        else {
            languageMap.put("Tamil", "ta");
            languageMap.put("Telugu", "te");
            languageMap.put("Marathi", "mr");
//        languageMap.put("Malayalam", "ml");
            languageMap.put("Hindi", "hi");
            languageMap.put("Gujarati", "gu");
            languageMap.put("English", "en");
            languageMap.put("Bengali", "bn");
            languageMap.put("Kannada", "kn");
        }

    }

    @Override
    public void onDestroy() {
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time - starting_time);
        userPreferences.addFeature(time, "Settings");
        Log.d("Duration check", "" + time + " " + userPreferences.getFeatureListAsJsonArray());
        super.onDestroy();
    }
}