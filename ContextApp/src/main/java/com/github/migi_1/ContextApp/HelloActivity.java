package com.github.migi_1.ContextApp;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.jme3.app.AndroidHarness;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class HelloActivity extends AndroidHarness implements SensorEventListener{
        
        private Main application;
        private SensorManager mSensorManager;
        private Sensor mSensor;
        private TextView tv;
        
        public HelloActivity(){
        // Set the application class to run
        appClass = "com.github.migi_1.ContextApp.Main";
        // Try ConfigType.FASTEST; or ConfigType.LEGACY if you have problems
//        eglConfigType = ConfigType.BEST;
        // Exit Dialog title & message
//        exitDialogTitle = "Exit?";
//        exitDialogMessage = "Press Yes";
//        // Enable verbose logging
////        eglConfigVerboseLogging = false;
//        // Choose screen orientation
          // Enable MouseEvents being generated from TouchEvents (default = true)
//        mouseEventsEnabled = true;
//        // Set the default logging level (default=Level.INFO, Level.ALL=All Debug Info)
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
        
    }

         @Override  
    protected void onResume()  
    {  
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
    }
         
    @Override  
    protected void onStop()  
    {  
        //unregister the sensor listener  
        mSensorManager.unregisterListener(this);  
        super.onStop();  
    } 
    
        
        @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        //tv = (TextView) findViewById(R.id.text_view);
        super.onCreate(savedInstanceState);
        
        application = (Main ) getJmeApplication();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        if (application == null) {

        return;

        }
        Log.d("main", Float.toString(se.values[0]) + " " + Float.toString(se.values[1]) + " " + Float.toString(se.values[2]));
        
//        if (app==null){
//
//            app=(Main) getJmeApplication();
//
//        return;
//
//        }
        application.enqueue(new Callable() {



            public Object call() throws Exception {

            application.gyroscopeChange();
            return null;

            }

            });




            

        
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        
   }

}