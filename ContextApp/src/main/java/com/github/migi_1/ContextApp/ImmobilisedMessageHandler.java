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
            if (message.getImmobilised()) {
                main.immobilise(true);
            }
            else {
                main.immobilise(false);
            }
        }
    }

    @Override
    public Class<ImmobilisedMessage> getMessageClass() { 
        return ImmobilisedMessage.class;
    }

    
    
}
