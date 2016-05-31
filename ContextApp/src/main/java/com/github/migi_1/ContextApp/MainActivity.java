package com.github.migi_1.ContextApp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        
        private Button leftButton, middleButton, rightButton, trigger;
        
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
        while (true) {
            if (posHolder.getPosition() != null) {
            	break;
       	    }
        }

        atkMessenger = new AttackMessenger(this);

        setButtonsAndScreen();

        // add logging functionality
        setButtons(leftButton, "left");
        setButtons(middleButton, "middle");
        setButtons(rightButton, "right");
        setButtons(trigger, "trigger");
    }

    /**
     * Checks the position to set the buttons and content view.
     */
    public void setButtonsAndScreen() {
        switch (posHolder.getPosition()) {
            case FRONTRIGHT:
                makeFRbuttons();
                setContentView(R.layout.android_ingame_fr);
                break;
            case FRONTLEFT:
                makeFLbuttons();
                setContentView(R.layout.android_ingame_fl);
                break;
            case BACKRIGHT:
                makeBRbuttons();
                setContentView(R.layout.android_ingame_br);
                break;
            case BACKLEFT:
                makeBLbuttons();
                setContentView(R.layout.android_ingame_bl);
                break;
            default:
              	break;
        }
    }
        
    /**
     * Makes the buttons for the FRONTRIGHT position.
     */
    public void makeFRbuttons() {
        // Retrieve buttons
        leftButton = (Button) findViewById(R.id.FR_button_left);
        middleButton = (Button) findViewById(R.id.FR_button_middle);
        rightButton = (Button) findViewById(R.id.FR_button_right);
        trigger = (Button) findViewById(R.id.FR_button_trigger);
    }
        
    /**
     * Makes the buttons for the FRONTLEFT position.
     */
    public void makeFLbuttons() {
        // Retrieve buttons
        leftButton = (Button) findViewById(R.id.FL_button_left);
        middleButton = (Button) findViewById(R.id.FL_button_middle);
        rightButton = (Button) findViewById(R.id.FL_button_right);
        trigger = (Button) findViewById(R.id.FR_button_trigger);
    }
        
    /**
     * Makes the buttons for the BACKRIGHT position.
     */
    public void makeBRbuttons() {
        // Retrieve buttons
        leftButton = (Button) findViewById(R.id.BR_button_left);
        middleButton = (Button) findViewById(R.id.BR_button_middle);
        rightButton = (Button) findViewById(R.id.BR_button_right);
        trigger = (Button) findViewById(R.id.FR_button_trigger);
    }
        
    /**
     * Makes the buttons for the BACKLEFT position.
     */
    public void makeBLbuttons() {
        // Retrieve buttons
        leftButton = (Button) findViewById(R.id.BL_button_left);
        middleButton = (Button) findViewById(R.id.BL_button_middle);
        rightButton = (Button) findViewById(R.id.BL_button_right);
        trigger = (Button) findViewById(R.id.FR_button_trigger);
    }
        
    /**
     * Makes sure buttonpresses are logged and processed.
     * @param butt = the button to which a clicklistener is set
     * @param str = message to be logged
     */
    public void setButtons(Button butt, final String str) {

        butt.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.d("buttonpress", str);
            if (str.equals("trigger")) {
                Intent nextScreen = new Intent(getApplicationContext(), EventButtonPressActivity.class);
                startActivity(nextScreen);
            }
            else {
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