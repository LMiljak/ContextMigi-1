package com.github.migi_1.Context;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public interface Obstacle {

    public Spatial getModel();

    public void setModel(Spatial model);

    public void move(float f, float g, float h);

    public void scale(float f);

    public void move(Vector3f add);

}
