package com.github.migi_1.Context.serverside;

import java.io.IOException;

import com.jme3.network.Network;
import com.jme3.network.Server;

/**
 * A wrapper class for a com.jme3.network.Server.
 * 
 * SINGLETON class.
 */
public class ServerWrapper {
	
	/** The shingleton instance of this class. */
	private static final ServerWrapper INSTANCE = new ServerWrapper();
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
	public static ServerWrapper getInstance() {
		return INSTANCE;
	}
	
	/** Private Constructor to prevent instantiation. */
	private ServerWrapper() { }
	
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
	 * @throws IOException 
	 */
	public void startServer() throws IOException {
		if (server == null) {
			server = Network.createServer(PORT);
			server.start();
		}
	}
	
	/**
	 * Closes the Server.
	 */
	public void closeServer() {
		if (server != null) {
			server.close();
			server = null;
		}
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
