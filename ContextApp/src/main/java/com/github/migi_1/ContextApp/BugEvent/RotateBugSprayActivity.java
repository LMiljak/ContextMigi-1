/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.migi_1.ContextApp.R;
import java.util.Random;

/**
 *
 * @author Nils
 */
public class RotateBugSprayActivity extends Activity {
        private TextView spray_fr, spray_fl, spray_br, spray_bl;
        private Button bug_fr, bug_fl, bug_br, bug_bl;
        private float x1, x2, y1, y2, deltaHorizontal, deltaVertical;
        private Position spray_position;
        private Position bug_position;
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);
            
            
            
            spray_fr = (TextView) findViewById(R.id.eventBug_spray_fr);
//            spray_fl = (TextView) findViewById(R.id.eventBug_spray_fl);
//            spray_br = (TextView) findViewById(R.id.eventBug_spray_br);
//            spray_bl = (TextView) findViewById(R.id.eventBug_spray_bl);
            
            spray_fr.setVisibility(View.GONE);
//            spray_fl.setVisibility(View.GONE);
//            spray_br.setVisibility(View.GONE);
//            spray_bl.setVisibility(View.GONE);
            
            bug_fr = (Button) findViewById(R.id.eventBug_bug_fr);
//            bug_fl = (Button) findViewById(R.id.eventBug_bug_fl);
//            bug_br = (Button) findViewById(R.id.eventBug_bug_br);
//            bug_bl = (Button) findViewById(R.id.eventBug_bug_bl);
            
//            bug_fr.setVisibility(View.GONE);
//            bug_fl.setVisibility(View.GONE);
//            bug_br.setVisibility(View.GONE);
//            bug_bl.setVisibility(View.GONE);
            
            bug_position = initBug(new Random().nextInt(4));
            spray_position = initSpray(new Random().nextInt(4));
            
            bug_fr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", "FRONT RIGHT");
                    if(spray_fr.getVisibility() == View.VISIBLE) {
                        finish();
                    }
                }
            });
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
                    if(str.equals("bug") && spray_fr.getVisibility() == View.VISIBLE) {
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
                    //LEFT
                    if(deltaHorizontal < 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe LEFT");
                        spray_fr.setVisibility(View.VISIBLE);
                    }
                    
                    //RIGHT
                    if(deltaHorizontal > 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe RIGHT");
                        spray_fr.setVisibility(View.GONE);
                    }
                    
                    //UP
                    if(deltaVertical < 0 && Math.abs(deltaHorizontal) < Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe UP");
                        spray_fr.setVisibility(View.GONE);
                    }
                    
                    //DOWN
                    if(deltaVertical > 0 && Math.abs(deltaHorizontal) < Math.abs(deltaVertical)) {
                        Log.d("rotate", "Swipe DOWN");
                        spray_fr.setVisibility(View.GONE);
                    }
                    
                    break;
            }
            return false;
        }
        
        /**
         * FOR NOW: the ids of the carriers are the random number,
         * positioned around the platform as follows:
         * 01   ^
         * 32   |
         * The positioning is this way because of rotations of the spray. 
         * This will be changed in a further PR.
         */
        private Position initBug(int bugLoc) {
            if(bugLoc == 0) {
                return Position.FRONT_LEFT;
            } else if(bugLoc == 1) {
                return Position.FRONT_RIGHT;
            } else if(bugLoc == 2) {
                return Position.BACK_RIGHT;
            } else if(bugLoc == 3) {
                return Position.BACK_LEFT;
            } else {
                throw new IllegalStateException("Random value is changed!");
            }
        }
        
        private Position initSpray(int sprayLoc) {
            if(sprayLoc == 0) {
                return Position.FRONT_LEFT;
            } else if(sprayLoc == 1) {
                return Position.FRONT_RIGHT;
            } else if(sprayLoc == 2) {
                return Position.BACK_RIGHT;
            } else if(sprayLoc == 3) {
                return Position.BACK_LEFT;
            } else {
                throw new IllegalStateException("Random value is changed!");
            }
        }
}
