package com.example.thirdeye;
//import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;
import static com.example.thirdeye.LangTranslate.dpToPx;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
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
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TextSpeech extends AppCompatActivity {
    private ProgressDialog progressprocessing;
    private int count = 0;

    private long starting_time = 0;
    private SurfaceHolder surfaceViewHolder;
    private SurfaceView surfaceView;

    public List<String> words = new ArrayList<>();
    //    private ImageView imageView;
    private ImageButton btnTakePicture;
    private ImageButton btnPausePlay;
    private ImageButton btnReplay;
    private Button micc;
    private int langcounter = 0;

    private boolean s = false;
    private TextView textView;
    private Camera camera;
    private long currtime = 0;
    private boolean next = false;

    private static final String TAG = "ScanVoucherFragment";
    private float speechrate = MainActivity.speech_rate;
    private String outputlangugage = MainActivity.output_lang;
    private String input_lang = MainActivity.input_lang;
    private boolean blindness = MainActivity.blindness;
    private boolean flash = false;

    private RelativeLayout rellayout;
    byte[] data2 = null;
    TextRecognizer engtextrecog = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    TextRecognizer hindirecog =
            TextRecognition.getClient(new DevanagariTextRecognizerOptions.Builder().build());
    TextRecognizer japaneserecog = TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());
    TextRecognizer koreanrecog = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    private Vibrator vibe;
    private boolean isClickable = true;
    private TextToSpeech textToSpeech, textToSpeech2;
    private boolean isPaused = true;
    private MicHandler shakeListener;

    private static final int SPEECH_REQUEST_CODE = 1;
    private HashMap<String, String> languageMap = new HashMap<>();
    private boolean isPlaying = false;
    private PackageManager pm;
    private boolean deviceHasFlash = false;

    private static int index = 0;
    private static int index2 = 0;
    private StringBuilder alltext;
    private Thread camerathread = null;


    private ImageView back;


    private ProgressDialog waiting;
    public long shaketime = 0;
    private Spinner text_det_spinner;
    private boolean isLast = false;
    private int currentScrollPosition = 0;
    private MySharedPreferences sharedPreferences;


    private long lastpgdet = 0,lastpagedet2 = 0,lastpage3=0;
    private ArrayAdapter<String>  text_det_adapter;
    private boolean isCaptured = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.textspeech);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final ProgressDialog progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Loading...");
        progressDialog2.setCancelable(false);
        progressDialog2.show();
        starting_time = System.currentTimeMillis();
