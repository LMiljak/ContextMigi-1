package com.github.migi_1.Context.damageDealers;


/**
 * This class is a factory that produces StaticObstacle objects.
 * @author Marcel
 *
 */
public class StaticObstacleFactory extends DamageDealerFactory {

    /**
     * Generate a StaticObstacle object.
     */
    @Override
    public StaticObstacle produce() {
        return new StaticObstacle();
    }

}
