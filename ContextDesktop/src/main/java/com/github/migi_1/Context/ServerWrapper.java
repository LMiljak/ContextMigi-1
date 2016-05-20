package com.github.migi_1.Context;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.jme3.network.AbstractMessage;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;

/**
 * A wrapper class for a com.jme3.network.Server.
 *
 * SINGLETON class.
 */
public final class ServerWrapper {

	/** The message the server should be able to handle. */
	private static final List<Class<? extends AbstractMessage>> MESSAGE_TYPES
		= Arrays.asList(
				//Message types here
				);

	/** The singleton instance of this class. */
	private static final ServerWrapper INSTANCE = new ServerWrapper();
	/** The port on which the server is running. */
	private static final int PORT = 4321;

	/** The wrapped server Object. */
	private Server server;

	//Every message types is registered by the Serializer in this class initializer.
	static {
		for (Class<? extends AbstractMessage> messageType : MESSAGE_TYPES) {
			Serializer.registerClass(messageType);
		}
	}

	/**
	 * Gets the instance of this singleton class.
	 *
	 * @return
	 * 		The instance of this singleton class.
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
	 * @throws IOException if the server can't be created with the specified port.
	 * @throws IllegalStateException if the server is already running.
	 */
	public void startServer() throws IOException, IllegalStateException {
		if (server == null) {
			server = Network.createServer(PORT);
			server.start();

		} else {
			throw new IllegalStateException("Server is already running");
		}
	}

	/**
	 * Closes the Server.
	 *
	 * @throws IllegalStateException if the server is not running.
	 */
	public void closeServer() throws IllegalStateException {
		if (server != null) {
			server.close();
			server = null;
		} else {
			throw new IllegalStateException("Server has already stopped");
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
