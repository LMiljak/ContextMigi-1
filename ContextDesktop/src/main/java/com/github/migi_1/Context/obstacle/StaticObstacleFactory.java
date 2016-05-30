package com.github.migi_1.Context.obstacle;


/**
 * This class is a factory that produces StaticObstacle objects.
 * @author Marcel
 *
 */
public class StaticObstacleFactory extends AbstractObstacleFactory {

    /**
     * Generate a StaticObstacle object.
     */
    @Override
    public StaticObstacle produce() {
        return new StaticObstacle();
    }

}
