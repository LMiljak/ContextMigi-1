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
public class Platform implements IMovable {
    
    private static final String pathName = "Models/testPlatform.j3o";
    private Spatial model;
    private MovableBehaviour movableBehaviour;
    
    public Platform(Vector3f startLocation) {
        model = ProjectAssetManager.getInstance().getAssetManager().loadModel(pathName);
        model.setLocalTranslation(startLocation);
        movableBehaviour = new AcceleratorMovableBehaviour();
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
    
    /**
     * Move the platform.
     * @param location to which the platform should move.
     */
    @Override
    public void move(Vector3f location) {
        model.move(location);
        
    }

    /**
     * scale the model of the platform.
     * @param f factor to scale the platform to.
     */
    @Override
    public void scale(float f) {
        model.scale(f);       
    }
    
    /**
     * Returns the movable behaviour of the platform.
     * @return
     */
    @Override
    public MovableBehaviour getMovableBehaviour() {
        return movableBehaviour;
    }
    
    /**
     * Sets the movable behaviour of the platform.
     * @param mbh to set the movable behaviour to
     */
    @Override
    public void SetMovableBehaviour(MovableBehaviour mbh) {
        movableBehaviour = mbh;
    }
    
    
}
