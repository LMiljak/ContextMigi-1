package com.github.migi_1.ContextApp;

import android.widget.Button;

/**
 * This class contains the functions that retrieve the buttons to add 
 * functions to.
 */
public class MakeButtonFunctions {
    
    MainActivity act;
    Button leftButton;
    Button middleButton;
    Button rightButton;
    Button trigger;
    
    public MakeButtonFunctions(MainActivity act) {
        this.act = act;
    }
    
    /**
     * Makes the buttons for the FRONTRIGHT position.
     */
    public void makeFRbuttons() {
        // Retrieve buttons
        leftButton = (Button) act.findViewById(R.id.FR_button_left);
        middleButton = (Button) act.findViewById(R.id.FR_button_middle);
        rightButton = (Button) act.findViewById(R.id.FR_button_right);
        act.setButtons(leftButton, middleButton, rightButton);
        trigger = (Button) act.findViewById(R.id.FR_button_trigger);
        act.setTrigger(trigger);
    }
        
    /**
     * Makes the buttons for the FRONTLEFT position.
     */
    public void makeFLbuttons() {
        // Retrieve buttons
        leftButton = (Button) act.findViewById(R.id.FL_button_left);
        middleButton = (Button) act.findViewById(R.id.FL_button_middle);
        rightButton = (Button) act.findViewById(R.id.FL_button_right);
        act.setButtons(leftButton, middleButton, rightButton);
        trigger = (Button) act.findViewById(R.id.FR_button_trigger);
        act.setTrigger(trigger);
    }
        
    /**
     * Makes the buttons for the BACKRIGHT position.
     */
    public void makeBRbuttons() {
        // Retrieve buttons
        leftButton = (Button) act.findViewById(R.id.BR_button_left);
        middleButton = (Button) act.findViewById(R.id.BR_button_middle);
        rightButton = (Button) act.findViewById(R.id.BR_button_right);
        act.setButtons(leftButton, middleButton, rightButton);
        trigger = (Button) act.findViewById(R.id.FR_button_trigger);
        act.setTrigger(trigger);
    }
        
    /**
     * Makes the buttons for the BACKLEFT position.
     */
    public void makeBLbuttons() {
        // Retrieve buttons
        leftButton = (Button) act.findViewById(R.id.BL_button_left);
        middleButton = (Button) act.findViewById(R.id.BL_button_middle);
        rightButton = (Button) act.findViewById(R.id.BL_button_right);
        act.setButtons(leftButton, middleButton, rightButton);
        trigger = (Button) act.findViewById(R.id.FR_button_trigger);
        act.setTrigger(trigger);
    }
    
}
