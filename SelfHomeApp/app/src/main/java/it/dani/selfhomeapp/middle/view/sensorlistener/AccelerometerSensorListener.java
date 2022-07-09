package it.dani.selfhomeapp.middle.view.sensorlistener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.Arrays;
import java.util.function.Consumer;

public class AccelerometerSensorListener implements SensorEventListener {

    private static final double TRIGGER_VALUE = 1;
    private static final int DIM = 3;

    private float[] prevXAx;
    private float[] prevYAx;
    private float[] prevZAx;

    private Consumer<Double> consumer;

    public AccelerometerSensorListener(Consumer<Double> consumer)
    {
        this.consumer = consumer;

        this.prevXAx = new float[DIM];
        this.prevYAx = new float[DIM];
        this.prevZAx = new float[DIM];

        Arrays.fill(this.prevXAx,Float.MIN_VALUE);
        Arrays.fill(this.prevYAx,Float.MIN_VALUE);
        Arrays.fill(this.prevZAx,Float.MIN_VALUE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String result = "";
        for(float v : event.values)
        {
            if(result.length() != 0) result += "\t";
            result += v;
        }

        if(this.prevXAx[0] != Float.MIN_VALUE && this.prevYAx[0] != Float.MIN_VALUE && this.prevZAx[0] != Float.MIN_VALUE)
        {
            double preVector = Math.sqrt(Math.pow(this.prevXAx[0],2) + Math.pow(this.prevYAx[0],2) + Math.pow(this.prevZAx[0],2));
            double vector = Math.sqrt(Math.pow(event.values[0],2) + Math.pow(event.values[1],2) + Math.pow(event.values[2],2));
            double delta = vector - preVector;

            this.consumer.accept(delta);

            result += "\tvector = " + vector + "\tpreVector = " + preVector + "\tdelta = " + delta;

            if(Math.abs(delta) >= TRIGGER_VALUE);
        }

        for(int count = 1; count < DIM; count++)
        {
            this.prevXAx[count - 1] = this.prevXAx[count];
            this.prevYAx[count - 1] = this.prevYAx[count];
            this.prevZAx[count - 1] = this.prevZAx[count];
        }

        this.prevXAx[DIM - 1] = event.values[0];
        this.prevYAx[DIM - 1] = event.values[1];
        this.prevZAx[DIM - 1] = event.values[2];

        //Log.d("AccelerometerSensor","Values: " + result);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
