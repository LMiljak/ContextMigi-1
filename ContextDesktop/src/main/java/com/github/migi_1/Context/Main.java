package com.github.migi_1.Context;

import com.github.migi_1.Context.model.Environment;
import com.github.migi_1.Context.screens.MainMenu;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

import jmevr.app.VRApplication;

/**
 * Creates the main desktop application, it initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */
public class Main extends VRApplication {
    //the main menu state
    private MainMenu mainMenuState;

    //the game state
    private Environment environmentState;

    //the main application
    private static Main main;

    /**
     * Movements of the flycam.
     */
    private boolean forwards, back, left, right, up, down;

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
        main.setPauseOnLostFocus(true);
        main.start();
    }

    /**
     * First method that is called when the application launches.
     * Sets the input, initializes all states and loads the main menu.
     */
    @Override
    public void simpleInitApp() {
        initInputs();

        mainMenuState = new MainMenu();
        environmentState = new Environment();

        this.getStateManager().attach(mainMenuState);
    }

    /**
     * handles all updates.
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (getStateManager().hasState(environmentState)){
            getStateManager().getState(Environment.class).update(tpf);
        }
        if(forwards) environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf*8f));
        if(back) environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf*8f));
        if(left) environmentState.rotateCam(0f, 0.75f*tpf, 0f);
        if(right) environmentState.rotateCam(0, -0.75f*tpf, 0);
        if(up) environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(tpf*8f));
        if(down) environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(-tpf*8f));
    }

    /**
     * handles everything that needs rendering.
     * @param rm the rendermanager
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Key bindings:
     * Escape key: Exit the game
     * c: switches camera
     * When in flycam:
     * w: move forwards
     * s: move backwars
     * a: move left
     * d: move right
     * Lshift: move down
     * space: move up
     * ---MORE CAN BE ADDED IF NEEDED---
     */
     private void initInputs() {
         InputManager inputManager = getInputManager();
         addMappings(inputManager);
         ActionListener acl = new ActionListener() {

             @Override
             public void onAction(String name, boolean keyPressed, float tpf) {
                 System.out.println(environmentState.getCamera().toString());
                 if(name.equals("exit") && keyPressed) {
                     System.exit(0);
                 } else if(name.equals("cam_switch") && keyPressed) {
                     environmentState.swapCamera();
                 }

                 //Controls that only work with flycam.
                 if(environmentState.getCamera().toString().equals("FLY (Node)")) {
                     switch (name) {
                         case "forward":
                             forwards = keyPressed;
                             break;
                         case "back":
                             back = keyPressed;
                             break;
                         case "left":
                             left = keyPressed;
                             break;
                         case "right":
                             right = keyPressed;
                             break;
                         case "up":
                             up = keyPressed;
                             break;
                         case "down":
                             down = keyPressed;
                             break;
                         default: //Do nothing when an unknown button is pressed.
                     }
                 }
             }

         };
         addListeners(inputManager, acl);
    }

    /**
     * Method to configure the vr
     * called in the {@link simpleInitApp()} method;
     */
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
     * Adds all the mappings for the different function names to the different keys.
     * @param inputManager the inputmanager for which these keymappings are set.
     */
    private void addMappings(InputManager inputManager) {
        inputManager.addMapping("exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping("cam_switch", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_LSHIFT));
    }

    /**
     * Adds key-input event listeners to the inputmanager.
     * @param inputManager The input manager to which these listeners are added.
     * @param acl The actionlistener that listens to the key-input events.
     */
    private void addListeners(InputManager inputManager, ActionListener acl) {
        inputManager.addListener(acl, "exit");
        inputManager.addListener(acl, "cam_switch");
        inputManager.addListener(acl, "forward");
        inputManager.addListener(acl, "back");
        inputManager.addListener(acl, "left");
        inputManager.addListener(acl, "right");
        inputManager.addListener(acl, "up");
        inputManager.addListener(acl, "down");
    }


    /**
     * Returns the main menu state.
     * @return the mainMenu
     */
    public MainMenu getMainMenu() {
        return mainMenuState;
    }

    /**
     * Returns the environment state.
     * @return the env
     */
    public Environment getEnv() {
        return environmentState;
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
