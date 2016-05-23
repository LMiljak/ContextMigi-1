package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Interface for all items that can be displayed.
 *
 * @author Marcel
 *
 */
public interface IDisplayable {

    /**
     * Get the physical model of the object.
     * @return The physical model.
     */
    Spatial getModel();

    /**
     * Set the physical model of the object.
     * @param model the physical model
     */
    void setModel(Spatial model);

    /**
     * Moves the entity in the given direction.
     * @param location of where to move the entity to.
     */
    void move(Vector3f location);

    /**
     * Scale the object.
     * @param f scale factor
     */
    void scale(float f);


}
