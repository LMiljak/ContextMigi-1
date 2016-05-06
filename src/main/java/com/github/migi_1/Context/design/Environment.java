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
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * The Environement class handles all visual aspects of the world, excluding the characters and enemies etc.
 * @author Damian
 */
public class Environment {    
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private FlyByCamera flyCam;
    
    private Spatial testPlatform;
    private Spatial testWorld;
    private Spatial testCommander;
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
        
        //creates the lights 
        DirectionalLight sun = new DirectionalLight();
        DirectionalLight sun2 = new DirectionalLight();

        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());    

        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(0, -1f, -.2f).normalizeLocal());      
        
        //adds the lights to the root pane
        rootNode.addLight(sun);
        rootNode.addLight(sun2);        
        
        //creates shadowmap and attach it to the sun
        final int SHADOWMAP_SIZE=1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);
        
        //adds shadow filter and attaches it to the viewport
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);
        
        //initializes all spatials and set the positions
        testWorld = assetManager.loadModel("Models/testWorld.j3o");
        testWorld.move(0, -20, 0);
        testPlatform = assetManager.loadModel("Models/testPlatform.j3o");
        testPlatform.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        testPlatform.move(20, -18, -3);
        testCommander = assetManager.loadModel("Models/ninja.j3o");
        testCommander.rotate(0, -1.5f, 0);
        testCommander.move(23, -14, -3.5f);
        
        //attach all objects to the root pane
        rootNode.attachChild(testWorld);
        rootNode.attachChild(testPlatform);
        rootNode.attachChild(testCommander);

    }
    
    /**
     * update the entities
     */
    public void update() {
        testPlatform.move(-0.02f, 0, 0);
        testCommander.move(-0.02f, 0, 0);
    }
    
    /**
     * render the entities
     * @param rm manager of the renderengine
     */
    public void render(RenderManager rm) {        
        
    }
}
