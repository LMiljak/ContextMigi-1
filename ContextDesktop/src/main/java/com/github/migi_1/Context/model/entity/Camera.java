package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.FlyRotateBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.RotateBehaviour;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import jmevr.app.VRApplication;

/**
 * Class that handles the debugging camera.
 * It is treated as a spatial in order to move it around actively.
 * The flyCam is unable to be used in this application because it is an VR application.
 * This is the alterative.
 * @author Marcel
 *
 */
public class Camera extends Entity implements IRotatable {

    private FlyRotateBehaviour rotateBehaviour = new FlyRotateBehaviour();

    /**
     * Displays the view of this camera on the screen.
     */
    public void makeObserver() {
    	VRApplication.setObserver(this.getModel());
    }

	@Override
	public Spatial getDefaultModel() {
		return new Node();
	}

    @Override
    public RotateBehaviour getRotateBehaviour() {
        return rotateBehaviour;
    }

}
