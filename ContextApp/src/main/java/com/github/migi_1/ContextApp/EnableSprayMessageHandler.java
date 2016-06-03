package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.EnableSprayMessage;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;

import android.util.Log;

public class EnableSprayMessageHandler extends MessageListener<EnableSprayMessage>{

    private static final EnableSprayMessageHandler INSTANCE = new EnableSprayMessageHandler();

    private PlatformPosition position;

    private EnableSprayMessageHandler() { }

    public static EnableSprayMessageHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void messageReceived(Object source, EnableSprayMessage message) {
        Log.d("rotate", "OMGOMGOMG");
        this.position = message.getPosition();
    }

    @Override
    public Class<EnableSprayMessage> getMessageClass() {
        return EnableSprayMessage.class;
    }

    /**
     * Gets the position of this client.
     *
     * @return
     *      The position of this client.
     */
    public PlatformPosition getPosition() {
        return position;
    }

}
