/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * @author Nils
 */
public class RotateBugSprayActivity extends Activity {
    //private TextView spray;
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.android_event_bugs_fr);
            Button bugButton = (Button) findViewById(R.id.eventBug_bug_fr);
            //spray = (TextView) findViewById(R.id.eventBug_spray);
            // add logging functionality
            setButtons(bugButton, "bug");
        }
            /**
         * Makes sure buttonpresses are logged.
         * @param butt = the button to which a clicklistener is set
         * @param str = message to be logged
         */
        public void setButtons(Button button, final String str) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("buttonpress ", str);
                    if(str.equals("bug")) {
                        finish();
                    }
                    finish();
                }
            });
        }
}
