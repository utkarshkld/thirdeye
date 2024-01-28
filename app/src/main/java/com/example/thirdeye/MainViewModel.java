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

import androidx.lifecycle.ViewModel;

/**
 *  This ViewModel is used to store object detector helper settings
 */
public class MainViewModel extends ViewModel {

    private int _delegate = ObjectDetectorHelper.DELEGATE_CPU;
    private float _threshold = ObjectDetectorHelper.THRESHOLD_DEFAULT;
    private int _maxResults = ObjectDetectorHelper.MAX_RESULTS_DEFAULT;
    private int _model = ObjectDetectorHelper.MODEL_EFFICIENTDETV0;

    public int getCurrentDelegate() {
        return _delegate;
    }

    public float getCurrentThreshold() {
        return _threshold;
    }

    public int getCurrentMaxResults() {
        return _maxResults;
    }

    public int getCurrentModel() {
        return _model;
    }

    public void setDelegate(int delegate) {
        _delegate = delegate;
    }

    public void setThreshold(float threshold) {
        _threshold = threshold;
    }

    public void setMaxResults(int maxResults) {
        _maxResults = maxResults;
    }

    public void setModel(int model) {
        _model = model;
    }
}
