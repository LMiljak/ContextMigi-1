/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StartBugEventMessage;

import android.util.Log;

/**
 *
 * @author Nils
 */
public class StartBugEventListener extends MessageListener<StartBugEventMessage> {

    private static final StartBugEventListener INSTANCE = new StartBugEventListener();

    private StartBugEventListener() { }

    public static StartBugEventListener getInstance() {
        return INSTANCE;
    }

    @Override
    public void messageReceived(Object source, StartBugEventMessage message) {
        Log.d("rotate", "STARTING BUG EVENT");
    }

    @Override
    public Class<StartBugEventMessage> getMessageClass() {
        return StartBugEventMessage.class;
    }

}
