package com.github.migi_1.ContextApp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import com.jme3.app.AndroidHarness;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * This class contains the main activity that is started you run the project.
 * 
 * @author Marcel
 */
public class HelloActivity extends AndroidHarness{
        
        private Main application;
        private SensorManager mSensorManager;
        private AccelerometerSensor as;
        /**
         * Configure the game instance that is launched and start the logger.
         */
        public HelloActivity(){
        /** Set the application class to run **/
        appClass = "com.github.migi_1.ContextApp.Main";
        as = new AccelerometerSensor(this);
        /** Start the log manager **/
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
        
    }

       /**
        * This method runs the app is resumed.
        */
        @Override  
        protected void onResume(){  
            super.onResume();
            
            /** register the lister for the accelerometer **/
            mSensorManager.registerListener(as, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
        }
         
        /**
         * The the app is closed.
         */
        @Override  
        protected void onStop(){  
            /** unregister the sensor listener  **/
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

            /** instantiate the application **/
            application = (Main ) getJmeApplication();

            /** start the sensor manager **/
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
        }

        /**
         * This method is called when the senser notices a change.
         * @param se 
         */
//        @Override
//        public void onSensorChanged(SensorEvent se) {
//
//            /** check whether the game has already been instantiated **/
//            if (application == null) {
//                return;
//            }
//
//            /** log the sensor values **/
//            Log.d("main", Float.toString(se.values[0]) + " " + Float.toString(se.values[1]) + " " + Float.toString(se.values[2]));
//
//            /** this is an example of how you can call a method in the game **/
//            application.enqueue(new Callable() {
//
//                //@Override
//                public Object call() throws Exception {
//                
//                    /** Example of how you can make a call to a method in the game instance **/
//                    application.gyroscopeChange();
//                    return null;
//                }
//             });
//        }

        /** Auto-generated method **/
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int i) {
//        }
//        
        public Main getMain(){
            return application;
        }
        


}