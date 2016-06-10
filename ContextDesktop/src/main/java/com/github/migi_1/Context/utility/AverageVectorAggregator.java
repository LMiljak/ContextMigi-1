package com.github.migi_1.Context.utility;

import java.util.Collection;

import com.jme3.math.Vector3f;

/**
 * A VectorAggregator that takes the average of other vectors.
 */
public class AverageVectorAggregator implements IVectorAggregator {

	/**
	 * Averages a collection of vectors.
	 * 
	 * @param vectors
	 * 		The list of vectors to average.
	 * @return
	 * 		The vector that is the average of all the other vectors.
	 */
	@Override
	public Vector3f aggregate(Collection<Vector3f> vectors) {
		int size = vectors.size();
		
		if (size == 0) {
			return Vector3f.ZERO;
		} else {
			IVectorAggregator summer = new SummingVectorAggregator();
			Vector3f sum = summer.aggregate(vectors);
			return sum.divide(size);
		}
	}

}
