package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;

public class StopEventMessageHandler extends MessageListener<StopEventToVRMessage> {

    private Main main;

    public StopEventMessageHandler(Main main) {
        this.main = main;
        main.getServer().getServer().addMessageListener(this);
    }

    @Override
    public void messageReceived(Object source, StopEventToVRMessage message) {
        main.handleStopBugEvent();
    }

    @Override
    public Class<StopEventToVRMessage> getMessageClass() {

        return null;
    }
}
