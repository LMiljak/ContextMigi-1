/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

import android.util.Log;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StartBugEventMessage;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;

/**
 *
 * @author Nils
 */
public class StartBugEventMessageListener extends MessageListener<StartBugEventMessage> {

    private MainActivity main;
    
    public StartBugEventMessageListener() { }
    
    public StartBugEventMessageListener(MainActivity mainActivity) {
        this.main = mainActivity;
        main.getClient().getClient().addMessageListener(this);
    }
    
    @Override
    public void messageReceived(Object source, StartBugEventMessage message) {
        Log.d("rotate", "Starting the Random event!");
        main.startBugEvent();
    }

    @Override
    public Class<StartBugEventMessage> getMessageClass() {
        return StartBugEventMessage.class;
    }
    
}
