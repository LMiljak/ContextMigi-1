package com.github.migi_1.Context.utility;

import com.jme3.asset.AssetManager;

/**
 * This class makes the asset manager accessible from any class, after it has been initialized.
 *
 * SINGLETON class
 * @author Marcel
 *
 */
public final class ProjectAssetManager {



    /**
     * Instance of the Asset Manager.
     */
    private AssetManager assetManager;

    /**
     * Singleton instance.
     */
    private static final ProjectAssetManager instance = new ProjectAssetManager();

    /**
     * Singleton initialisation method.
     * @return newly created instance
     */
    public static ProjectAssetManager getInstance() {
        return instance;

    }

    /**
     * Return the asset manager.
     * @return Instance Manager instance.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Set a new Asset Manager instance.
     * @param assetManager new Asset Manager instance
     */
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

}
