package com.github.migi_1.Context.vr;

import com.github.migi_1.Context.model.World;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import jmevr.app.VRApplication;

/**
 * VR handler for the current world.
 * @author Nils
 */
public class VRHandler extends VRApplication {

    /**
     * VR main.
     */
    private VRHandler vrh;

    /**
     * Movements of the flycam.
     */
    private boolean forwards, back, left, right, up, down;
    /**
     * The vrCamera.
     */
    private static Spatial vrObserver;

    /**
     * The free flyCamera.
     */
    private static Spatial flyObserver;

    /**
     * Object in which the world and environment are created.
     */
    private World world;


    /**
     * The method that needs to be called in the main app.
     * @param vrhandler the VRHandler that is responsible for the visualisation of the game in the Oculus.
     */
    public void initVR(){
        vrh = this;
        vrh.configureVR();
        vrh.start();

    }

    /**
     * Method to configure the VR.
     * Called in the {@link initVR() } method.
     */
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
     * Method called when the vrhandler is started.
     * Sets the key inputs and initializes the cameras as well.
     */
    @Override
    public void simpleInitApp() {
        initInputs();

        vrObserver = new Node("VR");
        flyObserver = new Node("FLY");
        world = new World(vrObserver, flyObserver, getViewPort(), getAssetManager(), rootNode);
        world.init();
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
                  if(name.equals("exit") && keyPressed) {
                      System.exit(0);
                  } else if(name.equals("cam_switch") && keyPressed) {
                      world.swapCamera();
                  }

                  //Controls that only work with flycam.
                  if(world.getCamera().toString().equals("FLY (Node)")) {
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
       * Overwritten method that updates the current world.
       * Moves the flyCam when the button is pressed (that is picked up by the actionlistener in the {@link initInputs() } method).
       */
       @Override
       public void simpleUpdate(float tpf){
           world.update();
           if(forwards) world.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf*8f));
           if(back) world.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf*8f));
           if(left) world.rotateCam(0f, 0.75f*tpf, 0f);
           if(right) world.rotateCam(0, -0.75f*tpf, 0);
           if(up) world.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(tpf*8f));
           if(down) world.moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(-tpf*8f));
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
       * Render the world.
       */
        @Override
        public void simpleRender(RenderManager rm) {
            world.render(rm);
        }
}