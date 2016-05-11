package com.github.migi_1.Context.vr;



import com.github.migi_1.Context.model.World;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;

import jmevr.app.VRApplication;

/**
 *
 * @author NilsHullegien
 */
public class VRHandler extends VRApplication {

    // set some VR settings & start the app
    public static void main(String[] args){
        VRHandler test = new VRHandler();
        //test.preconfigureVRApp(PRECONFIG_PARAMETER.USE_STEAMVR_COMPOSITOR, false); // disable the SteamVR compositor (kinda needed at
        test.preconfigureVRApp(PRECONFIG_PARAMETER.FLIP_EYES, false);
        test.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_OVERDRAW, true); // show gui even if it is behind things
        test.preconfigureVRApp(PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING, false); // faster VR rendering, requires some vertex shader changes (see jmevr/shaders/Unshaded.j3md)
        test.preconfigureVRApp(PRECONFIG_PARAMETER.NO_GUI, false);
        test.preconfigureFrustrumNearFar(0.1f, 512f); // set frustum distances here before app starts
        //test.preconfigureResolutionMultiplier(0.666f); the moment)
        test.preconfigureVRApp(PRECONFIG_PARAMETER.USE_CUSTOM_DISTORTION, false); // use full screen distortion, maximum FOV, possibly quicker (not compatible with instancing)
        test.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW, false); // runs faster when set to false, but will allow mirroring
        test.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, false); // render two eyes, regardless of SteamVR
        test.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true);// you can downsample for performance reasons
        test.start();
    }

    // general objects for scene management
    private World world;

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

     @Override
     public void simpleUpdate(float tpf){
         world.update();
     }

     /**
      * Key bindings
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


    @Override
    public void simpleRender(RenderManager rm) {
        world.render(rm);
    }
}