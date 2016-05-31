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
        position = PlatformPosition.FRONTRIGHT;

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
        switch (position) {
            case FRONTRIGHT:
                mbFunctions.makeFRbuttons();
                setContentView(R.layout.android_ingame_fr);
                break;
            case FRONTLEFT:
                mbFunctions.makeFLbuttons();
                setContentView(R.layout.android_ingame_fl);
                break;
            case BACKRIGHT:
                mbFunctions.makeBRbuttons();
                setContentView(R.layout.android_ingame_br);
                break;
            case BACKLEFT:
                mbFunctions.makeBLbuttons();
                setContentView(R.layout.android_ingame_bl);
                break;
            default:
              	break;
        }
    }
    
    /**
     * Sets the attack buttons.
     * @param leftButton
     *              The left attack button
     * @param middleButton
     *              The middle attack button
     * @param rightButton 
     *              The right attack button
     */
    public void setButtons(Button leftButton, Button middleButton, 
            Button rightButton) {
        this.leftButton = leftButton;
        this.middleButton = middleButton;
        this.rightButton = rightButton;
    }
    
    /**
     * Sets the trigger button.
     * @param trigger
     *              This button triggers random events.
     *              Is used for testing purposes.
     */
    public void setTrigger(Button trigger) {
        this.trigger = trigger;
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
                atkMessenger.sendAttack(posHolder.getPosition(), str);
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