package com.github.migi_1.ContextApp;

import android.widget.Button;

/**
 * This class contains the functions that retrieve the buttons to add 
 * functions to.
 */
public class MakeButtonFunctions {
    
    private MainActivity act;
    
    /**
     * This creates an instance of the MakeButtonFunctions.
     * @param act
     * 			The instance of the application that calls this function
     */
    public MakeButtonFunctions(MainActivity act) {
        this.act = act;
    }
    
    public void setFrontRight() {
        
        act.setContentView(R.layout.android_ingame_fr);
        act.setButtonClick((Button) act.findViewById(R.id.FR_button_left), "left");
        act.setButtonClick((Button) act.findViewById(R.id.FR_button_middle), "middle");
        act.setButtonClick((Button) act.findViewById(R.id.FR_button_right), "right");
        act.setButtonClick((Button) act.findViewById(R.id.FR_button_trigger), "trigger");
        
    }
    
    public void setFrontLeft() {
        
        act.setContentView(R.layout.android_ingame_fl);
        act.setButtonClick((Button) act.findViewById(R.id.FL_button_left), "left");
        act.setButtonClick((Button) act.findViewById(R.id.FL_button_middle), "middle");
        act.setButtonClick((Button) act.findViewById(R.id.FL_button_right), "right");
        act.setButtonClick((Button) act.findViewById(R.id.FL_button_trigger), "trigger");
        
    }
    
    public void setBackLeft() {
        
        act.setContentView(R.layout.android_ingame_bl);
        act.setButtonClick((Button) act.findViewById(R.id.BL_button_left), "left");
        act.setButtonClick((Button) act.findViewById(R.id.BL_button_middle), "middle");
        act.setButtonClick((Button) act.findViewById(R.id.BL_button_right), "right");
        act.setButtonClick((Button) act.findViewById(R.id.BL_button_trigger), "trigger");
        
    }
    
    public void setBackRight() {
        
        act.setContentView(R.layout.android_ingame_br);
        act.setButtonClick((Button) act.findViewById(R.id.BR_button_left), "left");
        act.setButtonClick((Button) act.findViewById(R.id.BR_button_middle), "middle");
        act.setButtonClick((Button) act.findViewById(R.id.BR_button_right), "right");
        act.setButtonClick((Button) act.findViewById(R.id.BR_button_trigger), "trigger");
        
    }
    
}
