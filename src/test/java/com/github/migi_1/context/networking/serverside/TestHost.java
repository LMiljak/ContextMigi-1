package com.github.migi_1.context.networking.serverside;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jme3.network.Client;
import com.jme3.network.Network;

/**
 * A test suite for the Host class.
 */
public class TestHost {

	/** The test subject. */
	private Host host;
	
	/**
	 * Initialises the host before every test case.
	 */
	@Before
	public void setup() {
		host = Host.getInstance();
	}
	
	/**
	 * Cleans the host up after every test case.
	 */
	@After
	public void cleanup() {
		host.closeServer();
	}
	
	/**
	 * Tests to make sure the server hasn't started yet.
	 */
	@Test
	public void testNotStartedServer() {
		try {
			Network.connectToServer("localhost", host.getPort());
			fail(); //The server hasn't started yet, so we should fail if no exception was raised when trying to connect.
		} catch (IOException e) { /* We failed to connect, woohoo! */}
	}
	
	/**
	 * Tests to make sure that the server has succesfully started with the correct port.
	 */
	@Test
	public void testStartServer() {
		int port = host.getPort();
		
		try {
			host.startServer();
			Network.connectToServer("localhost", port);
		} catch (IOException e) {
			fail();
		}
	}
}
