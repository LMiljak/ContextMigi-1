package com.github.migi_1.ContextApp.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
    
    /**
     * Initialises the test object for this test suite.
     * 
     * @throws IOException  
     */
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
    
    /**
     * Tests the getClient method.
     */
    @Test
    public void testGetClient() {
        assertEquals(client, clientWrapper.getClient());
    }
    
    /**
     * Verifies that the client.start() and client.close() methods
     * have been called a certain amount of times.
     * 
     * @param starts
     *      The amount of times the start method should have been called.
     * @param closes 
     *      The amount of times the close method should have been called.
     */
    private void verifyStartsAndCloses(int starts, int closes) {
        Mockito.verify(client, Mockito.times(starts)).start();
        Mockito.verify(client, Mockito.times(closes)).close();
    }
    
    /**
     * Asserts that the startServer method actually starts the server.
     */
    @Test
    public void testStartClient() {
        clientWrapper.startClient();
        
        verifyStartsAndCloses(1, 0);
    }
    
    /**
     * Asserts that the closeServer method does nothing when it's already closed.
     */
    @Test
    public void testCloseClosedClient() {
        clientWrapper.closeClient();
        
        verifyStartsAndCloses(0, 0);
    }
    
    /**
     * Asserts that Starting then Closing actually starts then closes the server.
     */
    @Test
    public void testStartCloseClient() {
        clientWrapper.startClient();
        clientWrapper.closeClient();
        
        verifyStartsAndCloses(1, 1);
    }
    
    /**
     * Asserts that starting the client twice does nothing the second time.
     */
    @Test
    public void testStartStartClient() {
        clientWrapper.startClient();
        clientWrapper.startClient();
        
        verifyStartsAndCloses(1, 0);
    }
    
    /**
     * Asserts that starting, then closing, then starting, then closing
     * actually starts the client twice and closes it twice.
     */
    @Test
    public void testStartCloseStartCloseClient() {
        clientWrapper.startClient();
        clientWrapper.closeClient();
        clientWrapper.startClient();
        clientWrapper.closeClient();
        
        verifyStartsAndCloses(2, 2);
    }
    
}
