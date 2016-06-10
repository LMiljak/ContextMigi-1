package com.github.migi_1.Context.obstacle;

import com.github.migi_1.Context.model.MainEnvironment;

public class MovingObstacleFactory extends AbstractObstacleFactory {

    private MainEnvironment environment;

    public MovingObstacleFactory(MainEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public Obstacle produce() {
        System.out.println("hallo");
        return new MovingObstacle(environment.getLeftBound(), environment.getRightBound());
    }

}
