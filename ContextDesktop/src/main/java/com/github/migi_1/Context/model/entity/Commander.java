package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.RotateBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class that handles everything that have to do with the commander.
 * It implements collidable to register collisions.
 * It implements IMovable to register a movable behaviour. A movable behaviour
 * Sets how the commander moves after every tick.
 * @author Damian
 *
 */
public class Commander extends Camera implements IRotatable {

    //String of the path to the commander model
    private static final String PATHNAME = "Models/emptyCommander.j3o";
    private RotateBehaviour rotateBehaviour;

    /**
     * Constructor of the commander.
     * @param startLocation
     * 		startLocation location where the Commander will be initialised
     * @param platform
     * 		The platform on which the Commander is standing.
     */
    public Commander(Vector3f startLocation, Platform platform) {
        super();
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);

        setMoveBehaviour(platform.getMoveBehaviour());
        rotateBehaviour = platform.getRotateBehaviour();
    }


    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }


	@Override
	public RotateBehaviour getRotateBehaviour() {
		return rotateBehaviour;
	}

}
