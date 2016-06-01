/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

/**
 *
 * @author Nils
 */
public final class RotateBugSprayHandler {
        private TextView spray_fl, spray_br, spray_bl;
        private Button bug_fl, bug_br, bug_bl;
        private Position spray_position;
        private RotateBugSprayActivities spray_activity;
        private Position bug_position;
        
        private static RotateBugSprayHandler INSTANCE = new RotateBugSprayHandler();
        
        public static RotateBugSprayHandler getInstance() {
            return INSTANCE;
        }

        private RotateBugSprayHandler() {
            initBugLocation(new Random().nextInt(4));
            initSprayLocation(new Random().nextInt(4));
            Log.d("rotate", "Bug (init): " + bug_position.toString());
            Log.d("rotate", "Spray (init): " + spray_position.toString());
        }
        
        public void updateSprayPosition(Position newPosition, RotateBugSprayActivities locationInstance) {
            disableAllSpray();
            Log.d("rotate", "updatingSprayPosition");
            RotateBugSprayActivities oldPosition = getActiveSprayPosition();
            Log.d("rotate", "updating on: " + oldPosition);
            oldPosition.enableSprayButton();
            
            //spray_position = newPosition;
        }
        
        private void disableAllSpray() {
            
        }
        
        private RotateBugSprayActivities getActiveSprayPosition() {
            return spray_activity;
//            if(activity_FR.getPosition().equals(spray_position)) {
//                return activity_FR;
//            }
//            throw new IllegalStateException("UNKNOWN STATE: " + spray_position.toString());
        }
        
        /**
         * FOR NOW: the ids of the carriers are the random number,
         * positioned around the platform as follows:
         * 01   ^
         * 32   |
         * The positioning is this way because of rotations of the spray. 
         * This will be changed in a further PR.
         */
        private void initBugLocation(int bugLoc) {
//            if(bugLoc == 0) {
//                bug_position = Position.FRONT_LEFT;
//            } else if(bugLoc == 1) {
//                bug_position = Position.FRONT_RIGHT;
//            } else if(bugLoc == 2) {
//                bug_position = Position.BACK_RIGHT;
//            } else if(bugLoc == 3) {
//                bug_position = Position.BACK_LEFT;
//            } else {
//                throw new IllegalStateException("Random value has been changed!");
//            }
            bug_position = Position.FRONT_RIGHT;
            
        }
        
        private void initSprayLocation(int sprayLoc) {
            if(sprayLoc == 0) {
                spray_position = Position.FRONT_LEFT;
            } else if(sprayLoc == 1) {
//                spray_fr.setVisibility(View.VISIBLE);
                spray_position = Position.FRONT_RIGHT;
            } else if(sprayLoc == 2) {
                spray_position = Position.BACK_RIGHT;
            } else if(sprayLoc == 3) {
                spray_position = Position.BACK_LEFT;
            } else {
                throw new IllegalStateException("Random value has been changed!");
            }
        }
}
