package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

/**
 * MoveBehaviour for the Carrier class.
 *
 * @author Marcel
 *
 */
public class CarrierMoveBehaviour extends EntityMoveBehaviour {

    private Vector3f moveVector;

    private int immobalized;

    private Commander commander;

    private Boolean catchUp;

    private Carrier carrier;

    private Vector3f relativeLocation;

    /**
     * Constructor.
     * @param moveVector initial moveVector
     * @param carrier designated carrier
     */
    public CarrierMoveBehaviour(Vector3f moveVector, Carrier carrier) {
        this.moveVector = moveVector;
        this.immobalized = 0;
        this.carrier = carrier;
        this.catchUp = false;
    }

    @Override
    public void collided() {
        immobalized = 120;
        catchUp = true;
    }

    @Override
    public Vector3f getMoveVector() {
        updateMoveVector();
        if (immobalized > 0) {
            return new Vector3f(0, 0, 0);
        }
        if (catchUp) {
            return moveVector.mult(2.0f);
        }
        return moveVector;
    }

    @Override
    public void updateMoveVector() {
        Vector3f destination = commander.getModel().getLocalTranslation().add(relativeLocation);


        if (immobalized > 0) {
            immobalized -= 1;
        }
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
     * @param relativeLocation
     */
    public void setRelativeLocation(Vector3f relativeLocation) {
        this.relativeLocation = relativeLocation;

    }

}
