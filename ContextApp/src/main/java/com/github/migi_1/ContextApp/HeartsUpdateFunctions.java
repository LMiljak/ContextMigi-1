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
     * 		The instance of the application that calls this function
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
        
        switch (health) {
            case 1:
                act.makeRed(1);
                act.makeGrey(2);
                act.makeGrey(3);
                break;
            case 2:
                act.makeRed(1);
                act.makeRed(2);
                act.makeGrey(3);
                break;
            case 3:
                act.makeRed(1);
                act.makeRed(2);
                act.makeRed(3);
                break;
            default:
                act.makeGrey(1);
                act.makeGrey(2);
                act.makeGrey(3);
                break;
        }
    }

}
