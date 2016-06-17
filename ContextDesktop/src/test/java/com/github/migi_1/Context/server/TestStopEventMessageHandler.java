package com.github.migi_1.Context.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;
import com.jme3.network.Server;

/**
 * Tests everything to do with the StopEventMessageHandler class.
 * @author Nils
 *
 */
public class TestStopEventMessageHandler {

    private StopEventMessageHandler stopEventMessageHandler;
    private StopEventToVRMessage stopEventMessage;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;
    
    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @Before
    public void setUp() throws Exception {
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);
        stopEventMessage = Mockito.mock(StopEventToVRMessage.class);

        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);

        stopEventMessageHandler = new StopEventMessageHandler(main);
    }
    
    /**
     * Tests if the handleStopBugEvent method is called when a message has been received.
     */
    @Test
    public void messageReceivedTest() {
        stopEventMessageHandler.messageReceived(null, stopEventMessage);
        Mockito.verify(main).handleStopBugEvent();
    }
    
    /**
     * Test the getMessage method.
     */
    @Test
    public void getMessageClassTest() {
        assertEquals(StopEventToVRMessage.class, stopEventMessageHandler.getMessageClass());
    }

}
