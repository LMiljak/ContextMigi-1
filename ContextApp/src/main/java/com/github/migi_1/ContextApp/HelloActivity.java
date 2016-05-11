package com.github.migi_1.ContextApp;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.jme3.app.AndroidHarness;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class HelloActivity extends AndroidHarness implements SensorEventListener{
        
        private Main app;
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
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_FASTEST);
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
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        Log.d("main", Float.toString(se.values[0]) + " " + Float.toString(se.values[1]) + " " + Float.toString(se.values[2]));
        
//        if (app==null){
//
//            app=(Main) getJmeApplication();
//
//        return;
//
//        }
        //app.gyroscopeChange();
        
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        
   }

}