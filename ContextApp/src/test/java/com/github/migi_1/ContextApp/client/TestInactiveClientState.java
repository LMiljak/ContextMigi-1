package com.github.migi_1.ContextApp.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.jme3.network.Client;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test suite for the InactiveClientState class.
 */
public class TestInactiveClientState extends TestClientState {

    @Override
    public void setUp() {
        Client client = Mockito.mock(Client.class);
        super.setClientState(new InactiveClientState(client), client);
    }

    @Override
    public void testOnActivate() {
        super.getClientState().onActivate();
        
        Mockito.verify(getClientState().getClient(), Mockito.times(1)).close();
    }

    @Override
    public void testOnDeactivate() { }
    
    /**
     * Tests the equals method that it returns true with an equal object.
     */
    @Test
    public void testEqualsTrue() {
        assertEquals(getClientState(), new InactiveClientState(getClientState().getClient()));
    }
    
    /**
     * Tests the equals method that it returns false with a different state type.
     */
    @Test
    public void testEqualsFalseDifferentClass() {
        assertNotEquals(getClientState(), new ActiveClientState(getClientState().getClient()));
    }
    
    /**
     * Tests the equals method that it returns false with a different client.
     */
    @Test
    public void testEqualsFalseDifferentClient() {
        assertNotEquals(getClientState(), new InactiveClientState(Mockito.mock(Client.class)));
    }
}
