package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
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
public class Commander extends Camera {

    //String of the path to the commander model
    private static final String PATHNAME = "Models/ninja.j3o";

    /**
     * Constructor of the commander.
     * @param startLocation 
     * 		startLocation location where the Commander will be initialised
     * @param platformBehaviour
     * 		The behaviour of the platform on which this commander is standing.
     */
    public Commander(Vector3f startLocation, MoveBehaviour platformBehaviour) {
        super();
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        
        setMoveBehaviour(platformBehaviour);
    }


    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }

}
