package com.github.migi_1.Context.model;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.damageDealers.DamageDealer;
import com.github.migi_1.Context.damageDealers.DamageDealerGenerator;
import com.github.migi_1.Context.model.entity.Camera;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.ConstantSpeedMoveBehaviour;
import com.github.migi_1.Context.model.entity.Platform;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * The Environment class handles all visual aspects of the world, excluding the characters and enemies etc.
 * @author Damian
 */
public class MainEnvironment extends Environment {
    private ViewPort viewPort;

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

    private boolean flyCamActive;

    private LevelGenerator levelGenerator;


    private CollisionResults results;

    private DamageDealerGenerator damageDealerGenerator;


    /**
     * First method that is called after the state has been created.
     * Handles all initialization of parameters needed for the Environment.
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        viewPort = app.getViewPort();
        flyObs = new Camera();
        steering = 0.f;
        flyCamActive = false;

        viewPort.setBackgroundColor(BACKGROUNDCOLOR);


        damageDealerGenerator = new DamageDealerGenerator(COMMANDER_LOCATION);

        results = new CollisionResults();

        //creates the lights
        initLights();

        //creates shadowmap and attach it to the sun
        initShadows();

        //initializes all spatials and set the positions
        initSpatials();

        //Init the camera
        initCameras();
    }

    @Override
    public void update(float tpf) {
    	super.update(tpf);

        checkCollision();
    	updateTestWorld();
    }

    private void checkCollision() {
        if (results.size() > 0) {
            getRootNode().detachChild(results.getClosestCollision().getGeometry());
            damageDealerGenerator.getObstacles().remove(results.getClosestCollision().getGeometry());
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
        getRootNode().addLight(sun);
        getRootNode().addLight(sun2);
    }

    /**
     * Initializes all shadows of the scene.
     */
    private void initShadows() {
        DirectionalLightShadowRenderer dlsr =
                new DirectionalLightShadowRenderer(getAssetManager(), SHADOWMAP_SIZE, SHADOW_SPLITS);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

        //adds shadow filter and attaches it to the viewport
        DirectionalLightShadowFilter dlsf =
                new DirectionalLightShadowFilter(getAssetManager(), SHADOWMAP_SIZE, SHADOW_SPLITS);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(getAssetManager());
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
        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(COMMANDER_LOCATION)) {
            addDisplayable(levelPiece);
        }

        for (DamageDealer damageDealer : damageDealerGenerator.getObstacles().values()) {
            damageDealer.collideWith(platform.getModel().getWorldBound(), results);
            addDisplayable(damageDealer);
        }

        addEntity(platform);
        addEntity(commander);
    }

    /**
     * Initializes the cameras and sets its location and rotation.
     * Starts with the VR camera.
     */
    private void initCameras() {
        commander.getModel().rotate(0f, COMMANDER_ROTATION, 0f);
        flyObs.getModel().setLocalTranslation(new Vector3f(-12f, 0f, -16f));
        flyObs.getModel().setLocalRotation(new Quaternion(0f, 0f, 0f, 1f));

        commander.makeObserver();
        addEntity(flyObs);
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
            VRApplication.setObserver(commander.getModel());
            flyCamActive = false;
        }
    }

    /**
     * Updates the test world.
     */
    private void updateTestWorld() {

        //delete level piece when it too far back
        for (LevelPiece levelPiece : levelGenerator.deleteLevelPieces(commander.getModel().getLocalTranslation())) {
        	removeDisplayable(levelPiece);
        }
        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(commander.getModel().getLocalTranslation())) {
        	addDisplayable(levelPiece);
        }
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
