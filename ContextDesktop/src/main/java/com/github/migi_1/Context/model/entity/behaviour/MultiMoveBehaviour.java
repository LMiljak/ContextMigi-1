package com.github.migi_1.Context.model.entity.behaviour;

import java.util.ArrayList;

public abstract class MultiMoveBehaviour extends MoveBehaviour {

	protected ArrayList<MoveBehaviour> behaviours = new ArrayList<>();
	
	public MultiMoveBehaviour(MoveBehaviour behaviour1, MoveBehaviour... behaviour2) {
		behaviours.add(behaviour1);
		for (MoveBehaviour behaviour : behaviour2) {
			behaviours.add(behaviour);
		}
	}
	
	@Override
	public void updateMoveVector() { }
}
