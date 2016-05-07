package com.github.migi_1.Context.clientside;

import static org.junit.Assert.assertNull;
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

import com.github.migi_1.Context.serverside.ServerWrapper;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.Server;

/**
 * Test suite for the ClientWrapper class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Network.class)
public class TestClientWrapper {

	/**
	 * Executes the following instructions in order:
	 *   1. Starts a server on the same port as a server from
	 *      the ServerWrapper class would run on.
	 *   2. Asserts that getClient returns null.
	 *   3. Starts the client.
	 *   4. Asserts that the client has successfully been started,
	 *      connected and that the server knows it has been connected.
	 *   5. Asserts that calling startClient again throws an
	 *      IllegalStateException.
	 *   6. Closes the client.
	 *   7. Asserts that the client is no longer started and no 
	 *      longer connected to the server.
	 *   8. Asserts that getClient returns null.
	 *   9. Asserts that calling closeClient throws an IllegalStateException. 
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@Test(timeout = 1000)
	public void testClientWrapper() throws IllegalArgumentException, IllegalAccessException, IOException {
		final int fakePort = 42;
		int actualPort = Whitebox.getField(ServerWrapper.class, "PORT").getInt(null);
		
		Server server = Network.createServer(fakePort);
		server.start();
		
		Client fakeClient = Network.connectToServer("localhost", fakePort);
		
		PowerMockito.mockStatic(Network.class);
		
		//Making sure that when a client is created, it connects to the fake port we are using.
		//Otherwise bad things will happen during testing.
		Mockito.when(Network.connectToServer("localhost", actualPort)).thenReturn(fakeClient);
		
		ClientWrapper client = ClientWrapper.getInstance();
		
		testGetNotStartedClient(client);
		testStartClient(client, server);
		testStartStartedClient(client);
		testCloseClient(client, server);
		testGetNotStartedClient(client);
		testCloseClosedClient(client);
	}
	
	/**
	 * Asserts that getClient returns null if the client hasn't started yet.
	 * 
	 * @param client
	 * 		The non-started clientwrapper.
	 */
	private void testGetNotStartedClient(ClientWrapper client) {
		assertNull(client.getClient());
	}
	
	/**
	 * Starts the client and asserts that the client has successfully started,
	 * and connected to the right server.
	 * 
	 * @param client
	 * 		The non-started clientwrapper.
	 * @param server
	 * 		The server running on this localhost.
	 * @throws IOException 
	 */
	private void testStartClient(ClientWrapper client, Server server) throws IOException {
		assertFalse(server.hasConnections());
		
		client.startClient("localhost");
		
		assertTrue(client.getClient().isStarted());
		while (!client.getClient().isConnected()); //Waiting for the client to connect. If it fails to do so
			//within one second (the given timeout), the test fails
		assertTrue(server.hasConnections());
	}
	
	/**
	 * Asserts that the startClient method throws an IllegalStateException 
	 * if the client has already started.
	 * 
	 * @param client
	 * 		The started clientwrapper.
	 * @throws IOException 
	 */
	private void testStartStartedClient(ClientWrapper client) throws IOException {
		try {
			client.startClient("localhost");
			fail();
		} catch (IllegalStateException e) { }
	}
	
	/**
	 * Calls the closeClient method and asserts that the client has been closed.
	 * 
	 * @param client
	 * 		The started clientwrapper.
	 * @param server
	 * 		The server that is connected to the client.
	 */
	private void testCloseClient(ClientWrapper client, Server server) {
		Client c = client.getClient();
		
		client.closeClient();
		
		assertFalse(c.isStarted());
		assertFalse(c.isConnected());
		while (!server.hasConnections()); //Waiting for the server to know that the
			//client has disconnected. This fails if the timeout (one second) has been reached.
	}
	
	/**
	 * Asserts that the closeClient method throws an IllegalStateException
	 * if the client isn't started.
	 * 
	 * @param client
	 * 		The closed clientwrapper.
	 */
	private void testCloseClosedClient(ClientWrapper client) {
		try {
			client.closeClient();
			fail();
		} catch (IllegalStateException e) { }
	}
}
