package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.enemy.Enemy;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.math.Vector3f;

/**
 * Class for handling the spots where enemies can stand next to a carrier.
 * @author TUDelft SID
 *
 */
public class EnemySpot {
    
    private Direction direction;
    private boolean occupied;
    private Vector3f location;
    private PlatformPosition carrierId;
    private Carrier carrier;
    private Commander commander;
    private Enemy enemy;
    
    /**
     * Directions in which the enemy spots can face.
     */
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    };
    
    /**
     * Constructor of the EnemySpot.
     * @param location Location of the enemy spot relative to the carrier.
     * @param carrier carrier of which this spot is.
     * @param commander to find the relative location of the target spot
     * @param direction in which the spot faces, looking from the carrier.
     */
    public EnemySpot(Vector3f location, Carrier carrier, Commander commander, Direction direction) {
        this.commander = commander;
        this.location = location;
        this.direction = direction;
        this.carrierId = carrier.getPosition();
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
        return commander.getModel().getLocalTranslation().add(location).add(carrier.getRelativeLocation());
    }
    /**
     * Returns the offset from the of the enemy spot and the carrier.
     * @return the location of that spot.
     */
    public Vector3f getOffset() {
        return location;
    }

    /**
     * @return the carrierId
     */
    public PlatformPosition getCarrierId() {
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
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * @param enemy the enemy to set
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        setOccupied(true);
    }

    /**
     * @param carrier the carrier to set
     */
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }  
    
}
