package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

public class EnemySpot {
    
    public Direction direction;
    private boolean occupied;
    private Vector3f location;
    private int carrierId;
    private Carrier carrier;
    private Commander commander;
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    };
    
    public EnemySpot(Vector3f location, Carrier carrier, Commander commander, Direction direction) {
        this.commander = commander;
        this.location = location;
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
        System.out.println(commander.getMoveBehaviour().getMoveVector());
        return commander.getModel().getLocalTranslation().add(location).add(carrier.getRelativeLocation());
    }
    /**
     * 
     * @return
     */
    public Vector3f getOffset() {
        return location;
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

    /**
     * @return the carrier
     */
    public Entity getCarrier() {
        return carrier;
    }
    
        
}
