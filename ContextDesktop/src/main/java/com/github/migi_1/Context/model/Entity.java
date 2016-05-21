package com.github.migi_1.Context.model;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Entity {

    private int health;
    private Spatial spatial;

    public Entity(Spatial spatial, Vector3f location) {
        this.spatial = spatial;
        spatial.setLocalTranslation(location);        
        health = 2;
    }

    /**
     * @return the locX
     */
    public float getLocX() {
        return spatial.getLocalTranslation().x;
    }

    /**
     * @return the locY
     */
    public float getLocY() {
        return spatial.getLocalTranslation().y;
    }

    /**
     * @return the locZ
     */
    public float getLocZ() {
        return spatial.getLocalTranslation().z;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return the spatial
     */
    public Spatial getSpatial() {
        return spatial;
    }

    /**
     * @param spatial the spatial to set
     */
    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }
    
    
    
    


}
