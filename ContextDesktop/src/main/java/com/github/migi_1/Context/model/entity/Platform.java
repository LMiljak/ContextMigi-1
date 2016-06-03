package com.github.migi_1.Context.model.entity;

import java.util.HashMap;

import com.github.migi_1.Context.model.entity.behaviour.AcceleratingMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.AccelerometerMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.SumMultiMoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Entity that handles everything that has to do with the platform.
 * @author Damian
 *
 */
public class Platform extends Entity {

    private static final String PATHNAME = "Models/testPlatform.j3o";
    private static final Vector3f MOVE_VECTOR = new Vector3f(-0.2f, 0, 0);
    private HashMap<PlatformPosition, Carrier> carriers = new HashMap<>(4);
    /**
     * constructor of the platform.
     * @param startLocation location where the carrier will be initialized
     */
    public Platform(Vector3f startLocation) {
        super();
        
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        setMoveBehaviour(new SumMultiMoveBehaviour(
				new AccelerometerMoveBehaviour(), 
				new AcceleratingMoveBehaviour(MOVE_VECTOR)
			));
    }

    /**
     * Adds a Carrier to this Platform.
     * If there already is a Carrier on the position of the new Carrier,
     * the old one gets removed.
     * 
     * @param carrier
     * 		The carrier to add.
     */
    public void addCarrier(Carrier carrier) {
    	carriers.put(carrier.getPosition(), carrier);
    }
    
    /**
     * Gets a carrier of this platform.
     * 
     * @param position
     * 		The position of the carrier under the platform.
     * @return
     * 		The carrier under the given position. Null if there
     * 		is no carrier.
     */
    public Carrier getCarrier(PlatformPosition position) {
    	return carriers.get(position);
    }
    
    /**
     * Checks if the platform is fully being carried by four carriers.
     * 
     * @return
     * 		True iff the platform is carried by four carriers.
     */
    public boolean isFull() {
    	return carriers.size() == PlatformPosition.values().length;
    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }



}
