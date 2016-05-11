package com.github.migi_1.Context;

import com.github.migi_1.Context.screens.MainMenu;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * Creates the main desktop application, it initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */
public class Main extends SimpleApplication {
    private MainMenu menu;
    
    /**
     * main function of the appication, sets some meta-parameters of the application
     * and starts it.
     * @param args 
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.setPauseOnLostFocus(true);
        app.setDisplayFps(true);
        app.start();
    }
    
    /**
     * initializes the main menu and starts it.
     */
    @Override
    public void simpleInitApp() {
        menu = new MainMenu();
        menu.initMenu(flyCam, assetManager, inputManager, audioRenderer, guiViewPort);
    }
    
    /**
     * handles all updates.
     * @param tpf 
     */
    @Override
    public void simpleUpdate(float tpf) {
        
    }
    
    /**
     * handles everything that needs rendering.
     * @param rm 
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
