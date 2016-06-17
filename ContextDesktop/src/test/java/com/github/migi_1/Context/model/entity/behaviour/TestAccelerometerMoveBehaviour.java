package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 * Test suite for the Accelerometer move behaviour.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class})
public class TestAccelerometerMoveBehaviour {

    private AccelerometerMoveBehaviour acceleroMoveBehaviour;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;

    /**
     * Setup for the tests.
     */
    @Before
    public void setUp() {
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);
        PowerMockito.mockStatic(Main.class);
        BDDMockito.given(Main.getInstance()).willReturn(main);
        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);

        acceleroMoveBehaviour = Mockito.spy(new AccelerometerMoveBehaviour(ip -> true));
    }

    /**
     * Verify the listener is added to the server.
     */
    @Test
    public void testConstructor() {
        Mockito.verify(server).addMessageListener(Mockito.any());
    }

    /**
     * Verifies the getter for the move vector works the way it should.
     */
    @Test
    public void testGetMoveVector() {
        Vector3f moveVector = acceleroMoveBehaviour.getMoveVector();
        assertEquals(Vector3f.class, moveVector.getClass());
        assertEquals(0, moveVector.getX(), 0);
        assertEquals(0, moveVector.getY(), 0);
        assertEquals(0, moveVector.getZ(), 0);
    }

    /**
     * Checks that the updateMoveVector method shouldn't change the move vector.
     */
    @Test
    public void testUpdateMoveVectorNoChange() {
        Vector3f oldMoveVector = acceleroMoveBehaviour.getMoveVector();
        acceleroMoveBehaviour.updateMoveVector();
        assertEquals(oldMoveVector, acceleroMoveBehaviour.getMoveVector());
    }

    /**
     * Checks that when a message is received and it is a correct instance,
     * the move vector is updated.
     */
    @Test
    public void testMessageReceived() {
    	HostedConnection h = Mockito.mock(HostedConnection.class);
        Vector3f oldMoveVector = acceleroMoveBehaviour.getMoveVector();
        acceleroMoveBehaviour.messageReceived(h, new AccelerometerMessage(new Vector3f(10, 10, 10)));
        //Verify the moveVector is updated.
        assertNotEquals(oldMoveVector, acceleroMoveBehaviour.getMoveVector());
    }

}
