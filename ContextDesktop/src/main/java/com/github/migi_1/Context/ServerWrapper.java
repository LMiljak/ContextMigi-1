package com.github.migi_1.Context;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.migi_1.ContextMessages.AccelerometerMessage;

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
				AccelerometerMessage.class
				//, more message types here
				);

	/** The singleton instance of this class. */
	private static final ServerWrapper INSTANCE = new ServerWrapper();
	/** The port on which the server is running. */
	private static final int PORT = 4321;
	/** The amount of times the server should restart before sending an error.*/
	private static final int RESTART_ATTEMPTS = 10;
	
	
	private static boolean initialised = false;
	
	private Server server;
	private ServerState state;

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
	 * @throws IllegalStateException if the class has not yet been initialised.
	 */
	public static ServerWrapper getInstance() throws IllegalStateException {
		if (!initialised) {
			throw new IllegalStateException("The server has not yet been initialised.");
		}
		return INSTANCE;
	}
	
	/**
	 * Initialises the Server.
	 * 
	 * @throws IOException if the server failed to get created after a certain amount of attempts.
	 * @throws IllegalStateException if this class has already been initialised.
	 */
	public static synchronized void initialize() throws IOException, IllegalStateException {
		INSTANCE.createServer();
		INSTANCE.state = new InactiveServerState(INSTANCE.server);
		
		initialised = true;
	}

	/** Private constructor to prevent initialisation. */
	private ServerWrapper() { }

	/**
	 * Creates the server.
	 * 
	 * @throws IOException if the server failed to get created after a certain amount of attempts.
	 */
	private void createServer() throws IOException {
		Logger logger = Logger.getGlobal();
		
		for (int attempt = 1; attempt <= RESTART_ATTEMPTS; attempt++) {
			try {
				server = Network.createServer(PORT);
				logger.log(Level.INFO, "Successfully created a server on port " + PORT + ".");
				return;
			} catch (IOException e) {
				logger.log(Level.WARNING, "Failed to create server: " + e.getMessage() + ". Retrying.");
			}
		}
		
		final String failMessage = "Failed to create server after " + RESTART_ATTEMPTS + " attempts";
		logger.log(Level.SEVERE, failMessage);
		
		throw new IOException(failMessage);
	}
	
	/**
	 * Gets the wrapped Server.
	 * This server can be used to for example: send messages.
	 * 
	 * @return
	 * 		The wrapped Server.
	 */
	public Server getServer() {
		return server;
	}
	
	/**
	 * Starts the server.
	 */
	public void startServer() {
		switchState(new ActiveServerState(server));
	}
	
	/**
	 * Closes the server.
	 */
	public void closeServer() {
		
	}
	
	/**
	 * Switches the current state to a new state.
	 * 
	 * @param newState
	 * 		The new state of the server.
	 */
	private void switchState(ServerState newState) {
		state.onDeactivate();
		state = newState;
		state.onActivate();
	}
}
