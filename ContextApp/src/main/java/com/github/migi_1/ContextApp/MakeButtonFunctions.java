package com.github.migi_1.ContextApp;

import android.widget.Button;
import android.widget.RemoteViews;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * This class contains the functions that retrieve the buttons to add 
 * functions to.
 */
public class MakeButtonFunctions {
    
    private MainActivity act;
    
    public RemoteViews view;
    public Button left;
    public Button middle;
    public Button right;
    public Button trigger;
    
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
        
        view = new RemoteViews(act.getPackageName(), R.layout.android_ingame);
        
        setButtonText(position);
        view.setTextViewText(R.id.Button_trigger, "Trigger");
        
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
        
        view.setTextViewText(R.id.Button_left, "HIT NW");
        view.setTextViewText(R.id.Button_middle, "HIT NE");
        view.setTextViewText(R.id.Button_right, "HIT SE");
        
    }
    
    /**
     * Sets the text of the attack buttons for the front left player.
     */
    public void setFLText() {
        
        view.setTextViewText(R.id.Button_left, "HIT SW");
        view.setTextViewText(R.id.Button_middle, "HIT NW");
        view.setTextViewText(R.id.Button_right, "HIT NE");
        
    }
    
    /**
     * Sets the text of the attack buttons for the back right player.
     */
    public void setBRText() {
        
        view.setTextViewText(R.id.Button_left, "HIT NE");
        view.setTextViewText(R.id.Button_middle, "HIT SE");
        view.setTextViewText(R.id.Button_right, "HIT SW");
        
    }
    
    /**
     * Sets the text of the attack buttons for the back left player.
     */
    public void setBLText() {
        
        view.setTextViewText(R.id.Button_left, "HIT SE");
        view.setTextViewText(R.id.Button_middle, "HIT SW");
        view.setTextViewText(R.id.Button_right, "HIT NW");
        
    }
    
}
