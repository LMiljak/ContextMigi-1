package com.github.migi_1.Context.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.jme3.network.Server;

public class TestEnableSprayToVRMessageHandler {

    private EnableSprayToVRMessageHandler enableSprayMsgHandler;
    private EnableSprayToVRMessage enableSprayMessage;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;

    @Before
    public void setUp() throws Exception {
        enableSprayMessage = Mockito.mock(EnableSprayToVRMessage.class);
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);

        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);

        enableSprayMsgHandler = new EnableSprayToVRMessageHandler(main);
    }

    @Test
    public void messageReceivedTest() {
        enableSprayMsgHandler.messageReceived(null, enableSprayMessage);
        Mockito.verify(main).handleEnableSprayMessage(Mockito.any());
    }

    @Test
    public void getMessageClassTest() {
        assertEquals(EnableSprayToVRMessage.class, enableSprayMsgHandler.getMessageClass());
    }

}
