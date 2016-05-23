package com.github.migi_1.Context.model;

import com.jme3.asset.AssetManager;

public class ProjectAssetManager {

    private  static ProjectAssetManager instance;

    private AssetManager assetManager;

    private ProjectAssetManager() { }

    public static ProjectAssetManager getInstance() {
        if (instance == null) {
            instance = new ProjectAssetManager();
        }

        return instance;

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager){
        this.assetManager = assetManager;
    }

}
