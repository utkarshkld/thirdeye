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

import android.content.res.AssetManager;
import android.view.Surface;

public class Yolov8Ncnn {
    // Native method to load the model with specified model ID and CPU/GPU usage
    public native boolean loadModel(AssetManager mgr, int modelid, int cpugpu);

    // Static native method to get the list of objects
    public static native String[] getObjectlist();

    // Native method to open the camera with specified facing direction and magnifying level
    public native boolean openCamera(int facing, float magnifyinglevel);

    // Native method to close the camera
    public native boolean closeCamera();

    // Native method to set the output window where the camera preview will be displayed
    public native boolean setOutputWindow(Surface surface);

    // Load the native library "yolov8ncnn"
    static {
        System.loadLibrary("yolov8ncnn");
    }
}
