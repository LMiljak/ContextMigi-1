/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

import android.util.Log;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StartBugEventMessage;

/**
 *
 * @author Nils
 */
public class StartBugEventMessageHandler extends MessageListener<StartBugEventMessage> {

    private static final StartBugEventMessageHandler INSTANCE = 
                        new StartBugEventMessageHandler();
    
    public static StartBugEventMessageHandler getInstance() {
        return INSTANCE;
    }
    
    private StartBugEventMessageHandler() {
        ClientWrapper.getInstance().getClient().addMessageListener(this);
    }
    
    @Override
    public void messageReceived(Object source, StartBugEventMessage message) {
        Log.d("rotate", "START BUG EVENT");
    }

    @Override
    public Class<StartBugEventMessage> getMessageClass() {
        return StartBugEventMessage.class;
    }
    
}
