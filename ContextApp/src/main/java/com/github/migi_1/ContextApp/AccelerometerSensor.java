/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import java.util.concurrent.Callable;

/**
 * Sensor that receives information about the accelerometer of the Android device.
 */
public class AccelerometerSensor extends Activity implements SensorEventListener {

    private HelloActivity act;
    
    /**
     * Constructor for AccelerometerSensor.
     * 
     * @param act
     *      The main activity from which is was created.
     */
    public AccelerometerSensor(HelloActivity act) {
        this.act = act;
    }
    
    @Override
    public void onSensorChanged(SensorEvent se) {
        /** check whether the game has already been instantiated **/
            if (act.getMain() == null) {
                return;
            }

            /** log the sensor values **/
            Log.d("main", se.values[0] + " " + se.values[1] + " " + se.values[2]);

            /** this is an example of how you can call a method in the game **/
            act.getMain().enqueue(new Callable() {

                //@Override
                public Object call() throws Exception {
                
                    /** Example of how you can make a call to a method in the game instance **/
                    act.getMain().gyroscopeChange();
                    return null;
                }
             });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    
}
