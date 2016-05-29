package com.github.migi_1.Context.model.entity.behaviour;

import com.jme3.math.Vector3f;

/**
 * Move behaviour of a static (non-moving) object.
 *
 * @author Marcel
 *
 */
public class StaticMoveBehaviour extends MoveBehaviour {

    @Override
    public Vector3f getMoveVector() {
        return new Vector3f(0, 0, 0);
    }

}
