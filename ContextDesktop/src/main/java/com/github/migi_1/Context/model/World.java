
package com.github.migi_1.Context.model;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

/**
 * This class handles all logic of the game world itself.
 * @author Damian
 */
public class World {
    private Environment env;
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private Camera cam;

    /**
     * Constructor for the world object
     * @param cam, The camera that will display the viewpoint of the player
     * @param viewPort, Main screen of the game, this will be rendered
     * @param assetManager, loads and manages all assets of the world
     * @param rootNode, origin of the app
     */
    public World(Camera cam, ViewPort viewPort, AssetManager assetManager, Node rootNode) {
        this.cam = cam;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }
    /**
     * initializes all elements of the game world itself
     */
    public void init() {
        env = new Environment(cam, viewPort, assetManager, rootNode);
        env.init();
    }

    public void update() {
        env.update();
    }
    public void render(RenderManager rm) {
        env.render(rm);

    }

}
