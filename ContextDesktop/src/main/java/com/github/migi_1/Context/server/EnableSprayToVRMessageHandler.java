package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.github.migi_1.ContextMessages.MessageListener;

/**
 * Message handler for the received enable spray messages.
 * Handles EnableSprayToVR messages.
 */
public class EnableSprayToVRMessageHandler extends MessageListener<EnableSprayToVRMessage> {

    private Main main;

    /**
     * Constructor the the EnableSprayToVR message handler.
     * @param mainApp the main app.
     */
    public EnableSprayToVRMessageHandler(Main mainApp) {
        this.main = mainApp;
        //Add a listener to the server.
        main.getServer().getServer().addMessageListener(this);
    }

    @Override
    public void messageReceived(Object source, EnableSprayToVRMessage message) {
        main.handleEnableSprayMessage(message.getPosition());
    }

    @Override
    public Class<EnableSprayToVRMessage> getMessageClass() {
        return EnableSprayToVRMessage.class;
    }

}
