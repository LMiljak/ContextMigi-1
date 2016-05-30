package com.github.migi_1.Context.model.entity.behaviour;

import com.jme3.math.Vector3f;

public class SumMultiMoveBehaviour extends MultiMoveBehaviour {

	public SumMultiMoveBehaviour(MoveBehaviour behaviour1, MoveBehaviour... behaviour2) {
		super(behaviour1, behaviour2);
	}

	@Override
	public Vector3f getMoveVector() {
		Vector3f result = Vector3f.ZERO;
		
		for (MoveBehaviour behaviour : super.behaviours) {
			result = result.add(behaviour.getMoveVector());
		}
		
		return result;
	}
        
        @Override
        public void updateMoveVector(){
            // NOTHING
        }

}
