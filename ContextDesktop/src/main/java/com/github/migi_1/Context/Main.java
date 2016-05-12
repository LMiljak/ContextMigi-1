package com.github.migi_1.Context;

import com.github.migi_1.Context.screens.MainMenu;
import com.jme3.renderer.RenderManager;
import jmevr.app.VRApplication;

/**
 * Creates the main desktop application, it initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */
public class Main extends VRApplication {
    private MainMenu menu;
    private static Main vrh;
    /**
     * main function of the appication, sets some meta-parameters of the application
     * and starts it.
     * @param args 
     */
    public static void main(String[] args) {
        vrh = new Main();
        vrh.configureVR();
        vrh.start();
    }
    
    /**
     * initializes the main menu and starts it.
     */
    @Override
    public void simpleInitApp() {        
        vrh.setPauseOnLostFocus(true);
        menu = new MainMenu();
        menu.initMenu(vrh.getAssetManager(), vrh.getInputManager(), vrh.getAudioRenderer(),
                vrh.getGuiViewPort());
    }
    
    private void configureVR() {
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.FLIP_EYES, false);
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_OVERDRAW, true); // show gui even if it is behind things
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING, false); // faster VR rendering, requires some vertex shader changes (see jmevr/shaders/Unshaded.j3md)
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.NO_GUI, false);
        vrh.preconfigureFrustrumNearFar(0.1f, 512f); // set frustum distances here before app starts
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.USE_CUSTOM_DISTORTION, false); // use full screen distortion, maximum FOV, possibly quicker (not compatible with instancing)
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW, false); // runs faster when set to false, but will allow mirroring
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, false); // render two eyes, regardless of SteamVR
        vrh.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true);// you can downsample for performance reasons
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
