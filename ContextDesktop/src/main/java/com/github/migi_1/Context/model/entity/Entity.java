package com.github.migi_1.Context.model.entity;

public abstract class Entity {

    private MoveBehaviour moveBehaviour;

    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }
}
