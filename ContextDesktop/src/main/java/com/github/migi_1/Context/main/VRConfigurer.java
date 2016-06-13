package com.github.migi_1.Context.main;

import jmevr.app.VRApplication.PRECONFIG_PARAMETER;

/**
 * Configures the VR setup for the game.
 * @author Nils
 *
 */
public final class VRConfigurer {

    /**
     * Method to configure the vr.
     * @param main the main Menu that the VR has to be configured for.
     */
    public static void configureVR(Main main) {
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
        main.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW, true);
        // render two eyes, regardless of SteamVR
        main.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, false);
        // you can downsample for performance reasons
        main.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true);
    }
}
