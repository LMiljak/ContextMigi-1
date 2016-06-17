package com.github.migi_1.Context.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Server;

/**
 * Tests everything that has to with the AttackMessageHandler class.
 * @author Nils
 *
 */
public class TestAttackMessageHandler {

    private AttackMessageHandler attackMessageHandler;
    private Main main;
    private Carrier carrier;
    private PlatformPosition position;
    private ServerWrapper serverWrapper;
    private Server server;
    private AttackMessage attackMessage;

    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @Before
    public void setUp() throws Exception {
        attackMessage = Mockito.mock(AttackMessage.class);
        main = Mockito.mock(Main.class);
        carrier = Mockito.mock(Carrier.class);
        position = PlatformPosition.BACKLEFT;
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);

        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);

        attackMessageHandler = new AttackMessageHandler(main, carrier, position);
    }

    /**
     * Tests when a message has been received which is meant for a different player,
     * no direction was received.
     */
    @Test
    public void messageReceived_DifferentPosition_Test() {
        Mockito.when(attackMessage.getPosition()).thenReturn(PlatformPosition.FRONTRIGHT);
        attackMessageHandler.messageReceived(null, attackMessage);
        Mockito.verify(attackMessage, Mockito.times(0)).getDirection();
    }

    /**
     * Tests when a message is received for the correct player, the direction is receiced..
     */
    @Test
    public void messageReceived_SamePosition_Test() {
        Mockito.when(attackMessage.getPosition()).thenReturn(PlatformPosition.BACKLEFT);
        attackMessageHandler.messageReceived(null, attackMessage);
        Mockito.verify(attackMessage).getDirection();
    }

    /**
     * Tests the getter for the MessageClass.
     */
    @Test
    public void getMessageClassTest() {
        assertEquals(AttackMessage.class, attackMessageHandler.getMessageClass());
    }
}
