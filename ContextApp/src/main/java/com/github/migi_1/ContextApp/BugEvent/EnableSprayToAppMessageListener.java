package com.github.migi_1.ContextApp.BugEvent;

import com.github.migi_1.ContextMessages.EnableSprayToAppMessage;
import com.github.migi_1.ContextMessages.MessageListener;

import android.util.Log;

public class EnableSprayToAppMessageListener extends MessageListener<EnableSprayToAppMessage> {

    private static final EnableSprayToAppMessageListener INSTANCE = new EnableSprayToAppMessageListener();

    private EnableSprayToAppMessageListener() { }

    public static EnableSprayToAppMessageListener getInstance() {
        return INSTANCE;
    }

    @Override
    public void messageReceived(Object source, EnableSprayToAppMessage message) {
        Log.d("rotate", "ENABLE SPRAY FOR POSITION: " + message.getPosition());
    }

    @Override
    public Class<EnableSprayToAppMessage> getMessageClass() {
        return EnableSprayToAppMessage.class;
    }

}
