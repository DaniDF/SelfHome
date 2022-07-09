package it.dani.selfhomeapp.middle.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import it.dani.selfhomeapp.middle.view.sensorlistener.AccelerometerSensorListener;

public class SensorUtils {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private AccelerometerSensorListener accelerometerSensorListener;

    public void accelerometerSetup(Context context)
    {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        this.accelerometerSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("AccelerometerSensor","AccelerometerSensor: " + ((this.accelerometerSensor == null)? "NULL" : "PRESENT"));

        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String[] flashLight = new String[1];
        try {
            for(String c : cameraManager.getCameraIdList())
            {
                if(cameraManager.getCameraCharacteristics(c).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) flashLight[0] = c;
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        int[] counterShake = new int[1];
        boolean[] dir = new boolean[1];
        boolean[] flashLightState = new boolean[1];
        this.accelerometerSensorListener = new AccelerometerSensorListener(x -> {
            try {
                if(Math.abs(x) > 12 && counterShake[0] == 0)
                {
                    dir[0] = (x > 0);
                    counterShake[0]++;
                }
                else if(Math.abs(x) > 12 && counterShake[0] > 0 && (x > 0) != dir[0])
                {
                    dir[0] = (x > 0);
                    counterShake[0]++;
                }

                if(counterShake[0] > 3)
                {
                    counterShake[0] = 0;
                    cameraManager.setTorchMode(flashLight[0],(flashLightState[0] = !flashLightState[0]));
                }

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public SensorManager getSensorManager() {
        return this.sensorManager;
    }

    public Sensor getAccelerometerSensor() {
        return this.accelerometerSensor;
    }

    public AccelerometerSensorListener getAccelerometerSensorListener() {
        return this.accelerometerSensorListener;
    }
}
