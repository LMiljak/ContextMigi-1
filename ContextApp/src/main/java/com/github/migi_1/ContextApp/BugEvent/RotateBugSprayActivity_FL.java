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
public class RotateBugSprayActivity_FL extends Activity implements RotateBugSprayActivities {
    private TextView spray_fl;
    private Button bug_fl;
    private RotateBugSprayHandler rotationHandler;
    private float x1, x2, y1, y2, deltaHorizontal, deltaVertical;
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);
            
            rotationHandler = RotateBugSprayHandler.getInstance();
            
            spray_fl = (TextView) findViewById(R.id.eventBug_spray_fr);
            spray_fl.setVisibility(View.GONE);
            bug_fl = (Button) findViewById(R.id.eventBug_bug_fr);
            bug_fl.setVisibility(View.VISIBLE);
            
            bug_fl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rotate", "FRONT RIGHT");
                    if(spray_fl.getVisibility() == View.VISIBLE && bug_fl.getVisibility() == View.VISIBLE) {
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
                        newPosition = Position.BACK_LEFT;
                    }
                    
                    //Swipe right
                    if(deltaHorizontal > 0 && Math.abs(deltaHorizontal) > Math.abs(deltaVertical)) {
                        newPosition = Position.FRONT_RIGHT;
                    }
                    rotationHandler.updateSprayPosition(newPosition, this);
                }
            return false;
        }
        
        @Override
        public void enableSprayButton() {
            spray_fl.setVisibility(View.VISIBLE);    
        }

        @Override
        public void disableSprayButton() {
            spray_fl.setVisibility(View.GONE);
        }

        @Override
        public Position getPosition() {
            return Position.BACK_LEFT;
        }
}
