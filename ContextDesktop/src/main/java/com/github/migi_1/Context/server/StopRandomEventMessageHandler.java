package com.github.migi_1.Context.server;

import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StopEventMessage;

public class StopRandomEventMessageHandler extends MessageListener<StopEventMessage> {

    @Override
    public void messageReceived(Object source, StopEventMessage message) {
        System.out.println("MESSAGE RECEIVED! " + message.toString());
    }

    @Override
    public Class<StopEventMessage> getMessageClass() {
        return StopEventMessage.class;
    }

}
