/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.migi_1.ContextApp.MainActivity;
import com.github.migi_1.ContextMessages.PlatformPosition;
import java.util.concurrent.Executors;

/**
 * Used to have one clientwrapper per device.
 * This class is passed over when a new Activity starts. 
 */
public class ClientHub implements Parcelable {
    
    private PlatformPosition position;
    private ClientWrapper clientWrapper;
    private static MainActivity mainActivity;
    
    /**
     * Constructor for the clienthub.
     * Starts a clientWrapper.
     */
    public ClientHub(MainActivity main) {
        mainActivity = main;
        clientWrapper = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10), mainActivity);
    }
    
    /**
     * Getter for the position the clienthub is responsible for.
     * @return the position. 
     */
    public PlatformPosition getPosition() {
        return position;
    }
    
    /**
     * Setter for the position of the clienthub. 
     * @param newPosition the new position. 
     */
    public void setPosition(PlatformPosition newPosition) {
        position = newPosition;
    }
    
    /**
     * Getter for the clientwrapper.
     * @return the clientwrapper. 
     */
    public ClientWrapper getClientWrapper() {
        return clientWrapper;
    }

    /**
     * The following methods are added by the Parcelable interface.
     * This interface is implemented to pass the clienthub when a new Activity is started. 
     */
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position.getPosition());
    }
    
    public static final Parcelable.Creator<ClientHub> CREATOR = new Parcelable.Creator<ClientHub>() {

        @Override
        public ClientHub createFromParcel(Parcel source) {
            return new ClientHub(mainActivity);
        }

        @Override
        public ClientHub[] newArray(int size) {
            return new ClientHub[size];
        }
    };
}
