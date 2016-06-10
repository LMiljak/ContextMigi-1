package com.github.migi_1.ContextApp;


import java.io.IOException;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextMessages.LineairSpeedMessage;
import com.jme3.network.Client;

/**
 * Sensor that receives information about the accelerometer of the Android device.
 */
public class LinearSpeedSensor extends Activity implements SensorEventListener {

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
    public LinearSpeedSensor(MainActivity act, ClientWrapper client) {
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
        Log.d("main", "sensor change");
        if (act.getMain() == null) {
            return;
        }
        Log.d("main", "value: " + se.values[2]);
        float zforce = se.values[2];

        // log the sensor values
        //Log.d("main", xforce + " " + yforce + " " + zforce);
        //Sending the information to the Server.
        sendSensorInformation(zforce);
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
    private void sendSensorInformation(float zForce) {

        LineairSpeedMessage message = new LineairSpeedMessage(zForce);
        Log.d("main", "trying to send message");
        Client c = client.getClient();
        if (c.isStarted()) {
            Log.d("main", "trying to send message2");
            try {
                client.getClient().send(message);
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
                Log.d("main", "trying to send message3");
            } finally {
                System.out.println("aaaaah");
                Log.d("main", "trying to send message4");
            }
            Log.d("main", "sent message");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}
