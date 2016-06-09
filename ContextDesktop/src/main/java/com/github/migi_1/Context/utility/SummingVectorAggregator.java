package com.github.migi_1.Context.utility;

import java.util.Collection;

import com.jme3.math.Vector3f;

/**
 * An IVectorAggregator that can sum up a collection of vectors.
 */
public class SummingVectorAggregator implements IVectorAggregator {

	/**
	 * Sums up a collection of vectors.
	 * 
	 * @param vectors
	 * 		The list of vectors to sum up.
	 * @return
	 * 		The vector that is the sum of all the other vectors.
	 */
	@Override
	public Vector3f aggregate(Collection<Vector3f> vectors) {
		Vector3f result = Vector3f.ZERO;
		
		for (Vector3f vector : vectors) {
			result = result.add(vector);
		}
		
		return result;
	}

}
