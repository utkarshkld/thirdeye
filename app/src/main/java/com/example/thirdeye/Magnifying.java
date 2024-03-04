package com.example.thirdeye;


import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Magnifying extends AppCompatActivity {

    private int count = 0;
    private int camerazoom = 0;
    private PackageManager pm;
    private boolean deviceHasFlash = false;
    private SurfaceHolder surfaceViewHolder;
    private SurfaceView surfaceView;

    public List<String> words = new ArrayList<>();
    //    private ImageView imageView;
    private ImageButton btnTakePicture;
    private SeekBar seekBar;
    private Button micc;
    private int langcounter = 0;

    private boolean s = false;

    private Camera camera;
    public static final int REQUEST_CODE = 100;
    private static final String TAG = "ScanVoucherFragment";
    private float speechrate = MainActivity.speech_rate;
    private String outputlangugage = MainActivity.output_lang;
    private String input_lang = MainActivity.input_lang;
    private boolean blindness = MainActivity.blindness;
    private boolean flash = false;

    private RelativeLayout rellayout;
    byte[] data2 = null;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    private TextToSpeech textToSpeech, textToSpeech2;
    private ImageView back;
    private ImageView imageView;

    private boolean isCaptured = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnifying_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        flash = false;
        pm = getPackageManager();
        deviceHasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        initViews();
        requestPermission();
        initializeTextToSpeech();
        imageView = findViewById(R.id.viewimage);
        rellayout = findViewById(R.id.rellayout);
        btnTakePicture.setBackgroundResource(R.drawable.camera_icon);
        seekBar = findViewById(R.id.vertical_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    if (camera != null) {

                        camerazoom = seekBar.getProgress();
//                    startCamera();
                        Camera.Parameters params = camera.getParameters();
                        params.setZoom(camerazoom);
                        camera.setParameters(params);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (camera != null) {

                    camerazoom = seekBar.getProgress();
//                    startCamera();
                    Camera.Parameters params = camera.getParameters();
                    params.setZoom(camerazoom);
                    camera.setParameters(params);
                }
            }
        });
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibe.vibrate(50);
                if (!isCaptured){
                    isCaptured = true;
                takePicture();
                btnTakePicture.setImageResource(R.drawable.retake_icon);
                seekBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);


//                    Transition transition2 = new Slide(Gravity.TOP);
//                    transition2.setDuration(500);
//                    transition2.addTarget(R.id.textView);
//                    TransitionManager.beginDelayedTransition(rellayout, transition2);
                surfaceView.setVisibility(View.GONE);
                releaseCamera();

            } else
            {
                imageView.setVisibility(View.GONE);
                seekBar.setVisibility(View.VISIBLE);
                btnTakePicture.setImageResource(R.drawable.camera_icon);
                isCaptured = false;
//                    Transition transition2 = new Slide();
//                    transition2.setDuration(500);
//                    transition2.addTarget(R.id.textView);
//                    TransitionManager.beginDelayedTransition(rellayout, transition2);
                surfaceView.setVisibility(View.VISIBLE);

            }
        }
            });
        back = findViewById(R.id.exitbtntexttospeech);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Magnifying.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }


    @Override
    protected void onPause() {
        releaseCamera();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startCamera();
        super.onResume();
    }

    private void initViews() {
        surfaceView = findViewById(R.id.surfaceView);
        btnTakePicture = findViewById(R.id.btnTakePicture);
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
                    if(outputlangugage == "id"){
                        result = textToSpeech.setLanguage(new Locale("in"));
                    }else if(outputlangugage == "no"){
                        result = textToSpeech.setLanguage(new Locale("nb"));
                    }
                    else {
                        result = textToSpeech.setLanguage(new Locale(outputlangugage));
                    }
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
                            }

                            @Override
                            public void onError(String utteranceId) {
                                Log.i("TTS", "utterance error");
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

    private void takePicture() {
        try {
            byte[] imgBytes = CameraUtils.convertYuvToJpeg(data2, camera);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            imageView.setImageBitmap(rotatedBitmap);

        } catch (Exception e) {
            Log.e(TAG, "ERROR");
        }
    }



    private void setupSurfaceHolder() {
        surfaceViewHolder = surfaceView.getHolder();
        surfaceViewHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startCamera();
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
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        params.setZoom(seekBar.getProgress());

        if(deviceHasFlash && flash){
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }else if(flash && !deviceHasFlash){
            Toast.makeText(Magnifying.this,"Device doesnt have flash Light",Toast.LENGTH_LONG).show();
        }
        camera.setParameters(params);
        camera.setDisplayOrientation(CameraUtils.getRotationCompensation(CameraUtils.getCameraID(), Magnifying.this));

        try {
            camera.setPreviewDisplay(surfaceViewHolder);
            camera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

                data2 = data;
                    byte[] imgBytes = CameraUtils.convertYuvToJpeg(data2, camera);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                    if(!flash && detectdarkness(bitmap) && camera!=null && deviceHasFlash){
                        flash = true;
                        Camera.Parameters params = camera.getParameters();
                        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(params);
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



    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();

    }
    @Override
    public void onBackPressed() {
        releaseCamera();
        super.onBackPressed();
    }
}



