package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.jme3.network.Client;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.jme3.math.Vector3f;

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

            float xforce = se.values[0];
            float yforce = se.values[1];
            float zforce = se.values[2];

            // log the sensor values
            //Sending the information to the Server.
            sendSensorInformation(new Vector3f(xforce, yforce, zforce));
    }

    /**
     * Sends information about the accelerometer to the Server.
     * 
     * @param forces
     *      The acceleration forces across each dimension.
     */

    private void sendSensorInformation(Vector3f forces) {
        AccelerometerMessage message = new AccelerometerMessage(forces);
        Client c = client.getClient();
        if (c.isStarted() && !act.isImmobilised()) {
            client.getClient().send(message);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}
