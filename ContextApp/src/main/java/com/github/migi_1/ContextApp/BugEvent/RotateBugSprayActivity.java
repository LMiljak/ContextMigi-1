package com.github.migi_1.ContextApp.BugEvent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.migi_1.ContextApp.R;
import com.github.migi_1.ContextApp.client.ClientHub;
import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;

/**
 * The randomEvent bug activity class.
 */
public class RotateBugSprayActivity extends Activity {
    private TextView spray;
    private Button bug;
    private float x1, x2, y1, y2;
    private ClientWrapper clientEvent;
    private StopAllEventsMessageListener stopEventListener;
    private EnableSprayAppMessageHandler enableSprayListener;
    private PlatformPosition position;

    @Override
    protected void onResume() {
        Log.d("rotate", "RESUMING RE");
        super.onResume();

        clientEvent.startClient();

        setUI();
    }

    @Override
    protected void onStop() {
        Log.d("rotate", "STOPPING RE");
        super.onStop();
        clientEvent.closeClient();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("rotate", "Creating Random event");
        super.onCreate(savedInstanceState);
        position = (PlatformPosition) getIntent().getExtras().get("Position");
        Log.d("rotate", "Position get!");
        setContentView(R.layout.android_event_bugs);
        Log.d("rotate", "Everything going well?");
        ClientHub clientHub = (ClientHub) getIntent().getParcelableExtra("ClientHub");
        clientEvent = clientHub.getClientWrapper();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(spray.getVisibility() == View.VISIBLE) {
            trackSwipe(event);
        }
        return false;
    }

    /**
     * This method tracks the swipe movement on the android device.  
     * @param event the event created by the overwritten method "onTouchEvent"
     */
    private void trackSwipe(MotionEvent event) {
        switch(event.getAction()) {
            case (MotionEvent.ACTION_DOWN) :
                x1 = event.getX();
                y1 = event.getY();
                break;
            case (MotionEvent.ACTION_UP) :
                x2 = event.getX();
                y2 = event.getY();

                float deltaHorizontal = x2 - x1;
                float deltaVertical = y2 - y1;
                EnableSprayToVRMessage sprayMessage = null;

                switch(position) {
                    case BACKLEFT: 
                        if(swipeUp(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.FRONTLEFT);
                        } else if(swipeRight(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.BACKRIGHT);
                        }
                        break;
                    case BACKRIGHT:
                        if(swipeUp(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.FRONTRIGHT);
                        } else if(swipeLeft(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.BACKLEFT);
                        }
                        break;
                    case FRONTLEFT:
                        if(swipeDown(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.BACKLEFT);
                        } else if(swipeRight(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.FRONTRIGHT);
                        }
                        break;
                    case FRONTRIGHT:
                        if(swipeDown(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.BACKRIGHT);
                        } else if(swipeLeft(deltaHorizontal, deltaVertical)) {
                            sprayMessage = new EnableSprayToVRMessage(PlatformPosition.FRONTLEFT);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unknown position: " + position);
                }
                if(sprayMessage != null && clientEvent.getClient().isStarted()) {
                    Log.d("rotate", "Sending message from: " +  position + " to: " + sprayMessage.getPosition());
                    clientEvent.getClient().send(sprayMessage);
                    disableSprayButton();
                }
            }
    }

    /**
     * Enables the sprayButton for the current screen.
     */
    public void enableSprayButton() {
        spray.setVisibility(View.VISIBLE);
    }

    /**
     * Disables the sprayButton for the current screen. 
     */
    public void disableSprayButton() {
        spray.setVisibility(View.GONE);
    }

    /**
     * Stop the bug event
     */
    public void stopEvent() {
        finish();
    }

    /**
     * Getter for the clientWrapper. 
     * @return the clientWrapper.
     */
    public ClientWrapper getClient() {
        return clientEvent;
    }

    /**
     * Verifies if the motion detected on the screen was
     * a swipe up.
     * @param horizontal the horizontal movement. 
     * @param vertical the vertical movement.
     * @return true if the motion was a swipe up. 
     */
    private boolean swipeUp(float horizontal, float vertical) {
        return (vertical < 0 && Math.abs(horizontal) < Math.abs(vertical));
    }

    /**
     * Verifies if the motion detected on the screen was
     * a swipe down.
     * @param horizontal the horizontal movement. 
     * @param vertical the vertical movement.
     * @return true if the motion was a swipe down. 
     */
    private boolean swipeDown(float horizontal, float vertical) {
        return (vertical > 0 && Math.abs(horizontal) < Math.abs(vertical));
    }

    /**
     * Verifies if the motion detected on the screen was
     * a swipe left.
     * @param horizontal the horizontal movement. 
     * @param vertical the vertical movement.
     * @return true if the motion was a swipe left. 
     */
    private boolean swipeLeft(float horizontal, float vertical) {
        return (horizontal < 0 && Math.abs(horizontal) > Math.abs(vertical));
    }

    /**
     * Verifies if the motion detected on the screen was
     * a swipe right.
     * @param horizontal the horizontal movement. 
     * @param vertical the vertical movement.
     * @return true if the motion was a swipe right. 
     */
    private boolean swipeRight(float horizontal, float vertical) {
        return (horizontal > 0 && Math.abs(horizontal) > Math.abs(vertical));
    }

    /**
     * Getter for the platform position. 
     * @return the current position. 
     */
    public PlatformPosition getPosition() {
        return position;
    }

    /**
     * Sets the UI of the android app bugEvent, including buttons and listeners.
     */
    private void setUI() {
        //Initialize the listeners.
        stopEventListener = new StopAllEventsMessageListener(this);
        enableSprayListener = new EnableSprayAppMessageHandler(this);

        //Add the spray text and the bug button. 
        spray = (TextView) findViewById(R.id.eventBug_spray);
        bug = (Button) findViewById(R.id.eventBug_bug);
        spray.setVisibility(View.GONE);
        bug.setVisibility(View.GONE);

        //Get the initial positions for the bug and spray. 
        PlatformPosition bugPosition = (PlatformPosition) getIntent().getExtras().get("BugPosition");
        PlatformPosition sprayPosition = (PlatformPosition) getIntent().getExtras().get("SprayPosition");

        Log.d("rotate", "" + bugPosition);
        Log.d("rotate", "" + sprayPosition);
        //Set the positions to visible when the given position is the current position. 
        if (position == bugPosition) {
            bug.setVisibility(View.VISIBLE);
        }
        if (position == sprayPosition) {
            spray.setVisibility(View.VISIBLE);
        }

        //Ad a listener for the bug button. 
        bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spray.getVisibility() == View.VISIBLE && bug.getVisibility() == View.VISIBLE) {
                    //Send a stop message to the server. 
                    StopEventToVRMessage sprayMsg = new StopEventToVRMessage();
                    if(clientEvent.getClient().isStarted()) {
                        clientEvent.getClient().send(sprayMsg);
                    }
                }
            }
        });
    }
}