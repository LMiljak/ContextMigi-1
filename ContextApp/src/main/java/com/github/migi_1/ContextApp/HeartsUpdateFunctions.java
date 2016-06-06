package com.github.migi_1.ContextApp;

import android.widget.ImageView;
import com.github.migi_1.ContextMessages.PlatformPosition;

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
     * @param position 
     *                  The position of the player whose hearts will be updated
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
                makeRed(1);
                makeGrey(2);
                makeGrey(3);
                break;
            case 2:
                makeRed(1);
                makeRed(2);
                makeGrey(3);
                break;
            case 3:
                makeRed(1);
                makeRed(2);
                makeRed(3);
                break;
            default:
                makeGrey(1);
                makeGrey(2);
                makeGrey(3);
                break;
        }
    }

    /**
     * Makes a heart red.
     * @param heartid the id of the heart which has its sprite changed
     */
    public void makeRed(int heartid) {
        
        ImageView img = (ImageView) act.findViewById(getHeart(heartid));
        img.setImageResource(R.drawable.heart_red);
        
    }

    /**
     * Makes a heart grey.
     * @param heartid the id of the heart which has its sprite changed
     */
    public void makeGrey(int heartid) {
        
        ImageView img = (ImageView) act.findViewById(getHeart(heartid));
        img.setImageResource(R.drawable.heart_grey);

    }
    
    /**
     * Returns the right heart to update based on heartid.
     * @param heartid 
     *              the number of the heart that needs to be updated.
     *              represents the first, second, or third heart for a player
     * @return 
     *              the id of the heart that needs to be updated
     *              the exact id of that heart from that player
     */
    public int getHeart(int heartid) {
        switch (heartid) {
            case 1:
                return R.id.Heart_1;
            case 2:
                return R.id.Heart_2;
            default:
                return R.id.Heart_3;
        }
    }

}
