package com.github.migi_1.ContextApp;


import com.github.migi_1.ContextApp.BugEvent.RotateBugSprayActivity;

import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import com.github.migi_1.ContextApp.client.ClientHub;

import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.jme3.app.AndroidHarness;
import java.util.Random;
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
    private HeartsUpdateFunctions huFunctions;
    private MakeButtonFunctions mbFunctions;
    private PlatformPosition position;
    private ClientHub clientHub;
    private StartBugEventMessageListener startBugEventListener;

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

        clientHub = new ClientHub();

        // create the accelerometerSensor
        accelerometerSensor = new AccelerometerSensor(this, clientHub.getClientWrapper());
    }

    @Override  
    protected void onResume() {  
        super.onResume();

        clientHub.getClientWrapper().startClient();

        clientHub.getClientWrapper().getClient().addMessageListener(posHolder);

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

        clientHub.setPosition(position);
        setUI();
    }

    @Override
    protected void onStop() {  
        super.onStop();  
        // unregister the sensor listener
        mSensorManager.unregisterListener(accelerometerSensor);
    } 

    /**
     * Sets the UI of the android app in-game, including buttons and images.
     */
    public void setUI() {
        atkMessenger = new AttackMessenger(this);
        mbFunctions = new MakeButtonFunctions(this);
        huFunctions = new HeartsUpdateFunctions(this);
        startBugEventListener = new StartBugEventMessageListener(this);

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
                atkMessenger.sendAttack(posHolder.getPosition(), name);
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
        return clientHub.getClientWrapper();
    }

    /**
     * Starts the bug event. 
     */
    public void startBugEvent() {
        Intent nextScreen = new Intent(getApplicationContext(), RotateBugSprayActivity.class);
        nextScreen.putExtra("Position", posHolder.getPosition());
        nextScreen.putExtra("BugPosition", getRandomPosition());
        nextScreen.putExtra("SprayPosition", getRandomPosition());
        nextScreen.putExtra("ClientHub", (Parcelable) clientHub);
        Log.d("rotate", "Added all positions");
        startActivity(nextScreen);
    }

    /**
     * Retuns a random position. 
     * @return a random platform position.
     */
    private PlatformPosition getRandomPosition() {
        int randomNumber = new Random().nextInt(4);
        switch(randomNumber) {
            case 0:
                return PlatformPosition.BACKLEFT;
            case 1:
                return PlatformPosition.BACKRIGHT;
            case 2:
                return PlatformPosition.FRONTLEFT;
            case 3:
                return PlatformPosition.FRONTRIGHT;
            default:
                throw new IllegalStateException("Wrong number: " + randomNumber);
        }
    }
}