package com.github.migi_1.ContextApp.BugEvent;
 
import android.util.Log;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;


/**
* Message handler for the sotp all event message. 
*/
public class StopAllEventsMessageListener extends MessageListener<StopAllEventsMessage> {

    private RotateBugSprayActivity bugActivity;
    
    /**
     * Constructor for the StopAllEventMessage listener. 
     * @param activity bug event activity. 
     */
    public StopAllEventsMessageListener(RotateBugSprayActivity activity) { 
        this.bugActivity = activity;
        //Add a listener to the client. 
        bugActivity.getClient().getClient().addMessageListener(this);
    }

    @Override
    public void messageReceived(Object source, StopAllEventsMessage message) {
        Log.d("rotate", "MESSAGE RECEIVED, STOPPING ALL OF THE EVENTS!");
        bugActivity.stopEvent();
    }
    
    @Override
    public Class<StopAllEventsMessage> getMessageClass() {
        return StopAllEventsMessage.class;
    }
}