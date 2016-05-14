
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
     * @param vr, The camera that will display the viewpoint of the player
     * @param fly, The camera that you can freely move around with in the game.
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

    /**
     * Updates the world.
     */
    public void update() {
        env.update();
    }

    /**
     * Renders the world.
     * @param rm the RenderManager used.
     */
    public void render(RenderManager rm) {
        env.render(rm);
    }

    /**
     * Swap the cameras in the environment.
     * VRCam <-> FlyCam.
     */
    public void swapCamera() {
        env.swapCamera();
    }

    /**
     * Move the flycam.
     * @param move a vector representation of the movement of the flyCamera.
     */
    public void moveCam(Vector3f move) {
        env.moveCam(move);
    }

    /**
     * Rotate the flycam
     * @param x rotation value on the x-axis
     * @param y rotation value on the y-axis
     * @param z rotation value on the z-axis
     */
    public void rotateCam(float x, float y, float z) {
        env.rotateCam(x, y, z);
    }

    /**
     * Returns the current camera (which is a observer).
     * @return A string representation of the current camera:
     * "VR (Node)" or "FLY (Node)".
     */
    public String getCamera() {
        return env.getCamera();
    }

}
