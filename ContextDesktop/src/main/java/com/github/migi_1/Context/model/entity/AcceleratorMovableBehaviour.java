package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;
/**
 * MovableBehaviour for accelerating everything along the path.
 * @author Damian
 *
 */
public class AcceleratorMovableBehaviour extends MoveBehaviour {

    @Override
    public Vector3f getMoveVector() {
        return new Vector3f(-0.2f, 0, 0);

    }
}
