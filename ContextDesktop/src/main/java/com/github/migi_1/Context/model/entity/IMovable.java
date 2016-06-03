package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;

/**
 * Interface for every entity that moves, has a method getMovableBehaviour.
 * Different entities have different moving behaviours.
 *
 * @author Damian
 *
 */
public interface IMovable extends IDisplayable {

    /**
     * Method for getting the movable behaviour of an entity.
     * @return the current movable behaviour of the entity.
     */
    MoveBehaviour getMoveBehaviour();

    /**
     * Method for setting the movable behaviour of an entity.
     * @param moveBehaviour the behaviour of the movement to set the entities behaviour to.
     */
    void setMoveBehaviour(MoveBehaviour moveBehaviour);

}
