package com.github.migi_1.Context.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;
import com.jme3.network.Server;

public class TestStopEventMessageHandler {

    private StopEventMessageHandler stopEventMessageHandler;
    private StopEventToVRMessage stopEventMessage;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;

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

    @Test
    public void messageReceivedTest() {
        stopEventMessageHandler.messageReceived(null, stopEventMessage);
        Mockito.verify(main).handleStopBugEvent();
    }

    @Test
    public void getMessageClassTest() {
        assertEquals(StopEventToVRMessage.class, stopEventMessageHandler.getMessageClass());
    }

}
