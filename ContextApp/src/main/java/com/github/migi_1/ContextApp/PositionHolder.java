package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.PositionMessage;

public class PositionHolder extends MessageListener<PositionMessage> {

    private static final PositionHolder INSTANCE = new PositionHolder();
    
    private PlatformPosition position;
    
    private PositionHolder() { }
    
    public static PositionHolder getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void messageReceived(Object source, PositionMessage message) {
        this.position = message.getPosition();
    }

    @Override
    public Class<PositionMessage> getMessageClass() {
        return PositionMessage.class;
    }
    
    public PlatformPosition getPosition() {
        return position;
    }
    
    
    
}
