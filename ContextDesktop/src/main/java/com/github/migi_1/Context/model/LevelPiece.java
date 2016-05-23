package com.github.migi_1.Context.model;

import java.util.Random;

import com.github.migi_1.Context.model.entity.IDisplayable;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.scene.Spatial;

/**
 * Class that handles each instance of a levelPiece.
 * @author Marcel
 *
 */
public class LevelPiece implements IDisplayable {


    private static final int DIFFERENT_WORLDS = 5;

    private Spatial model;

    /**
     * Constructor for a levelPiece, uses a random number generator to choose from
     * several world modules.
     */
    public LevelPiece() {
        StringBuilder sb = new StringBuilder();
        sb.append("Models/world");
        sb.append(new Random().nextInt(DIFFERENT_WORLDS) + 1);
        sb.append(".j3o");
        this.model = ProjectAssetManager.getInstance().getAssetManager().loadModel(sb.toString());
    }

    /**
     * returns the model of the world piece.
     */
    @Override
    public Spatial getModel() {
        return model;
    }

    /**
     * Sets the model of the world piece.
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;

    }

}
