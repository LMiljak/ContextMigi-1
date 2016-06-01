package com.github.migi_1.ContextApp.client;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.jme3.network.Client;
import com.jme3.network.Network;
import org.junit.Before;

/**
 * Test suite for the ClientWrapper class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Network.class)
public class TestClientWrapper {

    private ClientWrapper clientWrapper;
    private Client client;
    
    @Before
    public void setUp() throws IOException {
        client = Mockito.mock(Client.class);
        
        PowerMockito.mockStatic(Network.class);
        try {
            Mockito.when(Network.connectToServer(Mockito.anyString(), Mockito.anyInt())).thenReturn(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        clientWrapper = new ClientWrapper("");
    }
    
    @Test
    public void testGetClient() {
        assertEquals(client, clientWrapper.getClient());
    }
    
    private void verifyStartsAndCloses(int starts, int closes) {
        Mockito.verify(client, Mockito.times(starts)).start();
        Mockito.verify(client, Mockito.times(closes)).close();
    }
    
    @Test
    public void testStartClient() {
        clientWrapper.startClient();
        
        verifyStartsAndCloses(1, 0);
    }
    
    @Test
    public void testCloseClosedClient() {
        clientWrapper.closeClient();
        
        verifyStartsAndCloses(0, 0);
    }
    
    @Test
    public void testStartCloseClient() {
        clientWrapper.startClient();
        clientWrapper.closeClient();
        
        verifyStartsAndCloses(1, 1);
    }
    
    @Test
    public void testStartStartClient() {
        clientWrapper.startClient();
        clientWrapper.startClient();
        
        verifyStartsAndCloses(1, 0);
    }
    
    @Test
    public void testStartCloseStartCloseClient() {
        clientWrapper.startClient();
        clientWrapper.closeClient();
        clientWrapper.startClient();
        clientWrapper.closeClient();
        
        verifyStartsAndCloses(2, 2);
    }
    
}
