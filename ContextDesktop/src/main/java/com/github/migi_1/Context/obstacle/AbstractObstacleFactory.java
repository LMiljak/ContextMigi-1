package com.github.migi_1.Context.obstacle;



/**
 * This class defines what functions all factories that create Obstacles must implement.
 * @author Marcel
 *
 */
public abstract class AbstractObstacleFactory {

    /**
     * Generate a Obstacle object.
     * @return generated Obstacle object
     */
    public abstract Obstacle produce();

}
