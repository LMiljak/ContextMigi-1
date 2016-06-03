package com.github.migi_1.ContextApp.BugEvent;

import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;

import android.util.Log;

/**
*
* @author Nils
*/
public class StopAllEventsMessageListener extends MessageListener<StopAllEventsMessage> {

    private static final StopAllEventsMessageListener INSTANCE = new StopAllEventsMessageListener();

    private StopAllEventsMessageListener() { }

    public static StopAllEventsMessageListener getInstance() {
        return INSTANCE;
    }

    @Override
    public void messageReceived(Object source, StopAllEventsMessage message) {
        Log.d("rotate", "STOPPING ALL EVENTS");
    }

    @Override
    public Class<StopAllEventsMessage> getMessageClass() {
        return StopAllEventsMessage.class;
    }

}
