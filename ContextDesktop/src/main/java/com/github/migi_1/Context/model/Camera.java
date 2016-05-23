package com.github.migi_1.Context.model;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


public abstract class Camera implements IDisplayable {

    private Spatial model;

    public Camera() {
        this.model = new Node();
    }

    @Override
    public Spatial getModel() {
        return model;
    }

    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }

    @Override
    public void move(Vector3f location) {
        model.move(location);
    }

    @Override
    public void scale(float f) {
        model.scale(f);

    }

}