//        trackAppInstallation(this,"Read Text");
        // Simulate work in the background
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Dismiss the progress dialog after 2 seconds
                progressDialog2.dismiss();
            }
        }, 3000);
        waiting = new ProgressDialog(TextSpeech.this);
        isLast = false;
        waiting.setCancelable(false);
        waiting.setMessage("please wait");
        text_det_spinner = findViewById(R.id.detectlnguage);
        progressprocessing = new ProgressDialog(TextSpeech.this);
        progressprocessing.setCancelable(false);
        deviceHasFlash = MainActivity.deviceHasFlash;
        index = 0;
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        initViews();
        words = new ArrayList<>();
        requestPermission();
        initializeTextToSpeech();
        rellayout = findViewById(R.id.rellayout);
        btnPausePlay.setVisibility(View.GONE);
        btnReplay.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        btnTakePicture.setBackgroundResource(R.drawable.camera_icon);
        Handler handler = new Handler();
        MyRunnable myRunnable = new MyRunnable(this, handler);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isClickable) {
                    isClickable = false;
                    vibe.vibrate(50);
                    if (!isPlaying) {

//
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                takePictureAndRecognizeText();
                                myRunnable.start();
                                isPlaying = true;
                                isPaused = true;
                                index = 0;
                                index2 = 0;
                                words = new ArrayList<>();
                                isCaptured = true;
                                textView.setText("");
                                btnPausePlay.setImageResource(R.drawable.stop_fill);
                                btnTakePicture.setImageResource(R.drawable.retake_icon);
                                btnPausePlay.setVisibility(View.VISIBLE);
                                btnReplay.setVisibility(View.VISIBLE);
                                Transition transition2 = new Slide(Gravity.TOP);
                                transition2.setDuration(500);
                                transition2.addTarget(R.id.textView);
                                TransitionManager.beginDelayedTransition(rellayout, transition2);
                                textView.setVisibility(View.VISIBLE);
                                surfaceView.setVisibility(View.GONE);
                            }
                        });
                        releaseCamera();

                    } else {

                        myRunnable.stop();
                        textToSpeech.stop();
                        isCaptured = false;
                        isPlaying = false;
                        btnPausePlay.setVisibility(View.GONE);
                        btnTakePicture.setImageResource(R.drawable.camera_icon);
                        btnReplay.setVisibility(View.GONE);
                        Transition transition2 = new Slide();
                        transition2.setDuration(500);
                        transition2.addTarget(R.id.textView);
                        TransitionManager.beginDelayedTransition(rellayout, transition2);
                        textView.setVisibility(View.GONE);
                        surfaceView.setVisibility(View.VISIBLE);
                        isPaused = false;
                        isClickable = true;
                    }
                }

            }
        });

        micc = findViewById(R.id.micButton1);
        shakeListener = new MicHandler(this);
        shakeListener.setOnShakeListener(() -> {
            Toast.makeText(TextSpeech.this, "Shake detected!", Toast.LENGTH_SHORT).show();
            if(System.currentTimeMillis()-shaketime >= 3000){
                shaketime = System.currentTimeMillis();
                startRecording();
            }

        });
        micc.setOnClickListener(v -> {

            startVoiceRecognition();
        });
        btnPausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(50);
                if (!isPaused) {
                    isPaused = true;
                    btnPausePlay.setImageResource(R.drawable.stop_fill);
                    textView.scrollTo(0,currentScrollPosition);
                    if(isLast){
                        isLast = false;
                        btnReplay.performClick();
                        return;
                    }
                    speakText(textView.getText().toString().substring(index), 0);

                } else {
                    isPaused = false;
                    btnPausePlay.setImageResource(R.drawable.play_fill);
                    enableTextViewScroll();
                    index2 = index;
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                }
            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();
                isPaused = true;
                vibe.vibrate(50);
                textView.scrollTo(0,0);
                index = 0;
                index2 = 0;
                speakText(textView.getText().toString(), 0);
                btnPausePlay.setImageResource(R.drawable.stop_fill);



            }
        });
        back = findViewById(R.id.exitbtntexttospeech);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent intent = new Intent(TextSpeech.this, MainActivity.class);
//                startActivity(intent);
            }
        });


        initializeLanguageMap();
        List<String> textDetList = new ArrayList<>(Arrays.asList("English", "Hindi","Marathi"));
        UserPreferences userPreferences = new UserPreferences(this);
        String country = userPreferences.getCountry();
        Log.d("checking country",""+country);
        if(!Objects.equals(country, "India")){
            textDetList.add("Korean");
            textDetList.add("Japanese");
        }


        text_det_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Store the selected index in SharedPreferences
                sharedPreferences.saveString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Not needed in this implementation
            }
        });
        text_det_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, textDetList);

        text_det_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        text_det_spinner.setDropDownHorizontalOffset(dpToPx(-3));

        text_det_spinner.setAdapter(text_det_adapter);
        sharedPreferences = new MySharedPreferences(this);
        String lang = sharedPreferences.getString();
        if(!lang.isEmpty()){
            text_det_spinner.setSelection(Integer.parseInt(lang));
        }

