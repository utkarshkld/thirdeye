package com.example.thirdeye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LangTranslate extends AppCompatActivity {

    private EditText editTextLetters;
    private ImageView backbtn;
    private CardView btnTranslate;
    private CardView btnVoiceInput;
    private Spinner sourceLanguageSpinner;
    private LinearLayout spinnerll;
    private Spinner targetLanguageSpinner;
    private TextToSpeech textToSpeech;
    private static final int SPEECH_REQUEST_CODE = 123;
    private TextView translatedTextView; // Added TextView to display translated text
    private String outputlangugage = MainActivity.output_lang;
    private String trans_input = MainActivity.trans_input;
    private float speeh_rate = MainActivity.speech_rate;


    // Map to map language codes to their full names
    private HashMap<String, String> languageMap = new HashMap<>();
    private boolean isdownloaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.langtranslate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        editTextLetters = findViewById(R.id.editTextTranslate);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnVoiceInput = findViewById(R.id.btnVoiceInput);
        sourceLanguageSpinner = findViewById(R.id.sourceLanguageSpinner);
        targetLanguageSpinner = findViewById(R.id.targetLanguageSpinner);
        spinnerll = findViewById(R.id.sourceLanguageSpinnerll);
        translatedTextView = findViewById(R.id.translatedText); // Initialize TextView
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        initializeMap();
        List<String> selectedLanguages = new ArrayList<>(languageMap.values()); // Get language names from the map
        List<String> keylang = new ArrayList<>(languageMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languageMap.keySet().toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sourceLanguageSpinner.setDropDownHorizontalOffset(dpToPx(-10));
        targetLanguageSpinner.setDropDownHorizontalOffset(dpToPx(-10));
        sourceLanguageSpinner.setDropDownWidth(width-dpToPx(40));
        targetLanguageSpinner.setDropDownWidth(width-dpToPx(40));
//        sourceLanguageSpinner.setDropDownVerticalOffset(dpToPx(26)-2);
//        targetLanguageSpinner.setDropDownVerticalOffset(dpToPx(26)-2);
        sourceLanguageSpinner.setAdapter(adapter);
        targetLanguageSpinner.setAdapter(adapter);
        sourceLanguageSpinner.setSelection(selectedLanguages.indexOf(trans_input));
        targetLanguageSpinner.setSelection(selectedLanguages.indexOf(outputlangugage));
        initializetexttospeech();
        editTextLetters.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus lost");
                    // Do whatever you want here
                } else {
                    Log.d("focus", "focused");
                }
            }
        });


        btnVoiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechRecognition();
            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextLetters.getText().toString())) {
                    Toast.makeText(LangTranslate.this, "No text allowed", Toast.LENGTH_SHORT).show();
                } else {
                    String sourceText = editTextLetters.getText().toString();

                    if (sourceText.equalsIgnoreCase("voice input")) {
                        startSpeechRecognition();
                    } else {
                        detectAndTranslateLanguage(sourceText);
                    }
                }
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the text you want to translate.");

        // Get the selected language code from the map
        String selectedSourceLanguage = languageMap.get(sourceLanguageSpinner.getSelectedItem().toString());
        String selectedTargetLanguage = languageMap.get(targetLanguageSpinner.getSelectedItem().toString());
        if (selectedSourceLanguage != null) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedSourceLanguage);
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Speech recognition is not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper function to get a key from a value in a map
    private String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0);
                editTextLetters.setText(spokenText);
                detectAndTranslateLanguage(spokenText);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initializetexttospeech(){
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);
                } else {
                    Toast.makeText(LangTranslate.this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT).show();
                }
            }
        },"com.google.android.tts");
        textToSpeech.setSpeechRate(speeh_rate);

    }
    private void detectAndTranslateLanguage(String sourceText) {
        LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();

        languageIdentifier.identifyLanguage(sourceText)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String sourceLanguage) {
                        // Get the selected language code from the map
                        String targetLanguage = languageMap.get(targetLanguageSpinner.getSelectedItem().toString());

                        TranslatorOptions options = new TranslatorOptions.Builder()
                                .setSourceLanguage(sourceLanguage)
                                .setTargetLanguage(targetLanguage)
                                .build();

                        Translator translator = Translation.getClient(options);

                        isdownloaded = false;
                        ProgressDialog progressDialog = new ProgressDialog(LangTranslate.this);
                        progressDialog.setMessage("Downloading the translation model...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        MyRunnable myRunnable = new MyRunnable(LangTranslate.this,new Handler(),progressDialog);
                        myRunnable.start();
                            translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    isdownloaded = true;
                                    myRunnable.stop();
                                    Task<String> result = translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                                        @Override
                                        public void onSuccess(String s) {
                                            translatedTextView.setText(s); // Set the translated text in the TextView
                                            translatedTextView.setVisibility(View.VISIBLE);
                                            textToSpeech.setLanguage(new Locale(targetLanguage));// Make the TextView visible
                                            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(LangTranslate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    myRunnable.stop();
                                    progressDialog.dismiss();

                                }
                            });
                        }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LangTranslate.this, "Language detection failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LangTranslate.this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> onBackPressed());
        // Show the AlertDialog
        builder.create().show();
    }
    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LangTranslate.this);
        builder.setMessage("No internet connection. Please check your connection and try again.");
        builder.setTitle("No Internet");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {});
        builder.create().show();
    }
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public class MyRunnable implements Runnable {
        private Handler handler;
        private Context context;

        private ProgressDialog dowloading;
        public MyRunnable(Context context, Handler handler,ProgressDialog dialog) {
            this.context = context;
            this.handler = handler;
            this.dowloading = dialog;

        }
        public void start() {
            handler.postDelayed(this, 60*1000); // Run the task after 30sec milliseconds (1 second)
        }
        public void stop() {
            handler.removeCallbacks(this); // Remove any pending callbacks
        }
        @Override
        public void run() {

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    dowloading.dismiss();
                    showNoInternetDialog();
                }
            });
        }
    }
    public void initializeMap(){
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
