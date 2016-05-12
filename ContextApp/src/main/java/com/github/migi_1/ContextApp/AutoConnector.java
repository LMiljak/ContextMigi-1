package com.github.migi_1.ContextApp;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;

/**
 * Connects a Client automatically to the first Server found
 * on the LAN.
 */
public class AutoConnector {
    
    private final ExecutorService executorService;
    private final ClientWrapper client;
    
    /**
     * Constructor for AutoConnector.
     * 
     * @param executorService
     *      Used to run this class on a seperate thread, otherwise
     *      the application will get stuck in an infinite loop if
     *      no Servers are found.
     * @param client
     *      The client to automatically connect to.
     */
    public AutoConnector(ExecutorService executorService, ClientWrapper client) {
        this.executorService = executorService;
        this.client = client;
    }
    
    /**
     * Automatically starts the  client by connecting it to the
     * first Server found on this LAN. This method is run on a seperate
     * Thread by the ExecutorService provided in the constructor.
     */
    public void autoStart() {
        ServerFinder serverFinder = ServerFinder.getInstance();

        serverFinder.findServers(executorService, getConnector());
    }
    
    /**
     * @return
     *      A ServerDiscoveryHandler that connects the client to a server
     *      that has been discovered.
     */
    private ServerDiscoveryHandler getConnector() {
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
