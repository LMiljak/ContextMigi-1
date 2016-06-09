package com.github.migi_1.Context.model.entity.behaviour;

import java.util.ArrayList;
import java.util.Collection;

import com.github.migi_1.Context.utility.DistanceVectorAggregator;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 * A RotateBehaviour specially designed for a Platform.
 * It uses the similarity of the platform's AccelerometerMoveBehaviours used for steering,
 * to determine how badly the Platform is rotating.
 */
public class PlatformRotateBehaviour extends RotateBehaviour {

	private float i = 0f;
	private Collection<AccelerometerMoveBehaviour> carrierBehaviours;
	private float badness = 1000f;
	private float similarity = 0.1f;
	
	private final Quaternion initialRotation;
	private Quaternion rotation;
	
	/**
	 * Constructor for PlatformRotateBehaviour.
	 * 
	 * @param carrierBehaviours
	 * 		The AccelerometerMoveBehaviours used by the Platform for steering.
	 * @param initialRotation
	 * 		The current rotation of the platform.
	 */
	public PlatformRotateBehaviour(
			Collection<AccelerometerMoveBehaviour> carrierBehaviours, 
			Quaternion initialRotation) {
		
		super();
		this.carrierBehaviours = carrierBehaviours;
		this.initialRotation = new Quaternion(initialRotation);
		this.rotation = initialRotation;
	}
	
	@Override
	public void updateRotateVector() {
		if (i >= 2 * Math.PI) {
			i = 0;
			rotation.set(initialRotation);
			similarity = getSimilarity().z;
		} else {
			float amplitude = (badness - badness * similarity) / 5 + 1;
			i += (float) (Math.PI / 200);
			Vector3f v = new Vector3f((float) (Math.cos(i * 2) / 150), 0.0f, (float) (Math.cos(i)) / 150);
			super.setRotateVector(v);
		}
	}
	
	private Vector3f getSimilarity() {
		DistanceVectorAggregator similer = new DistanceVectorAggregator();
		
		Collection<Vector3f> vectors = new ArrayList<>(carrierBehaviours.size());
		
		for (MoveBehaviour behaviour : carrierBehaviours) {
			vectors.add(behaviour.getMoveVector());
		}
		
		return similer.aggregate(vectors);
	}
	
}
