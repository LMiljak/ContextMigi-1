package com.github.migi_1.Context.server;

import com.github.migi_1.ContextMessages.ChangeSprayPositionMessage;
import com.github.migi_1.ContextMessages.MessageListener;

public class ChangeSprayPositionMessageHandler extends MessageListener<ChangeSprayPositionMessage>{

    @Override
    public void messageReceived(Object source, ChangeSprayPositionMessage message) {
        System.out.println("MESSAGE RECEIVED!" + source);
        System.out.println(message.getNewPosition());
    }

    @Override
    public Class<ChangeSprayPositionMessage> getMessageClass() {
        return ChangeSprayPositionMessage.class;
    }

}
