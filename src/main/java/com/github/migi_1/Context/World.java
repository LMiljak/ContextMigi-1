/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.Context;

import com.github.migi_1.Context.design.Environment;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

/**
 *
 * @author TUDelft SID
 */
public class World {
    private Environment env;
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private FlyByCamera flyCam;
    
    public World(FlyByCamera flyCam, ViewPort viewPort, AssetManager assetManager, Node rootNode) {
        this.flyCam = flyCam;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
        this.rootNode = rootNode;        
    }
    void init() {
        env = new Environment(flyCam, viewPort, assetManager, rootNode);
        env.init();
    }
    
}
