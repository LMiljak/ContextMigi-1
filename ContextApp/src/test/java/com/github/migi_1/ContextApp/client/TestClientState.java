package com.github.migi_1.ContextApp.client;

import static org.junit.Assert.assertEquals;

import com.jme3.network.Client;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract test suite for the ClienState class.
 */
public abstract class TestClientState {
    
    private ClientState clientState;
    private Client client;
    
    /**
     * Initialises the test objects. For subclasses that implement this, use
     * the setClientState method.
     */
    @Before
    public abstract void setUp();
    
    /**
     * Sets the test objects for this test suite.
     * 
     * @param clientState
     *      The clientState.
     * @param client 
     *      The client used to create the clientState.
     */
    protected void setClientState(ClientState clientState, Client client) {
        this.clientState = clientState;
        this.client = client;
    }
    
    /**
     * Gets the ClientState test object.
     * 
     * @return
     *      The ClientState test object.
     */
    protected ClientState getClientState() {
        return clientState;
    }
    
    /**
     * Tests the getClient method.
     */
    @Test
    public void testGetClient() {
        assertEquals(client, clientState.getClient());
    }
    
    /**
     * Tests the onActivate method.
     */
    @Test
    public abstract void testOnActivate();
    
    /**
     * Tests the onDeactivate method.
     */
    @Test
    public abstract void testOnDeactivate();
}
