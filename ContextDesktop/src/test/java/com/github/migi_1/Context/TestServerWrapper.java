package com.github.migi_1.Context;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
	
	private Server server;
	
	/**
	 * Initialises the attributes in this class used for testing.
	 */
	@Before
	public void setUp() {
		server = Mockito.mock(Server.class);
		
		PowerMockito.mockStatic(Network.class);
		try {
			//Instead of actually creating a server, which Travis really doesn't like, 
			//we mock the Network class to return our fake server.
			Mockito.when(Network.createServer(PORT)).thenReturn(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resets the fields in the ServerWrapper class at the end
	 * of each test. This is because otherwise the other in which tests are executed
	 * my affect for example the testGetInstanceNotInitialised(), because the class may
	 * have been initialised by another test.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@After
	public void resetFields() throws IllegalArgumentException, IllegalAccessException {
		Field initialised = Whitebox.getField(ServerWrapper.class, "initialised");
		
		if (initialised.getBoolean(null)) { //If the ServerWrapper has been initialised, we need to 'uninitialise' it.
			Whitebox.getField(ServerWrapper.class, "server").set(ServerWrapper.getInstance(), null);
			Whitebox.getField(ServerWrapper.class, "state").set(ServerWrapper.getInstance(), null);
			initialised.set(null, Boolean.FALSE);
		}
	}
	
	/**
	 * Checks that the getInstance method throws an IllegalStateException if the class hasn't been initialised yet.
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetInstanceNotInitialised() {
		ServerWrapper.getInstance();
	}
	
	/**
	 * Asserts that the getServer method returns the correct server.
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testGetServer() throws IOException {
		ServerWrapper.initialize();
		
		assertEquals(server, ServerWrapper.getInstance().getServer());
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
		ServerWrapper.initialize();
		
		ServerWrapper wrapper = ServerWrapper.getInstance();
		wrapper.startServer();
		
		verifyStartsAndCloses(1, 0);
	}
	
	/**
	 * Asserts that the closeServer does nothing because the server is already closed.
	 * @throws IOException if the initialisation of the server failed.
	 */
	@Test
	public void testCloseClosedServer() throws IOException {
		ServerWrapper.initialize();
		
		ServerWrapper wrapper = ServerWrapper.getInstance();
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
		ServerWrapper.initialize();
		
		ServerWrapper wrapper = ServerWrapper.getInstance();
		wrapper.startServer();
		wrapper.closeServer();
		
		verifyStartsAndCloses(1, 1);
	}
}
