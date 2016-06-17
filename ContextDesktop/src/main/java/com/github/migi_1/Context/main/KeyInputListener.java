package com.github.migi_1.Context.main;

/**
 * Interface for classes that listen to keys.
 * Register in InputHandler for it to work.
 */
public interface KeyInputListener {

	void onKeyPressed(int key);
	
	void onKeyReleased(int key);
	
}