//        Log.d("checking input language",input_lang);



    }

    @Override
    protected void onPause() {
        // Stop or pause speech synthesis when the activity is paused
        if (isPaused) {
            btnPausePlay.performClick();
        }
        releaseCamera();


        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();
    }
    private void disableTextViewScroll() {
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Ignore all touch events while speaking
                return true;
            }
        });
    }

    private void enableTextViewScroll() {
        textView.setOnTouchListener(null);
    }
    @Override
    protected void onResume() {

        super.onResume();
    }

    private void initViews() {
        surfaceView = findViewById(R.id.surfaceView);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnPausePlay = findViewById(R.id.btnPausePlay);
        btnReplay = findViewById(R.id.btnReplay);
        textView = findViewById(R.id.textView);
    }

    public void speaknextword() {
        if (index < words.size()) {//
            speakText(words.get(index), 0);
        }
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            onBackPressed();
        } else {
            setupSurfaceHolder();
        }
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result;

                    result = textToSpeech.setLanguage(new Locale(outputlangugage));

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "TextToSpeech: Language not supported.");
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
                                        textView.setText(textView.getText().toString());
                                        isPaused = true;
                                        btnPausePlay.performClick();
                                        isLast = true;
                                    }
                                });
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

//                                        Spannable textWithHighlights = new SpannableString(textView.getText().toString());
//                                        textWithHighlights.setSpan(new ForegroundColorSpan(Color.RED), a, b, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                                        textView.setText(textWithHighlights);
//                                        int lineStart = textView.getLayout().getLineForOffset(a);
//                                        textView.scrollTo(0, lineStart*((textView.getHeight()/20)+15));
//                                        Spannable textWithHighlights = new SpannableString(textView.getText().toString());
//                                        textWithHighlights.setSpan(new ForegroundColorSpan(Color.RED), a, b, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                                        textView.setText(textWithHighlights);
//
//                                        int lineStart = textView.getLayout().getLineForOffset(a);
//                                        int lineEnd = textView.getLayout().getLineForOffset(b);
//
//                                        int scrollLine = textView.getLayout().getLineForOffset(textView.getScrollY());
//                                        int visibleLines = textView.getHeight() / textView.getLineHeight();
//
//                                        if (lineEnd >= scrollLine + visibleLines - 1) {
//                                            // Scroll up by 3 lines if the end of the highlight is within the last visible line
//                                            int linesToScroll = 3;
//                                            int newScrollY = textView.getLayout().getLineTop(lineEnd - linesToScroll);
//                                            textView.scrollTo(0, newScrollY);
//                                        } else {
//                                            textView.scrollTo(0, textView.getLayout().getLineTop(lineStart));
//                                        }
                                        Spannable textWithHighlights = new SpannableString(textView.getText().toString());
                                        textWithHighlights.setSpan(new ForegroundColorSpan(Color.RED), a, b, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                        textView.setText(textWithHighlights);

                                        int totalLines = textView.getLayout().getLineCount();
                                        int endLine = textView.getLayout().getLineForOffset(b);
                                        int visibleLines = textView.getHeight() / textView.getLineHeight();

                                        // Calculate the line where the last visible line is currently
                                        int scrollY = textView.getScrollY();
                                        int firstVisibleLine = textView.getLayout().getLineForVertical(scrollY);
                                        int lastVisibleLine = firstVisibleLine + visibleLines - 1;

                                        // Check if the end of the highlight is on or beyond the last visible line
                                        if (endLine >= lastVisibleLine) {
                                            // Scroll up by a specific number of lines (3 lines in this case)
                                            int linesToScroll = 1;
                                            int newScrollLine = Math.max(0, endLine - linesToScroll);
                                            int newScrollY = textView.getLayout().getLineTop(newScrollLine);
                                            textView.scrollTo(0, newScrollY);
                                            currentScrollPosition = newScrollY;
                                        }else {
                                            currentScrollPosition = textView.getScrollY();
                                        }
//

                                    }
                                });
                            }

                        });
                    }

                } else {
                    Log.e(TAG, "TextToSpeech initialization failed.");
                }
            }

        }, "com.google.android.tts");
        textToSpeech2 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
