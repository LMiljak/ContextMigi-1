package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.jme3.network.AbstractMessage;
import java.io.IOException;
import java.util.Arrays;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.util.List;

/**
 * A wrapper class for a com.jme3.network.client object.
 * 
 * SHINGLETON class.
 */
public class ClientWrapper {
	
        /** The message the client should be able to handle. */
	private static final List<Class<? extends AbstractMessage>> MESSAGE_TYPES 
            = Arrays.asList(
		AccelerometerMessage.class,
                //, More message types here
                AbstractMessage.class //This abstract message doesn't need to be handled, but
                //is required for dumb Java 1.7 type inference.
		);
    
	/** The shingleton instance of this class. */
	private static final ClientWrapper INSTANCE = new ClientWrapper();
	/** The default port on which servers are running. */
	private static final int PORT = 4321;
	
	/** The wrapped client Object. */
	private Client client;
        
	//Every message types is registered by the Serializer in this class initializer.
        static {
            for (Class<? extends AbstractMessage> messageType : MESSAGE_TYPES) {
			Serializer.registerClass(messageType);
            }
        }
        
	/**
	 * Gets the instance of this shingleton class.
	 * 
	 * @return
	 * 		The instance of this shingleton class.
	 */
	public static ClientWrapper getInstance() {
		return INSTANCE;
	}
	
	/** Private Constructor to prevent instantiation. */
	private ClientWrapper() { }
	
	/**
	 * Starts and connects the client to a server.
	 * 
	 * @param host
	 * 		The ip address of the host.
	 * @return
	 * 		The created Client object.
	 * @throws IOException
	 * @throws IllegalStateException if the client has already started.
	 */
	public void startClient(String host) throws IOException, IllegalStateException {
		if (client == null) {
			client = Network.connectToServer(host, PORT);
			client.start();
		} else {
			throw new IllegalStateException("Client has already been started");
		}
	}
	
	/**
	 * Closes the Client.
	 */
	public void closeClient() {
		if (client != null) {
			client.close();
			client = null;
		} else {
			throw new IllegalStateException("Client has already been closed");
		}
	}
	
	/**
	 * Gets the Client.
	 * 
	 * @return
	 * 		The wrapped Client object. Null if the Client isn't running.
	 */
	public Client getClient() {
		return client;
	}
}
