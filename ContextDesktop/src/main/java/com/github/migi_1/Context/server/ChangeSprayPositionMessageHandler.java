package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.ChangeSprayPositionMessage;
import com.github.migi_1.ContextMessages.EnableSprayMessage;
import com.github.migi_1.ContextMessages.MessageListener;
import com.jme3.network.Server;

public class ChangeSprayPositionMessageHandler extends MessageListener<ChangeSprayPositionMessage>{

    private Main main;

    public ChangeSprayPositionMessageHandler(Main main) {
        this.main = main;
        main.getServer().getServer().addMessageListener(this);
    }

    @Override
    public void messageReceived(Object source, ChangeSprayPositionMessage message) {
        System.out.println("MESSAGE RECEIVED!" + source);
        System.out.println(message.getNewPosition());

        EnableSprayMessage newMsg = new EnableSprayMessage(message.getNewPosition());
        Server server = main.getServer().getServer();
        System.out.println("SENDING MESSAGE: " + (server != null));
        System.out.println(newMsg.getPosition());
        if(server != null) {
            server.getConnection(0).send(newMsg);
        }
        System.out.println("MESSAGE SEND!");
    }

    @Override
    public Class<ChangeSprayPositionMessage> getMessageClass() {
        return ChangeSprayPositionMessage.class;
    }

}
