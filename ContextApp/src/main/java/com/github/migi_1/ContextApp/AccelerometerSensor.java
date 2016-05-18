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
 *
 * @author Marcel
 */
public class AccelerometerSensor extends Activity implements SensorEventListener {

    private HelloActivity act;
    
    public AccelerometerSensor(HelloActivity act){
        this.act = act;
    }
    
    @Override
    public void onSensorChanged(SensorEvent se) {
        /** check whether the game has already been instantiated **/
            if (act.getMain() == null) {
                return;
            }

            /** log the sensor values **/
            Log.d("main", Float.toString(se.values[0]) + " " + Float.toString(se.values[1]) + " " + Float.toString(se.values[2]));

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
