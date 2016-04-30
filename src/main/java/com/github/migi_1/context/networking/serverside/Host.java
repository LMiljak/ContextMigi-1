package com.github.migi_1.context.networking.serverside;

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
	
	/** Private Constructor to prevent instantiation. */
	private Host() { }
	
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
	 * Starts the Server.
	 * 
	 * @return
	 * 		The started server.
	 * @throws IOException 
	 */
	public Server startServer() throws IOException {
		server = Network.createServer(PORT);
		return server;
	}
	
	/**
	 * Closes the Server.
	 */
	public void closeServer() {
		server.close();
	}
	
	/**
	 * Gets the Server.
	 * 
	 * @return
	 * 		The wrapped server object. Null if the server isn't started.
	 */
	public Server getServer() {
		return server;
	}
	
}
