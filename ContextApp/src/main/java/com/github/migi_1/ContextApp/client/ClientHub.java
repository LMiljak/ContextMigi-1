/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.migi_1.ContextMessages.PlatformPosition;
import java.util.concurrent.Executors;

/**
 *
 * @author Nils
 */
public class ClientHub implements Parcelable {
    
    private PlatformPosition position;
    private ClientWrapper clientWrapper;
    
    public ClientHub() {
        clientWrapper = AutoConnector.getInstance().autoStart(Executors.newFixedThreadPool(10));
    }
    
    public PlatformPosition getPosition() {
        return position;
    }
    
    public void setPosition(PlatformPosition newPosition) {
        position = newPosition;
    }
    
    public ClientWrapper getClientWrapper() {
        return clientWrapper;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position.getPosition());
    }
    
    public static final Parcelable.Creator<ClientHub> CREATOR = new Parcelable.Creator<ClientHub> () {

        @Override
        public ClientHub createFromParcel(Parcel source) {
            return new ClientHub();
        }

        @Override
        public ClientHub[] newArray(int size) {
            return new ClientHub[size];
        }
    };
}
