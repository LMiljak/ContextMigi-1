package com.github.migi_1.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.jme3.network.Server;

/**
 * Test suite for the ServerWrapper class.
 */
public class TestServerWrapper {

	/**
	 * Tests the following states of the serverwrapper in order:
	 *   1. Asserts that getServer returns null.
	 *   2. Calls the startServer method.
	 *   3. Asserts that getServer does not return null.
	 *   4. Asserts that the server is running.
	 *   5. Asserts that calling the startServer method again gives an Exception.
	 *   6. Stops the server.
	 *   7. Asserts that the server is no longer running.
	 *   8. Asserts that getServer returns null.
	 *   9. Asserts that calling the closeServer method again gives an Exception.
	 *
	 * @throws IOException
	 */
	@Test
	public void testServerWrapper() throws IOException {
		ServerWrapper server = ServerWrapper.getInstance();

		testGetNotStartedServer(server);
		testStartServer(server);
		testStartStartedServer(server);
		testCloseServer(server);
		testGetNotStartedServer(server);
		testCloseClosedServer(server);
	}

	/**
	 * Asserts that the getServer method returns null.
	 *
	 * @param server
	 * 		A not-started or stopped serverwrapper.
	 */
	private void testGetNotStartedServer(ServerWrapper server) {
		assertNull(server.getServer());
	}

	/**
	 * Starts the server and asserts that the getServer method
	 * returns the same server object as the startServer method.
	 * Also asserts that the server is running after the startServer call.
	 *
	 * @param server
	 * 		A not-started serverwrapper.
	 * @throws IOException
	 */
	private void testStartServer(ServerWrapper server) throws IOException {
		server.startServer();
		assertTrue(server.getServer().isRunning());
	}

	/**
	 * Asserts that the startServer method throws an IllegalStateException
	 * if the server is already running.
	 *
	 * @param server
	 * 		The running serverwrapper.
	 * @throws IOException
	 */
	private void testStartStartedServer(ServerWrapper server) throws IOException {
		try {
			server.startServer();
			fail();
		} catch (IllegalStateException e) {
		    System.out.println(e.getStackTrace());
		}
	}

	/**
	 * Asserts that the closeServer method stops the server.
	 *
	 * @param server
	 * 		The started serverwrapper.
	 */
	private void testCloseServer(ServerWrapper server) {
		Server s = server.getServer();

		server.closeServer();

		assertFalse(s.isRunning());
	}

	/**
	 * Asserts that the stopServer method throws an IllegalStateException
	 * if the server is not already running.
	 *
	 * @param server
	 * 		The closed serverwrapper.
	 * @throws IOException
	 */
	private void testCloseClosedServer(ServerWrapper server) throws IOException {
		try {
			server.closeServer();
			fail();
		} catch (IllegalStateException e) {
		    System.out.println(e.getStackTrace());
		}
	}

	/**
	 * Asserts that getPort returns the same port on which the
	 * the server should be running.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testGetPort() throws IllegalArgumentException, IllegalAccessException {
		//Cheaty way of getting private static fields using PowerMockito.
		int expectedPort = Whitebox.getField(ServerWrapper.class, "PORT").getInt(null);

		int actualPort = ServerWrapper.getInstance().getPort();

		assertEquals(expectedPort, actualPort);
	}
}
