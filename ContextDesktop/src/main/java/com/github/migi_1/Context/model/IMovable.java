package com.github.migi_1.Context.model;

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
     * @return
     */
    public MovableBehaviour getMovableBehaviour();
    
    /**
     * Method for setting the movable behaviour of an entity.
     * @param mbh
     */
    public void SetMovableBehaviour(MovableBehaviour mbh);
    
}
