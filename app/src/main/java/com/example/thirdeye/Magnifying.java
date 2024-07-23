package com.example.thirdeye;

//import static com.example.thirdeye.AnalyticsManager.trackAppInstallation;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.thirdeye.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Magnifying extends AppCompatActivity {

    private long starting_time = 0;
    private ProcessCameraProvider cameraProvider;
    private ImageButton exitbtn;
    private Vibrator vibe;

    boolean isFlashon = false;
    private CameraControl cameraControl;
    private CameraSelector cameraSelector;
    private SeekBar zoomSeekBar;
    private Camera camera;

    private PreviewView previewView;
    int cameraFacing = CameraSelector.LENS_FACING_BACK;
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                startCamera(cameraFacing);
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnifying_main);

        zoomSeekBar = findViewById(R.id.zoombar);
        previewView = findViewById(R.id.cameraPreview);
        exitbtn = findViewById(R.id.exitbtn);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        MainActivity.shakeListener.unregisterShakeListener();
        MainActivity.shakeListener.onDestroy();
//        trackAppInstallation(this,"Magnifying");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (ContextCompat.checkSelfPermission(Magnifying.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCamera(cameraFacing);
        }
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(50);
                onBackPressed();
//                Intent intent = new Intent(Magnifying.this,MainActivity.class);
//                startActivity(intent);

            }
        });
        starting_time = System.currentTimeMillis();
    }
    @Override
    public void onBackPressed(){
//        finishAffinity();
        Intent intent = new Intent(Magnifying.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    public void startCamera(int cameraFacing) {
        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);
        listenableFuture.addListener(() -> {
            try {
                cameraProvider = (ProcessCameraProvider) listenableFuture.get();
                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();
                ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(cameraFacing).build();
                cameraProvider.unbindAll();
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy image) {
                        // Decode the image to a Bitmap4
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Decode the image to a Bitmap
                                ;
                                if ((!isFlashon)) {

                                    Bitmap bitmap = decodeSampledBitmapFromImageProxy(image);
                                    // Calculate average brightness of the image
                                    boolean temp = detectdarkness(bitmap);
                                    Log.d("Flash", "" + temp + " " + image);

                                    // Enable or disable flash based on brightness level
                                    if (temp) {
                                        isFlashon = true;
                                        enableFlash();
                                    } else {
                                        disableFlash();
                                    }
                                    Log.d("Flash", "" + isFlashon + " " + System.currentTimeMillis());

                                    // Close the image proxy to release its resources
                                    image.close();
                                }
                            }
                        }).start();
                    }
                });
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture,imageAnalysis);
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                cameraControl = camera.getCameraControl();
                cameraControl.enableTorch(isFlashon);
                cameraControl.setLinearZoom(zoomSeekBar.getProgress()/50f);
//                zoomSeekBar.incrementProgressBy(20);
                zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // Calculate zoom level from seekbar progress
                        float zoomLevel = (float) progress/50f ;

                        // Set zoom level
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cameraControl.setLinearZoom(zoomLevel);
                            }
                        });

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Not needed in this implementation
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Not needed in this implementation
                        float zoomLevel = (float) seekBar.getProgress() /50f;

                        // Set zoom level
//                        cameraControl.setLinearZoom(zoomLevel);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cameraControl.setLinearZoom(zoomLevel);
                            }
                        });
                    }

                });
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @OptIn(markerClass = ExperimentalGetImage.class) public static Bitmap decodeSampledBitmapFromImageProxy(ImageProxy imageProxy) {
        Image image = imageProxy.getImage();
        if (image == null) {
            return null;
        }

        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);
        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
    private void enableFlash() {

        cameraControl.enableTorch(true);
    }

    private void disableFlash() {

        cameraControl.enableTorch(false);
    }
    private boolean detectdarkness(Bitmap bitmap){
        if(bitmap == null){
            return false;
        }
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


    private int aspectRatio(int width, int height) {
        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }
    @Override
    public void onRestart(){
        startCamera(cameraFacing);
        super.onRestart();
    }
    @Override
    public void onDestroy(){
        long end_time = System.currentTimeMillis();
        UserPreferences userPreferences = new UserPreferences(this);
        String time = userPreferences.convertMillisToMinutesSeconds(end_time-starting_time);
        userPreferences.addFeature(time,"Magnifying");
        Log.d("Duration check",""+time+" "+userPreferences.getFeatureListAsJsonArray());
        super.onDestroy();
    }
}
