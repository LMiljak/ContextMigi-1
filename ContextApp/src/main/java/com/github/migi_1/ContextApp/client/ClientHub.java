/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.client;

import com.github.migi_1.ContextMessages.PlatformPosition;
import java.util.concurrent.Executors;

/**
 * Used to have one clientwrapper per device.
 * This class is passed over when a new Activity starts.
 */
public class ClientHub {

    private ClientWrapper clientWrapper;
    private static volatile ClientHub INSTANCE = new ClientHub();
    private PlatformPosition position;

    /**
     * Constructor for the clienthub.
     * Starts a clientWrapper.
     * @param main the main activity used in starting the client.
     */
    private ClientHub() {
        clientWrapper = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10));
    }

    public static ClientHub getInstance() {
        return INSTANCE;
    }

    public ClientWrapper getClientWrapper() {
        return clientWrapper;
    }
    
    public void setPosition(PlatformPosition newPos) {
        position = newPos;
    }
    
    public PlatformPosition getPosition() {
        return position;
    }
}
