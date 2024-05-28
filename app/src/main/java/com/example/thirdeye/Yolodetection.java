// Tencent is pleased to support the open source community by making ncnn available.
//
// Copyright (C) 2021 THL A29 Limited, a Tencent company. All rights reserved.
//
// Licensed under the BSD 3-Clause License (the "License"); you may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
// https://opensource.org/licenses/BSD-3-Clause
//
// Unless required by applicable law or agreed to in writing, software distributed
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// CONDITIONS OF ANY KIND, either express or implied. See the License for the
// specific language governing permissions and limitations under the License.

package com.example.thirdeye;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Yolodetection extends Activity implements SurfaceHolder.Callback
{
    public static final int REQUEST_CAMERA = 100;
    private long starting_time = 0;
    private Map<String,Long> timestampmap = new HashMap<>();
    private SeekBar zoombar;
    private boolean partial_Blind = MainActivity.blindness;
    float currzoom = 1;

    private ImageButton backbtn;
    long prevtime = 0;
    private Yolov8Ncnn yolov8ncnn = new Yolov8Ncnn();
    private int facing = 1;
    private Spinner spinnerModel;
    private Spinner spinnerCPUGPU;
    private int current_model = 0;
    private int current_cpugpu = 0;
    private Vibrator vibe;

    private SurfaceView cameraView;
    private TextToSpeech ttsObject;
    private Button exitbtn,firstbutton,secondbutton;
    private TranslatorOptions options;
    private Translator translator;
    private ProgressDialog progressDialog;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cameraView = (SurfaceView) findViewById(R.id.cameraview);
        cameraView.getHolder().setFormat(PixelFormat.RGBA_8888);
        cameraView.getHolder().addCallback(this);
        ttsObject = new TextToSpeech(this, null);
        ttsObject.setSpeechRate(1.2f);
        backbtn = findViewById(R.id.backbtn22);
        exitbtn = findViewById(R.id.exitbtn22);
        View v = findViewById(R.id.black_overlay);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        starting_time = System.currentTimeMillis();
        if(!partial_Blind){
            v.setVisibility(View.VISIBLE);
        }
//        firstbutton = findViewById(R.id.zoomfirst);
//        secondbutton = findViewById(R.id.zoomsecond);

        progressDialog = new ProgressDialog(Yolodetection.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
//        firstbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        yolov8ncnn.closeCamera();
////
////                    }
////                });
//                currzoom = 1.5f;
//                yolov8ncnn.closeCamera();
//                yolov8ncnn.openCamera(1,1.5f);
//
//            }
//        });
//        secondbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
//////                        yolov8ncnn.closeCamera();
////
////                    }
////                });
//                currzoom = 2.0f;
//                yolov8ncnn.closeCamera();
//                yolov8ncnn.openCamera(1,2.0f);
//
//
//            }
//        });
        zoombar = findViewById(R.id.slidersetZoom);
//        zoombar.incrementProgressBy(2);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                zoombar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // Calculate zoom level from seekbar progress
                        float zoomLevel = (float) progress ;
                        Log.d("zoom level",""+zoomLevel);
//
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                yolov8ncnn.openCamera(1,zoomLevel);
                            }
                        }).start();

//                yolov8ncnn.ChangeZoomRatio(zoomLevel);


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Not needed in this implementation
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Not needed in this implementation
                        float zoomLevel = (float) seekBar.getProgress() ;
                        Log.d("zoom level",""+zoomLevel);
//                yolov8ncnn.closeCamera();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                yolov8ncnn.openCamera(1,zoomLevel);
                            }
                        }).start();

                        // Set zoom level

                    }
                });
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vibe.vibrate(50);
                if (ttsObject != null) {
                    ttsObject.stop();
                    ttsObject.shutdown();
                }
                onBackPressed();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                if (ttsObject != null) {
                    ttsObject.stop();
                    ttsObject.shutdown();
                }
                Intent intent = new Intent(Yolodetection.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        initNative(this);

//        Button buttonSwitchCamera = (Button) findViewById(R.id.buttonSwitchCamera);
//        buttonSwitchCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                int new_facing = 1 - facing;
//
//                yolov8ncnn.closeCamera();
//
//                yolov8ncnn.openCamera(new_facing);
//
//                facing = new_facing;
//            }
//        });
//
//        spinnerModel = (Spinner) findViewById(R.id.spinnerModel);
//        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
//            {
//                if (position != current_model)
//                {
//                    current_model = position;
//                    reload();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0)
//            {
//            }
//        });
//
//        spinnerCPUGPU = (Spinner) findViewById(R.id.spinnerCPUGPU);
//        spinnerCPUGPU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
//            {
//                if (position != current_cpugpu)
//                {
//                    current_cpugpu = position;
//                    reload();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0)
//            {
//            }
//        });
        options = new TranslatorOptions.Builder()
                .setSourceLanguage("en")
                .setTargetLanguage(MainActivity.output_lang)
                .build();
        reload();
        translator = Translation.getClient(options);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(System.currentTimeMillis()-prevtime>=200) {
                        Log.d("Hello from inside", "hellp");
                        prevtime = System.currentTimeMillis();
                        String[] arr = Yolov8Ncnn.getObjectlist();
//                    if(arr.length == 0){
//                        continue;
//                    }
                        for (String s : arr) {
                            long time = timestampmap.getOrDefault(s, 0L);
                            if (System.currentTimeMillis() - time >= 4000) {
                                timestampmap.put(s, System.currentTimeMillis());
                                if(s!=null && s.length() >0){
                                    TranslateandSay(s);
                                }
                                Log.d("Checking Objects", "" + s);
                            }

                        }
                        prevtime = System.currentTimeMillis();
                    }

                }
            }
        }).start();
    }
    private void reload()
    {
        boolean ret_init = yolov8ncnn.loadModel(getAssets(), current_model, current_cpugpu);
        if (!ret_init)
        {
            Log.e("MainActivity", "yolov8ncnn loadModel failed");
        }
        progressDialog.dismiss();
    }
    public void TranslateandSay(String s){

        // got a string
//        if(firsttime){
//            firsttime = false;
//
//        }
        translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                Task<String> result = translator.translate(s).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        s = s+"  .";
//                        translatedTextView.setText(s); // Set the translated text in the TextView
//                        translatedTextView.setVisibility(View.VISIBLE);
                        // Make the TextView visible
                        ttsObject.setLanguage(new Locale(""+MainActivity.output_lang));
                        ttsObject.speak(s, TextToSpeech.QUEUE_ADD, null, "myUtteranceID");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Yolodetection.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        yolov8ncnn.setOutputWindow(holder.getSurface());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }

        yolov8ncnn.openCamera(facing,zoombar.getProgress());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (ttsObject != null) {
            ttsObject.stop();
            ttsObject.shutdown();
        }
        yolov8ncnn.closeCamera();
    }
    protected void onDestroy() {
        if (ttsObject != null) {
            ttsObject.stop();
            ttsObject.shutdown();
        }
        yolov8ncnn.closeCamera();
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time-starting_time);
        userPreferences.addFeature(time,"Object Detection");
        Log.d("Duration check",""+time+" "+userPreferences.getFeatureListAsJsonArray());
        super.onDestroy();

    }
    @Override
    public void onBackPressed() {
//        finishAffinity();
        if (ttsObject != null) {
            ttsObject.stop();
            ttsObject.shutdown();
        }
        yolov8ncnn.closeCamera();
        Intent intent = new Intent(Yolodetection.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
