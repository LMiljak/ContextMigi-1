package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

/**
 * MoveBehaviour for the Carrier class.
 *
 * @author Marcel
 *
 */
public class CarrierMoveBehaviour extends EntityMoveBehaviour {

    private int immobalised;

    private Commander commander;

    private Boolean catchUp;

    private Carrier carrier;

    private Vector3f relativeLocation;

    private static final int NUMBER_FRAMES = 120;

    /**
     * Constructor.
     * @param moveVector initial moveVector
     * @param carrier designated carrier
     */
    public CarrierMoveBehaviour(Vector3f moveVector, Carrier carrier) {
        setMoveVector(moveVector);
        this.immobalised = 0;
        this.carrier = carrier;
        this.catchUp = false;
    }

    /**
     * When a collision has taken place.
     */
    @Override
    public void collided() {
        immobalised = NUMBER_FRAMES;
        catchUp = true;
    }

    /**
     * Return the move vector.
     */
    @Override
    public Vector3f getMoveVector() {
        updateMoveVector();

        //when immobalized, don't move forward
        if (immobalised > 0) {
            return new Vector3f(0, 0, 0);
        }

        //when catching up, move twice as fast
        if (catchUp) {
            return super.getMoveVector().mult(2.0f);
        }
        return super.getMoveVector();
    }

    /**
     * Update the move vector.
     */
    @Override
    public void updateMoveVector() {
        //corner location under the platform.
        Vector3f destination = commander.getModel().getLocalTranslation().add(relativeLocation);

        //become one frame closer to not being immobilised.
        if (immobalised > 0) {
            immobalised -= 1;
        }
        //when the carrier overshoots, put him in the right location.
        else if (carrier.getModel().getLocalTranslation().x < destination.x) {
            catchUp = false;
            carrier.getModel().setLocalTranslation(destination);
        }

    }

    /**
     * Set the commander.
     *
     * @param commander Commander object
     */
    public void setCommander(Commander commander) {
        this.commander = commander;
    }

    /**
     * Set the relative location to the commander.
     * @param relativeLocation location relative to commander
     */
    public void setRelativeLocation(Vector3f relativeLocation) {
        this.relativeLocation = relativeLocation;

    }

}
