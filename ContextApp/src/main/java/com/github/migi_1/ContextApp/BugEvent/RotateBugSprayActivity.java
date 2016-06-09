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

/**
 *
 * @author Nils
 */
public class RotateBugSprayActivity extends Activity {
    private TextView spray;
    private Button bug;
    private float x1, x2, y1, y2, deltaHorizontal, deltaVertical;
    private ClientWrapper clientEvent;
    private StopAllEventsMessageListener stopEventListener;
    
        /**
         * This method runs the app is resumed.
         */
        @Override
        protected void onResume() {
            Log.d("rotate", "RESUMING RE");
            super.onResume();
            
            clientEvent.startClient();
        }

        /**
         * Closes the app.
         */
        @Override
        protected void onStop() {
            Log.d("rotate", "STOPPING RE");
            super.onStop();
            
            clientEvent.closeClient();
        }
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);
            spray = (TextView) findViewById(R.id.eventBug_spray_fr);
            spray.setVisibility(View.VISIBLE);
            bug = (Button) findViewById(R.id.eventBug_bug_fr);
            bug.setVisibility(View.VISIBLE);
            
            clientEvent = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10));
            
            stopEventListener = new StopAllEventsMessageListener(this);

            bug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", "FRONT RIGHT");
                    if(spray.getVisibility() == View.VISIBLE && bug.getVisibility() == View.VISIBLE) {
                        Log.d("rotate", "Stopping random activity");
                        StopEventToVRMessage sprayMsg = new StopEventToVRMessage();
                        Log.d("rotate", "MESSAGE SEND: " + (clientEvent.getClient() != null));
                        if(clientEvent.getClient().isStarted()) {
                            clientEvent.getClient().send(sprayMsg);
                        }
                    }
                }
            });
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()) {
                case (MotionEvent.ACTION_DOWN) :
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case (MotionEvent.ACTION_UP) :
                    x2 = event.getX();
                    y2 = event.getY();

                    deltaHorizontal = x2 - x1;
                    deltaVertical = y2 - y1;

                    //Swipe down
                    if(deltaVertical > 0 && Math.abs(deltaHorizontal) < Math.abs(deltaVertical)) {
                        Log.d("rotate", "SWIPE DOWN");
                        EnableSprayToVRMessage sprayMsg = new EnableSprayToVRMessage(PlatformPosition.BACKRIGHT);
                        Log.d("rotate", "MESSAGE SEND: " + (clientEvent.getClient() != null));
                        if(clientEvent.getClient().isStarted()) {
                            clientEvent.getClient().send(sprayMsg);
                        }
                    }

                    //Swipe left
                    if(deltaHorizontal < 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        Log.d("rotate", "SWIPE LEFT");
                    }
                    Log.d("rotate", "UPDATING POSITION");
                }
            return false;
        }

        public void enableSprayButton() {
            Log.d("rotate", "OMG ITS WORKING :D:D:D");
            spray.setVisibility(View.VISIBLE);
        }

        public void disableSprayButton() {
            spray.setVisibility(View.GONE);
        }
        
        public void stopEvent() {
            finish();
        }
        
        public ClientWrapper getClient() {
            return clientEvent;
        }
}