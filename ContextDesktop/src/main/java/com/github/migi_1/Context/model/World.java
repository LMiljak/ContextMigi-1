
package com.github.migi_1.Context.model;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class handles all logic of the game world itself.
 * @author Damian
 */
public class World {
    private Environment env;
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private Spatial vrObs;
    private Spatial flyObs;

    /**
     * Constructor for the world object
     * @param cam, The camera that will display the viewpoint of the player
     * @param viewPort, Main screen of the game, this will be rendered
     * @param assetManager, loads and manages all assets of the world
     * @param rootNode, origin of the app
     */
    public World(Spatial vr, Spatial fly, ViewPort viewPort, AssetManager assetManager, Node rootNode) {
        this.vrObs = vr;
        this.flyObs = fly;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }
    /**
     * initializes all elements of the game world itself
     */
    public void init() {
        env = new Environment(vrObs, flyObs, viewPort, assetManager, rootNode);
        env.init();
    }

    public void update() {
        env.update();
    }
    public void render(RenderManager rm) {
        env.render(rm);
    }

    public void swapCamera() {
        env.swapCamera();
    }

    public String getCamera() {
        return env.getCamera();
    }

    public void moveObs(Vector3f move) {
        env.moveObs(move);
    }

    public void rotateObs(float x, float y, float z) {
        env.rotateObs(x, y, z);
    }

    public String getObserver() {
        return env.getObserver();
    }

}
