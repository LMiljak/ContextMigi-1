package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.ImmobilisedMessage;
import com.github.migi_1.ContextMessages.MessageListener;

public class ImmobilisedMessageHandler extends MessageListener<ImmobilisedMessage> {
    
    private MainActivity main;
    
    public ImmobilisedMessageHandler(MainActivity main) { 
        this.main = main;
        main.getClient().getClient().addMessageListener(this);
    }

    @Override
    public void messageReceived(Object source, ImmobilisedMessage message) {
        if (main.getPosHolder().getPosition() == message.getPosition()) {
            
        }
    }

    @Override
    public Class<ImmobilisedMessage> getMessageClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
