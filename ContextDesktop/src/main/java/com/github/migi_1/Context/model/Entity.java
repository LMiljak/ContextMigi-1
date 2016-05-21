package com.github.migi_1.Context.model;

import com.jme3.scene.Spatial;

public class Entity {

    private float locX;
    private float locY;
    private float locZ;
    private int health;
    private Spatial spatial;

    public Entity(Spatial spatial, float locX, float locY, float locZ) {
        this.spatial = spatial;
        spatial.setLocalTranslation(locX, locY, locZ);        
        health = 2;
    }

    /**
     * @return the locX
     */
    public float getLocX() {
        return locX;
    }

    /**
     * @return the locY
     */
    public float getLocY() {
        return locY;
    }

    /**
     * @return the locZ
     */
    public float getLocZ() {
        return locZ;
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
