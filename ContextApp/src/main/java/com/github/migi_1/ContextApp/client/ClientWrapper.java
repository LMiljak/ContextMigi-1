package com.github.migi_1.ContextApp.client;

import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.EnableSprayToAppMessage;
import com.github.migi_1.ContextMessages.HealthMessage;
import com.github.migi_1.ContextMessages.HitMissMessage;
import com.github.migi_1.ContextMessages.PositionMessage;
import com.github.migi_1.ContextMessages.StartBugEventMessage;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;
import com.github.migi_1.ContextMessages.StopEventToVRMessage;
import java.io.IOException;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;

/**
 * A wrapper class for a com.jme3.network.client object.
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
            Serializer.registerClass(AttackMessage.class);
            Serializer.registerClass(HitMissMessage.class);
            Serializer.registerClass(EnableSprayToAppMessage.class);
            Serializer.registerClass(EnableSprayToVRMessage.class);
            Serializer.registerClass(HealthMessage.class);
            Serializer.registerClass(PositionMessage.class);
            Serializer.registerClass(StartBugEventMessage.class);
            Serializer.registerClass(StopEventToVRMessage.class);
            Serializer.registerClass(StopAllEventsMessage.class);
            Serializer.registerClass(ImmobilisedMessage.class);
        }
	
        /**
         * Constructor for ClientWrapper.
         * Creates a client that starts at the inactive state.
         * 
         * @param host
         *      The address of the host to which the client should connect.
         * @throws IOException 
         *      If the client failed to get created after a certain amount of attempts.
         */
	public ClientWrapper(String host) throws IOException {
            this.client = createClient(host, PORT, RESTART_ATTEMPTS);
            
            final ClientState initialState = new InactiveClientState(client);
            this.state = initialState;
        }
        
        /**
         * Creates a client and connects it to the server.
         * 
         * @param host
         *      The address of the host.
         * @param port
         *      The port of the host.
         * @param restartAttempts
         *      The amount of times the client should attempt to get created 
         *      before throwing an exception.
         * @return
         *      The created client.
         * @throws IOException 
         *      If the client failes to get created after a certain amount of attempts.
         */
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
	
        /**
         * Starts the client.
         */
        public void startClient() {
            switchState(new ActiveClientState(client));
        }
        
        /**
         * Closes the client.
         */
        public void closeClient() {
            switchState(new InactiveClientState(client));
        }
        
        /**
         * Switches the current state of the client to a new state.
         * 
         * @param newState
         *      The new state of the client.
         */
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
