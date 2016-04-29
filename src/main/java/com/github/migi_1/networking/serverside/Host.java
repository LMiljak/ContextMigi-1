package com.github.migi_1.networking.serverside;

import java.io.IOException;

import com.jme3.network.Network;
import com.jme3.network.Server;

/**
 * A wrapper class for a com.jme3.network.Server.
 * 
 * SINGLETON class.
 */
public class Host {
	
	/** The shingleton instance of this class. */
	private static final Host INSTANCE = new Host();
	/** The port on which the server is running. */
	private static final int PORT = 4321;
	
	/** The wrapped server Object. */
	private Server server;
	
	/**
	 * Gets the instance of this shingleton class.
	 * 
	 * @return
	 * 		The instance of this shingleton class.
	 */
	public static Host getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Private Constructor to prevent instantiation.
	 * 
	 * @throws IOException 
	 */
	private Host() {
		try {
			server = Network.createServer(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the port on which the server is running.
	 * 
	 * @return
	 * 		The port on which the server is running.
	 */
	public int getPort() {
		return PORT;
	}
	
	/**
	 * Gets the wrapped server object.
	 * 
	 * @return
	 * 		The wrapped server object.
	 */
	public Server getServer() {
		return server;
	}
	
}
