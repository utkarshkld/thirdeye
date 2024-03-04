/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.thirdeye;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import androidx.annotation.VisibleForTesting;
import androidx.camera.core.ImageProxy;

import com.google.mediapipe.framework.image.BitmapImageBuilder;
import com.google.mediapipe.framework.image.MPImage;
import com.google.mediapipe.tasks.components.containers.Detection;
import com.google.mediapipe.tasks.core.BaseOptions;
import com.google.mediapipe.tasks.core.Delegate;
import com.google.mediapipe.tasks.vision.core.ImageProcessingOptions;
import com.google.mediapipe.tasks.vision.core.RunningMode;
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector;
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
// Need to sync the tts and object detection properly

public class ObjectDetectorHelper {
    public static final float THRESHOLD_DEFAULT = 0.5f;
    public static HashMap<String, String> translationMap = new HashMap<>();
    public static final int MAX_RESULTS_DEFAULT = 5;
    public static final int DELEGATE_CPU = 0;
    public static final int DELEGATE_GPU = 1;
    public static final int MODEL_EFFICIENTDETV0 = 0;
    public static final int MODEL_EFFICIENTDETV2 = 1;
    public static final int OTHER_ERROR = 0;
    public static final int GPU_ERROR = 1;
    public static final String TAG = "ObjectDetectorHelper";
//    private long temptime = 0, temptime2 = 0;
    public float threshold = THRESHOLD_DEFAULT;
    public int maxResults = MAX_RESULTS_DEFAULT;
    public int currentDelegate = DELEGATE_GPU;
    public int currentModel = MODEL_EFFICIENTDETV2;
    public RunningMode runningMode;
    public Context context;
    public DetectorListener objectDetectorListener;

    private static Map<String, Long> objectResolver = cMainActivity.objectResolver;
    private String currobject;
    private ObjectDetector objectDetector;
    private int imageRotation;
    private ImageProcessingOptions imageProcessingOptions;
    public static TextToSpeech textToSpeech;
//    private float speechrate = MainActivity.speech_rate;
    private final String output_lang = MainActivity.output_lang;
    public static String language;


