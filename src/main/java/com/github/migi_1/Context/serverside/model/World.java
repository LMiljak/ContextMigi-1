
package com.github.migi_1.Context.serverside.model;

import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
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
    private FlyByCamera flyCam;
    
    /**
     * Constructor for the world object
     * @param flyCam, The camera for flying around in the world (will be removed)
     * @param viewPort, Main screen of the game, this will be rendered
     * @param assetManager, loads and manages all assets of the world
     * @param rootNode, origin of the app
     */
    public World(FlyByCamera flyCam, ViewPort viewPort, AssetManager assetManager, Node rootNode) {
        this.flyCam = flyCam;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
        this.rootNode = rootNode;        
    }
    /**
     * initializes all elements of the game world itself
     */
    public void init() {
        env = new Environment(flyCam, viewPort, assetManager, rootNode);
        env.init();
    }
    
    public void update() {
        env.update();
    }
    public void render(RenderManager rm) {
        env.render(rm);
        
    }
    
}
