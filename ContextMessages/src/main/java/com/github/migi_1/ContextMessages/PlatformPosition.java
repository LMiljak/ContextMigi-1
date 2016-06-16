package com.github.migi_1.ContextMessages;

/**
 * Represents a position under a Platform.
 */
public enum PlatformPosition {

	FRONTLEFT(1, 1, "Location: front/left"),
	FRONTRIGHT(1, -1, "Location: front/right"),
	BACKLEFT(-1, 1, "Location: back/left"),
	BACKRIGHT(-1, -1, "Location: back/right");

	private int xFactor;
	private int zFactor;
	private String position;

	/**
	 * Constructor for PlatformPosition.
	 *
	 * @param xFactor
	 *            -1 if it's in the front, 1 if it's in the back.
	 * @param zFactor
	 *            1 if it's left, -1 if it's right.
	 * @param position
	 *            The string printed on an android device's screen for the
	 *            player to see in which position they are.
	 */
	PlatformPosition(int xFactor, int zFactor, String position) {
		this.xFactor = xFactor;
		this.zFactor = zFactor;
		this.position = position;
	}

	/**
	 * @return -1 if this position in the front, 1 if it's in the back.
	 */
	public int getxFactor() {
		return xFactor;
	}

	/**
	 * @return 1 if this position left, -1 if it's right.
	 */
	public int getzFactor() {
		return zFactor;
	}

	/**
	 * @return location The location of the carrier.
	 */
	public String getPosition() {
		return position;
	}

}
