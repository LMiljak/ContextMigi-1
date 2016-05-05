/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.Context.design;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.FlyByCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * The Environement class handles all visual aspects of the world, excluding the characters and enemies etc.
 * @author Damian
 */
public class Environment {    
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private FlyByCamera flyCam;
    
    /**
     * Constructor for the environment object
     * @param flyCam, The camera for flying around in the world (will be removed)
     * @param viewPort, Main screen of the game, this will be rendered
     * @param assetManager, loads and manages all assets of the world
     * @param rootNode, origin of the app
     */
    public Environment(FlyByCamera flyCam, ViewPort viewPort, AssetManager assetManager, Node rootNode) {
        this.flyCam = flyCam;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }

    public void init() {
        //deprecated method, it does however make it possible to load assets from a non default location
        assetManager.registerLocator("assets", FileLocator.class);           
        
        flyCam.setMoveSpeed(50);      
        viewPort.setBackgroundColor(ColorRGBA.Blue);

        DirectionalLight sun = new DirectionalLight();
        DirectionalLight sun2 = new DirectionalLight();

        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());    

        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(0, -1f, -.2f).normalizeLocal());      

        rootNode.addLight(sun2);
        rootNode.addLight(sun2);

        Spatial testWorld = assetManager.loadModel("Models/testWorld.j3o");
        testWorld.move(0, -20, 0);
        rootNode.attachChild(testWorld);

    }
}