//                for(Locale t : textToSpeech2.getAvailableLanguages()){
//                    Log.d("hui",t.toString());
//                }
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech2.setLanguage(new Locale("en","IN"));
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "TextToSpeech: Language not supported.");
                    }
                } else {
                    Log.e(TAG, "TextToSpeech initialization failed.");
                }
            }

        }, "com.google.android.tts");
        textToSpeech2.setSpeechRate(1.3f);
        textToSpeech.setSpeechRate(speechrate);


    }

    private void takePictureAndRecognizeText() {

        isLast = false;
        try {
            byte[] imgBytes = CameraUtils.convertYuvToJpeg(data2, camera);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            InputImage image = InputImage.fromBitmap(bitmap, CameraUtils.getRotationCompensation(CameraUtils.getCameraID(), this));
            progressprocessing.setMessage("Processing...");
            progressprocessing.show();
            langcounter = 0;
            alltext = new StringBuilder("");
            StringBuilder expirydatebuilder = new StringBuilder();
            textView.setTextAppearance(R.style.text);
            textView.setBackgroundResource(R.drawable.speechtext_view);
            textView.setMovementMethod(new ScrollingMovementMethod());
//            detecttextwithlanguage(engtextrecog,image,"English",expirydatebuilder); // This can detect the all the latin language
            // Hindi and marathi
            alltext = new StringBuilder();
//            detecttextwithlanguage(koreanrecog,image,"Korean",alltext,expirydatebuilder);
            String selectedlang = text_det_spinner.getSelectedItem().toString();

            // Trying to detect all the languages
            if(selectedlang.equals("Hindi")) {
                detecttextwithlanguage(hindirecog, image,selectedlang,expirydatebuilder);
            }else if(selectedlang.equals("Marathi")){
                detecttextwithlanguage(hindirecog, image,selectedlang,expirydatebuilder);
            }else if(selectedlang.equals("Japanese")){
                detecttextwithlanguage(japaneserecog,image,selectedlang,expirydatebuilder);
            }else if(selectedlang.equals("Korean")){
                detecttextwithlanguage(koreanrecog,image,selectedlang,expirydatebuilder);
            }else{
                 detecttextwithlanguage(engtextrecog,image,selectedlang,expirydatebuilder);
            }



        } catch (Exception e) {
            Log.e(TAG, "ERROR");
        }
    }

    private void detecttextwithlanguage(TextRecognizer textrecog, InputImage image,String language,StringBuilder expirydatebuilder) {
        textrecog.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text result) {
//                Log.e(TAG, "onSuccess: result size is " + result.getTextBlocks().size());
                StringBuilder stringBuilder = new StringBuilder(),stringBuilder1 = new StringBuilder();

                for (Text.TextBlock block : result.getTextBlocks()) {
                    for (Text.Line line : block.getLines()) {
                        for (Text.Element element : line.getElements()) {
                            String elementText = element.getText();//
                            stringBuilder1.append(elementText + " ");
                        }
                    }
                }
                // will get identified text here have to modify here
                String x = stringBuilder1.toString();
                String temp = x.toLowerCase();
//                "(use[ ]*by|best[ ]*before|exp|expiry|mfg|mfd|manufacturing)(date)*[. /-:]*(date)*[. /-:]*((([0O][1-9]|[12]\\d|3[01])[-./7]([0O][1-9]|1[0-2])[-./7](20\\d{2}))|(([0O][1-9]|[12]\\d|3[01])\\.([0O][1-9]|1[0-2])\\.(20\\d{2}|[0-9]{2}))|(([0O][1-9]|[12]\\d|3[01])\\/([0O][1-9]|1[0-2])\\/(20\\d{2}|[0-9]{2}))|(([0O][1-9]|[12]\\d|3[01])[-./7]([0O][1-9]|1[0-2])[-./7]\\d{2})|(([0O][1-9]|[12]\\d|3[01])\\.([0O][1-9]|1[0-2])\\.\\d{2})|(([0O][1-9]|[12]\\d|3[01])\\/([0O][1-9]|1[0-2])\\/\\d{2})|(([0O][1-9]|1[0-2]|(january|february|march|april|may|june|july|august|september|october|november|december)|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))[-./7 ]*(20[0-9]{2}))|(([0O][1-9]|1[0-2]|(january|february|march|april|may|june|july|august|september|october|november|december)|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))[-./ 7]*([0-9]{2}))$)"
                Pattern expiryDatePattern = Pattern.compile("([uj]se *by|best *before|exp|expiry|mfg|mfd|manufacturing)[., /-:(date)]*((([0O][1-9]|[12]\\d|3[01])[-,./]*([0O][1-9]|1[0-2])[-,./]*(20\\d{2}))|(([0O][1-9]|[12]\\d|3[[0O]1])\\.([0O][1-9]|1[O0-2])\\.(2[0O]\\d{2}|[O0-9]{2}))|(([0O][1-9]|[12]\\d|3[[0O]1])\\/([0O][1-9]|1[O0-2])\\/(2[0O]\\d{2}|[O0-9]{2}))|(([0O][1-9]|[12]\\d|3[[0O]1])[-./,]*([0O][1-9]|1[O0-2])[-./,]*\\d{2})|(([0O][1-9]|[12]\\d|3[O01])\\.([0O][1-9]|1[O0-2])\\.\\d{2})|(([0O][1-9]|[12]\\d|3[O01])\\/([0O][1-9]|1[O0-2])\\/\\d{2})|(([0O][1-9]|1[O0-2]|(january|february|march|april|may|june|july|august|september|october|november|december)|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))[-./ ,]*(2[0O][O0-9]{2}))|(([0O][1-9]|1[O0-2]|(january|february|march|april|may|june|july|august|september|october|november|december)|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))[-./ ,]*([O0-9]{2}))$)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = expiryDatePattern.matcher(temp);//
                if(language.equals("English")) {
                    while (matcher.find()) {
                        // If the line contains the expiry date, append it to the expiryDateBuilder
                        String categ = matcher.group(1);
                        String Date = matcher.group(2);
                        Log.d("expiry use", "onSuccess: " + categ + " " + Date + temp + " " + x);
                        // removing cat firs
                        int a = 0, b = 0;
                        if (categ != null) {
//                        a = temp.indexOf(categ);
//                        if(a!=-1)
//                        x = x.substring(0, a - 1) + x.substring(a + categ.length());
                        }
                        if (Date != null) {

//                        b = temp.indexOf(Date) - categ.length();
//                        if(b!=-1)
//                        x = x.substring(0, b - 1) + x.substring(b + Date.length());
                        }
                        if (categ.equals("mfd") || categ.equals("manufacturing") || categ.equals("mfg")) {
                            Log.d("expiry use", "onSuccess: " + a + " " + b);
                            expirydatebuilder.append("Manufacturing Date : ").append(matcher.group(2)).append("\n");
                        } else if (categ != null) {
                            Log.d("expiry use", "onSuccess: " + a + " " + b + "manufacture");
                            expirydatebuilder.append("Expire Date : ").append(matcher.group(2)).append("\n");
                        }
                    }
                }
                stringBuilder.append(x);

                // Get the selected language code from the map
                String targetLanguage = outputlangugage;
//                Toast.makeText(TextSpeech.this, targetLanguage, Toast.LENGTH_SHORT).show();

                TranslatorOptions options = new TranslatorOptions.Builder()
                        .setSourceLanguage(languageMap.get(language))
                        .setTargetLanguage(targetLanguage)
                        .build();
                Translator translator = Translation.getClient(options);
                ProgressDialog progressDialog = new ProgressDialog(TextSpeech.this);
                progressDialog.setMessage("Downloading the translation model...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                MyRunnable2 myRunnable2 = new MyRunnable2(TextSpeech.this, new Handler(), progressDialog);
                myRunnable2.start();
                translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void unused) {
                        s = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myRunnable2.stop();
                                progressDialog.dismiss();
                                progressprocessing.dismiss();

                            }
                        });



                        Task<String> result = translator.translate(stringBuilder.toString()).addOnSuccessListener(new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String s) {

                                isClickable = true;
                                alltext.append(s);
                                alltext.insert(0,expirydatebuilder);
                                textView.setText(alltext.toString());
                                speakText(alltext.toString(), 0);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                isClickable = true;
                                progressprocessing.dismiss();
//                                Toast.makeText(TextSpeech.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressprocessing.dismiss();
                        isClickable = true;
                        myRunnable2.stop();
                        progressDialog.dismiss();
                    }
                });

            }
        });


    }
    private void speakText(String text, int startPosition) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "word");
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }
    private void setupSurfaceHolder() {
        surfaceViewHolder = surfaceView.getHolder();
        surfaceViewHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startCamera();
                    }
                }).start();


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


                Log.e(TAG, "surfaceChanged: Changing");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
