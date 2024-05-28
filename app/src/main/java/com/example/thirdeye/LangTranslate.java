package com.example.thirdeye;

//import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LangTranslate extends AppCompatActivity {

    private EditText editTextLetters;
    private MicHandler shakeListener;
    private ImageButton btnreplay;
    private ImageButton btnpauseplay;
    private ImageButton donebtn;
    private long starting_time = 0;
    private boolean isfirstime;
    private int width;
    private Spinner sourceLanguageSpinner;
    private boolean isLast = false;



    private TextToSpeech textToSpeech;
    private static final int SPEECH_REQUEST_CODE = 123;
    private TextView translatedTextView; // Added TextView to display translated text
    private String outputlangugage = MainActivity.output_lang;

    private float speeh_rate = MainActivity.speech_rate;
    private boolean isPaused = true;
    private String translationlang = MainActivity.output_lang;
    private  Vibrator vibe;
    private long shaketime = 0;
    private int currentScrollPosition = 0;


    // Map to map language codes to their full names
    private HashMap<String, String> languageMap = MainActivity.languageMap;
    private int index = 0,index2 = 0;

    private static final int MAX_DURATION_MS = 10000; // Maximum duration in milliseconds (e.g., 20 seconds)

    private boolean isListening = false;
    private Handler timeoutHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.langtranslate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent intent = getIntent();
        isfirstime = true;
        translationlang = intent.getStringExtra("Translation lang");
        Log.d("Lang translate intent", "onCreate: "+translationlang);
        editTextLetters = findViewById(R.id.editTextTranslate);
        btnreplay = findViewById(R.id.btnReplay);
        btnpauseplay = findViewById(R.id.btnPausePlay);
        donebtn = findViewById(R.id.donebtn);
        ImageButton micbtn = findViewById(R.id.speakwords);
        ImageButton backbtn = findViewById(R.id.backbtn);
        sourceLanguageSpinner = findViewById(R.id.sourceLanguageSpinner);
        translatedTextView = findViewById(R.id.translatedText); // Initialize TextView
        isLast = false;
        translatedTextView.setMovementMethod(new ScrollingMovementMethod());
        starting_time = System.currentTimeMillis();
//        trackAppInstallation(this,"Translate Text");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                onBackPressed();
//                Intent intent = new Intent(LangTranslate.this,MainActivity.class);
//                startActivity(intent);
//                if (textToSpeech != null) {
//                    textToSpeech.stop();
//                    textToSpeech.shutdown();
//                }
            }
        });
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        List<String> selectedLanguages = new ArrayList<>(languageMap.values()); // Get language names from the map
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, languageMap.keySet().toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sourceLanguageSpinner.setDropDownHorizontalOffset(dpToPx(-10));
//        targetLanguageSpinner.setDropDownHorizontalOffset(dpToPx(-10));
        sourceLanguageSpinner.setDropDownWidth(width-dpToPx(40));
        translatedTextView.setTextAppearance(R.style.text);
//        targetLanguageSpinner.setDropDownWidth(width-dpToPx(40));
//        sourceLanguageSpinner.setDropDownVerticalOffset(dpToPx(26)-2);
//        targetLanguageSpinner.setDropDownVerticalOffset(dpToPx(26)-2);
        sourceLanguageSpinner.setAdapter(adapter);
        Log.d("Output lang : ",outputlangugage+" "+languageMap.values());
        sourceLanguageSpinner.setSelection(selectedLanguages.indexOf(translationlang));
