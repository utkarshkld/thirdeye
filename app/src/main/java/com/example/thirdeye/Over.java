/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.thirdeye;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.mediapipe.tasks.components.containers.Detection;
import com.google.mediapipe.tasks.vision.core.RunningMode;
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Over extends View {

    private Map<String, Float> objectResolver = new HashMap<>();
    private String currobject = "";
    private ObjectDetectorResult results;
    private Paint boxPaint;
    private Paint textBackgroundPaint;
    private Paint textPaint;
    private float scaleFactor;
    private Rect bounds;
    private int outputWidth;
    private int outputHeight;
    private int outputRotate;
    private RunningMode runningMode = RunningMode.IMAGE;
    private TextToSpeech textToSpeech;

    public Over(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaints();
        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(getContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(ObjectDetectorHelper.TAG, "TextToSpeech: Language not supported.");
                }
            } else {
                Log.e(ObjectDetectorHelper.TAG, "TextToSpeech initialization failed.");
            }
        });
    }

    public void clear() {
        results = null;
        textPaint.reset();
        textBackgroundPaint.reset();
        boxPaint.reset();
        invalidate();
        initPaints();
    }

    public void setRunningMode(RunningMode runningMode) {
        this.runningMode = runningMode;
    }

    private void initPaints() {
        textBackgroundPaint = new Paint();
        textBackgroundPaint.setColor(Color.BLACK);
        textBackgroundPaint.setStyle(Paint.Style.FILL);
        textBackgroundPaint.setTextSize(50f);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(50f);

        boxPaint = new Paint();
        boxPaint.setColor(ContextCompat.getColor(getContext(), R.color.mp_primary));
        boxPaint.setStrokeWidth(8F);
        boxPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (results != null) {

            for (Detection detection : results.detections()) {
                RectF boxRect = new RectF(
                        detection.boundingBox().left,
                        detection.boundingBox().top,
                        detection.boundingBox().right,
                        detection.boundingBox().bottom
                );

                Matrix matrix = new Matrix();
                matrix.postTranslate(-outputWidth / 2f, -outputHeight / 2f);
                matrix.postRotate(outputRotate);

                if (outputRotate == 90 || outputRotate == 270) {
                    matrix.postTranslate(outputHeight / 2f, outputWidth / 2f);
                } else {
                    matrix.postTranslate(outputWidth / 2f, outputHeight / 2f);
                }
                matrix.mapRect(boxRect);

                float top = boxRect.top * scaleFactor;
                float bottom = boxRect.bottom * scaleFactor;
                float left = boxRect.left * scaleFactor;
                float right = boxRect.right * scaleFactor;

//                RectF drawableRect = new RectF(left, top, right, bottom);
                canvas.drawRect(new RectF(left, top, right, bottom), boxPaint);

                String category = detection.categories().get(0).categoryName();
                String drawableText = category + " " +
                        String.format(Locale.getDefault(), "%.2f", detection.categories().get(0).score());



//                Log.d("object here", category);

                textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length(), bounds);
                float textWidth = bounds.width();
                float textHeight = bounds.height();

                canvas.drawRect(
                        left,
                        top,
                        left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                        top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                        textBackgroundPaint
                );

                canvas.drawText(
                        drawableText,
                        left,
                        top + bounds.height(),
                        textPaint
                );
            }
        }
    }

    public void setResults(ObjectDetectorResult detectionResults, int outputHeight, int outputWidth, int imageRotation) {
        results = detectionResults;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.outputRotate = imageRotation;

        int[] rotatedWidthHeight = getRotatedWidthHeight(imageRotation);

        scaleFactor = (runningMode == RunningMode.LIVE_STREAM) ?
                Math.max(getWidth() * 1f / rotatedWidthHeight[0], getHeight() * 1f / rotatedWidthHeight[1]) :
                Math.min(getWidth() * 1f / rotatedWidthHeight[0], getHeight() * 1f / rotatedWidthHeight[1]);

        invalidate();
    }

    private int[] getRotatedWidthHeight(int imageRotation) {
        switch (imageRotation) {
            case 0:
            case 180:
                return new int[]{outputWidth, outputHeight};
            case 90:
            case 270:
                return new int[]{outputHeight, outputWidth};
            default:
                return new int[]{outputWidth, outputHeight};
        }
    }

    private static final int BOUNDING_RECT_TEXT_PADDING = 8;
}
