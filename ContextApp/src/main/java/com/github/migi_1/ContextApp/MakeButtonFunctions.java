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
    private Button trigger;
    
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
        trigger = (Button) act.findViewById(R.id.Button_trigger);
        
        setButtonText(position);
        trigger.setText("Trigger");
        
        act.setButtonClick(left, "left");
        act.setButtonClick(middle, "middle");
        act.setButtonClick(right, "right");
        act.setButtonClick(trigger, "trigger");
    }
    
    /**
     * Chooses which function to call to set the attack buttons' text,
     * based on the position.
     * @param position 
     *              The PlatformPosition of the player.
     */
    public void setButtonText(PlatformPosition position) {
        
        switch (position) {
            case FRONTLEFT :
                setFLText();
                break;
            case BACKRIGHT :
                setBRText();
                break;
            case BACKLEFT :
                setBLText();
                break;
            default :
                setFRText();
                break;
        }
        
    }
    
    /**
     * Sets the text of the attack buttons for the front right player.
     */
    public void setFRText() {
        
        left.setText("HIT NW");
        middle.setText("HIT NE");
        right.setText("HIT SE");
        
    }
    
    /**
     * Sets the text of the attack buttons for the front left player.
     */
    public void setFLText() {
        
        left.setText("HIT SW");
        middle.setText("HIT NW");
        right.setText("HIT NE");
        
    }
    
    /**
     * Sets the text of the attack buttons for the back right player.
     */
    public void setBRText() {
        
        left.setText("HIT NE");
        middle.setText("HIT SE");
        right.setText("HIT SW");
        
    }
    
    /**
     * Sets the text of the attack buttons for the back left player.
     */
    public void setBLText() {
        
        left.setText("HIT SE");
        middle.setText("HIT SW");
        right.setText("HIT NW");
        
    }
    
}
