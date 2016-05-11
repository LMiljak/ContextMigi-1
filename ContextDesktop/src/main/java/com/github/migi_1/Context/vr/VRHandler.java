package com.github.migi_1.Context.vr;



import com.github.migi_1.Context.model.World;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;

import jmevr.app.VRApplication;

/**
 * VR handler for the current world.
 * @author NilsHullegien
 */
public class VRHandler extends VRApplication {

    //VR main
    private VRHandler vrh;


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
        world = new World(getCamera(), getViewPort(), getAssetManager(), rootNode);
        world.init();

        // print out what device we have
        if( VRApplication.getVRHardware() != null ) {
            System.out.println("Attached device: " + VRApplication.getVRHardware().getName());
        }
    }

    /**
     * Overwritten method that updates the current world.
     */
     @Override
     public void simpleUpdate(float tpf){
         world.update();
     }

     /**
      * Key bindings:
      * Escape key: Exit the game
      * ---MORE CAN BE ADDED IF NEEDED---
      */
      private void initInputs() {
          InputManager inputManager = getInputManager();
          inputManager.addMapping("exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
          ActionListener acl = new ActionListener() {

              @Override
              public void onAction(String name, boolean keyPressed, float tpf) {
                  if(name.equals("exit") && keyPressed){
                      System.exit(0);
                  }
              }

          };
          inputManager.addListener(acl, "exit");
     }


      /**
       * Render the world.
       */
    @Override
    public void simpleRender(RenderManager rm) {
        world.render(rm);
    }
}