package com.github.migi_1.Context;

import java.util.concurrent.TimeUnit;

import org.junit.rules.Timeout;

import com.github.migi_1.Context.model.Environment;
import com.github.migi_1.Context.screens.MainMenu;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import jmevr.app.VRApplication;

/**
 * Creates the main desktop application, it initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */
public class Main extends VRApplication {
    private MainMenu mainMenu;
    private Environment env;
    private static Main main;
    private boolean test = false;
    /**
     * main function of the appication, sets some meta-parameters of the application
     * and starts it.
     * @param args 
     */
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Carried Away");
        settings.setResolution(1280, 720);
        
        main = new Main();
        main.setSettings(settings);
        main.configureVR();
        main.start();
    }
    
    /**
     * initializes the main menu and starts it.
     */
    @Override
    public void simpleInitApp() {        
        main.setPauseOnLostFocus(true);
        mainMenu = new MainMenu();
        env = new Environment(this.rootNode);
        
        this.getStateManager().attach(mainMenu);
        
        
    }
    
    private void configureVR() {
        main.preconfigureVRApp(PRECONFIG_PARAMETER.FLIP_EYES, false);
        main.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_OVERDRAW, true); // show gui even if it is behind things
        main.preconfigureVRApp(PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING, false); // faster VR rendering, requires some vertex shader changes (see jmevr/shaders/Unshaded.j3md)
        main.preconfigureVRApp(PRECONFIG_PARAMETER.NO_GUI, false);
        main.preconfigureFrustrumNearFar(0.1f, 512f); // set frustum distances here before app starts
        main.preconfigureVRApp(PRECONFIG_PARAMETER.USE_CUSTOM_DISTORTION, false); // use full screen distortion, maximum FOV, possibly quicker (not compatible with instancing)
        main.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW, false); // runs faster when set to false, but will allow mirroring
        main.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, false); // render two eyes, regardless of SteamVR
        main.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true);// you can downsample for performance reasons
    }
    
    /**
     * handles all updates.
     * @param tpf 
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (test){ 
            System.out.println("asdas");  
            getStateManager().detach(mainMenu);             
            getStateManager().attach(env);
            //getStateManager().getState(Environment.class).update(tpf);
            test = false; 
        }
        //getStateManager().getState(Environment.class).update(tpf);
    }
    
    /**
     * handles everything that needs rendering.
     * @param rm 
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void setTest() {
        test = true;
    }
    
}
