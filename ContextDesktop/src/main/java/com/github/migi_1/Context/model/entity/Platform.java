package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;

/**
 * Class that handles everything that have to do with the platform.
 * It implements collidable to register collisions.
 * It implements IMovable to register a movable behaviour. A movable behaviour
 * Sets how the platform moves after every tick.
 * @author Damian
 *
 */
public class Platform extends Entity {

    private static final String PATHNAME = "Models/testPlatform.j3o";


    /**
     * constructor of the platform.
     * @param startLocation location where the carrier will be initialized
     */
    public Platform(Vector3f startLocation) {
        super();
        setModel(ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME));
        getModel().setLocalTranslation(startLocation);
        setMoveBehaviour(new AcceleratorMoveBehaviour());
    }


}
