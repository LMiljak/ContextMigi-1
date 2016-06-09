/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

import android.util.Log;
import com.github.migi_1.ContextMessages.EnableSprayToAppMessage;
import com.github.migi_1.ContextMessages.MessageListener;

/**
 *
 * @author Nils
 */
public class EnableSprayAppMessageHandler extends MessageListener<EnableSprayToAppMessage> {

    private RotateBugSprayActivity bugActivity;
    
    public EnableSprayAppMessageHandler() { }
    
    public EnableSprayAppMessageHandler(RotateBugSprayActivity activity) {
        this.bugActivity = activity;
        bugActivity.getClient().getClient().addMessageListener(this);
    }
    
    @Override
    public void messageReceived(Object source, EnableSprayToAppMessage message) {
        Log.d("rotate", "Spray enabled for screen: " + message.getPosition());
        bugActivity.enableSprayButton(message.getPosition());
    }

    @Override
    public Class<EnableSprayToAppMessage> getMessageClass() {
        return EnableSprayToAppMessage.class;
    }
    
}
