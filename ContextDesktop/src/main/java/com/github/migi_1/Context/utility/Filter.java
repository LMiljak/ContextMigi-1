package com.github.migi_1.Context.utility;

/**
 * Interface for standard filters.
 * 
 * @param <T>
 * 		The type of the objects to be filtered.
 */
public interface Filter<T> {

	/**
	 * Checks if an object should be filtered.
	 * 
	 * @param object
	 * 		The object to filter.
	 * @return
	 * 		True iff the object should be filtered (false if it should be thrown out).
	 */
	boolean filter(T object);
	
}
