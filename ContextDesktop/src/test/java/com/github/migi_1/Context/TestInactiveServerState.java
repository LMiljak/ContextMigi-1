package com.github.migi_1.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.network.Server;

/**
 * Test suite for the InactiveServerState class.
 */
public class TestInactiveServerState extends TestServerState {

	@Override
	public void setUp() {
		Server server = Mockito.mock(Server.class);
		
		super.setServerState(new InactiveServerState(server), server);
	}

	@Override
	public void testOnActivate() {
		super.getServerState().onActivate();
		
		//Verify that the server is being closed.
		Mockito.verify(super.getServerState().getServer(), Mockito.times(1)).close();
	}

	@Override
	public void testOnDeactivate() { }

	/**
	 * Tests that the equals method returns true when called with an equal state.
	 */
	@Test
	public void testEqualsTrue() {
		assertEquals(getServerState(), new InactiveServerState(getServerState().getServer()));
	}
	
	/**
	 * Tests that the equals method returns false when called with the ActiveServerState.
	 */
	@Test
	public void testEqualsFalseDifferentClass() {
		assertNotEquals(getServerState(), new ActiveServerState(getServerState().getServer()));
	}
	
	/**
	 * Tests that the equals method returns false when called with an InActiveServerState with a different server.
	 */
	@Test
	public void testEqualsFalseDifferentServer() {
		assertNotEquals(getServerState(), new InactiveServerState(Mockito.mock(Server.class)));
	}
}
