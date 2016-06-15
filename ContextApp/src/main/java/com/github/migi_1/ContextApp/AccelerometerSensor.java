package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.jme3.network.Client;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

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
     * @param client
     *      The clientwrapper used for communication with the server.
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

            float xforce = se.values[0];
            float yforce = se.values[1];
            float zforce = se.values[2];

            // log the sensor values
            Log.d("main", xforce + " " + yforce + " " + zforce);
            //Sending the information to the Server.
            sendSensorInformation(xforce, yforce, zforce);
    }

    /**
     * Sends information about the accelerometer to the Server.
     * 
     * @param xforce
     *      Acceleration force along the x axis (including gravity).
     * @param yforce
     *      Acceleration force along the y axis (including gravity).
     * @param zforce
     *      Acceleration force along the z axis (including gravity).
     */

    private void sendSensorInformation(float xforce, float yforce, float zforce) {
        AccelerometerMessage message = new AccelerometerMessage(xforce, yforce, zforce);
        Client c = client.getClient();
        if (c.isStarted()) {
            client.getClient().send(message);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}
