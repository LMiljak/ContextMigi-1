package com.github.migi_1.ContextApp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 *
 * @author Remi
 */
public class MainGame extends Activity {
    
    private HelloActivity act;
    
    /**
     * Constructor for MainGame.
     * 
     * @param act
     *      The main activity from which it was created.
     */
    public MainGame(HelloActivity act) {
        this.act = act;
    }
    
    /**
     * Projects the game's UI to the device's screen.
     * @param savedInstanceState 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // set the ui to the ingame screen
            setContentView(R.layout.android_ingame_fr);
            
            // Retrieve buttons
            Button leftButton = (Button) findViewById(R.id.FR_button_left);
            Button middleButton = (Button) findViewById(R.id.FR_button_middle);
            Button rightButton = (Button) findViewById(R.id.FR_button_right);
            
            // add logging functionality
            setButtons(leftButton, "left");
            setButtons(middleButton, "middle");
            setButtons(rightButton, "right");
    }
    
    /**
         * Makes sure buttonpresses are logged.
         * @param butt = the button to which a clicklistener is set
         * @param str = message to be logged
         */
        public void setButtons(Button butt, final String str) {

            butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("buttonpress", str);
            }
            });
        }
}
