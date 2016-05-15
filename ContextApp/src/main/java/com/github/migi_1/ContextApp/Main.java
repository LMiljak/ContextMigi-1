package com.github.migi_1.ContextApp;

import com.github.migi_1.Context.screens.MainMenuAndroid;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    private MainMenuAndroid menu;
    private static Main app;
    
    public static void main(String[] args) {
        app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        menu = new MainMenuAndroid();
        menu.initMenu(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(),
                app.getGuiViewPort());
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
    public void gyroscopeChange(){
    }
}
