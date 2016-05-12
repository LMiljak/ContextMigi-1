package com.github.migi_1.ContextApp;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;

/**
 * Connects a Client automatically to the first Server found
 * on the LAN.
 * 
 * SHINGLETON class.
 */
public class AutoConnector {
    
    private static final AutoConnector INSTANCE = new AutoConnector();
    
    /**
     * Gets the instance of this class.
     * 
     * @return 
     *      The instance of this class.
     */
    public static AutoConnector getInstance() {
        return INSTANCE;
    }
    
    /** Private constructor to prevent initialization. */
    private AutoConnector() { }
    
    /**
     * Automatically starts the  client by connecting it to the
     * first Server found on this LAN.
     * 
     * @param executorService
     *      On which executorService the server finder should be executed.
     * @param client 
     *      The client that should be started.
     */
    public void autoStart(ExecutorService executorService, ClientWrapper client) {
        ServerFinder serverFinder = ServerFinder.getInstance();
        
        serverFinder.findServers(executorService, getConnector(client));
    }
    
    /**
     * @param client 
     *      The client that should be started on servery discovery.
     * @return
     *      A ServerDiscoveryHandler that connects the client to a server
     *      that has been discovered.
     */
    private ServerDiscoveryHandler getConnector(final ClientWrapper client) {
        return new ServerDiscoveryHandler() {
            @Override
            public void onServerDiscovery(InetAddress server) {
                try {
                    client.startClient(server.getHostAddress());
                    ServerFinder.getInstance().stop();
                } catch (Exception e) {
                    e.printStackTrace();
                    //Connection failed. Trying again.
                    onServerDiscovery(server);
                }
            }
        };
    }
    
}
