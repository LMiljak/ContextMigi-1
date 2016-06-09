package com.github.migi_1.Context.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.github.migi_1.ContextMessages.HealthMessage;
import com.github.migi_1.ContextMessages.PositionMessage;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;
import com.jme3.network.AbstractMessage;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;

/**
 * A wrapper class for a com.jme3.network.Server.
 */
public class ServerWrapper {

	/** The message the server should be able to handle. */
	private static final List<Class<? extends AbstractMessage>> MESSAGE_TYPES
		= Arrays.asList(
				AccelerometerMessage.class,
//				EnableSprayToAppMessage.class,
				EnableSprayToVRMessage.class,
//				StartBugEventMessage.class,
				StopEventToVRMessage.class,
				StopAllEventsMessage.class,
				PositionMessage.class,
                HealthMessage.class,
                AttackMessage.class
				);

	/** The port on which the server is running. */
	private static final int PORT = 4321;
	/** The amount of times the server should restart before sending an error.*/
	private static final int RESTART_ATTEMPTS = 10;

	private Server server;
	private ServerState state;

	//Every message types is registered by the Serializer in this class initializer.
	static {
		for (Class<? extends AbstractMessage> messageType : MESSAGE_TYPES) {
			Serializer.registerClass(messageType);
		}
	}

	/**
	 * Constructor for ServerWrapper.
	 * Creates a server that starts inactive.
	 *
	 * @throws IOException
	 * 		If the Server failed to get created after a certain amount of attempts.
	 */
	public ServerWrapper() throws IOException {
		this.server = createServer(PORT, RESTART_ATTEMPTS);

		final ServerState initialState = new InactiveServerState(server);
		this.state = initialState;
	}

	/**
	 * Creates a server.
	 *
	 * @param port
	 * 		The port on which the server should be running.
	 * @param restartAttempts
	 * 		The amount of times the server is allowed to fail to start
	 * 		until an exception is thrown.
	 * @throws IOException if the server failed to get created after a certain amount of attempts.
	 * @return The created server.
	 */
	private Server createServer(int port, int restartAttempts) throws IOException {
		Logger logger = Logger.getGlobal();

		for (int attempt = 1; attempt <= restartAttempts; attempt++) {
			try {
				Server server = Network.createServer(port);
				logger.log(Level.INFO, "Successfully created a server on port " + port + ".");
				return server;
			} catch (IOException e) {
				logger.log(Level.WARNING, "Failed to create server: " + e.getMessage() + ". Retrying.");
			}
		}

		final String failMessage = "Failed to create server after " + restartAttempts + " attempts";
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
		switchState(new InactiveServerState(server));
	}

	/**
	 * Switches the current state to a new state.
	 *
	 * @param newState
	 * 		The new state of the server.
	 */
	private void switchState(ServerState newState) {
		if (!newState.equals(state)) {
			state.onDeactivate();
			state = newState;
			state.onActivate();
		}
	}
}
