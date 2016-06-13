package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;

/**
 * Message handler for the received stop event messages.
 * Handles StopEventToVR messages.
 */
public class StopEventMessageHandler extends MessageListener<StopEventToVRMessage> {

    private Main main;

    /**
     * Constructor for the StopEvent message handler.
     * @param main the main app.
     */
    public StopEventMessageHandler(Main main) {
        this.main = main;
        //Add the listener to the server.
        main.getServer().getServer().addMessageListener(this);
    }

    @Override
    public void messageReceived(Object source, StopEventToVRMessage message) {
        main.handleStopBugEvent();
    }

    @Override
    public Class<StopEventToVRMessage> getMessageClass() {
        return StopEventToVRMessage.class;
    }
}
