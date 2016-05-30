package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.behaviour.ConstantSpeedMoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.Context.server.DamageMessenger;
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

    private Main main;
    private DamageMessenger dm;
    
    private int health;
    private PlatformPosition position;

    /**
     * constructor of the carrier.
     * @param startLocation location where the carrier will be initialized
     * @param position position of the carrier under the platform.
     */
    public Carrier(Vector3f startLocation, PlatformPosition position, Main main) {
        super();   
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        setMoveBehaviour(new ConstantSpeedMoveBehaviour(MOVE_VECTOR));
        
        this.main = main;
        dm = new DamageMessenger(main);
        
        health = 3;      
        this.position = position;
    }



    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int h) {
        health = h;
        dm.sendHealth(getHealth(), getPosition());
    }
    
    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        dm.sendHealth(getHealth(), getPosition());
    	if (getHealth() <= 0) {
    		onKilled();
    	}
    }

    /**
     * Gets the position of this Carrier under the Platform.
     * 
     * @return
     * 		The position of this Carrier under the Platform.
     */
    public PlatformPosition getPosition() {
        return position;
    }

    @Override
    public void onKilled() {

    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }
}
