package com.github.migi_1.ContextApp;


import com.github.migi_1.ContextApp.BugEvent.RotateBugSprayActivity;

import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextApp.client.ClientHub;
import com.github.migi_1.ContextMessages.Direction;
import com.jme3.app.AndroidHarness;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * This class contains the main activity that is started you run the project.
 */
public class MainActivity extends AndroidHarness {

    private Main application;
    private SensorManager mSensorManager;
    private AccelerometerSensor accelerometerSensor;
    private PositionHolder posHolder;
    private AttackMessenger atkMessenger;
    private HealthMessageHandler healthListener;
    private HitMissMessageHandler hitMissListener;
    private MakeButtonFunctions mbFunctions;
    private PlatformPosition position;
    private ClientHub clientHub = ClientHub.getInstance();
    private StartBugEventMessageListener startBugEventListener;
    private ClientWrapper client;
    private ArrayList<ImageView> images;
    
    private Timer timer;
    private TimerTask timerTask;
    
    private boolean cooldown;
    private boolean eventStarted;
    
    final Handler handler = new Handler();

    /**
     * Configure the game instance that is launched and start the logger.
     */
    public MainActivity() {

        // Set the application class to run
        appClass = "com.github.migi_1.ContextApp.Main";

        posHolder = PositionHolder.getInstance();

        // Start the log manager
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
    }

    @Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiate the application
        application = (Main) getJmeApplication();

        //start the sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        setContentView(R.layout.android_searching); 

        clientHub.getClientWrapper().startClient();
        
        getClient().getClient().addMessageListener(posHolder);
                
        while (true) {
            if (posHolder.getPosition() != null) {
                position = posHolder.getPosition();
                break;
            }
        }
        
        setContentView(R.layout.android_ingame);
        
        // set cooldown to false
        setCooldown(false);

        // create the accelerometerSensor
        accelerometerSensor = new AccelerometerSensor(this, getClient());
    }
    
   /**
    * This method runs the app is resumed.
    */
    @Override  
    public void onResume() {
        super.onResume();

        // register the lister for the accelerometer
        mSensorManager.registerListener(accelerometerSensor, 
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        eventStarted = false;
        
        setUI();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
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
     * Sets the UI of the android app in-game, including buttons and images.
     */
    public void setUI() {
        images = new ArrayList<ImageView>();
        
        images.add((ImageView) findViewById(R.id.Heart_1));
        images.add((ImageView) findViewById(R.id.Heart_2));
        images.add((ImageView) findViewById(R.id.Heart_3));
        
        atkMessenger = new AttackMessenger(this);
        mbFunctions = new MakeButtonFunctions(this);
        hitMissListener = new HitMissMessageHandler(this);
        healthListener = new HealthMessageHandler(this);

        startBugEventListener = new StartBugEventMessageListener(this);
        
        TextView textView = (TextView) findViewById(R.id.Location);
        textView.setText(position.getPosition());

        mbFunctions.setButtons(position);
    }

    /**
     * Makes sure buttonpresses are logged and processed.
     * @param button
     *              the button to which a clicklistener is set
     * @param name 
     *              message to be logged
     */
    public void setButtonClick(Button button, final Direction direction) {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                
                if (!cooldown) {
                    atkMessenger.sendAttack(posHolder.getPosition(), direction);
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
     * Getter of client.
     * @return client ClientWrapper
     */
    public ClientWrapper getClient() {
        return clientHub.getClientWrapper();
    }

    /**
     * Starts the bug event. 
     */
    public void startBugEvent() {
        if (!eventStarted) {
            eventStarted = true;
            Intent nextScreen = new Intent(getApplicationContext(), RotateBugSprayActivity.class);
            nextScreen.putExtra("Position", position);
            nextScreen.putExtra("BugPosition", getRandomPosition());
            nextScreen.putExtra("SprayPosition", getRandomPosition());
            startActivity(nextScreen);
        }
    }

    /**
     * Retuns a random position. 
     * @return a random platform position.
     */
    private PlatformPosition getRandomPosition() {
        int randomNumber = new Random().nextInt(4);
        return PlatformPosition.values()[randomNumber];
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
        }
        else {
            // Sound effect miss
            setCooldown(true);
            
            timer = new Timer();
            initializeTimerTask();
            timer.schedule(timerTask, 2000);
        }
    }
    
    /**
    * Calls functions to make the hearts the right colour.
    * @param health the amount of health that has to be displayed in grey
    *      and red hearts.
    */
    public void setHealth(final int health) {
        
        runOnUiThread( new Runnable() {
            
            @Override
            public void run() {
                switch (health) {
                    case 0:
                        images.get(0).setImageResource(R.drawable.heart_grey);
                        images.get(1).setImageResource(R.drawable.heart_grey);
                        images.get(2).setImageResource(R.drawable.heart_grey);
                        break;
                    case 1:
                        images.get(0).setImageResource(R.drawable.heart_red);
                        images.get(1).setImageResource(R.drawable.heart_grey);
                        images.get(2).setImageResource(R.drawable.heart_grey);
                        break;
                    case 2:
                        images.get(0).setImageResource(R.drawable.heart_red);
                        images.get(1).setImageResource(R.drawable.heart_red);
                        images.get(2).setImageResource(R.drawable.heart_grey);
                        break;
                    case 3:
                        images.get(0).setImageResource(R.drawable.heart_red);
                        images.get(1).setImageResource(R.drawable.heart_red);
                        images.get(2).setImageResource(R.drawable.heart_red);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            
        });
        
    }
    
    /**
     * Initializes the TimerTask, which will set cooldown back to false.
     */
    public void initializeTimerTask() {
         
        timerTask = new TimerTask() {
            @Override
            public void run() {
                 
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setCooldown(false);
                    }
                }); 
                
            }
        };
    }
    
}