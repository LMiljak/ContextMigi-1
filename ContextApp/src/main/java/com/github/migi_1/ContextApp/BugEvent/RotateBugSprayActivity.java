/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

import com.github.migi_1.ContextApp.ClientWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Client;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * @author Nils
 */
public class RotateBugSprayActivity extends Activity {
    private TextView spray_fr;
    private Button bug_fr;
    private float x1, x2, y1, y2, deltaHorizontal, deltaVertical;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setContentView(R.layout.android_event_bugs_fr);
//            spray_fr = (TextView) findViewById(R.id.eventBug_spray_fr);
            spray_fr.setVisibility(View.VISIBLE);
//            bug_fr = (Button) findViewById(R.id.eventBug_bug_fr);
            bug_fr.setVisibility(View.VISIBLE);

            bug_fr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", "FRONT RIGHT");
                    if(spray_fr.getVisibility() == View.VISIBLE && bug_fr.getVisibility() == View.VISIBLE) {
                        
                        Client client = ClientWrapper.getInstance().getClient();
                        if(client != null) {
                            Log.d("rotate", "STOP MESSAGE");
                            //Send stop all activities message.
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
                        disableSprayButton();
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
            spray_fr.setVisibility(View.VISIBLE);
        }

        public void disableSprayButton() {
            spray_fr.setVisibility(View.GONE);
        }
}