//
            }
        });
    }
    private void startCamera() {

        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);

        if(deviceHasFlash && flash){
//            Toast.makeText(TextSpeech.this,"Flash ON",Toast.LENGTH_SHORT).show();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }else if(flash && !deviceHasFlash){
            Toast.makeText(TextSpeech.this,"Device doesnt have flash Light",Toast.LENGTH_LONG).show();
        }
        camera.setParameters(params);
        camera.setDisplayOrientation(CameraUtils.getRotationCompensation(CameraUtils.getCameraID(), TextSpeech.this));

        try {
            camera.setPreviewDisplay(surfaceViewHolder);
            camera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(final byte[] data, final Camera camera) {


                // Place the entire contents of your onPreviewFrame method here
                // Remember to replace references to UI elements with appropriate methods to update them on the UI thread

                data2 = data;


                if (System.currentTimeMillis() - currtime >= 800&& !next) {
                    next = true;
                    camerathread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isCaptured) {
                                byte[] imgBytes = CameraUtils.convertYuvToJpeg(data2, camera);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                                if (!flash && detectdarkness(bitmap) && deviceHasFlash) {
                                    flash = true;
                                    Camera.Parameters params = camera.getParameters();
                                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                    camera.setParameters(params);
                                    return;
                                }
                                InputImage image = InputImage.fromBitmap(bitmap, CameraUtils.getRotationCompensation(CameraUtils.getCameraID(), TextSpeech.this));
                                ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
                                labeler.process(image)
                                        .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                                            @Override
                                            public void onSuccess(List<ImageLabel> labels) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                labeler.process(image)
                                        .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                                            @Override
                                            public void onSuccess(List<ImageLabel> labels) {
                                                next = false;
//                                                for (ImageLabel label : labels) {
//                                                    String text = label.getText();
//                                                    float confidence = label.getConfidence();
//                                                    int index = label.getIndex();
//                                                    if (confidence >= 0.65 && text.equals("Paper")) {
//                                                        // every 200ms vibrate for 50ms
//                                                        Log.d("Current time",""+System.currentTimeMillis());
//
//                                                        if (System.currentTimeMillis() - lastpgdet >= 10000) {
//                                                            lastpagedet2 = 0;
//                                                            lastpgdet = System.currentTimeMillis();
//                                                            count = 0;
//                                                        }
//                                                        if (System.currentTimeMillis() - lastpagedet2 >= 1200 && System.currentTimeMillis() - lastpagedet2 <= (lastpagedet2 == 0 ? System.currentTimeMillis() : 3200) && !isCaptured) {
//                                                            lastpagedet2 = System.currentTimeMillis();
//                                                            if(count == 0){
//                                                                Log.d("Capturing in", ""+Integer.valueOf(3-count).toString());
//                                                                speaktext2("Capturing in"+Integer.valueOf(3-count).toString());
//                                                            }else{
//                                                                Log.d("Capturing in", ""+Integer.valueOf(3-count).toString());
//                                                                speaktext2(Integer.valueOf(3-count).toString());
//                                                            }
//
//                                                            ++count;
//                                                        }
//                                                        if (count == 4) {
//                                                            count = 0;
//                                                            textToSpeech2.stop();
//                                                            if(!isCaptured) {
//                                                                isCaptured = true;
//                                                                btnTakePicture.performClick();
//                                                                releaseCamera();
//                                                            }
//                                                        }
//                                                    }
//                                                }
                                            }
                                        });
                            }

                        }

                    });
                    camerathread.start();
                    currtime = System.currentTimeMillis();
                }

            }
        });
    }

    public void resetCamera() {
        if (surfaceViewHolder.getSurface() == null) {
            return;
        }
        if (camera != null) {
            camera.stopPreview();
            try {
                camera.setPreviewDisplay(surfaceViewHolder);
//
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "onRequestPermissionsResult: Permission check");
        btnPausePlay.setBackgroundResource(R.drawable.stop_fill);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: Permission Granted");
                    setupSurfaceHolder();
                    startCamera();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "You need to allow access permissions", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
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
        Button mic1 = findViewById(R.id.micButton1);
        mic1.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0).toLowerCase();


                if (spokenText.contains("click") || spokenText.contains("capture")) {
                    if (isPlaying) {
                        speaktext2("Go to capture mode first.");
                    } else {
                        btnTakePicture.performClick();
                    }
                } else if (spokenText.contains("replay")) {
                    if (isPlaying) {
                        btnReplay.performClick();
                    } else {
                        speaktext2("Capture the photo first");
                    }
                } else if (spokenText.contains("pause") || spokenText.contains("stop")) {
                    if (isPlaying) {
                        if (textToSpeech != null) {
                            textToSpeech.stop();
                        }
                        isPaused = false;
                        btnPausePlay.setBackgroundResource(R.drawable.play_fill);
                    } else {
                        speaktext2("Capture the photo first");
                    }

                } else if (spokenText.contains("play")) {
                    if (isPlaying) {
                        isPaused = true;
                        btnPausePlay.setBackgroundResource(R.drawable.stop_fill);
                        speaknextword();
                    } else {
                        speaktext2("Capture the photo first.");
                    }
                } else if (spokenText.contains("re take") || spokenText.contains("re-capture") || spokenText.contains("retake") || spokenText.contains("recapture")) {
                    if (isPlaying) {
                        speaktext2("Getting in Capture Mode");
                        btnTakePicture.performClick();
                    } else {
                        speaktext2("Already In capture Mode, Say capture for capturing");
                    }
                } else {
                    Toast.makeText(this, "Command not recognized", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private boolean detectdarkness(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int darkPixels = 0;
        // Iterate through each pixel of the bitmap
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getPixel(x, y);

                // Extract RGB components from the pixel
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
//                Log.d("Detecting Darkness",""+red+" "+green+" "+blue);

                // Check if each primary color intensity is greater than 178
                if (red < 100 && green < 100 && blue < 100) {
                    darkPixels++;
                }
            }
        }
        float total = width*height;
        float threshhold = (darkPixels/total);
        return threshhold > 0.8;
    }

    public void speaktext2(String text) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech2.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            textToSpeech2.speak(text, TextToSpeech.QUEUE_ADD, null);
        }
    }

