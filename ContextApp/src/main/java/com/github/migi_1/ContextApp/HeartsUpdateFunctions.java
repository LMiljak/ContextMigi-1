package com.github.migi_1.ContextApp;

import android.widget.ImageView;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * Contains functions which changes the hearts that represent the 
 * player's health.
 */
public class HeartsUpdateFunctions {

    private MainActivity act;
    private PlatformPosition position;
    
    /**
     * This creates an instance of the HeartsUpdateFunctions.
     * @param act
     * 			The instance of the application that calls this function
     * @param position 
     *                  The position of the player whose hearts will be updated
     */
    public HeartsUpdateFunctions(MainActivity act, PlatformPosition position) {
        this.act = act;
        this.position = position;
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
     * Returns the right heart to update based on heartid and position.
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
                switch (position) {
                    case FRONTLEFT:
                        return R.id.FL_heart_1;
                    case BACKLEFT:
                        return R.id.BL_heart_1;
                    case BACKRIGHT:
                        return R.id.BR_heart_1;
                    default:
                        return R.id.FR_heart_1;
                }
            case 2:
                switch (position) {
                    case FRONTLEFT:
                        return R.id.FL_heart_2;
                    case BACKLEFT:
                        return R.id.BL_heart_2;
                    case BACKRIGHT:
                        return R.id.BR_heart_2;
                    default:
                        return R.id.FR_heart_2;
                }
            default:
                switch (position) {
                    case FRONTLEFT:
                        return R.id.FL_heart_3;
                    case BACKLEFT:
                        return R.id.BL_heart_3;
                    case BACKRIGHT:
                        return R.id.BR_heart_3;
                    default:
                        return R.id.FR_heart_3;
                }
        }
    }
    
    /**
     * Sets the position of the HeartUpdateFunctions.
     * @param position 
     *              The position of the player who's hearts are updated.
     */
    public void setPosition(PlatformPosition position) {
        this.position = position;
    }

}
