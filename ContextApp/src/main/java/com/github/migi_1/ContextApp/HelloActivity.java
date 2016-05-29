package com.github.migi_1.ContextApp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import com.jme3.app.AndroidHarness;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * This class contains the main activity that is started you run the project.
 * 
 * @author Marcel
 */
public class HelloActivity extends AndroidHarness {
        
        private Main application;
        private SensorManager mSensorManager;
        private AccelerometerSensor as;
        /**
         * Configure the game instance that is launched and start the logger.
         */
        public HelloActivity() {
        // Set the application class to run
        appClass = "com.github.migi_1.ContextApp.Main";
        
        //Create the accelerometer sensor.
        as = new AccelerometerSensor(this);
        
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
            mSensorManager.registerListener(as, 
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
         
        /**
         * Closes the app.
         */
        @Override
        protected void onStop() {  
            // unregister the sensor listener
            mSensorManager.unregisterListener(as);  
            super.onStop();  
        } 
    
        /**
         * Instanciate the game instance.
         * Instanciate the sensor manager.
         * @param savedInstanceState 
         */
        @Override  
        public void onCreate(Bundle savedInstanceState) {  
            super.onCreate(savedInstanceState);

            // instantiate the application
            application = (Main) getJmeApplication();

            // start the sensor manager
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            
            // show the user the app is looking for a server
            setContentView(R.layout.android_searching);
            
            // start the autoconnector
            AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10), 
                    ClientWrapper.getInstance());
            
            // set the ui to the ingame screen
            setContentView(R.layout.android_ingame_fr);
            
            // Retrieve buttons
            Button leftButton = (Button) findViewById(R.id.FR_button_left);
            Button middleButton = (Button) findViewById(R.id.FR_button_middle);
            Button rightButton = (Button) findViewById(R.id.FR_button_right);
            
            // add logging functionality
            setButtons(leftButton, "left");
            setButtons(middleButton, "middle");
            setButtons(rightButton, "right");
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
        
        /**
         * Makes sure buttonpresses are logged.
         * @param butt = the button to which a clicklistener is set
         * @param str = message to be logged
         */
        public void setButtons(Button butt, final String str) {

            butt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("buttonpress", str);
            }
            });
        }

}