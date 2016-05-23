package com.github.migi_1.Context.damageDealers;

import com.github.migi_1.Context.model.entity.MovableBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * A non-moving obstacle.
 * @author Marcel
 *
 */
public class StaticObstacle implements DamageDealer {

    /** File location of model. **/
    private static final String MODEL_FILE = "Models/testCube2.j3o";

    /** Physical model of object. **/
    private Spatial model;

    /**
     * Instantiate object.
     * @param assetManager AssetManager that provides access to asset files.
     */
    public StaticObstacle() {
        setModel(ProjectAssetManager.getInstance().getAssetManager().loadModel(MODEL_FILE));
        model.move(0.0f, -2.0f, 0.0f);
    }

    /**
     * Return the physical model.
     */
    @Override
    public Spatial getModel() {
        return model;
    }

    /**
     * Set the physical model.
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }

    /**
     * Scale the physical model.
     */
    @Override
    public void scale(float f) {
        model.scale(f);

    }

    /**
     * Move the physical model.
     */
    @Override
    public void move(Vector3f add) {
        model.move(add);

    }

    /**
     * Move the physical model.
     */
    @Override
    public void move(float f, float g, float h) {
        model.move(f, g, h);

    }

    /**
     * Add collision for the physical model.
     */
    @Override
    public void collideWith(Spatial testPlatform, CollisionResults results) {
       this.getModel().collideWith(testPlatform.getWorldBound(), results);

    }

    @Override
    public MovableBehaviour getMovableBehaviour() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void SetMovableBehaviour(MovableBehaviour mbh) {
        // TODO Auto-generated method stub

    }




}
