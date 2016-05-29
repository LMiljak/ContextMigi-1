package com.github.migi_1.ContextMessages;

/**
 * Represents a position under a Platform.
 */
public enum PlatformPosition {

	FRONTLEFT(-1, 1),
	FRONTRIGHT(-1, -1),
	BACKLEFT(1, 1),
	BACKRIGHT(1, -1);
	
	private int xFactor;
	private int zFactor;
	
	/**
	 * Constructor for PlatformPosition.
	 * 
	 * @param xFactor
	 * 		-1 if it's in the front, 1 if it's in the back.
	 * @param zFactor
	 * 		1 if it's left, -1 if it's right.
	 */
	PlatformPosition(int xFactor, int zFactor) {
		this.xFactor = xFactor;
		this.zFactor = zFactor;
	}

	/**
	 * @return
	 * 		-1 if this position in the front, 1 if it's in the back.
	 */
	public int getxFactor() {
		return xFactor;
	}

	/**
	 * @return
	 * 		1 if this position left, -1 if it's right.
	 */
	public int getzFactor() {
		return zFactor;
	}
	
	
	
}
