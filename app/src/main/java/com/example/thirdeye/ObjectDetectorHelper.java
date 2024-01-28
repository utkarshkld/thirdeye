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
import android.graphics.Bitmap;
import android.os.Build;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.text.SpannableString;
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
    private long temptime = 0, temptime2 = 0;
    public float threshold = THRESHOLD_DEFAULT;
    public int maxResults = MAX_RESULTS_DEFAULT;
    public int currentDelegate = DELEGATE_GPU;
    public int currentModel = MODEL_EFFICIENTDETV2;
    public RunningMode runningMode;
    public Context context;
    public DetectorListener objectDetectorListener;

    private Map<String, Long> objectResolver;
    private String currobject;
    private ObjectDetector objectDetector;
    private int imageRotation;
    private ImageProcessingOptions imageProcessingOptions;
    public static TextToSpeech textToSpeech;
    private float speechrate = MainActivity.speech_rate;

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
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "TextToSpeech: Language not supported.");
                }
            } else {
                Log.e(TAG, "TextToSpeech initialization failed.");
            }
        }, "com.google.android.tts");
        textToSpeech.setSpeechRate(speechrate);
    }

    private void speakText(String text, int startPosition) {
        SpannableString spannableString = new SpannableString(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(spannableString, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            textToSpeech.speak(spannableString.toString(), TextToSpeech.QUEUE_ADD, null, null);
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
                    .setScoreThreshold(.50f)
                    .setRunningMode(runningMode)
                    .setMaxResults(5);

            imageProcessingOptions = ImageProcessingOptions.builder()
                    .setRotationDegrees(imageRotation)
                    .build();

            switch (runningMode) {
                case LIVE_STREAM:
                    optionsBuilder.setRunningMode(runningMode)
                            .setResultListener(this::returnLivestreamResult)
                            .setErrorListener(this::returnLivestreamError);
                    break;
            }

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

//    public ResultBundle detectVideoFile(Uri videoUri, long inferenceIntervalMs) throws IOException {
//        if (runningMode != RunningMode.VIDEO) {
//            throw new IllegalArgumentException("Attempting to call detectVideoFile while not using RunningMode.VIDEO");
//        }
//        if (objectDetector == null) {
//            return null;
//        }
//
//        long startTime = SystemClock.uptimeMillis();
//        boolean didErrorOccurred = false;
//
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        retriever.setDataSource(context, videoUri);
//        String videoLengthMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//        Integer width = null;
//        Integer height = null;
//
//        Bitmap firstFrame = retriever.getFrameAtTime(0);
//        if (firstFrame != null) {
//            width = firstFrame.getWidth();
//            height = firstFrame.getHeight();
//        }
//
//        if (videoLengthMs == null || width == null || height == null) {
//            return null;
//        }
//
//        List<ObjectDetectorResult> resultList = new ArrayList<>();
//        long numberOfFrameToRead = Long.parseLong(videoLengthMs) / inferenceIntervalMs;
//
//        for (int i = 0; i <= numberOfFrameToRead; i++) {
//            long timestampMs = i * inferenceIntervalMs;
//            Bitmap frame = retriever.getFrameAtTime(timestampMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
//
//            if (frame != null) {
//                Bitmap argb8888Frame = frame.copy(Bitmap.Config.ARGB_8888, false);
//                MPImage mpImage = new BitmapImageBuilder(argb8888Frame).build();
//                ObjectDetectorResult detectionResult = objectDetector.detectForVideo(mpImage, timestampMs);
//
//                if (detectionResult != null) {
//                    resultList.add(detectionResult);
//                } else {
//                    didErrorOccurred = true;
//                    objectDetectorListener.onError("ResultBundle could not be returned in detectVideoFile",OTHER_ERROR);
//                }
//            } else {
//                didErrorOccurred = true;
//                objectDetectorListener.onError("Frame at specified time could not be retrieved when detecting in video.",GPU_ERROR);
//            }
//        }
//
//        retriever.release();
//        long inferenceTimePerFrameMs = (SystemClock.uptimeMillis() - startTime) / numberOfFrameToRead;
//
//        if (didErrorOccurred) {
//            return null;
//        } else {
//            return new ResultBundle(resultList, inferenceTimePerFrameMs, height, width);
//        }
//    }

    public void detectLivestreamFrame(ImageProxy imageProxy) {
        if (runningMode != RunningMode.LIVE_STREAM) {
            throw new IllegalArgumentException("Attempting to call detectLivestreamFrame while not using RunningMode.LIVE_STREAM");
        }


        Log.d(TAG, "detectLivestreamFrame: 111");
        temptime = System.currentTimeMillis();
        long frameTime = SystemClock.uptimeMillis();
        Bitmap bitmapBuffer = Bitmap.createBitmap(imageProxy.getWidth(), imageProxy.getHeight(), Bitmap.Config.ARGB_8888);
//        imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.getPlanes()[0].getBuffer()); }
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

        MPImage mpImage = new BitmapImageBuilder(bitmapBuffer).build();
        if (System.currentTimeMillis() - temptime2 >= 1000) {
            temptime2 = System.currentTimeMillis();
            detectAsync(mpImage, frameTime);
        }


    }

    @VisibleForTesting
    public void detectAsync(MPImage mpImage, long frameTime) {
        if (cMainActivity.isPlay) {
            objectDetector.detectAsync(mpImage, imageProcessingOptions, frameTime);
        }
    }

    private void returnLivestreamResult(ObjectDetectorResult result, MPImage input) {
        long finishTimeMs = SystemClock.uptimeMillis();
        long inferenceTime = finishTimeMs - result.timestampMs();
        String language = cMainActivity.spinnerLanguages.getSelectedItem().toString();
        for (Detection t : result.detections()) {
            long currTime = System.currentTimeMillis();
            currobject = t.categories().get(0).categoryName();
            long lastSpeakTime = objectResolver.getOrDefault(currobject, 0L);
            if (lastSpeakTime + 2000<= currTime) {
                textToSpeech.setLanguage(new Locale(cMainActivity.languageMap.get(language)));
                speakText(translationMap.get(language+"_"+currobject),0);
                objectResolver.replace(currobject,currTime);

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



