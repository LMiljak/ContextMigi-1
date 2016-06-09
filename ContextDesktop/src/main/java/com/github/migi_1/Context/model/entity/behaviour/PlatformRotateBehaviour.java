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
	
	private Collection<AccelerometerMoveBehaviour> carrierBehaviours;
	private final Quaternion initialRotation;
	private Quaternion rotation;
	
	private final float baseAmplitude = 0.05f;
	private final int speed = 200;
	private float disSimilarity = 0.0f;
	private float time = 0f;
	
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
		if (time >= 2 * Math.PI) {
			time = 0;
			rotation.set(initialRotation);
			disSimilarity = getDisSimilarity().z;
		} else {
			time += (float) (Math.PI / speed);
			
			float amplitude = (baseAmplitude * disSimilarity);
			
			Vector3f res = new Vector3f(
					(float) (Math.cos(time * 2) * amplitude), 
					0.0f,
					(float) (Math.cos(time)) * amplitude);
			
			super.setRotateVector(res);
		}
	}
	
	private Vector3f getDisSimilarity() {
		DistanceVectorAggregator aggregator = new DistanceVectorAggregator();
		
		Collection<Vector3f> vectors = new ArrayList<>(carrierBehaviours.size());
		
		for (MoveBehaviour behaviour : carrierBehaviours) {
			vectors.add(behaviour.getMoveVector());
		}
		
		return aggregator.aggregate(vectors);
	}
	
}
