package com.github.migi_1.Context.model;

import com.github.migi_1.Context.model.entity.IDisplayable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Class that handles the debugging camera.
 * It is treated as a spatial in order to move it around actively.
 * The flyCam is unable to be used in this application because it is an VR application.
 * This is the alterative.
 * @author Marcel
 *
 */
public abstract class Camera implements IDisplayable {

    private Spatial model;

    /**
     * Contructor of the camera, treats it as a node.
     */
    public Camera() {
        this.model = new Node();
    }

    /**
     * returns the model of the camera.
     * 
     */
    @Override
    public Spatial getModel() {
        return model;
    }

    /**
     * Sets the model of the camera.
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }

    /**
     * moves the camera to the given location.
     * @param location location to move the camera to.
     */
    @Override
    public void move(Vector3f location) {
        model.move(location);
    }

    /**
     * scales the camera, won't be used, function of Spatial.
     */
    @Override
    public void scale(float f) {
        model.scale(f);

    }

}
