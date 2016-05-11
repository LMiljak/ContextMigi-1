package com.github.migi_1.Context;

import com.github.migi_1.Context.screens.MainMenu;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;

/**
 * 
 * @author Damian
 */
public class Main extends SimpleApplication {
    private MainMenu menu;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        menu = new MainMenu();
        menu.initMenu(flyCam, assetManager, inputManager, audioRenderer, guiViewPort);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
