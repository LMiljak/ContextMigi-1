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
 * SINGLETON class.
 */
public class ClientWrapper {
    
	/** The shingleton instance of this class. */
	private static final ClientWrapper INSTANCE = new ClientWrapper();
	/** The default port on which servers are running. */
	private static final int PORT = 4321;
	
	/** The wrapped client Object. */
	private Client client;
        
	//Every message types is registered by the Serializer in this class initializer.
        static {
            Serializer.registerClass(AccelerometerMessage.class);
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
