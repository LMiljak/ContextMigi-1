package com.github.migi_1.Context.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.jme3.network.Server;

/**
 * Tests everything that has to do with the EnableSprayToVRMessageHandler class.
 * @author Nils
 *
 */
public class TestEnableSprayToVRMessageHandler {

    private EnableSprayToVRMessageHandler enableSprayMsgHandler;
    private EnableSprayToVRMessage enableSprayMessage;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;
    
    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
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
    
    /**
     * Tests if a message is received about enabling the sprayMessage,
     *  the main class executes handleEnableSprayMesssage.
     */
    @Test
    public void messageReceivedTest() {
        enableSprayMsgHandler.messageReceived(null, enableSprayMessage);
        Mockito.verify(main).handleEnableSprayMessage(Mockito.any());
    }

    /**
     * Tests the getter for the messageClass.
     */
    @Test
    public void getMessageClassTest() {
        assertEquals(EnableSprayToVRMessage.class, enableSprayMsgHandler.getMessageClass());
    }

}
