package com.github.migi_1.Context.model.entity;

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
public class Commander implements IMovable, Collidable {
    
    //String of the path to the commander model
    private static final String pathName = "Models/ninja.j3o";
    private Spatial model;
    private MovableBehaviour movableBehaviour;
    
    /**
     * Constructor for the commander entity.
     */
    public Commander() {
        model = ProjectAssetManager.getInstance().getAssetManager().loadModel(pathName);
    }
    
    /**
     * Returns the model of the commander.
     * @return
     */
    @Override
    public Spatial getModel() {
        return model;
    }
    
    /**
     * Sets the model of the commander.
     * @param model
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;        
    }
    
    /**
     * Move the commander.
     * @param location to which the commander should move.
     */
    @Override
    public void move(Vector3f location) {
        model.setLocalTranslation(location);
        
    }

    /**
     * scale the model of the commander.
     * @param f factor to scale the commander to.
     */
    @Override
    public void scale(float f) {
        model.scale(f);       
    }
    
    /**
     * Returns the movable behaviour of the commander.
     * @return
     */
    @Override
    public MovableBehaviour getMovableBehaviour() {
        return movableBehaviour;
    }
    
    /**
     * sets the movable behaviour of the commander.
     * @param mbh to set the movable behaviour to
     */
    @Override
    public void SetMovableBehaviour(MovableBehaviour mbh) {
        movableBehaviour = mbh;
        
    }
    
    /**
     * Method that registers a collision.
     * @param arg0 Collidable to check the collision against.
     * @param arg1 The register to where the collision results are added to.
     * @return whether the collision was succesul or not, 0 means succesful.
     * @throws UnsupportedCollisionException When the collision is unsupported by the jmonkey collision checker.
     */
    @Override
    public int collideWith(Collidable arg0, CollisionResults arg1)
            throws UnsupportedCollisionException {
        model.collideWith(arg0, arg1);
        return 0;
    }

}
