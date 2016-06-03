package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.jme3.math.Vector3f;

/**
 * MoveBehaviour for the Carrier class.
 *
 * @author Marcel
 *
 */
public class CarrierMoveBehaviour extends EntityMoveBehaviour {

    private int immobilized;

    private Commander commander;

    private Boolean catchUp;

    private Carrier carrier;

    private Vector3f relativeLocation;

    private MainEnvironment environment;

    private static final int NUMBER_FRAMES = 120;

    /**
     * Constructor.
     * @param carrier Carrier to follow
     * @param moveVector initial moveVector
     * @param environment The environment to follow
     */
    public CarrierMoveBehaviour(Carrier carrier, Vector3f moveVector, MainEnvironment environment) {

        this.immobilized = 0;
        this.environment = environment;
        this.commander = environment.getCommander();
        this.carrier =  carrier;
        this.relativeLocation = carrier.getRelativeLocation();
        this.catchUp = false;
        setMoveVector(moveVector);
    }

    /**
     * When a collision has taken place.
     */
    @Override
    public void collided() {
        immobilized = NUMBER_FRAMES;
        catchUp = true;
    }

    /**
     * Return the move vector.
     */
    @Override
    public Vector3f getMoveVector() {
        if (carrier == null) {
            carrier = environment.getCarriers().get(0);
        }
        updateMoveVector();

        //when immobilized, don't move forward
        if (immobilized > 0) {
            return new Vector3f(0, 0, 0);
        }

        Vector3f moveVector = super.getMoveVector();
        moveVector.setX(moveVector.getX() - getAcceleratingFactor());
        //when catching up, move twice as fast
        if (catchUp) {
            return moveVector.mult(2.0f);
        }
        return moveVector;
    }

    /**
     * Update the move vector.
     */
    @Override
    public void updateMoveVector() {
        //corner location under the platform.
        Vector3f destination = commander.getModel().getLocalTranslation().add(relativeLocation);
        //become one frame closer to not being immobilised.
        if (immobilized > 0) {
            immobilized -= 1;
        }
        //when the carrier overshoots, put him in the right location.
        else if (carrier.getModel().getLocalTranslation().x < destination.x) {
            catchUp = false;
            carrier.getModel().setLocalTranslation(destination);
        }

    }

    /**
     * Set the carrier, by id.
     * @param id The id of the carrier
     */
    public void carrierId(int id) {
        carrier = environment.getCarriers().get(id);
    }

    /**
     * Set the relative location to the commander.
     * @param relativeLocation location relative to commander
     */
    public void setRelativeLocation(Vector3f relativeLocation) {
        this.relativeLocation = relativeLocation;

    }

    /**
     * Get the immobilized attribute.
     * @return attribute
     */
    public int getImmobilized() {
        return immobilized;
    }

    /**
     * Set the immobilized attribute.
     * @param immobilized attribute
     */
    public void setImmobilized(int immobilized) {
        this.immobilized = immobilized;
    }

    /**
     * Get the catchUp boolean.
     * @return attribute
     */
    public Boolean getCatchUp() {
        return catchUp;
    }

    /**
     * Set the cathUp boolean.
     * @param catchUp attribute
     */
    public void setCatchUp(Boolean catchUp) {
        this.catchUp = catchUp;
    }

    /**
     * Get the corresponding carrier.
     * @return the carrier
     */
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * Set the carrier.
     * @param carrier Carrier to set
     */
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    /**
     * Get the relative location.
     * @return relativeLocation
     */
    public Vector3f getRelativeLocation() {
        return relativeLocation;
    }

    /**
     * Get the NUMBER_FRAMES attribute.
     * @return NUMBER_FRAMES
     */
    public static int getNumberFrames() {
        return NUMBER_FRAMES;
    }



}
