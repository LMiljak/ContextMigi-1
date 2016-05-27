package com.github.migi_1.Context.damageDealers;

import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IKillable;
import com.github.migi_1.Context.model.entity.StaticMoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * A non-moving obstacle.
 * @author Marcel
 *
 */
public class StaticObstacle extends Entity implements DamageDealer, IKillable {

    /** File location of model. **/
    private static final String PATHNAME = "Models/testCube2.j3o";

    private int health;

    /**
     * Instantiate object.
     */
    public StaticObstacle() {
        super();
        setModel(getDefaultModel());
        setMoveBehaviour(new StaticMoveBehaviour());
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
    public int collideWith(Collidable arg0, CollisionResults arg1)
            throws UnsupportedCollisionException {

        System.out.println("hoi");
        return getModel().collideWith(arg0, arg1);
    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }


}
