package com.example.thirdeye;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class objdet extends AppCompatActivity {

    private static final String TAG = "ScanVoucherFragment";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    private final Handler handler = new Handler();
    byte[] data2 = null;
    boolean isStart = false;
    private int lastSpokenTextPosition = 0;
    private SurfaceHolder surfaceViewHolder;
    private SurfaceView surfaceView;
    private ImageView imageView;
    private Button btnTakePicture;
    private TextView textView;
    private TextToSpeech textToSpeech;
    private Camera camera;
    private TableLayout tb;
    private final Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Perform your task here
            textView.setVisibility(View.VISIBLE);
            takePictureAndRecognizeText();

            // Repeat the task every 1 seconds

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.objdet);
        initViews();
        requestPermission();
        initializeTextToSpeech();
        textView.setVisibility(View.GONE);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                isStart = !isStart;

                if (isStart) {
                    // Start the periodic task
                    textToSpeech.stop();
                    btnTakePicture.setBackgroundResource(R.drawable.stop_icon);
                    handler.postDelayed(runnableCode, 0);
                } else {
                    // Stop the periodic task
                    textView.setVisibility(View.GONE);
                    btnTakePicture.setBackgroundResource(R.drawable.start_record);
                    handler.removeCallbacks(runnableCode);
                }
            }
        });

    }

    private void takePictureAndRecognizeText() {
        try {



            byte[] imgBytes = CameraUtils.convertYuvToJpeg(data2, camera);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
//            imageView.setImageBitmap(bitmap);

            InputImage image = InputImage.fromBitmap(bitmap, CameraUtils.getRotationCompensation(CameraUtils.getCameraID(), this));
            ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
            labeler.process(image)
                    .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                        @Override
                        public void onSuccess(List<ImageLabel> labels) {
                            // Task completed successfully
                            // ...
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    });
            // [END process_image]

            // Process image with example onSuccess()
            labeler.process(image)
                    .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                        @Override
                        public void onSuccess(List<ImageLabel> labels) {
                            // [START get_image_label_info]

                            StringBuilder stringBuilder = new StringBuilder();
                            for (ImageLabel label : labels) {
                                String text = label.getText();
                                float confidence = label.getConfidence();
                                int index = label.getIndex();
                                if (confidence >= 0.6) {

                                    // adding to table
//                                    Toast.makeText(objdet.this,text, Toast.LENGTH_SHORT).show();
                                    stringBuilder.append(text+"\n");

                                }
                            }

//                            textView.setTextAppearance(R.style.text);
//                            textView.setBackgroundResource(R.drawable.speechtext_view);
                            textView.setMovementMethod(new ScrollingMovementMethod());

                            textView.setText(stringBuilder.toString());
                            speakText(textView.getText().toString(), 0);
                            // [END get_image_label_info]
                        }
                    });
//                        resetCamera();


        } catch (Exception e) {
            Log.e(TAG, "ERROR");
        }
        resetCamera();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "TextToSpeech: Language not supported.");
                    }
                } else {
                    Log.e(TAG, "TextToSpeech initialization failed.");
                }
            }
        });
    }

    private void initViews() {
        surfaceView = findViewById(R.id.obdsurfaceView);
        btnTakePicture = findViewById(R.id.obdbtnTakePicture);
        textView = findViewById(R.id.obdtextView);

    }

    private void speakText(String text, int startPosition) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
        lastSpokenTextPosition = startPosition;
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

        } else {
            setupSurfaceHolder();
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
                resetCamera();
                Log.e(TAG, "surfaceChanged: Changing");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //releaseCamera();
            }
        });
    }

    private void startCamera() {
        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(params);
        camera.setDisplayOrientation(CameraUtils.getRotationCompensation(CameraUtils.getCameraID(), this));
        try {
            camera.setPreviewDisplay(surfaceViewHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                //     Log.e(TAG, "onPreviewFrame: getyk");
                data2 = data;
            }
        });
    }

    public void resetCamera() {
        if (surfaceViewHolder.getSurface() == null) {
            // Return if preview surface does not exist
            return;
        }
        if (camera != null) {
            // Stop if preview surface is already running.
            camera.stopPreview();
            try {
                // Set preview display
                camera.setPreviewDisplay(surfaceViewHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Start the camera preview...
            camera.startPreview();
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
