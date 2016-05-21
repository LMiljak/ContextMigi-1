package com.github.migi_1.Context.model;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

public class Carrier extends Entity {
    
    private static final String pathName = "Models/ninja.j3o";
    //To differentiate between the 4 carriers
    private String id;
    
    public Carrier(AssetManager assetManager, Vector3f location, String id) {
        super(assetManager.loadModel(pathName), location);
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    
    
}
