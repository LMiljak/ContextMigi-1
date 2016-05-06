package com.github.migi_1.Context.serverside;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.jme3.network.Server;

/**
 * Test suite for the ServerWrapper class.
 * 
 * These are tests in its simplest forms. The tests can't get much more complicated
 * than this since somehow these tests get messed up if the server opens and closes
 * too often.
 */
public class TestServerWrapper {

	private ServerWrapper server;
	
	/**
	 * Initializes the server field before each test case
	 */
	@Before
	public void setup() {
		server = ServerWrapper.getInstance();
	}
	
	/**
	 * Helper method that loops until the server has succesfully stopped.
	 */
	private void waitForServerToStop(Server server) {
		while (server.isRunning());
	}
	
	/**
	 * Asserts that the getServer returns null if the server hasn't started yet.
	 */
	@Test
	public void testGetUnstartedServer() {
		assertNull(server.getServer());
	}
	
	/**
	 * Asserts that startServer() returns the same server getServer() returns after it has been started.
	 * @throws IOException 
	 */
	@Test
	public void testGetStartedServer() throws IOException {
		Server s = server.startServer();
		
		assertSame(s, server.getServer());
		
		server.closeServer();
		waitForServerToStop(s);
	}
	
	/**
	 * Asserts that the port on which the server was created is the correct one.
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testGetPort() throws IllegalArgumentException, IllegalAccessException {
		//Cheaty way using PowerMockito to get a private static field of a class.
		int correctPort = Whitebox.getField(ServerWrapper.class, "PORT").getInt(null); 
		
		assertEquals(correctPort, server.getPort());
	}
	
}
