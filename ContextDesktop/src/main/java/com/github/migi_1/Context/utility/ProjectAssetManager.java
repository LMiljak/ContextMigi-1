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
     * Object instance.
     */
    private  static ProjectAssetManager instance;

    /**
     * Instance of the Asset Manager.
     */
    private AssetManager assetManager;

    /**
     * Private singleton constructor.
     */
    private ProjectAssetManager() { }

    /**
     * Singleton initialisation method.
     * @return newly created instance
     */
    public static ProjectAssetManager getInstance() {
        if (instance == null) {
            instance = new ProjectAssetManager();
        }

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
