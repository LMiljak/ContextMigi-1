package com.github.migi_1.Context.model;

import java.util.Random;

import com.github.migi_1.Context.model.entity.IMovable;
import com.github.migi_1.Context.model.entity.MovableBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class LevelPiece implements IMovable {


    private static final int DIFFERENT_WORLDS = 5;

    private Spatial model;

    public LevelPiece() {
        StringBuilder sb = new StringBuilder();
        sb.append("Models/world");
        sb.append(new Random().nextInt(DIFFERENT_WORLDS) + 1);
        sb.append(".j3o");
        this.model = ProjectAssetManager.getInstance().getAssetManager().loadModel(sb.toString());
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
    public void scale(float f) {
        model.scale(f);

    }

    @Override
    public void move(Vector3f add) {
        model.move(add);

    }

    @Override
    public MovableBehaviour getMovableBehaviour() {
        return null;
    }

    @Override
    public void setMovableBehaviour(MovableBehaviour mbh) {

    }

}
