package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

public class EnemySpot {
    
    public Direction direction;
    private boolean occupied;
    private Vector3f location;
    private int carrierId;
    private Entity carrier;
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    };
    
    public EnemySpot(Vector3f location, Carrier carrier, Direction direction) {
        this.location = location.add(carrier.getModel().getLocalTranslation());
        this.direction = direction;
        this.carrierId = carrier.getId();
        this.carrier = carrier;
        occupied = false;
    }

    /**
     * @return the occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * @param occupied the occupied to set
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * @return the location
     */
    public Vector3f getLocation() {
        return location.add(carrier.getModel().getLocalTranslation());
    }

    /**
     * @return the carrierId
     */
    public int getCarrierId() {
        return carrierId;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }
    
    
    
    
    
    
    
    
}
