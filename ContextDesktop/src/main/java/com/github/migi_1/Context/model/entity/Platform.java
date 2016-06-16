package com.github.migi_1.Context.model.entity;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.behaviour.AcceleratingMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.AccelerometerMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.MultiMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.PlatformRotateBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.RotateBehaviour;
import com.github.migi_1.Context.utility.AverageVectorAggregator;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.Context.utility.SummingVectorAggregator;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Entity that handles everything that has to do with the platform.
 * @author Damian
 *
 */
public class Platform extends Entity implements IRotatable {

    private static final String PATHNAME = "Models/platform.j3o";
    private static final Vector3f MOVE_VECTOR = new Vector3f(-0.06f, 0, 0);
    private HashMap<PlatformPosition, Carrier> carriers = new HashMap<>(4);
    private RotateBehaviour rotateBehaviour;

    /**
     * Constructor of the platform.
     *
     * @param startLocation
     * 		location where the carrier will be initialised
     * @param environment
     * 		The environment that contains this platform.
     * @param carrierAssigner
     * 		The carrierAssigner that contains the addresses of each carrier.
     */
    public Platform(Vector3f startLocation, MainEnvironment environment, CarrierAssigner carrierAssigner) {
        super();

        ArrayList<AccelerometerMoveBehaviour> carrierBehaviours = new ArrayList<>(4);
        for (PlatformPosition position : PlatformPosition.values()) {
        	carrierBehaviours.add(
        			new AccelerometerMoveBehaviour(ip -> ip.equals(carrierAssigner.getAddress(position)))
        	);
        }


        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        setMoveBehaviour(
        	new MultiMoveBehaviour(
        		new SummingVectorAggregator(),
        		new AcceleratingMoveBehaviour(MOVE_VECTOR), //Responsible for going forwards
        		new MultiMoveBehaviour(//Responsible for steering
        			new AverageVectorAggregator(),
        			carrierBehaviours.get(0),
        			carrierBehaviours.get(1),
        			carrierBehaviours.get(2),
        			carrierBehaviours.get(3)
        		)
        	)
        );

        this.rotateBehaviour = new PlatformRotateBehaviour(carrierBehaviours, getModel().getLocalRotation());
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

    /**
     * Returns all carriers currently in the game.
     * @return
     *    Carriers in the game. If none, it will return null.
     */
    public ArrayList<Carrier> getCarriers() {
        ArrayList<Carrier> results = new ArrayList<Carrier>();
        for (Carrier carrier : carriers.values()) {
            results.add(carrier);
        }
        return results;
    }

	@Override
	public RotateBehaviour getRotateBehaviour() {
		return rotateBehaviour;
	}



}
