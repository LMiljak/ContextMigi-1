package com.github.migi_1.ContextApp.client;

import com.github.migi_1.ContextMessages.AccelerometerMessage;
import java.io.IOException;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;

/**
 * A wrapper class for a com.jme3.network.client object.
 * 
 * SINGLETON class.
 */
public class ClientWrapper {
    
	/** The default port on which servers are running. */
	private static final int PORT = 4321;
        /** The amount of times the client should restart before sending an error.*/
        private static final int RESTART_ATTEMPTS = 10;
	
	private Client client;
        private ClientState state;
        
	//Every message types is registered by the Serializer in this class initializer.
        static {
            Serializer.registerClass(AccelerometerMessage.class);
        }
	
	public ClientWrapper(String host) throws IOException {
            this.client = createClient(host, PORT, RESTART_ATTEMPTS);
            
            final ClientState initialState = new InactiveClientState(client);
            this.state = initialState;
        }
        
        private Client createClient(String host, int port, int restartAttempts) throws IOException {
            IOException exception = null;
            
            for (int attempt = 1; attempt <= restartAttempts; attempt++) {
                try {
                    Client result = Network.connectToServer(host, port);
                    return result;
                } catch (IOException e) {
                    exception = e;
                }
            }
            
            throw new IOException("Failed create client after " + restartAttempts + "attempts"
                    + ": " + exception.getMessage());
        }
	
        public void startClient() {
            switchState(new ActiveClientState(client));
        }
        
        public void closeClient() {
            switchState(new InactiveClientState(client));
        }
        
        private void switchState(ClientState newState) {
            if (!newState.equals(state)) {
                state.onDeactivate();
                state = newState;
                state.onActivate();
            }
        }
        
	/**
	 * Gets the Client.
	 * 
	 * @return
	 *      The wrapped Client object. Null if the Client isn't running.
	 */
	public Client getClient() {
            return client;
	}
}
