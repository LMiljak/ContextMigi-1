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
 * @author NilsHullegien
 */
public class VRHandler extends VRApplication {

    //VR main
    private VRHandler vrh;

    private boolean forw, back, left, right, up, down;
    private static Spatial vrObserver;
    private static Spatial flyObserver;


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

    // general objects for scene management
    private World world;

    /**
     * Method called when the vrhandler is started.
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
      * ---MORE CAN BE ADDED IF NEEDED---
      */
      private void initInputs() {
          InputManager inputManager = getInputManager();
          inputManager.addMapping("exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
          inputManager.addMapping("cam_switch", new KeyTrigger(KeyInput.KEY_C));
          inputManager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
          inputManager.addMapping("back", new KeyTrigger(KeyInput.KEY_S));
          inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
          inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
          inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_SPACE));
          inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_LSHIFT));
          ActionListener acl = new ActionListener() {

              @Override
              public void onAction(String name, boolean keyPressed, float tpf) {
                  if(name.equals("exit") && keyPressed) {
                      System.exit(0);
                  } else if(name.equals("cam_switch") && keyPressed) {
                      world.swapCamera();
                  }
                  //Controls that should only work with flycam.
                  System.out.println("CURRENT OBS: " + world.getObserver().toString());
                  if(world.getObserver().toString().equals("FLY (Node)")) {
                      if(name.equals("forward")){
                          if(keyPressed){
                              forw = true;
                          } else {
                              forw = false;
                          }
                      } else if(name.equals("back")){
                          if(keyPressed){
                              back = true;
                          } else {
                              back = false;
                          }
                      } else if(name.equals("left")){
                          if(keyPressed){
                              left = true;
                          } else {
                              left = false;
                          }
                      } else if(name.equals("right")){
                          if(keyPressed){
                              right = true;
                          } else {
                              right = false;
                          }
                      } else if(name.equals("up")){
                          if(keyPressed){
                              up = true;
                          } else {
                              up = false;
                          }
                      } else if(name.equals("down")){
                          if(keyPressed){
                              down = true;
                          } else {
                              down = false;
                          }
                      }

                  }
              }

          };
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
       * Overwritten method that updates the current world.
       */
       @Override
       public void simpleUpdate(float tpf){
           world.update();
           if(forw){
               System.out.println("MOVING FORWARD");
               world.moveObs(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf*8f));
           }
           if(back){
               System.out.println("MOVING BACKWARD");
               world.moveObs(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf*8f));
           }
           if(left){
               System.out.println("MOVING LEFT");
               world.rotateObs(0f, 0.75f*tpf, 0f);
           }
           if(right){
               System.out.println("MOVING RIGHT");
               world.rotateObs(0, -0.75f*tpf, 0);
           }
           if(up) {
               System.out.println("MOVING UP");
               world.moveObs(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(tpf*8f));
           }
           if(down) {
               System.out.println("MOVING DOWN");
               world.moveObs(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(-tpf*8f));
           }
       }


      /**
       * Render the world.
       */
    @Override
    public void simpleRender(RenderManager rm) {
        world.render(rm);
    }
}