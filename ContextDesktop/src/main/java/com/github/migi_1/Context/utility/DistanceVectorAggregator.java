package com.github.migi_1.Context.utility;

import java.util.ArrayList;
import java.util.Collection;

import com.jme3.math.Vector3f;

/**
 * A vector aggregator that gives a vector that gives some information about
 * the similarity between all other vectors.
 * 
 * It does this as follows:
 * 		1. Takes the average vector of the vectors.
 * 		2. For each vector, the 
 * 		3. Takes the Root Mean Square of all distance vectors.
 * 
 * Then for x,y,z in the resulting vector, the value is lower when 
 * that value is similar in all the given vectors.
 */
public class DistanceVectorAggregator implements IVectorAggregator {

	@Override
	public Vector3f aggregate(Collection<Vector3f> vectors) {
		AverageVectorAggregator averager = new AverageVectorAggregator();
		Vector3f average = averager.aggregate(vectors);
		
		ArrayList<Vector3f> newVectors = new ArrayList<>(vectors.size());
		for (Vector3f vector : vectors) {
			Vector3f subtracted = average.subtract(vector);
			newVectors.add(subtracted.mult(subtracted));
		}
		
		Vector3f mean = averager.aggregate(newVectors);
		return new Vector3f((float) Math.sqrt(mean.x), (float) Math.sqrt(mean.y), (float) Math.sqrt(mean.z));
	}

}
