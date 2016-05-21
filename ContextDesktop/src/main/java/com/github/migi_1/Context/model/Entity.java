package com.github.migi_1.Context.model;

public class Entity {
    
    private float locX;
    private float locY;
    private float locZ;
    private int health;
    
    public Entity(float locX, float locY, float locZ) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
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
    
    
}
