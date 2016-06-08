package com.github.migi_1.ContextApp;

import android.widget.Button;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * This class contains the functions that retrieve the buttons to add 
 * functions to.
 */
public class MakeButtonFunctions {
    
    private MainActivity act;
    
    private Button left;
    private Button middle;
    private Button right;
    
    /**
     * This creates an instance of the MakeButtonFunctions.
     * @param act
     * 			The instance of the application that calls this function
     */
    public MakeButtonFunctions(MainActivity act) {

        this.act = act;

    }
    
    /**
     * Sets the buttons for the android UI.
     * @param position 
     *              The PlatformPosition of the player.
     */
    public void setButtons(PlatformPosition position) {
        left = (Button) act.findViewById(R.id.Button_left);
        middle = (Button) act.findViewById(R.id.Button_middle);
        right = (Button) act.findViewById(R.id.Button_right);
        
        setButtonText(position);
        
        act.setButtonClick(left, "left");
        act.setButtonClick(middle, "middle");
        act.setButtonClick(right, "right");
    }
    
    /**
     * Chooses which directions to write on the buttons, based on the position.
     * @param position 
     *              The PlatformPosition of the player.
     */
    public void setButtonText(PlatformPosition position) {
        
        if (position.equals(PlatformPosition.FRONTRIGHT) 
                || position.equals(PlatformPosition.BACKRIGHT)) {
            left.setText("HIT NORTH");
            middle.setText("HIT EAST");
            right.setText("HIT SOUTH");
        }
        else {
            left.setText("HIT SOUTH");
            middle.setText("HIT WEST");
            right.setText("HIT NORTH");
        }
        
    }
    
}
