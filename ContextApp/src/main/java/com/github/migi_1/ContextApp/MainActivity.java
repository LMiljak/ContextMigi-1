package com.github.migi_1.ContextApp;

import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;

import com.github.migi_1.ContextApp.BugEvent.RotateBugSprayActivity;
import com.github.migi_1.ContextApp.client.AutoConnector;
import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.jme3.app.AndroidHarness;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * This class contains the main activity that is started you run the project.
 *
 * @author Marcel
 */
public class MainActivity extends AndroidHarness {

        private Main application;
        private SensorManager mSensorManager;
        private AccelerometerSensor accelerometerSensor;
        private ClientWrapper client;

        /**
         * Configure the game instance that is launched and start the logger.
         */
        public MainActivity() {
            // Set the application class to run
            appClass = "com.github.migi_1.ContextApp.Main";

            // Start the log manager
            LogManager.getLogManager().getLogger("").setLevel(Level.INFO);

        }

        /**
         * This method runs the app is resumed.
         */
        @Override
        protected void onResume() {
            super.onResume();

            // register the lister for the accelerometer
            mSensorManager.registerListener(accelerometerSensor,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_FASTEST);

            client.startClient();
        }

        /**
         * Closes the app.
         */
        @Override
        protected void onStop() {
            super.onStop();
            mSensorManager.unregisterListener(accelerometerSensor);

            client.closeClient();

        }
        /**
         * Instanciate the game instance.
         * Instanciate the sensor manager.
         * @param savedInstanceState
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.android_ingame_fr);

            //instantiate the application
            application = (Main) getJmeApplication();

            //start the sensor manager
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            setContentView(R.layout.android_searching);

            // create the client
            client = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10));

            // create te accelerometerSensor
            accelerometerSensor = new AccelerometerSensor(this, client);

            // set the ui to the ingame screen
            setContentView(R.layout.android_ingame_fr);

            // Retrieve buttons
            Button leftButton = (Button) findViewById(R.id.FR_button_left);
            Button middleButton = (Button) findViewById(R.id.FR_button_middle);
            Button rightButton = (Button) findViewById(R.id.FR_button_right);
            Button triggerButton = (Button) findViewById(R.id.FR_button_trigger);

            // add logging functionality
            setButtons(leftButton, "left");
            setButtons(middleButton, "middle");
            setButtons(rightButton, "right");
            setButtons(triggerButton, "trigger");
        }

        /**
         * Makes sure buttonpresses are logged.
         * @param butt = the button to which a clicklistener is set
         * @param str = message to be logged
         */
        public void setButtons(Button butt, final String str) {

            butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", str);
                    if(str.equals("trigger")) {
                        Intent nextScreen = new Intent(getApplicationContext(), RotateBugSprayActivity.class);
                        startActivity(nextScreen);
                    }
                }
                });
        }

        /**
         * Gets the instance of this Application.
         *
         * @return
         *      The instance of this Application.
         */
        public Main getMain() {
            return application;
        }
}