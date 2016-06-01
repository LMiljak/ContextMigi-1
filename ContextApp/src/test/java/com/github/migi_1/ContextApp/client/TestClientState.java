/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.client;

import static org.junit.Assert.assertEquals;

import com.jme3.network.Client;
import org.junit.Before;
import org.junit.Test;

public abstract class TestClientState {
    
    private ClientState clientState;
    private Client client;
    
    @Before
    public abstract void setUp();
    
    protected void setClientState(ClientState clientState, Client client) {
        this.clientState = clientState;
        this.client = client;
    }
    
    protected ClientState getClientState() {
        return clientState;
    }
    
    @Test
    public void testGetClient() {
        assertEquals(client, clientState.getClient());
    }
    
    @Test
    public abstract void testOnActivate();
    
    @Test
    public abstract void testOnDeactivate();
}
