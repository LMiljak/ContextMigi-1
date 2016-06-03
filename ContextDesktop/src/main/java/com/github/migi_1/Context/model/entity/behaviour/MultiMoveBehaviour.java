package com.github.migi_1.Context.model.entity.behaviour;

import java.util.ArrayList;

/**
 * Abstract MoveBehaviour class for every behaviour that is composed
 * of multiple other behaviours (constructor ensures that there is
 * always at least one behaviour in this class).
 */
public abstract class MultiMoveBehaviour extends MoveBehaviour {

	protected ArrayList<MoveBehaviour> behaviours = new ArrayList<>();
	
	/**
	 * Constructor for MultiMoveBehaviour.
	 * Requires at least one MoveBehaviour.
	 * 
	 * @param behaviour1
	 * 		One of the behaviour this MultiMoveBehaviour is composed of.
	 * @param behaviour2
	 * 		The rest of the behaviours this MultiMoveBehaviour is composed of.
	 */
	public MultiMoveBehaviour(MoveBehaviour behaviour1, MoveBehaviour... behaviour2) {
		behaviours.add(behaviour1);
		for (MoveBehaviour behaviour : behaviour2) {
			behaviours.add(behaviour);
		}
	}
	
	@Override
	public void updateMoveVector() { }
}