//    protected void onResume(){
//        startCamera();
//        super.onResume();
//    }
    @Override
    protected void onDestroy() {
        releaseCamera();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time-starting_time);
        userPreferences.addFeature(time,"Text To Speech");
        Log.d("Duration check",""+time+" "+userPreferences.getFeatureListAsJsonArray());
        super.onDestroy();


    }

    @Override
    public void onBackPressed() {
//        progressprocessing.dismiss();
//        finishAffinity();
        Intent intent = new Intent(TextSpeech.this,MainActivity.class);
        startActivity(intent);
        releaseCamera();
        if(textToSpeech!=null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onBackPressed();
    }


    private void initializeLanguageMap() {
        // Add languages and their Locale codes to the HashMap
//        languageMap.put("Afrikaans", "af");
//        //languageMap.put("Albanian", "sq");
//        languageMap.put("Arabic", "ar");
//        languageMap.put("Bengali", "bn");
//        languageMap.put("Bulgarian", "bg");
//        //languageMap.put("Catalan", "ca");
//        languageMap.put("Chinese", "zh");
//        //languageMap.put("Croatian", "hr");
//        languageMap.put("Czech", "cs");
//        languageMap.put("Danish", "da");
//        languageMap.put("Dutch", "nl");
//        languageMap.put("English", "en");
//        languageMap.put("Finnish", "fi");
//        languageMap.put("French", "fr");
//        languageMap.put("Galician", "gl");
//        //languageMap.put("Georgian", "ka");
//        languageMap.put("German", "de");
//        languageMap.put("Greek", "el");
//        languageMap.put("Gujarati", "gu");
//        //languageMap.put("Haitian", "ht");
//        //languageMap.put("Hebrew", "he");
//        languageMap.put("Hindi", "hi");
//        languageMap.put("Hungarian", "hu");
//        languageMap.put("Icelandic", "is");
//        languageMap.put("Indonesian", "id");
//        languageMap.put("Italian", "it");
        languageMap.put("Japanese", "ja");
//        languageMap.put("Kannada", "kn");
        languageMap.put("Korean", "ko");
//        languageMap.put("Latvian", "lv");
//        languageMap.put("Lithuanian", "lt");
//        //languageMap.put("Macedonian", "mk");
//        languageMap.put("Malay", "ms");
//        languageMap.put("Malayalam", "ml");
//        //languageMap.put("Maltese", "mt");
//        languageMap.put("Marathi", "mr");
//        languageMap.put("Norwegian", "no");
//        languageMap.put("Polish", "pl");
//        languageMap.put("Portuguese", "pt");
//        languageMap.put("Romanian", "ro");
//        languageMap.put("Russian", "ru");
//        languageMap.put("Slovak", "sk");
//        //languageMap.put("Slovenian", "sl");
//        languageMap.put("Spanish", "es");
//        //languageMap.put("Swahili", "sw");
//        languageMap.put("Swedish", "sv");
//        //languageMap.put("Tagalog", "tl");
//        languageMap.put("Tamil", "ta");
//        languageMap.put("Telugu", "te");
//        languageMap.put("Thai", "th");
//        languageMap.put("Turkish", "tr");
//        languageMap.put("Ukrainian", "uk");
//        languageMap.put("Urdu", "ur");
//        languageMap.put("Vietnamese", "vi");
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

    public class MyRunnable implements Runnable {
        private Handler handler;
        private boolean isRunning;
        private Context context;
        private ProgressDialog progressDialog;

        public MyRunnable(Context context, Handler handler) {
            this.context = context;
            this.handler = handler;
            this.isRunning = false;
            this.progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Processing...");
        }

        public void start() {
            isRunning = true;
            progressDialog.show();
            handler.postDelayed(this, 1500); // Run the task after 1000 milliseconds (1 second)
        }

        public void stop() {
            isRunning = false;
            progressDialog.dismiss();
            handler.removeCallbacks(this); // Remove any pending callbacks
        }

        @Override
        public void run() {
            // Your task logic here

            // Simulate a long-running task
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Disable the task after running for 1 second
            isRunning = false;

            // Dismiss the progressDialog on the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }
    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TextSpeech.this);
        builder.setMessage("No internet connection. Please check your connection and try again.");
        builder.setTitle("No Internet");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        builder.create().show();
    }

    public class MyRunnable2 implements Runnable {
        private Handler handler;
        private Context context;

        private ProgressDialog dowloading;

        public MyRunnable2(Context context, Handler handler, ProgressDialog dialog) {
            this.context = context;
            this.handler = handler;
            this.dowloading = dialog;

        }

        public void start() {
            handler.postDelayed(this, 60 * 1000); // Run the task after 30sec  (1 second)
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
    public class MySharedPreferences {

        private static final String PREF_NAME = "TextToSpeech";
        private static final String KEY_STRING = "TextLanguage";

        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor;

        public MySharedPreferences(Context context) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }

        // Method to save a string
        public void saveString(int value) {

            editor.putString(KEY_STRING, String.valueOf(value));
            editor.apply();
        }

        // Method to retrieve a string
        public String getString() {
            return sharedPreferences.getString(KEY_STRING, "");
        }
    }

}



