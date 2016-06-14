package com.github.migi_1.ContextApp;

import android.widget.Button;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.Direction;

/**
 * This class contains the functions that retrieve the buttons to add 
 * functions to.
 */
public class MakeButtonFunctions {
    
    private MainActivity act;
    
    private Button left, middle, right;
    
    
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
        
        setButtonTextAndFunctions(position);
    }
    
    /**
     * Chooses which directions to write on the buttons and to attach to their 
     * functions, based on the position.
     * @param position 
     *              The PlatformPosition of the player.
     */
    public void setButtonTextAndFunctions(PlatformPosition position) {
        
        if (position.equals(PlatformPosition.FRONTRIGHT) 
                || position.equals(PlatformPosition.BACKRIGHT)) {
            left.setText("HIT NORTH");
            middle.setText("HIT EAST");
            right.setText("HIT SOUTH");
            
            act.setButtonClick(left, Direction.NORTH);
            act.setButtonClick(middle, Direction.EAST);
            act.setButtonClick(right, Direction.SOUTH);
        }
        else {
            left.setText("HIT SOUTH");
            middle.setText("HIT WEST");
            right.setText("HIT NORTH");
            
            act.setButtonClick(left, Direction.SOUTH);
            act.setButtonClick(middle, Direction.WEST);
            act.setButtonClick(right, Direction.NORTH);
        }
        
    }
    
}
