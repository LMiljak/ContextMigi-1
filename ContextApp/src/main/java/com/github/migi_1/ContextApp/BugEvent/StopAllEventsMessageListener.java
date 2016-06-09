package com.github.migi_1.ContextApp.BugEvent;
 
import android.util.Log;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;


/**
*
* @author Nils
*/
public class StopAllEventsMessageListener extends MessageListener<StopAllEventsMessage> {

    private RotateBugSprayActivity bugActivity;
    
    public StopAllEventsMessageListener() { }
    
    public StopAllEventsMessageListener(RotateBugSprayActivity activity) { 
        this.bugActivity = activity;
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