package com.github.migi_1.Context.main;

/**
 * Interface for classes that listen to keys.
 * Register in InputHandler for it to work.
 */
public interface KeyInputListener {

	/**
	 * Called when a registered key has been pressed.
	 * 
	 * @param key
	 * 		The key that got pressed.
	 */
	void onKeyPressed(int key);
	
	/**
	 * Called when a registered key has been released.
	 * 
	 * @param key
	 * 		The key that got released.
	 */
	void onKeyReleased(int key);
	
}
