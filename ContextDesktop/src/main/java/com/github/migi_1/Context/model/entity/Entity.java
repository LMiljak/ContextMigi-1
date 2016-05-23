package com.github.migi_1.Context.model.entity;

public abstract class Entity {

    protected MoveBehaviour moveBehaviour;

    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }
}
