package com.github.migi_1.Context.model.entity.behaviour;

import java.util.Collection;

import com.jme3.math.Vector3f;

/**
 * Functional interface that aggregates several vectors into
 * one or more vectors.
 */
public interface IVectorAggregator {

	/**
	 * Aggregates a Collection of vectors into one vector.
	 * 
	 * @param vectors
	 * 		The vectors to aggregate.
	 * @return
	 * 		The aggregated vector.
	 */
	Vector3f aggregate(Collection<Vector3f> vectors);
	
}