    public ObjectDetectorHelper(float threshold, int maxResults, int currentDelegate, int currentModel,
                                RunningMode runningMode, Context context, DetectorListener objectDetectorListener) {
        this.threshold = threshold;
        this.maxResults = maxResults;
        this.currentDelegate = currentDelegate;
        this.currentModel = currentModel;
        this.runningMode = runningMode;
        this.context = context;
        this.objectDetectorListener = objectDetectorListener;
        objectResolver = new HashMap<>();
        currobject = "";
        setupObjectDetector();
        initializeTextToSpeech();
        initializemap();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result;
                if(output_lang.equals("id")){
                    result = textToSpeech.setLanguage(new Locale("in"));
                }else if(output_lang.equals("no")){
                    result = textToSpeech.setLanguage(new Locale("nb"));
                }
                else {
                    result = textToSpeech.setLanguage(new Locale(output_lang));
                }
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "TextToSpeech: Language not supported.");
                }else{

                        // Set up utterance progress listener
                        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String utteranceId) {
                                // Set your flag to true when speech starts isWordspeaking = true;
                            }

                            @Override
                            public void onDone(String utteranceId) {
                                // Set your flag to false when speech is completed


                            }

                            @Override
                            public void onError(String utteranceId) {
                                // Handle errors if needed
                            }
                        });

                }
            } else {
                Log.e(TAG, "TextToSpeech initialization failed.");
            }
        }, "com.google.android.tts");
        textToSpeech.setSpeechRate(1.2f);
    }

    private void speakText(String text, int startPosition) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, "myUtteranceId");
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, "myUtteranceId");
        }
    }

    public void clearObjectDetector() {
        if (objectDetector != null) {
            objectDetector.close();
            objectDetector = null;
        }
    }

    public void setupObjectDetector() {
        BaseOptions.Builder baseOptionsBuilder = BaseOptions.builder();
        baseOptionsBuilder.setDelegate(Delegate.CPU);
        String modelName;
        modelName = "efficientdet-lite2.tflite";
        baseOptionsBuilder.setModelAssetPath(modelName);
        try {
            ObjectDetector.ObjectDetectorOptions.Builder optionsBuilder = ObjectDetector.ObjectDetectorOptions.builder()
                    .setBaseOptions(baseOptionsBuilder.build())
                    .setScoreThreshold(.55f)
                    .setRunningMode(runningMode)
                    .setMaxResults(3);
            imageProcessingOptions = ImageProcessingOptions.builder()
                    .setRotationDegrees(imageRotation)
                    .build();
                    optionsBuilder.setRunningMode(runningMode).setResultListener(this::returnLivestreamResult).setErrorListener(this::returnLivestreamError);


            ObjectDetector.ObjectDetectorOptions options = optionsBuilder.build();
            objectDetector = ObjectDetector.createFromOptions(context, options);
        } catch (IllegalStateException e) {
            objectDetectorListener.onError("Object detector failed to initialize. See error logs for details", OTHER_ERROR);
            Log.e(TAG, "TFLite failed to load model with error: " + e.getMessage());
        } catch (RuntimeException e) {
            objectDetectorListener.onError("Object detector failed to initialize. See error logs for details", GPU_ERROR);
            Log.e(TAG, "Object detector failed to load model with error: " + e.getMessage());
        }
    }

    public boolean isClosed() {
        return objectDetector == null;
    }


    public void detectLivestreamFrame(ImageProxy imageProxy) {
        if (runningMode != RunningMode.LIVE_STREAM) {
            throw new IllegalArgumentException("Attempting to call detectLivestreamFrame while not using RunningMode.LIVE_STREAM");
        }

        long frameTime = SystemClock.uptimeMillis();
        Bitmap bitmapBuffer = Bitmap.createBitmap(imageProxy.getWidth(), imageProxy.getHeight(), Bitmap.Config.ARGB_8888);


        try {
            bitmapBuffer.copyPixelsFromBuffer(imageProxy.getPlanes()[0].getBuffer());

        } catch (Exception e) {
            // handle the exception
        } finally {
            imageProxy.close();
        }

        if (imageProxy.getImageInfo().getRotationDegrees() != imageRotation) {
            imageRotation = imageProxy.getImageInfo().getRotationDegrees();
            clearObjectDetector();
            setupObjectDetector();
            return;
        }

        if(!cMainActivity.flash){
            Log.d("Detecting Darkness",""+detectdarkness(bitmapBuffer));
            if(detectdarkness(bitmapBuffer)){
                cMainActivity.flash = true;
                cMainActivity.isPlay=false;
                Intent intent = new Intent(context, cMainActivity.class);
                intent.putExtra("flash",true);
                context.startActivity(intent);

            }
        }
        if(cMainActivity.isPlay) {
            MPImage mpImage = new BitmapImageBuilder(bitmapBuffer).build();
            if(mpImage!=null)
            detectAsync(mpImage, frameTime);
        }
    }



    @VisibleForTesting
    public void detectAsync(MPImage mpImage, long frameTime) {
        if (cMainActivity.isPlay) {
            if(objectDetector!=null)
            objectDetector.detectAsync(mpImage, imageProcessingOptions, frameTime);
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

    private void returnLivestreamResult(ObjectDetectorResult result, MPImage input) {
        long finishTimeMs = SystemClock.uptimeMillis();
        long inferenceTime = finishTimeMs - result.timestampMs();
        language = cMainActivity.languageMap.get(output_lang);
        Log.d("objectdetector helper",language);
        for (Detection t : result.detections()) {
            long currTime = System.currentTimeMillis();
            currobject = t.categories().get(0).categoryName();
            long lastSpeakTime = objectResolver.getOrDefault(currobject, 0L);
            if (lastSpeakTime + 4000 <= currTime ) {
//                Log.d("objectdetector helper",language+"_"+currobject);
                speakText(translationMap.get(language+"_"+currobject),0);
                objectResolver.put(currobject,currTime);

            }
        }
        objectDetectorListener.onResults(new ResultBundle(
                Collections.singletonList(result),
                inferenceTime,
                input.getHeight(),
                input.getWidth(),
                imageRotation
        ));


    }

    private void returnLivestreamError(RuntimeException error) {
        objectDetectorListener.onError(error.getMessage() != null ? error.getMessage() : "An unknown error has occurred", OTHER_ERROR);
    }


    public interface DetectorListener {
        void onError(String error, int errorCode);

        void onResults(ResultBundle resultBundle);
    }

    public static class ResultBundle {
        private List<ObjectDetectorResult> results;
        private long inferenceTime;
        private int inputImageHeight;
        private int inputImageWidth;
        private int inputImageRotation;

        public ResultBundle(List<ObjectDetectorResult> results, long inferenceTime, int inputImageHeight, int inputImageWidth) {
            this(results, inferenceTime, inputImageHeight, inputImageWidth, 0);
        }

        public ResultBundle(List<ObjectDetectorResult> results, long inferenceTime, int inputImageHeight, int inputImageWidth, int inputImageRotation) {
            this.results = results;
            this.inferenceTime = inferenceTime;
            this.inputImageHeight = inputImageHeight;
            this.inputImageWidth = inputImageWidth;
            this.inputImageRotation = inputImageRotation;
        }

        public List<ObjectDetectorResult> getResults() {
            return results;
        }

        public long getInferenceTime() {
            return inferenceTime;
        }

        public int getInputImageHeight() {
            return inputImageHeight;
        }

        public int getInputImageWidth() {
            return inputImageWidth;
        }

        public int getInputImageRotation() {
            return inputImageRotation;
        }
    }
    public void initializemap(){
        settranslationmap.initialize();
        settinglang2.initialize2();
    }
}



