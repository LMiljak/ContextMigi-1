package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

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
    private Spatial model;


    /**
     * constructor of the platform.
     * @param startLocation location where the carrier will be initialized
     */
    public Platform(Vector3f startLocation) {
        model = ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
        model.setLocalTranslation(startLocation);
        moveBehaviour = new AcceleratorMoveBehaviour();
    }

    /**
     * Returns the model of the platform.
     * @return
     */
    @Override
    public Spatial getModel() {
        return model;
    }

    /**
     * Sets the model of the platform.
     * @param model
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }


}
