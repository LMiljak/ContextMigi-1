package com.github.migi_1.ContextApp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
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
        private SoundPool soundPool;
        
        private int[] soundIds;
        private boolean cooldown;
        
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

        // Start the sensor manager.
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Set the view.
        setContentView(R.layout.android_searching);  
        // Set the client.
        client = com.github.migi_1.ContextApp.client.AutoConnector.getInstance()
                .autoStart(Executors.newFixedThreadPool(10));    
        // Create the accelerometerSensor.
        accelerometerSensor = new AccelerometerSensor(this, client);
        // Set cooldown to false.
        setCooldown(false);
        // Create the soundPool.
        createSoundPool();
        
        // wait until position is received
        /*while (true) {
            if (posHolder.getPosition() != null) {
                position = posHolder.getPosition();
            	break;
       	    }
        }*/
        position = PlatformPosition.FRONTLEFT;
        // Set the UI.
        setUI();
        
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
            
        client.closeClient();
        
        soundPool.release();
            
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
    public void setButtonClick(Button button, final String string) {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                
                if(cooldown == false) {
                    atkMessenger.sendAttack(posHolder.getPosition(), string);
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
    
    /**
     * Creates the SoundPool, allows for volume control and adds sfx.
     */
    public void createSoundPool() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundIds = new int[3];
        soundIds[0] = soundPool.load(this, R.raw.gethit, 1);
        soundIds[1] = soundPool.load(this, R.raw.miss, 1);
        soundIds[2] = soundPool.load(this, R.raw.hit, 1);
    }
    
    /**
     * Getter for soundPool.
     * @return the SoundPool, so that other classes can make it play a sound effect
     */
    public SoundPool getSoundPool() {
        return soundPool;
    }
    
    /**
     * Getter for soundIds.
     * @return the array of sound effects so that they can be played by other classes.
     */
    public int[] getSoundIds() {
        return soundIds;
    }
    
    /*
     * Setter for cooldown
     * @param cooldown 
     *              Boolean that determines whether or not a player can use attacks.
     */
    public void setCooldown(boolean cooldown) {
        this.cooldown = cooldown;
    }
    
}