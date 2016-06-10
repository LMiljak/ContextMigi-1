package com.github.migi_1.Context.obstacle;

public class MovingObstacleFactory extends AbstractObstacleFactory {

    @Override
    public Obstacle produce() {
        return new MovingObstacle();
    }

}
