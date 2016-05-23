package com.github.migi_1.Context.model.entity;


/**
 * Interface for every entity that moves, has a method getMovableBehaviour.
 * Different entities have different moving behaviours.
 * 
 * @author Damian
 *
 */
public interface IMovable extends IDisplayable {

    /**
     * Method for gettin the movable behaviour of an entity.
     * @return the current movable behaviour of the entity.
     */
    MovableBehaviour getMovableBehaviour();

    /**
     * Method for setting the movable behaviour of an entity.
     * @param mbh the bahviour of the movement to set the entities behaviour to.
     */
    void setMovableBehaviour(MovableBehaviour mbh);

}
