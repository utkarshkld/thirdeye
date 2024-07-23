package com.example.thirdeye;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MicHandler implements SensorEventListener {

    // Threshold for detecting a shake movement
    private static final int SHAKE_THRESHOLD = 20; // Adjust this value as needed

    // Time interval to prevent multiple shake detections in quick succession
    private static final int SHAKE_TIME_INTERVAL = 5000; // Adjust this value as needed

    private SensorManager sensorManager;
    private OnShakeListener onShakeListener;
    private long lastShakeTime;

    public MicHandler(Context context) {
        // Get the SensorManager from the system service
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        registerShakeListener();
    }

    // Interface definition for a callback to be invoked when a shake is detected
    public interface OnShakeListener {
        void onShake();
    }

    // Set the listener for shake detection
    public void setOnShakeListener(OnShakeListener listener) {
        this.onShakeListener = listener;
    }

    // Register the accelerometer sensor to start listening for shake events
    public void registerShakeListener() {
        if (sensorManager != null) {
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    // Unregister the accelerometer sensor to stop listening for shake events
    public void unregisterShakeListener() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Check if the sensor event is from the accelerometer
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();

            // Check if the time interval since the last shake is greater than the defined threshold
            if ((currentTime - lastShakeTime) > SHAKE_TIME_INTERVAL) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Calculate the acceleration excluding gravity
                double acceleration = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

                // If the calculated acceleration is greater than the shake threshold
                if (acceleration > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime;

                    // If a shake listener is set, call the onShake method
                    if (onShakeListener != null) {
                        onShakeListener.onShake();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing for now
    }

    // Call this method to clean up resources when the object is destroyed
    public void onDestroy() {
        unregisterShakeListener();
    }
}
