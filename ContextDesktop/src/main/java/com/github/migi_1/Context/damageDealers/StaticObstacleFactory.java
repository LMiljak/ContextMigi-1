package com.github.migi_1.Context.damageDealers;

import com.jme3.asset.AssetManager;

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
    public StaticObstacle produce(AssetManager assetManager) {
        return new StaticObstacle(assetManager);
    }

}
