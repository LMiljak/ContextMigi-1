package com.github.migi_1.Context.model;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Interface for all items that are displayed.
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
     * Move the object.
     * @param x Translation in x direction
     * @param y Translation in y direction
     * @param z Translation in z direction
     */
    void move(float x, float y, float z);

    /**
     * Scale the object.
     * @param f scale factor
     */
    void scale(float f);

    /**
     * Move the object.
     * @param add Translation vector
     */
    void move(Vector3f add);


}
