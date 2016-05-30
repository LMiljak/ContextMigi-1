/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

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
        private TextView spray;
        private float x1, x2, y1, y2, deltaHorizontal, deltaVertical;
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);
            Button bugButton = (Button) findViewById(R.id.eventBug_bug_fr);
            spray = (TextView) findViewById(R.id.eventBug_spray_fr);
            spray.setVisibility(View.GONE);
            
            // add logging functionality
            setButtons(bugButton, "bug");
        }
            /**
         * Makes sure buttonpresses are logged.
         * @param butt = the button to which a clicklistener is set
         * @param str = message to be logged
         */
        public void setButtons(Button button, final String str) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", str);
                    if(str.equals("bug") && spray.getVisibility() == View.VISIBLE) {
                        finish();
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
                    Log.d("rotate", "HORIZONTAL: " + deltaHorizontal);
                    Log.d("rotate", "VERTICAL: " + deltaVertical);
                    //LEFT
                    if(deltaHorizontal < 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe LEFT");
                        spray.setVisibility(View.VISIBLE);
                    }
                    
                    //RIGHT
                    if(deltaHorizontal > 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe RIGHT");
                        spray.setVisibility(View.GONE);
                    }
                    
                    //UP
                    if(deltaVertical < 0 && Math.abs(deltaHorizontal) < Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe UP");
                        spray.setVisibility(View.GONE);
                    }
                    
                    //DOWN
                    if(deltaVertical > 0 && Math.abs(deltaHorizontal) < Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe DOWN");
                        spray.setVisibility(View.GONE);
                    }
                    
                    break;
            }
            return false;
        }
}
