package com.github.migi_1.Context.obstacle;

import com.github.migi_1.Context.model.MainEnvironment;

/**
 * Factory class for the MovingObstacle class.
 * @author Marcel
 *
 */
public class MovingObstacleFactory extends AbstractObstacleFactory {

    private MainEnvironment environment;

    /**
     * Constructor.
     * @param environment Argument that is needed the object this factory is producing.
     */
    public MovingObstacleFactory(MainEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public Obstacle produce() {
        return new MovingObstacle(environment.getLeftBound(), environment.getRightBound());
    }

}
