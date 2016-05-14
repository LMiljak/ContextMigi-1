package com.github.migi_1.Context.model;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

import jmevr.app.VRApplication;

/**
 * The Environment class handles all visual aspects of the world, excluding the characters and enemies etc.
 * @author Damian
 */
public class Environment {
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

    private static final float PLATFORM_SPEED = 0.02f;

    private ViewPort viewPort;
    private AssetManager assetManager;
    private Node rootNode;

    private Spatial vrObs;
    private Spatial flyObs;

    private Spatial testPlatform;
    private Spatial testWorld;
    private Spatial testCommander;

    private DirectionalLight sun;
    private DirectionalLight sun2;
    /**
     * Constructor for the environment object
     * @param cam, The camera that will display the viewpoint of the player
     * @param viewPort, Main screen of the game, this will be rendered
     * @param assetManager, loads and manages all assets of the world
     * @param rootNode, origin of the app
     */
    public Environment(Spatial vr, Spatial fly, ViewPort viewPort, AssetManager assetManager, Node rootNode) {
        this.vrObs = vr;
        this.flyObs = fly;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }

    /**
     * Initializes everything of the game world.
     */
    public void init() {
        //deprecated method, it does however make it possible to load assets from a non default location
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
        testWorld = assetManager.loadModel("Models/testWorld.j3o");
        testWorld.move(WORLD_LOCATION);
        testPlatform = assetManager.loadModel("Models/testPlatform.j3o");
        testPlatform.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        testPlatform.move(PLATFORM_LOCATION);
        testCommander = assetManager.loadModel("Models/ninja.j3o");
        testCommander.rotate(0, COMMANDER_ROTATION, 0);
        testCommander.move(COMMANDER_LOCATION);

        //attach all objects to the root pane
        rootNode.attachChild(testWorld);
        rootNode.attachChild(testPlatform);
        rootNode.attachChild(testCommander);
    }

    /**
     * Initializes the camera and sets its location and rotation.
     */
    private void initCamera() {
        vrObs.setLocalTranslation(new Vector3f(0f, 0f, 0f));
        vrObs.rotate(0f, COMMANDER_ROTATION, 0f);
        flyObs.setLocalTranslation(new Vector3f(-12f, 0f, -16f));
        flyObs.setLocalRotation(new Quaternion(0f, 0f, 0f, 1f));

        VRApplication.setObserver(vrObs);
        rootNode.attachChild(vrObs);
    }

    /**
     * Update the entities and camera.
     */
    public void update() {
        testPlatform.move(-PLATFORM_SPEED, 0, 0);
        testCommander.move(-PLATFORM_SPEED, 0, 0);
        Spatial tempCam = rootNode.detachChildAt(3);
        if(tempCam.getName().equals("VR")) {
            tempCam.setLocalTranslation(testCommander.getLocalTranslation());
            System.out.println("Location: " + tempCam.getLocalTranslation());
            rootNode.attachChild(tempCam);
        } else if(tempCam.getName().equals("FLY")){
            rootNode.attachChild(tempCam);
        } else {
            throw new IllegalStateException("A wrong node has entered rootnode where the camera should be: " + tempCam.getName());
        }
    }

    /**
     * render the entities
     * @param rm manager of the renderengine
     */
    public void render(RenderManager rm) {}


    public void swapCamera() {
        Spatial obs = rootNode.detachChildAt(3);
        if(obs.getName().equals("VR")) {
            VRApplication.setObserver(flyObs);
            rootNode.attachChild(flyObs);
        } else if(obs.getName().equals("FLY")){
            VRApplication.setObserver(vrObs);
            rootNode.attachChild(vrObs);
        }
    }

    public String getCamera() {
        return rootNode.getChild(3).getName();
    }

    public void moveObs(Vector3f move) {
        flyObs.move(move);
        System.out.println("New Pos: " + flyObs.getLocalTranslation());
    }

    public void rotateObs(float x, float y, float z) {
        System.out.println("Old rotation: " + rootNode.getChild(3).getLocalRotation());
        flyObs.rotate(x, y, z);
        System.out.println("New rotation: " + rootNode.getChild(3).getLocalRotation());
    }

    public String getObserver() {
        return VRApplication.getObserver().toString();
    }
}
