package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.collision.Collidable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class that handles everything that have to do with the carrier.
 * It implements collidable to register collisions.
 * It implements IMovable to register a movable behaviour. A movable behaviour
 * Sets how the carrier moves after every tick.
 * @author Damian
 *
 */
public class Carrier extends Entity implements Collidable, IKillable {

    //String of the path to the carrier model
    private static final String PATHNAME = "Models/ninja.j3o";
    private static final Vector3f MOVE_VECTOR = new Vector3f(-0.2f, 0, 0);

    private int health;
    private int id; //Represents the location of the carrier under the platform.

    /**
     * constructor of the carrier.
     * @param startLocation location where the carrier will be initialized
     * @param id to keep the 4 carriers apart
     */
    public Carrier(Vector3f startLocation, int id) {
        super();
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        setMoveBehaviour(new ConstantSpeedMoveBehaviour(MOVE_VECTOR));
        health = 3;
        this.id = id;
    }



    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int h) {
        health = h;
        sendHealth();
    }
    
    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        sendHealth();
    	if (getHealth() <= 0) {
    		onKilled();
    	}
    }

    /**
     * Gets the id that represents the location of the Carrier under the platform.
     *
     * @return
     * 		the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onKilled() {

    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }

    /**
     * This function sends the health value to the right android app.
     */
    public void sendHealth() {
        // TODO
    }
}
