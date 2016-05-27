package com.github.migi_1.Context.model;

import com.github.migi_1.Context.model.entity.IDisplayable;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.scene.Spatial;

/**
 * Class that creates the path the carriers walk on.
 * @author Nils
 */
public class Path implements IDisplayable {
    private Spatial model;

    /**
     * Constructor for path.
     */
    public Path() {
        this.model = ProjectAssetManager.getInstance().getAssetManager().loadModel("Models/path.j3o");
    }

    @Override
    public Spatial getModel() {
        return model;
    }

    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }

}
