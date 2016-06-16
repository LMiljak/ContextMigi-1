package com.github.migi_1.Context.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jme3.network.Server;

/**
 * Test suite for the ServerState class.
 * This class should be extended by every test suite of a class that also extends the ServerState class.
 */
public abstract class TestServerState {

	private ServerState serverState;
	private Server server;

	/**
	 * Initialises the serverState attribute. This can be done through the setServerState method.
	 */
	@Before
	public abstract void setUp();

	/**
	 * Sets the serverState attribute that is used for testing.
	 *
	 * @param serverState
	 * 		The new serverState attribute used for testing.
	 * @param server
	 * 		The supposed server of this serverState.
	 */
	protected void setServerState(ServerState serverState, Server server) {
		this.serverState = serverState;
		this.server = server;
	}

	/**
	 * Gets the serverState object used for testing.
	 *
	 * @return
	 * 		The serverState object used for testing.
	 */
	protected ServerState getServerState() {
		return serverState;
	}

	/**
	 * Tests the getServer method.
	 */
	@Test
	public void testGetServer() {
		assertEquals(server, serverState.getServer());
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