//        Log.d("This is the Output language",""+MainActivity.output_lang);
//        targetLanguageSpinner.setSelection(selectedLanguages.indexOf(outputlangugage));
        initializetexttospeech();
        btnpauseplay.setVisibility(View.GONE);
        btnreplay.setVisibility(View.GONE);
        shakeListener = new MicHandler(this);
        shakeListener.setOnShakeListener(() -> {
            if(System.currentTimeMillis()-shaketime >= 3000){
                vibe.vibrate(50);
                shaketime = System.currentTimeMillis();
                startSpeechRecognition();
            }
        });
        btnreplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.stop();
                isPaused = true;
                vibe.vibrate(50);
                btnpauseplay.setImageResource(R.drawable.stop_fill);
                currentScrollPosition=0;
                translatedTextView.scrollTo(0,0);
                index = 0;
                index2 = 0;
                speakText(translatedTextView.getText().toString(), 0);
            }
        });
        btnpauseplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnpauseplay","I am clicked");
                vibe.vibrate(50);
                if (!isPaused) {
                    isPaused = true;
                    btnpauseplay.setImageResource(R.drawable.stop_fill);
//                    currentScrollPosition = translatedTextView.getScrollY();
                    translatedTextView.scrollTo(0, currentScrollPosition);
                    if(isLast){
                        isLast = false;
                        btnreplay.performClick();
                        return;
                    }
                    speakText(translatedTextView.getText().toString().substring(index), 0);

                } else {
                    isPaused = false;
                    enableTextViewScroll();
                    btnpauseplay.setImageResource(R.drawable.play_fill);
                    index2 = index;
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                }
            }
        });

        micbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                index = 0;
                index2 = 0;
                textToSpeech.stop();
                editTextLetters.setText("");
                startSpeechRecognition();
            }
        });
        sourceLanguageSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showPopupWindow(sourceLanguageSpinner, adapter);
                    return true;
                }
                return true;
            }
        });
        sourceLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the item selection here
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                // Do something with the selected language
                if(isfirstime){
                    isfirstime = false;
                    return;
                }
                donebtn.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected if needed
            }
        });
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                btnpauseplay.setVisibility(View.VISIBLE);
                btnpauseplay.setImageResource(R.drawable.stop_fill);
                isPaused = true;
                btnreplay.setVisibility(View.VISIBLE);
                index = 0;
                index2 = 0;
                if(!editTextLetters.getText().toString().isEmpty()){
                    detectAndTranslateLanguage(editTextLetters.getText().toString());
                }
            }
        });
    }
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
        popupWindow.setOutsideTouchable(true); // Allow the popup window to be dismissed when touched outside
        popupWindow.setHeight(dpToPx(350));
        popupWindow.setWidth(width-dpToPx(40));
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
        popupWindow.showAsDropDown(spinnerDefaultLanguage,dpToPx(-10),0);
    }
    private void speakText(final String text, final int startPosition) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "word");
            }
        }).start();
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
    private void startSpeechRecognition() {


            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the text you want to translate.");

            // Get the selected language code from the map
            String selectedSourceLanguage = outputlangugage  ;

            if (selectedSourceLanguage != null) {
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedSourceLanguage);
            }


            try {
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Speech recognition is not supported on this device.", Toast.LENGTH_SHORT).show();
            }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0);
                editTextLetters.setText(spokenText);
                btnpauseplay.setVisibility(View.VISIBLE);
                btnreplay.setVisibility(View.VISIBLE);
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
                    int result;

                    result = textToSpeech.setLanguage(new Locale(outputlangugage));

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("IN Language Translate", "TextToSpeech: Language not supported.");
                    }else {

                        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String utteranceId) {
                                Log.i("TTS", "utterance started");
                            }

                            @Override
                            public void onDone(String utteranceId) {
                                Log.i("TTS", "utterance done");
                                enableTextViewScroll();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        isPaused = true;
                                        translatedTextView.setText(translatedTextView.getText().toString());
                                        btnpauseplay.performClick();
                                        isLast = true;
                                    }
                                });
                                //
                            }

                            @Override
                            public void onError(String utteranceId) {
                                Log.i("TTS", "utterance error");
                            }

                            @Override
                            public void onRangeStart(String utteranceId,
                                                     int start,
                                                     int end,
                                                     int frame) {
//

                                start = start + index2;
                                end = end + index2;
                                index = end;
                                final int a = start, b = end;
                                disableTextViewScroll();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        if(b <= translatedTextView.getText().toString().length()){
//                                            Spannable textWithHighlights = new SpannableString(translatedTextView.getText().toString());
//                                            textWithHighlights.setSpan(new ForegroundColorSpan(Color.RED), a, b, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                                            translatedTextView.setText(textWithHighlights);
//                                            // Calculate line start for the 'end' offset
//                                            int lineStart = translatedTextView.getLayout().getLineForOffset(a);
//                                            translatedTextView.scrollTo(0, lineStart*((translatedTextView.getHeight()/20)+30));
//                                            Log.d("Checking indexes", ""+(index+1) + " "+translatedTextView.getText().toString().length());
//                                            if(index == translatedTextView.getText().toString().length()) {//
//                                                btnpauseplay.performClick();
//                                                isLast = true;//
//                                            }
//                                        }

                                        // Scroll to the calculated line
//                                        translatedTextView.scrollTo(0, lineStart*((translatedTextView.getHeight()/20)-5));
//                                        textView.setText(textWithHighlights);
//                                        int lineStart = textView.getLayout().getLineForOffset(b);
//                                        textView.scrollTo(0, lineStart*((textView.getHeight()/20)-5));
                                        Spannable textWithHighlights = new SpannableString(translatedTextView.getText().toString());
                                        textWithHighlights.setSpan(new ForegroundColorSpan(Color.RED), a, b, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                        translatedTextView.setText(textWithHighlights);

                                        int totalLines = translatedTextView.getLayout().getLineCount();
                                        int endLine = translatedTextView.getLayout().getLineForOffset(b);
                                        int visibleLines = translatedTextView.getHeight() / translatedTextView.getLineHeight();

                                        // Calculate the line where the last visible line is currently
                                        int scrollY = translatedTextView.getScrollY();
                                        int firstVisibleLine = translatedTextView.getLayout().getLineForVertical(scrollY);
                                        int lastVisibleLine = firstVisibleLine + visibleLines - 1;

                                        // Check if the end of the highlight is on or beyond the last visible line
                                        if (endLine >= lastVisibleLine) {
                                            // Scroll up by a specific number of lines (3 lines in this case)
                                            int linesToScroll = 1;
                                            int newScrollLine = Math.max(0, endLine - linesToScroll);
                                            int newScrollY = translatedTextView.getLayout().getLineTop(newScrollLine);
                                            translatedTextView.scrollTo(0, newScrollY);
                                            currentScrollPosition = newScrollY;
                                        }else {
                                            currentScrollPosition = translatedTextView.getScrollY();
                                        }
                                    }
                                });
                            }

                        });
                    }

                } else {
                    Log.e("IN Language Translates", "TextToSpeech initialization failed.");
                }
            }
        },"com.google.android.tts");
        textToSpeech.setSpeechRate(speeh_rate);

    }
    private void detectAndTranslateLanguage(String sourceText) {
                        TranslatorOptions options = new TranslatorOptions.Builder()
                                .setSourceLanguage(outputlangugage)
                                .setTargetLanguage(Objects.requireNonNull(languageMap.get(sourceLanguageSpinner.getSelectedItem())))
                                .build();
                        Translator translator = Translation.getClient(options);
                        ProgressDialog progressDialog = new ProgressDialog(LangTranslate.this);
                        progressDialog.setMessage("Downloading the translation model...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
//                        MyRunnable myRunnable = new MyRunnable(LangTranslate.this,new Handler(),progressDialog);
//                        myRunnable.start();
                            translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
//                                    myRunnable.stop();
                                    Task<String> result = translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                                        @Override
                                        public void onSuccess(String s) {

                                            translatedTextView.setText(s); // Set the translated text in the TextView
                                            translatedTextView.setVisibility(View.VISIBLE);
                                            // Make the TextView visible
                                            textToSpeech.setLanguage(new Locale(Objects.requireNonNull(languageMap.get(sourceLanguageSpinner.getSelectedItem()))));
                                            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, "myUtteranceID");
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
//                                    myRunnable.stop();
                                    progressDialog.dismiss();

                                }
                            });
    }
    private void disableTextViewScroll() {
        translatedTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Ignore all touch events while speaking
                return true;
            }
        });
    }

    private void enableTextViewScroll() {
        translatedTextView.setOnTouchListener(null);
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
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time-starting_time);
        userPreferences.addFeature(time,"Language Translate");
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
//        finishAffinity();
        Intent intent = new Intent(LangTranslate.this,MainActivity.class);
        startActivity(intent);
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
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
//            handler.postDelayed(this, 60*1000); // Run the task after 30sec milliseconds (1 second)
        }
        public void stop() {
            handler.removeCallbacks(this); // Remove any pending callbacks
        }
        @Override
        public void run() {

//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
////                    dowloading.dismiss();
////                    showNoInternetDialog();
//                }
//            });
        }
    }
}