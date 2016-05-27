package com.github.migi_1.Context;

import java.io.IOException;
import java.util.concurrent.Executors;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.screens.MainMenu;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * Creates the main desktop application. It initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */

public class Main extends VRApplication {
    //the main menu state
    private MainMenu mainMenuState;

    //the game state
    private MainEnvironment environmentState;

    //the main application
    private static Main main;


    /**
     * Movements of the flycam.
     */
    private boolean forwards, back, left, right, up, down;

    /**
     * main function of the appication, sets some meta-parameters of the application
     * and starts it.
     *
     * @param args
     * 		ignored.
     */
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Carried Away");
        settings.setResolution(1280, 720);
        settings.setVSync(true);

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
        environmentState = new MainEnvironment();
        ProjectAssetManager.getInstance().setAssetManager(getAssetManager());
        this.getStateManager().attach(mainMenuState);
        startServer();
    }

    /**
     * Starts the server and allows clients to connect to it.
     */
    private void startServer() {
    	try {
        	ClientFinder.getInstance().findClients(Executors.newFixedThreadPool(1));
			ServerWrapper.getInstance().startServer();
			new AccelerometerMessageHandler(this);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * handles all updates.
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (getStateManager().hasState(environmentState)) {
            getStateManager().getState(MainEnvironment.class).update(tpf);
        }
        if (forwards) {
        	environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf * 8f));
        }
        if (back) {
        	environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf * 8f));
        }
        if (left) {
        	environmentState.rotateCam(new Vector3f(0f, 0.75f * tpf, 0f));
        }
        if (right) {
        	environmentState.rotateCam(new Vector3f(0, -0.75f * tpf, 0));
        }
        if (up) {
        	environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(tpf * 8f));
        }
        if (down) {
        	environmentState.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(-tpf * 8f));
        }
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
     * Initialises the game inputs (keys).
     *
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
//                 System.out.println(environmentState.getCamera().toString());
                 if (name.equals("exit") && keyPressed) {
                     System.exit(0);
                 } else if (name.equals("cam_switch") && keyPressed) {
                     environmentState.swapCamera();
                 }

                 //Controls that only work with flycam.
                 if (environmentState.getFlyCamActive()) {
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
                 checkSteering(name, keyPressed);

             }

         };
         addListeners(inputManager, acl);
    }

     /**
      * Key binding for steering.
      * left: move left
      * right: move right
      * @param name Name of key action.
      * @param keyPressed True when pressed, false when released.
      */
     private void checkSteering(String name, boolean keyPressed) {
         if (keyPressed) {
             if (name.equals("steer_left")) {
                 environmentState.steer(-1.f);
             }
             else if (name.equals("steer_right")) {
                environmentState.steer(1.f);
            }
        }
        if (!keyPressed && (name.equals("steer_left") || name.equals("steer_right"))) {
            environmentState.steer(0.f);
        }
     }

    /**
     * Configures the VR.
     * Method to configure the vr.
     * called in the {@link simpleInitApp()} method;
     */
    private void configureVR() {
        main.preconfigureVRApp(PRECONFIG_PARAMETER.FLIP_EYES, false);
        // show gui even if it is behind things
        main.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_OVERDRAW, true);
        // faster VR rendering, requires some vertex shader changes (see jmevr/shaders/Unshaded.j3md)
        main.preconfigureVRApp(PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING, false);
        main.preconfigureVRApp(PRECONFIG_PARAMETER.NO_GUI, false);
        // set frustum distances here before app starts
        main.preconfigureFrustrumNearFar(0.1f, 512f);
        // use full screen distortion, maximum FOV, possibly quicker (not compatible with instancing)
        main.preconfigureVRApp(PRECONFIG_PARAMETER.USE_CUSTOM_DISTORTION, false);
        // runs faster when set to false, but will allow mirroring
        main.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW, false);
        // render two eyes, regardless of SteamVR
        main.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, false);
        // you can downsample for performance reasons
        main.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true);
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
        inputManager.addMapping("steer_left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("steer_right", new KeyTrigger(KeyInput.KEY_RIGHT));
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
        inputManager.addListener(acl, "steer_left");
        inputManager.addListener(acl, "steer_right");
    }


    /**
     * Steers the platform depending on the orientation of an accelerometer.
     *
     * @param orientation
     * 		The acceleration force along the z axis (including gravity).
     */
    public void handleAccelerometerMessage(float orientation) {
        environmentState.steer(orientation);
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
    public MainEnvironment getEnv() {
        return environmentState;
    }

    /**
     * @return
     * 		The root node.
     */
    @Override
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * Returns the only instance of main.
     * @return main.
     */
    public static Main getInstance() {
        return main;
    }
}
