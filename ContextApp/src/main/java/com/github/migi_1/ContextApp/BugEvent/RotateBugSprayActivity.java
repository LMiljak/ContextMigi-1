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
import com.github.migi_1.ContextApp.Carrier_BL;
import com.github.migi_1.ContextApp.Carrier_BR;
import com.github.migi_1.ContextApp.Carrier_FL;
import com.github.migi_1.ContextApp.Carrier_FR;
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
        private Carrier_FL carrier1;
        private Carrier_FR carrier2;
        private Carrier_BR carrier3;
        private Carrier_BL carrier4;
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);
            
//            initBug(new Random().nextInt(4));
//            initSpray(new Random().nextInt(4));
//            
//            carrier1 = Carrier_FL.getInstance();
//            carrier2 = Carrier_FR.getInstance();
//            carrier3 = Carrier_BR.getInstance();
//            carrier4 = Carrier_BL.getInstance();
            
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
            
            bug_fr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", "FRONT RIGHT");
                    if(spray_fr.getVisibility() == View.VISIBLE) {
                        finish();
                    }
                }
            });
            
//            bug_fl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("rotate", "FRONT LEFT");
//                    if(spray_fl.getVisibility() == View.VISIBLE) {
//                        finish();
//                    }
//                }
//            });
//            
//            bug_br.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("rotate", "BACK RIGHT");
//                    if(spray_br.getVisibility() == View.VISIBLE) {
//                        finish();
//                    }
//                }
//            });
//            
//            bug_bl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("rotate", "BACK LEFT");
//                    if(spray_bl.getVisibility() == View.VISIBLE) {
//                        finish();
//                    }
//                }
//            });
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
                    Log.d("rotate", "HORIZONTAL: " + deltaHorizontal);
                    Log.d("rotate", "VERTICAL: " + deltaVertical);
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
        private void initBug(int bugLoc) {
            bug_fr.setVisibility(View.VISIBLE);
//            if(bugLoc == 0) {
//                spray_fl.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Front-left");
//            }
//            
//            if(bugLoc == 1) {
//                spray_fr.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Front-right");
//            }
//            
//            if(bugLoc == 2) {
//                spray_bl.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Back-left");
//            }
//            
//            if(bugLoc == 3) {
//                spray_br.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Back-right");
//            }
        }
        
        private void initSpray(int sprayLoc) {
            spray_fr.setVisibility(View.VISIBLE);
//            if(sprayLoc == 0) {
//                spray_fl.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Front-left");
//            }
//            
//            if(sprayLoc == 1) {
//                spray_fr.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Front-right");
//            }
//            
//            if(sprayLoc == 2) {
//                spray_bl.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Back-left");
//            }
//            
//            if(sprayLoc == 3) {
//                spray_br.setVisibility(View.VISIBLE);
//                Log.d("rotate", "Location: Back-right");
//            }
        }
}
