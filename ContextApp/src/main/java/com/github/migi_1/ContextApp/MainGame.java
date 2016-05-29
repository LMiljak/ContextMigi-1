package com.github.migi_1.ContextApp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
        
        /**
         * Calls functions to make the hearts the right colour.
         * @param health the amount of health that has to be displayed in grey
         *      and red hearts.
         */
        public void setHealth(int health) {
            if (health > 0) {
                makeRed(1);
                if (health > 1){
                    makeRed(2);
                    if (health > 2)
                        makeRed(3);
                    else
                        makeGrey(3);
                }
                else {
                    makeGrey(2);
                    makeGrey(3);
                }
            }
            else {
                makeGrey(1);
                makeGrey(2);
                makeGrey(3);
            }
        }
        
        /**
         * Makes a heart red
         * @param heartid the id of the heart which has its sprite changed
         */
        public void makeRed(int heartid) {
            ImageView img;
            if (heartid == 1) {
                img = (ImageView) findViewById(R.id.FR_heart_1);
            }
            else if (heartid == 2) {
                img = (ImageView) findViewById(R.id.FR_heart_2);
            }
            else {
                img = (ImageView) findViewById(R.id.FR_heart_3);
            }
            img.setImageResource(R.drawable.heart_red);
        }
        
        /**
         * Makes a heart grey
         * @param heartid the id of the heart which has its sprite changed
         */
        public void makeGrey(int heartid) {
            ImageView img;
            if (heartid == 1) {
                img = (ImageView) findViewById(R.id.FR_heart_1);
            }
            else if (heartid == 2) {
                img = (ImageView) findViewById(R.id.FR_heart_2);
            }
            else {
                img = (ImageView) findViewById(R.id.FR_heart_3);
            }
            img.setImageResource(R.drawable.heart_grey);
        }
}
