/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.migi_1.ContextApp.R;
import com.github.migi_1.ContextApp.client.AutoConnector;
import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;
import com.jme3.network.Client;
import java.util.concurrent.Executors;
import javax.swing.text.Position;

/**
 *
 * @author Nils
 */
public class RotateBugSprayActivity extends Activity {
    private TextView spray;
    private Button bug;
    private float x1, x2, y1, y2;
    private ClientWrapper clientEvent;
    private StopAllEventsMessageListener stopEventListener;
    private EnableSprayAppMessageHandler enableSprayListener;
    private PlatformPosition position;
    
        /**
         * This method runs the app is resumed.
         */
        @Override
        protected void onResume() {
            super.onResume();
            
            clientEvent.startClient();
            setUI();
        }

        /**
         * Closes the app.
         */
        @Override
        protected void onStop() {
            super.onStop();
            clientEvent.closeClient();
        }
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            
            super.onCreate(savedInstanceState);
            position = (PlatformPosition) getIntent().getExtras().get("Position");
            setContentView(R.layout.android_event_bugs);
            Log.d("rotate", "Everything going well?");
            clientEvent = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10));
            Log.d("rotate", "client? " +  clientEvent.toString());
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(spray.getVisibility() == View.VISIBLE) {
                trackSwipe(event);
            }
            return false;
        }
        
        /**
         * Position is known here, so we can use the position attribute. 
         * @param event 
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
                        disableSprayButton(position);
                    }
            }
        }

        public void enableSprayButton() {
            spray.setVisibility(View.VISIBLE);
        }

        public void disableSprayButton(PlatformPosition position) {
            spray.setVisibility(View.GONE);
        }
        
        public void stopEvent() {
            finish();
        }
        
        public ClientWrapper getClient() {
            return clientEvent;
        }
        
        private boolean swipeUp(float horizontal, float vertical) {
            return (vertical < 0 && Math.abs(horizontal) < Math.abs(vertical));
        }
        
        private boolean swipeDown(float horizontal, float vertical) {
            return (vertical > 0 && Math.abs(horizontal) < Math.abs(vertical));
        }
        
        private boolean swipeLeft(float horizontal, float vertical) {
            return (horizontal < 0 && Math.abs(horizontal) > Math.abs(vertical));
        }
        
        private boolean swipeRight(float horizontal, float vertical) {
            return (horizontal > 0 && Math.abs(horizontal) > Math.abs(vertical));
        }

        public PlatformPosition getPosition() {
            return position;
        }
        
        private void setUI() {
            
            stopEventListener = new StopAllEventsMessageListener(this);
            enableSprayListener = new EnableSprayAppMessageHandler(this);
            
            spray = (TextView) findViewById(R.id.eventBug_spray);
            bug = (Button) findViewById(R.id.eventBug_bug);
            spray.setVisibility(View.GONE);
            bug.setVisibility(View.GONE);
            
            PlatformPosition bugPosition = (PlatformPosition) getIntent().getExtras().get("BugPosition");
            PlatformPosition sprayPosition = (PlatformPosition) getIntent().getExtras().get("SprayPosition");
            
            Log.d("rotate", "" + bugPosition);
            Log.d("rotate", "" + sprayPosition);
            if (position == bugPosition) {
                bug.setVisibility(View.VISIBLE);
            }
            if (position == sprayPosition) {
                spray.setVisibility(View.VISIBLE);
            }
            
            bug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(spray.getVisibility() == View.VISIBLE && bug.getVisibility() == View.VISIBLE) {
                        StopEventToVRMessage sprayMsg = new StopEventToVRMessage();
                        if(clientEvent.getClient().isStarted()) {
                            clientEvent.getClient().send(sprayMsg);
                        }
                    }
                }
            });
        }
}