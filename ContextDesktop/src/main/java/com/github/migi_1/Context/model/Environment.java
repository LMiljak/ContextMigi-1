package com.github.migi_1.Context.model;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.Main;
import com.github.migi_1.Context.damageDealers.DamageDealer;
import com.github.migi_1.Context.damageDealers.DamageDealerGenerator;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.ConstantSpeedMoveBehaviour;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
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

    private Camera vrObs;
    private Camera flyObs;

    private static final ColorRGBA BACKGROUNDCOLOR = ColorRGBA.Blue;
    private static final Vector3f SUNVECTOR = new Vector3f(-.5f, -.5f, -.5f);
    private static final Vector3f SUNVECTOR_2 = new Vector3f(0, -1f, -.2f);

    private static final int SHADOWMAP_SIZE = 1024;
    private static final int SHADOW_SPLITS = 3;

    private static final Vector3f WORLD_LOCATION = new Vector3f(300, -20, 0);

    private static final Vector3f PLATFORM_LOCATION = new Vector3f(20, -18, -1);
    private static final Vector3f COMMANDER_LOCATION = new Vector3f(23, -14, -1f);

    private static final float COMMANDER_ROTATION = -1.5f;

    private Platform platform;
    private Commander commander;

    private DirectionalLight sun;
    private DirectionalLight sun2;

    private float steering;


    private CollisionResults results;

    private float decay;

    private DamageDealerGenerator obstacleGenerator;

    private boolean flyCamActive;

    private LevelGenerator levelGenerator;

    /**
     * First method that is called after the state has been created.
     * Handles all initialization of parameters needed for the Environment.
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        super.initialize(stateManager, app);
        this.app = (Main) app;
        assetManager = ProjectAssetManager.getInstance().getAssetManager();

        viewPort = app.getViewPort();
        vrObs = new Camera();
        flyObs = new Camera();
        rootNode = this.app.getRootNode();
        steering = 0.f;
        results = new CollisionResults();
        decay = 1;
        obstacleGenerator = new DamageDealerGenerator(this);
        flyCamActive = false;

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
        initCameras();
    }

    /**
     * Update the entities and cameras.
     */
    @Override
    public void update(float tpf) {

        super.update(tpf);

//        float adjustedSpeed = -PLATFORM_SPEED * decay;
        platform.move(platform.getMoveBehaviour().getMoveVector());
        commander.move(commander.getMoveBehaviour().getMoveVector());
//        NOT USED IN THIS VERSION, WILL BE REFACTORED IN SEPERATE BRANCH, MAY STILL BE NEEDED
//        Vector3f loc = commander.getModel().getLocalTranslation();//
//        float xAxis = 1;
//        float zAxis = 0;
//        if (loc.z > 5f) {
//            zAxis = 0.5f;
//        } else if (loc.z < -5f) {
//            zAxis = -0.5f;
//        } else {
//            zAxis = steering * STEERING_ANGLE;
//            xAxis = (float) Math.sqrt(1 - Math.pow(zAxis, 2));
//        }

        platform.move(platform.getMoveBehaviour().getMoveVector());
        commander.move(commander.getMoveBehaviour().getMoveVector());
        vrObs.getModel().setLocalTranslation(commander.getModel().getLocalTranslation());

        updateTestWorld();

        //add collision check for all obstacles
        for (DamageDealer damageDealer : obstacleGenerator.getObstacles().values()) {
            damageDealer.collideWith(platform.getModel().getWorldBound(), results);
            damageDealer.move(damageDealer.getMoveBehaviour().getMoveVector());
        }

        // regain speed
        if (decay < 1.0f) {
            decay = decay + 0.01f;
        }

        //if a collision takes place, remove the colliding object and slow down
        if (results.size() > 0) {
            rootNode.detachChild(results.getClosestCollision().getGeometry());
            obstacleGenerator.getObstacles().remove(results.getClosestCollision().getGeometry());
            results = new CollisionResults();
            ((ConstantSpeedMoveBehaviour) platform.getMoveBehaviour()).collided();
            ((ConstantSpeedMoveBehaviour) commander.getMoveBehaviour()).collided();
        }
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
        DirectionalLightShadowRenderer dlsr =
                new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, SHADOW_SPLITS);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

        //adds shadow filter and attaches it to the viewport
        DirectionalLightShadowFilter dlsf =
                new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, SHADOW_SPLITS);
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

        levelGenerator = new LevelGenerator(WORLD_LOCATION);
        platform = new Platform(PLATFORM_LOCATION);
        commander = new Commander(COMMANDER_LOCATION);

        //attach all objects to the root pane
        for (DamageDealer obs : obstacleGenerator.getObstacles().values()) {
            rootNode.attachChild(obs.getModel());
        }
        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(COMMANDER_LOCATION)) {
            rootNode.attachChild(levelPiece.getModel());
        }

        rootNode.attachChild(platform.getModel());
        rootNode.attachChild(commander.getModel());
    }

    /**
     * Initializes the cameras and sets its location and rotation.
     * Starts with the VR camera.
     */
    private void initCameras() {
        vrObs.getModel().setLocalTranslation(new Vector3f(0f, 0f, 0f));
        vrObs.getModel().rotate(0f, COMMANDER_ROTATION, 0f);
        flyObs.getModel().setLocalTranslation(new Vector3f(-12f, 0f, -16f));
        flyObs.getModel().setLocalRotation(new Quaternion(0f, 0f, 0f, 1f));

        VRApplication.setObserver(vrObs.getModel());
        rootNode.attachChild(vrObs.getModel());
        rootNode.attachChild(flyObs.getModel());
    }

    /**
     * Renders the entities.
     * @param rm manager of the renderengine
     */
    @Override
    public void render(RenderManager rm) { }

    /**
     * Moves the flycam.
     * @param move a vector representation of the movement of the flyCamera.
     */
    public void moveCam(Vector3f move) {
        flyObs.getModel().move(move);
    }

    /**
     * Rotates the flycam.
     * @param rotation the rotation vector.
     */
    public void rotateCam(Vector3f rotation) {
        flyObs.getModel().rotate(rotation.x, rotation.y, rotation.z);
    }

    /**
     * Returns the current camera (which is a observer).
     * @return A string representation of the current camera:
     * "VR (Node)" or "FLY (Node)".
     */
    public boolean getFlyCamActive() {
        return flyCamActive;
    }

    /**
     * Swap the cameras in the environment.
     * VRCam <-> FlyCam.
     */
    public void swapCamera() {
        if (!flyCamActive) {
            VRApplication.setObserver(flyObs.getModel());
            flyCamActive = true;
        } else {
            VRApplication.setObserver(vrObs.getModel());
            flyCamActive = false;
        }
    }

    /**
     * Updates the test world.
     */
    private void updateTestWorld() {

        //delete level piece when it too far back
        for (LevelPiece levelPiece : levelGenerator.deleteLevelPieces(commander.getModel().getLocalTranslation())) {
            rootNode.detachChild(levelPiece.getModel());
        }
        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(commander.getModel().getLocalTranslation())) {
            rootNode.attachChild(levelPiece.getModel());
        }
    }

    /**
     * Returns the location of the commander.
     * @return location of the commander
     */
    public Vector3f getCommanderLocation() {
        return COMMANDER_LOCATION;
    }

    /**
     * Return the assetMananager.
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Update the steering direction.
     * @param orientation This translates to the steering ange.
     */
    public void steer(float orientation) {
        steering = orientation;
    }

    /**
     * Handles everything that happens when the Envrironment state is detached from the main application.
     */
    @Override
    public void cleanup() {
        super.cleanup();
    }

    /**
     * Setter for the flycam.
     * @param cam the new flycam.
     */
    public void setFlyCam(Camera cam) {
        flyObs = cam;
    }

    /**
     * Returns the steering variable.
     * @return steering.
     */
    public float getSteering() {
        return steering;
    }
}
