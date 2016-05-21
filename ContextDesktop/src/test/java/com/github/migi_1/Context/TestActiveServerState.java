package com.github.migi_1.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.network.Server;

/**
 * Test suite for the ActiveServerState class.
 */
public class TestActiveServerState extends TestServerState {

	@Override
	public void setUp() {
		Server server = Mockito.mock(Server.class);
		
		super.setServerState(new ActiveServerState(server), server);
	}

	@Override
	public void testOnActivate() {
		super.getServerState().onActivate();
		
		//Verify that the server is being started.
		Mockito.verify(super.getServerState().getServer(), Mockito.times(1)).start();
	}

	@Override
	public void testOnDeactivate() {
		// TODO Auto-generated method stub

	}

	/**
	 * Tests that the equals method returns true when called with an equal state.
	 */
	@Test
	public void testEqualsTrue() {
		assertEquals(getServerState(), new ActiveServerState(getServerState().getServer()));
	}
	
	/**
	 * Tests that the equals method returns false when called with the InactiveServerState.
	 */
	@Test
	public void testEqualsFalseDifferentClass() {
		assertNotEquals(getServerState(), new InactiveServerState(getServerState().getServer()));
	}
	
	/**
	 * Tests that the equals method returns false when called with an ActiveServerState with a different server.
	 */
	@Test
	public void testEqualsFalseDifferentServer() {
		assertNotEquals(getServerState(), new ActiveServerState(Mockito.mock(Server.class)));
	}

}
