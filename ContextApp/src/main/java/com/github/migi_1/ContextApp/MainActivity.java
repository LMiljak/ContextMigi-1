package com.github.migi_1.ContextApp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.github.migi_1.ContextMessages.PlatformPosition;
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
        private AccelerometerSensor accSensor;
        private PositionHolder posHolder;
        private AttackMessenger atkMessenger;
        private HeartsUpdateFunctions huFunctions;
        private MakeButtonFunctions mbFunctions;
        private PlatformPosition position;
        
        /**
         * Configure the game instance that is launched and start the logger.
         */
        public MainActivity() {
            
        // Set the application class to run
        appClass = "com.github.migi_1.ContextApp.Main";
        
        //Create the accelerometer sensor.
        accSensor = new AccelerometerSensor(this);
        posHolder = PositionHolder.getInstance();
        huFunctions = new HeartsUpdateFunctions(this);
        mbFunctions = new MakeButtonFunctions(this);
        
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
        setContentView(R.layout.android_searching);

        //instantiate the application
        application = (Main) getJmeApplication();
        application.setDisplayFps(false);
        application.setDisplayStatView(false);

        //start the sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // start the autoconnector
        AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10), 
                ClientWrapper.getInstance());

        // wait until position is received
        /*while (true) {
            if (posHolder.getPosition() != null) {
                position = posHolder.getPosition();
            	break;
       	    }
        */
        position = PlatformPosition.BACKRIGHT;
        
        chooseScreen();
        
    }
    
    /**
     * Chooses which function to call to set view and buttons.
     */
    public void chooseScreen() {
        
        if (position == PlatformPosition.FRONTLEFT) {
            setFrontLeft();
        }
        else if (position == PlatformPosition.BACKRIGHT) {
            setBackRight();
        }
        else if (position == PlatformPosition.BACKLEFT) {
            setBackLeft();
        }
        else {
            setFrontRight();
        }    
        
    }
    
    public void setFrontRight() {
        
        setContentView(R.layout.android_ingame_fr);
        atkMessenger = new AttackMessenger(this);
        setButtonClick((Button) findViewById(R.id.FR_button_left), "left");
        setButtonClick((Button) findViewById(R.id.FR_button_middle), "middle");
        setButtonClick((Button) findViewById(R.id.FR_button_right), "right");
        setButtonClick((Button) findViewById(R.id.FR_button_trigger), "trigger");
        
    }
    
    public void setFrontLeft() {
        
        setContentView(R.layout.android_ingame_fl);
        atkMessenger = new AttackMessenger(this);
        setButtonClick((Button) findViewById(R.id.FL_button_left), "left");
        setButtonClick((Button) findViewById(R.id.FL_button_middle), "middle");
        setButtonClick((Button) findViewById(R.id.FL_button_right), "right");
        setButtonClick((Button) findViewById(R.id.FL_button_trigger), "trigger");
        
    }
    
    public void setBackLeft() {
        
        setContentView(R.layout.android_ingame_bl);
        atkMessenger = new AttackMessenger(this);
        setButtonClick((Button) findViewById(R.id.BL_button_left), "left");
        setButtonClick((Button) findViewById(R.id.BL_button_middle), "middle");
        setButtonClick((Button) findViewById(R.id.BL_button_right), "right");
        setButtonClick((Button) findViewById(R.id.BL_button_trigger), "trigger");
        
    }
    
    public void setBackRight() {
        
        setContentView(R.layout.android_ingame_br);
        atkMessenger = new AttackMessenger(this);
        setButtonClick((Button) findViewById(R.id.BR_button_left), "left");
        setButtonClick((Button) findViewById(R.id.BR_button_middle), "middle");
        setButtonClick((Button) findViewById(R.id.BR_button_right), "right");
        setButtonClick((Button) findViewById(R.id.BR_button_trigger), "trigger");
        
    }
        
    /**
     * Makes sure buttonpresses are logged and processed.
     * @param butt = the button to which a clicklistener is set
     * @param str = message to be logged
     */
    public void setButtonClick(Button butt, final String str) {

        butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("buttonpress", str);
                if (!str.equals("trigger")) {
                    atkMessenger.sendAttack(posHolder.getPosition(), str);
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
    
}