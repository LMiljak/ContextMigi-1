package com.github.migi_1.ContextApp;

import android.widget.ImageView;

/**
 * Contains functions which changes the hearts that represent the 
 * player's health.
 */
public class HeartsUpdateFunctions {

    private MainActivity act;
    
    /**
     * This creates an instance of the HeartsUpdateFunctions.
     * @param act
     * 			The instance of the application that calls this function
     */
    public HeartsUpdateFunctions(MainActivity act) {
        this.act = act;
    }
    
   /**
    * Calls functions to make the hearts the right colour.
    * @param health the amount of health that has to be displayed in grey
    *      and red hearts.
    */
    public void setHealth(int health) {
        if (health > 0) {
            makeRed(1);
            if (health > 1) {
                makeRed(2);
                if (health > 2) {
                    makeRed(3);
                }
                else {
                    makeGrey(3);
                }
            }
            else {
                makeGrey(2);
                makeGrey(3);
            }
        }
        else {
            makeGrey(1);
            makeGrey(2);
            makeGrey(3);
        }
    }

    /**
     * Makes a heart red.
     * @param heartid the id of the heart which has its sprite changed
     */
    public void makeRed(int heartid) {
        ImageView img;
        if (heartid == 1) {
            img = (ImageView) act.findViewById(R.id.FR_heart_1);
        }
        else if (heartid == 2) {
            img = (ImageView) act.findViewById(R.id.FR_heart_2);
        }
        else {
            img = (ImageView) act.findViewById(R.id.FR_heart_3);
        }
        img.setImageResource(R.drawable.heart_red);
    }

    /**
     * Makes a heart grey.
     * @param heartid the id of the heart which has its sprite changed
     */
    public void makeGrey(int heartid) {
        ImageView img;
        if (heartid == 1) {
            img = (ImageView) act.findViewById(R.id.FR_heart_1);
        }
        else if (heartid == 2) {
            img = (ImageView) act.findViewById(R.id.FR_heart_2);
        }
        else {
            img = (ImageView) act.findViewById(R.id.FR_heart_3);
        }
        img.setImageResource(R.drawable.heart_grey);
    }

}
