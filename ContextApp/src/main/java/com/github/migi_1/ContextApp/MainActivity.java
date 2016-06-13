package com.github.migi_1.ContextApp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private HealthMessageHandler healthListener;
    private HitMissMessageHandler hitMissListener;
    private HeartsUpdateFunctions huFunctions;
    private MakeButtonFunctions mbFunctions;
    private PlatformPosition position;
    private ClientWrapper client;
    private ImageView img1, img2, img3;
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

        //start the sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        client = com.github.migi_1.ContextApp.client.AutoConnector.getInstance()
                .autoStart(Executors.newFixedThreadPool(10), this);
            
        // create te accelerometerSensor
        accelerometerSensor = new AccelerometerSensor(this, client);
        
        // set cooldown to false
        setCooldown(false);
        
    }
    
    /**
     * Shows a 'toast' giving the player instructions on how to get the app to 
     * work with the game and closes the app.
     */
    public void alert() {
        CharSequence text = "Start the game before running the app";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
        finish();
    }
    
   /**
    * This method runs the app is resumed.
    */
    @Override  
    protected void onResume() {  
        super.onResume();
        
        setContentView(R.layout.android_searching); 
        
        img3 = (ImageView) findViewById(R.id.Heart_1);
        img3 = (ImageView) findViewById(R.id.Heart_2);
        img3 = (ImageView) findViewById(R.id.Heart_3);

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
        
        // close the client
        client.closeClient();
        
        // clear the position
        posHolder.clearPosition();
            
        super.onStop();  
    } 
    
    /**
     * Sets the UI of the android app in-game, including buttons and images.
     */
    public void setUI() {
        atkMessenger = new AttackMessenger(this);
        mbFunctions = new MakeButtonFunctions(this);
        huFunctions = new HeartsUpdateFunctions(this);
        hitMissListener = new HitMissMessageHandler(this);
        
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
                
                if (!cooldown) {
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
     * Setter for cooldown.
     * @param cooldown 
     *              Boolean that determines whether or not a player can use attacks.
     */
    public void setCooldown(boolean cooldown) {
        this.cooldown = cooldown;
    }
    
    /**
     * Checks whether or not the carrier's attack has hit or not and calls the 
     * functions corresponding to the boolean value.
     * @param hit 
     *          whether or not the attack was successful
     */
    public void hitMiss(boolean hit) {
        if (hit) {
            // Sound effect hit
            Log.d("attack", "successful");
        }
        else {
            // Sound effect miss
            Log.d("attack", "missed");
            setCooldown(true);
            setCooldown(false);
        }
    }
    
    /**
     * Makes a heart red.
     * @param heartid the id of the heart which has its sprite changed
     */
    public void makeRed(int heartid) {
        
        switch (heartid) {
            case 1:
                img1.setImageResource(R.drawable.heart_red);
                break;
            case 2:
                img2.setImageResource(R.drawable.heart_red);
                break;
            case 3:
                img3.setImageResource(R.drawable.heart_red);
                break;
            default:
                throw new IllegalArgumentException();
        }
        
    }

    /**
     * Makes a heart grey.
     * @param heartid the id of the heart which has its sprite changed
     */
    public void makeGrey(int heartid) {
        
        switch (heartid) {
            case 1:
                img1.setImageResource(R.drawable.heart_grey);
                break;
            case 2:
                img2.setImageResource(R.drawable.heart_grey);
                break;
            case 3:
                img3.setImageResource(R.drawable.heart_grey);
                break;
            default:
                throw new IllegalArgumentException();
        }

    }
    
}