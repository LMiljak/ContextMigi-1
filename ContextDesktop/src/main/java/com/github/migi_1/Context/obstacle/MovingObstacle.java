package com.github.migi_1.Context.obstacle;

import com.github.migi_1.Context.model.entity.behaviour.MovingObstacleMoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class defines the move behaviour of a moving obstacle.
 * @author Marcel
 *
 */
public class MovingObstacle extends Obstacle {

    /** File location of model. **/
    private static final String PATHNAME = "Models/slimeObstacle.j3o";

    private int health;

    /**
     * Constructor.
     * @param leftBound BoundingBox on the left
     * @param rightBound BoundingBox on the right
     */
    public MovingObstacle(BoundingBox leftBound, BoundingBox rightBound) {
        super();
        setModel(getDefaultModel().scale(7.0f).rotate(0, 4.75f, 0));
        setMoveBehaviour(new MovingObstacleMoveBehaviour(this, leftBound, rightBound));
        getModel().move(new Vector3f(0, -2.0f, 0));
        health = 1;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;

    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;

    }

    @Override
    public void onKilled() {

    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }

}
