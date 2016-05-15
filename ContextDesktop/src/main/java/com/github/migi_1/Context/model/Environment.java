package com.github.migi_1.Context.model;

import java.util.ArrayList;

import com.github.migi_1.Context.Main;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * The Environment class handles all visual aspects of the world, excluding the characters and enemies etc.
 * @author Damian
 */
public class Environment extends AbstractAppState {
    private Main app;
    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;
    private Camera cam;

    private static final ColorRGBA BACKGROUNDCOLOR = ColorRGBA.Blue;
    private static final Vector3f SUNVECTOR = new Vector3f(-.5f,-.5f,-.5f);
    private static final Vector3f SUNVECTOR_2 = new Vector3f(0, -1f, -.2f);

    private static final int SHADOWMAP_SIZE = 1024;
    private static final int SHADOW_SPLITS = 3;

    private static final Vector3f WORLD_LOCATION = new Vector3f(0, -20, 0);
    private static final Vector3f PLATFORM_LOCATION = new Vector3f(20, -18, -3);
    private static final Vector3f COMMANDER_LOCATION = new Vector3f(23, -14, -3.5f);
    private static final float COMMANDER_ROTATION = -1.5f;

    private static final Vector3f CAMERA_LOCATION = new Vector3f(22, -14, -3.5f);

    private static final float PLATFORM_SPEED = 0.2f;

    private Spatial testPlatform;
    private ArrayList<Spatial> testWorld;
    private Spatial testCommander;

    private DirectionalLight sun;
    private DirectionalLight sun2;

    /**
     * First method that is called after the state has been created.
     * Handles all initialization of parameters needed for the Environment.
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
        super.initialize(stateManager, app);
        this.app = (Main) app;
        this.testWorld = new ArrayList();
        assetManager = app.getAssetManager();
        viewPort = app.getViewPort();
        cam = app.getCamera();
        rootNode = this.app.getRootNode();

        //deprecated method, it does however makse it possible to load assets from a non default location
        assetManager.registerLocator("assets", FileLocator.class);

        viewPort.setBackgroundColor(BACKGROUNDCOLOR);

        //creates the lights
        initLights();

        //creates shadowmap and attach it to the sun
        initShadows();

        //initializes all spatials and set the positions
        initSpatials();

        //Init the camera
        initCamera();


    }

    /**
     * Updates all enitites of the environment.
     */
    @Override
    public void update(float tpf) {
        super.update(tpf);
        testPlatform.move(-PLATFORM_SPEED, 0, 0);
        testCommander.move(-PLATFORM_SPEED, 0, 0);
        Vector3f camLoc = testCommander.getLocalTranslation();
        cam.setLocation(new Vector3f(camLoc.x - PLATFORM_SPEED, camLoc.y, camLoc.z));
       
        
    }

    /**
     * Initializes all lights of the scene.
     */
    private void initLights() {
        sun = new DirectionalLight();
        sun2 = new DirectionalLight();

        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(SUNVECTOR).normalizeLocal());

        sun2.setColor(ColorRGBA.White);
        sun2.setDirection(new Vector3f(SUNVECTOR_2).normalizeLocal());

        //adds the lights to the root pane
        rootNode.addLight(sun);
        rootNode.addLight(sun2);
    }

    /**
     * Initializes all shadows of the scene.
     */
    private void initShadows() {
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, SHADOW_SPLITS);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

        //adds shadow filter and attaches it to the viewport
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, SHADOW_SPLITS);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);
    }

    /**
     * Initializes all objects and translations/rotations of the scene.
     */
    private void initSpatials() {
        for(int i = 0; i < 5; i++){
            testWorld.add(assetManager.loadModel("Models/testWorld.j3o"));
            testWorld.get(i).move(WORLD_LOCATION);
            BoundingBox bb = (BoundingBox)testWorld.get(i).getWorldBound();
            WORLD_LOCATION.x -=2*bb.getXExtent();
//            WORLD_LOCATION.x -= 2*testWorld.get(i).getWorldTranslation()
        }


        testPlatform = assetManager.loadModel("Models/testPlatform.j3o");
        testPlatform.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        testPlatform.move(PLATFORM_LOCATION);
        testCommander = assetManager.loadModel("Models/ninja.j3o");
        testCommander.rotate(0, COMMANDER_ROTATION, 0);
        testCommander.move(COMMANDER_LOCATION);

        //attach all objects to the root pane
        for(Spatial sp : testWorld){
            rootNode.attachChild(sp);
        }

        rootNode.attachChild(testPlatform);
        rootNode.attachChild(testCommander);
    }

    /**
     * Initializes the camera and sets its location and rotation.
     */
    private void initCamera() {
        cam.setLocation(CAMERA_LOCATION);
        cam.setRotation(testCommander.getLocalRotation());
    }

    /**
     * render the entities
     * @param rm manager of the renderengine
     */
    @Override
    public void render(RenderManager rm) {

    }

    /**
     * Handles everything that happens when the Envrironment state is detached from the main application.
     */
    @Override
    public void cleanup() {
        super.cleanup();
    }

    ////////////////////////////////////Below methods might be used later on when the pause screen is introduced.
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInitialized() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setEnabled(boolean arg0) {
        // TODO Auto-generated method stub

    }


}
