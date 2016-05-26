package com.github.migi_1.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jme3.network.Network;
import com.jme3.network.Server;

/**
 * Test suite for the ServerWrapper class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Network.class)
public class TestServerWrapper {

	/** The port on which the server is running. */
	private static final int PORT = 4321;
	
	private ServerWrapper wrapper;
	private Server server;
	
	/**
	 * Initialises the attributes in this class used for testing.
	 * @throws IOException 
	 */
	@Before
	public void setUp() throws IOException {
		server = Mockito.mock(Server.class);
		
		PowerMockito.mockStatic(Network.class);
		try {
			//Instead of actually creating a server, which Travis really doesn't like, 
			//we mock the Network class to return our fake server.
			Mockito.when(Network.createServer(PORT)).thenReturn(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		wrapper = new ServerWrapper();
	}
	
	/**
	 * Asserts that the getServer method returns the correct server.
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testGetServer() throws IOException {
		assertEquals(server, wrapper.getServer());
	}
	
	/**
	 * Verifies that the server.start() method and server.close() method
	 * have been called a several amount of times.
	 * 
	 * @param starts
	 * 		The amount of times the server.start method should have been called.
	 * @param closes
	 * 		The amount of times the server.close method should have been called.
	 */
	private void verifyStartsAndCloses(int starts, int closes) {
		Mockito.verify(server, Mockito.times(starts)).start();
		Mockito.verify(server, Mockito.times(closes)).close();
	}
	
	/**
	 * Asserts that the startServer method actually starts the server.
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testStartServer() throws IOException {
		wrapper.startServer();
		
		verifyStartsAndCloses(1, 0);
	}
	
	/**
	 * Asserts that the closeServer does nothing because the server is already closed.
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testCloseClosedServer() throws IOException {
		wrapper.closeServer();
		
		verifyStartsAndCloses(0, 0);
	}
	
	/**
	 * Assert that after starting and then closing the server:
	 * 		start has been called 1 time.
	 * 		close has been called 1 time.
	 * 
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testStartCloseServer() throws IOException {
		wrapper.startServer();
		wrapper.closeServer();
		
		verifyStartsAndCloses(1, 1);
	}
	
	/**
	 * Assert that after starting and then starting it again the server:
	 * 		start has been called 1 time.
	 * 		close has been called 0 times.
	 * 
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testStartStartServer() throws IOException {
		wrapper.startServer();
		wrapper.startServer();
		
		verifyStartsAndCloses(1, 0);
	}
	
	/**
	 * Assert that after starting, closing, starting and closing again it again, the server:
	 * 		start has been called 2 times.
	 * 		close has been called 2 times.
	 * 
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testStartCloseStartCloseServer() throws IOException {
		wrapper.startServer();
		wrapper.closeServer();
		wrapper.startServer();
		wrapper.closeServer();
		
		verifyStartsAndCloses(2, 2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFailedToCreateServer() throws IOException {
		Mockito.when(Network.createServer(PORT)).thenThrow(IOException.class);
		
		final int restartAttempts = 10;
		
		try {
			new ServerWrapper();
			fail();
		} catch (IOException e) {
			PowerMockito.verifyStatic(Mockito.times(restartAttempts + 1)); // + 1`because we create another ServerWrapper in
																		  //the setup method.
			Network.createServer(Mockito.anyInt());
		}
	}
}
