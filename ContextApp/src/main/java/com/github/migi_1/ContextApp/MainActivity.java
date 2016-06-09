package com.github.migi_1.ContextApp;


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

import android.widget.TextView;

import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.jme3.app.AndroidHarness;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * This class contains the main activity that is started you run the project.
 *
 * @author Marcel
 */
public class MainActivity extends AndroidHarness {

        private Main application;
        private SensorManager mSensorManager;
        private AccelerometerSensor accelerometerSensor;
        private PositionHolder posHolder;
        private AttackMessenger atkMessenger;
        private HeartsUpdateFunctions huFunctions;
        private MakeButtonFunctions mbFunctions;
        private PlatformPosition position;
        private ClientWrapper client;

        /**
         * Configure the game instance that is launched and start the logger.
         */
        public MainActivity() {
            
            // Set the application class to run
            appClass = "com.github.migi_1.ContextApp.Main";

            //Create the accelerometer sensor.
            accelerometerSensor = new AccelerometerSensor(this, client);
            posHolder = PositionHolder.getInstance();

            // Start the log manager
            LogManager.getLogManager().getLogger("").setLevel(Level.INFO);

        }
        
    /**
     * Instanciate the game instance.
     * Instanciate the sensor manager.
     * @param savedInstanceState 
     */
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        
        super.onCreate(savedInstanceState);

        //instantiate the application
        application = (Main) getJmeApplication();
        application.setDisplayFps(false);
        application.setDisplayStatView(false);

        //start the sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        setContentView(R.layout.android_searching);  

        client = com.github.migi_1.ContextApp.client.AutoConnector.getInstance()
                .autoStart(Executors.newFixedThreadPool(10));
            
        // create te accelerometerSensor
        accelerometerSensor = new AccelerometerSensor(this, client);
        
    }
    
   /**
    * This method runs the app is resumed.
    */
    @Override  
    protected void onResume() {  
        super.onResume();

        client.startClient();
        client.getClient().addMessageListener(posHolder);
        
        // register the lister for the accelerometer
        mSensorManager.registerListener(accelerometerSensor, 
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        
        while (true) {
            if (posHolder.getPosition() != null) {
                position = posHolder.getPosition();
            	break;
       	    }
        }
        
        setUI();
    }
    
        /**
         * Closes the app.
         */
        @Override
        protected void onStop() {  
            // unregister the sensor listener
            mSensorManager.unregisterListener(accelerometerSensor);


            super.onStop();  
        } 
    
        /**
         * Sets the UI of the android app in-game, including buttons and images.
         */
        public void setUI() {
            atkMessenger = new AttackMessenger(this);
            mbFunctions = new MakeButtonFunctions(this);
            huFunctions = new HeartsUpdateFunctions(this);

            setContentView(R.layout.android_ingame);

            TextView textView = (TextView) findViewById(R.id.Location);
            textView.setText(position.getPosition());

            mbFunctions.setButtons(position);
        }
    
        /**
         * Makes sure buttonpresses are logged and processed.
         * @param button
         *              the button to which a clicklistener is set
         * @param string 
         *              message to be logged
         */
        public void setButtonClick(Button button, final String name) {

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        Log.d("rotate", name);
                        if(name.equals("trigger")) {
                            Log.d("rotate", "TRIGGERED");
                            Intent nextScreen = new Intent(getApplicationContext(), RotateBugSprayActivity.class);
                            startActivity(nextScreen);
                        } else {
                            atkMessenger.sendAttack(posHolder.getPosition(), name);
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

        /**
         * Returns the application's PositionHolder.
         * @return posHolder
         */
        public PositionHolder getPosHolder() {
            return posHolder;
        }
    
        /**
         * Returns the app's instance of the HeartsUpdateFunctions class.
         * @return huFunctions HeartsUpdateFunctions
         */
        public HeartsUpdateFunctions getHUFunctions() {
            return huFunctions;
        }
    
    /**
     * Getter of client.
     * @return client ClientWrapper
     */
    public ClientWrapper getClient() {
        return client;
    }
}