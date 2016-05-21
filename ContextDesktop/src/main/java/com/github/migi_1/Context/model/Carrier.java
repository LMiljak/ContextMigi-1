package com.github.migi_1.Context.model;

import com.jme3.asset.AssetManager;

public class Carrier extends Entity {
    
    private static final String pathName = "Models/ninja.j3o";
    //To differentiate between the 4 carriers
    private String id;
    
    public Carrier(AssetManager assetManager, float locX, float locY, float locZ, String id) {
        super(assetManager.loadModel(pathName), locX, locY, locZ);
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
