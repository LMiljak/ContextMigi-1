package com.github.migi_1.Context;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class StaticObstacle {

    private static final String MODEL_FILE = "Models/testCube2.j3o";

    private Spatial model;

    public StaticObstacle(AssetManager assetManager) {
        setModel(assetManager.loadModel(MODEL_FILE));
    }

    public Spatial getModel() {
        return model;
    }

    public void setModel(Spatial model) {
        this.model = model;
    }

    public void scale(float f) {
        model.scale(f);

    }

    public void move(Vector3f add) {
        model.move(add);

    }

    public void move(float f, float g, float h) {
        model.move(f, g, h);

    }




}
