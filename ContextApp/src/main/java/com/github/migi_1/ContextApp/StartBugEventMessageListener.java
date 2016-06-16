package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StartBugEventMessage;

/**
 * Message handler for the start bug event message.
 * Receives StartBugEvent messages send to client (app).
 */
public class StartBugEventMessageListener extends MessageListener<StartBugEventMessage> {

    private MainActivity main;
    
    /**
     * The constructor for the StartBugEventMessage listener. 
     * @param mainActivity the main activity in which "the listener listens to messages". 
     */
    public StartBugEventMessageListener(MainActivity mainActivity) {
        this.main = mainActivity;
        main.getClient().getClient().addMessageListener(this);
    }
    
    @Override
    public void messageReceived(Object source, StartBugEventMessage message) {
        main.startBugEvent(message.getBugPosition(), message.getSprayPosition());
    }

    @Override
    public Class<StartBugEventMessage> getMessageClass() {
        return StartBugEventMessage.class;
    }
}
