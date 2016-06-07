package com.github.migi_1.Context.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Camera;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.model.entity.behaviour.CarrierMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.EntityMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.SumMultiMoveBehaviour;
import com.github.migi_1.Context.obstacle.Obstacle;
import com.github.migi_1.Context.obstacle.ObstacleSpawner;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
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
    private static final Vector3f RELATIVE_CARRIER_LOCATION = new Vector3f(-2, -3.5f, 3);

    private static final float COMMANDER_ROTATION = -1.5f;

    private Platform platform;
    private Commander commander;
    private ArrayList<Carrier> carriers;
    private DirectionalLight sun;
    private DirectionalLight sun2;

    private float steering;

    private boolean flyCamActive;

    private LevelGenerator levelGenerator;


    private HashMap<Entity, CollisionResults> results;

    private ObstacleSpawner obstacleSpawner;

    private BoundingBox boundingBoxWallLeft;

    private BoundingBox boundingBoxWallRight;


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



        results = new HashMap<Entity, CollisionResults>();

        //creates the lights
        initLights();

        //creates shadowmap and attach it to the sun
        initShadows();

        //initializes all spatials and set the positions
        initSpatials();

        //Init the camera
        initCameras();

        new CarrierAssigner(platform, ((Main) app).getServer(), this);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        checkObstacleCollision();
        checkPathCollision();
        updateTestWorld();
    }    

    /**
     * Handle collision checking.
     */
    private void checkObstacleCollision() {
        //add collision check for all obstacles

        for (Obstacle staticObstacle : obstacleSpawner.getObstacles()) {
            for (Entry<Entity, CollisionResults> entry: results.entrySet()) {
                staticObstacle.collideWith(entry.getKey().getModel().getWorldBound(), entry.getValue());
            }
        }

        //check whether a collision has taken place.
        //only one object can collide each update, two prevent two object from taking damage.
        Boolean collided  = false;
        for (Entry<Entity, CollisionResults> entry: results.entrySet()) {
            if (entry.getValue().size() > 0 && !collided) {
                collided = true;
                removeDisplayable(obstacleSpawner.removeDamageDealer());
                entry.setValue(new CollisionResults());
                ((EntityMoveBehaviour) entry.getKey().getMoveBehaviour()).collided();

            }
        }

        //reset all CollisionResults.
        for (Entry<Entity, CollisionResults> entry: results.entrySet()) {
            entry.setValue(new CollisionResults());
        }

    }

    private void checkPathCollision() {
        for (Carrier carrier : carriers) {
            if (boundingBoxWallLeft.intersects(carrier.getModel().getWorldBound())) {
                ((SumMultiMoveBehaviour) platform.getMoveBehaviour()).collisionLeft();
                ((SumMultiMoveBehaviour) commander.getMoveBehaviour()).collisionLeft();
            }
            else if (boundingBoxWallRight.intersects(carrier.getModel().getWorldBound())) {
                ((SumMultiMoveBehaviour) platform.getMoveBehaviour()).collisionRight();
                ((SumMultiMoveBehaviour) commander.getMoveBehaviour()).collisionRight();
            }
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
        carriers = createCarriers();
        obstacleSpawner = new ObstacleSpawner(commander);

        createWallBoundingBoxes();
        //attach all objects to the root pane
        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(COMMANDER_LOCATION)) {
            addDisplayable(levelPiece);
        }
        for (Obstacle staticObstacle : obstacleSpawner.getObstacles()) {
            addDisplayable(staticObstacle);
        }
        for (Path path : levelGenerator.getPathPieces(COMMANDER_LOCATION)) {
            addDisplayable(path);
        }

        addEntity(platform);
        addEntity(commander);
        for (Carrier carrier : carriers) {
            addEntity(carrier);
        }
    }

    private void createWallBoundingBoxes() {
        Path path = new Path();
        boundingBoxWallLeft = new BoundingBox(
                new Vector3f(0, 0, path.getModel().center().getLocalTranslation().z 
                        + ((BoundingBox) path.getModel().getWorldBound()).getZExtent()),
                        Float.MAX_VALUE,
                        100f, 1f);

        boundingBoxWallRight = new BoundingBox(
                new Vector3f(0, 0, path.getModel().center().getLocalTranslation().z 
                        - 1.5f * ((BoundingBox) path.getModel().getWorldBound()).getZExtent()),
                        Float.MAX_VALUE,
                        100f, 1f);
    }

    /**
     * Create the carriers.
     * @return Array with carriers
     */
    private ArrayList<Carrier> createCarriers() {
        carriers = new ArrayList<Carrier>();
        float x, y, z;
        y = RELATIVE_CARRIER_LOCATION.y;
        for (PlatformPosition position : PlatformPosition.values()) {
            x = RELATIVE_CARRIER_LOCATION.x;
            z = RELATIVE_CARRIER_LOCATION.z;

            //put two carriers on the right side.
            z = z * position.getzFactor();

            //put two carriers on the back side.
            x = x * position.getxFactor();

            Vector3f relativeLocation = new Vector3f(x, y, z);
            Carrier newCarrier = new Carrier(relativeLocation, position, this);
            ((CarrierMoveBehaviour) newCarrier.getMoveBehaviour()).setRelativeLocation(relativeLocation);
            results.put(newCarrier, new CollisionResults());
            carriers.add(newCarrier);
        }
        return carriers;
    }

    /**
     * Get the Commander.
     * @return commander
     */
    public Commander getCommander() {
        return commander;
    }

    /**
     * Get the carriers.
     * @return carriers
     */
    public ArrayList<Carrier> getCarriers() {
        return carriers;
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
        Vector3f loc = commander.getModel().getLocalTranslation();
        addDisplayables(loc);
        removeDisplayables(loc);
    }
    /**
     * Responsible for adding everything that needs displaying to the rootnode.
     * @param loc
     */
    private void addDisplayables(Vector3f loc) {

        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(loc)) {
            addDisplayable(levelPiece);
        }

        for (Path path : levelGenerator.getPathPieces(loc)) {
            addDisplayable(path);
        }

        //update the Obstacles
        for (Obstacle staticObstacle : obstacleSpawner.getObstacles()) {
            addDisplayable(staticObstacle);
        }

    }

    /**
     * Responsible for calling remove method on all entities that need removing from the rootnode.
     * @param loc
     */
    private void removeDisplayables(Vector3f loc) {

        //delete level piece when it is too far back
        for (LevelPiece levelPiece : levelGenerator.deleteLevelPieces(loc)) {
            removeDisplayable(levelPiece);
        }

        //delete path when it is too far back
        for (Path path : levelGenerator.deletePathPieces(loc)) {
            removeDisplayable(path);
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
     * Handles everything that happens when the MainEnvironment state is detached from the main application.
     */
    @Override
    public void cleanup() {
        viewPort.clearProcessors();
        this.getRootNode().removeLight(sun);
        this.getRootNode().removeLight(sun2);
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
