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

/**
 *
 * @author Nils
 */
public class RotateBugSprayActivity_FR extends Activity implements RotateBugSprayActivities {
    private TextView spray_fr;
    private Button bug_fr;
    private RotateBugSprayHandler rotationHandler;
    private float x1, x2, y1, y2, deltaHorizontal, deltaVertical;
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);

            rotationHandler = RotateBugSprayHandler.getInstance();
            
            spray_fr = (TextView) findViewById(R.id.eventBug_spray_fr);
            spray_fr.setVisibility(View.GONE);
            bug_fr = (Button) findViewById(R.id.eventBug_bug_fr);
            bug_fr.setVisibility(View.GONE);
            
            bug_fr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", "FRONT RIGHT");
                    if(spray_fr.getVisibility() == View.VISIBLE && bug_fr.getVisibility() == View.VISIBLE) {
                        finish();
                    }
                }
            });
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Position newPosition  = Position.FRONT_RIGHT;
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
                        newPosition = Position.BACK_RIGHT;
                    }
                    
                    //Swipe left
                    if(deltaHorizontal < 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        newPosition = Position.FRONT_LEFT;
                    }
                    Log.d("rotate", "UPDATING POSITION");
                    rotationHandler.updateSprayPosition(newPosition, this);
                }
            return false;
        }
        
        @Override
        public void enableSprayButton() {
            
            Log.d("rotate", "OMG ITS WORKING :D:D:D");
            spray_fr.setVisibility(View.VISIBLE);
        }
        
        @Override
        public void disableSprayButton() {
            spray_fr.setVisibility(View.GONE);
        }
        
        @Override
        public Position getPosition() {
            return Position.FRONT_RIGHT;
        }
}
