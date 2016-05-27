package com.github.migi_1.ContextApp;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * The Main class of the Application.
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    private static Main app;

    /**
     * Starts the application.
     * 
     * @param args 
     *      Ignored.
     */
    public static void main(String[] args) {
        app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    /**
     * This method has no functionality, it is merely used to show you can communicate between activity and game.
     */
    public void gyroscopeChange() {
    }
}
