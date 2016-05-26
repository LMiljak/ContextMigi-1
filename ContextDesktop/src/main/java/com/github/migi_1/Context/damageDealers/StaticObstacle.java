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


    /**
     * Instantiate object.
     * @param assetManager AssetManager that provides access to asset files.
     */
    public StaticObstacle() {
        super();
        setModel(getDefaultModel());
        setMoveBehaviour(new StaticMoveBehaviour());
        getModel().move(new Vector3f(0, -2.0f, 0));
    }

    @Override
    public int getHealth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setHealth(int health) {
        // TODO Auto-generated method stub

    }

    @Override
    public void takeDamage(int damage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onKilled() {
        // TODO Auto-generated method stub

    }

    @Override
    public int collideWith(Collidable arg0, CollisionResults arg1)
            throws UnsupportedCollisionException {
        getModel().collideWith(arg0, arg1);
        return 0;
    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }


}
