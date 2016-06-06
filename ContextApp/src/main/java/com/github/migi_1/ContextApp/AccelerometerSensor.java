package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextApp.client.ClientWrapper;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.jme3.network.Client;

/**
 * Sensor that receives information about the accelerometer of the Android device.
 */
public class AccelerometerSensor extends Activity implements SensorEventListener {

    private MainActivity act;
    private ClientWrapper client;
    
    /**
     * Constructor for AccelerometerSensor.
     * 
     * @param act
     *      The main activity from which it was created.
     */
    public AccelerometerSensor(MainActivity act, ClientWrapper client) {
        this.act = act;
        this.client = client;
    }
    
    /**
     * Method called when the sensor reads a new input.
     * Logs the input values queues a new action, in this case the gyroscopechange method in the main class.
     */
    @Override
    public void onSensorChanged(SensorEvent se) {
            // check whether the game has already been instantiated
            if (act.getMain() == null) {
                return;
            }

            float x_force = se.values[0];
            float y_force = se.values[1];
            float z_force = se.values[2];
            
            // log the sensor values
            Log.d("main", x_force + " " + y_force + " " + z_force);
            //Sending the information to the Server.
            sendSensorInformation(x_force, y_force, z_force);
    }

    /**
     * Sends information about the accelerometer to the Server.
     * 
     * @param x_force
     *      Acceleration force along the x axis (including gravity).
     * @param y_force
     *      Acceleration force along the y axis (including gravity).
     * @param z_force
     *      Acceleration force along the z axis (including gravity).
     */
    private void sendSensorInformation(float x_force, float y_force, float z_force) {
        AccelerometerMessage message = new AccelerometerMessage(x_force, y_force, z_force);
        
        Client c = client.getClient();
        if (c.isStarted()) {
            client.getClient().send(message);
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    
}
