/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.client;

import java.util.concurrent.Executors;

/**
 * Used to have one clientwrapper per device.
 * This class is passed over when a new Activity starts.
 */
public final class ClientHub {

    private ClientWrapper clientWrapper;
    private static volatile ClientHub instance = new ClientHub();

    /**
     * Constructor for the clienthub.
     * Starts a clientWrapper.
     * @param main the main activity used in starting the client.
     */
    private ClientHub() {
        clientWrapper = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10));
    }

    /**
     * Returns an instance of the clientHub.
     * @return the clienthub.
     */
    public static ClientHub getInstance() {
        return instance;
    }

    /**
     * Returns the clientWrapper that is created in the clienthub. 
     * @return the clientwrapper. 
     */
    public ClientWrapper getClientWrapper() {
        return clientWrapper;
    }
}
