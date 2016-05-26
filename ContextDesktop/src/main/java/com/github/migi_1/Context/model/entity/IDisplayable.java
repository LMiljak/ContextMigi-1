package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Interface for all objects that can be displayed on an environment.
 *
 * @author Marcel
 *
 */
public interface IDisplayable {

    /**
     * Gets the physical model of the object.
     * @return The physical model.
     */
    Spatial getModel();

    /**
     * Sets the physical model of the object.
     * @param model the physical model
     */
    void setModel(Spatial model);

    /**
     * Moves the entity in the given direction.
     * @param location The new location of this IDisplayable.
     */
    default void move(Vector3f location) {
        getModel().move(location);
    }

    /**
     * Scales the object.
     * @param factor The scale factor
     */
    default void scale(float factor) {
        getModel().scale(factor);
    }


}
