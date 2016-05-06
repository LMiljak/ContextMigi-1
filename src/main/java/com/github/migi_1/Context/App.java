package com.github.migi_1.Context;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class App extends SimpleApplication {
    World world;
    
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        world = new World(flyCam, viewPort, assetManager, rootNode);
        world.init();
    }

    @Override
    public void simpleUpdate(float tpf) {
        world.update();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        world.render(rm);
    }
}
