package com.github.migi_1.Context.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.enemy.Enemy;
import com.github.migi_1.Context.enemy.EnemySpawner;
import com.github.migi_1.Context.main.HUDController;
import com.github.migi_1.Context.main.InputHandler;
import com.github.migi_1.Context.main.KeyInputListener;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.FlyCamera;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.obstacle.Obstacle;
import com.github.migi_1.Context.obstacle.ObstacleSpawner;
import com.github.migi_1.Context.score.Score;
import com.github.migi_1.Context.score.ScoreController;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.ImmobilisedMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.StartBugEventMessage;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.Server;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * The Environment class handles all visual aspects of the world, excluding the characters and enemies etc.
 * @author Damian
 */
public class MainEnvironment extends Environment implements KeyInputListener {
    private ViewPort viewPort;

    private FlyCamera flyObs;

    private static final ColorRGBA BACKGROUNDCOLOR = ColorRGBA.Blue;
    private static final Vector3f SUNVECTOR = new Vector3f(-.5f, -.5f, -.5f);
    private static final Vector3f SUNVECTOR_2 = new Vector3f(0, -1f, -.2f);

    private static final int SHADOWMAP_SIZE = 1024;
    private static final int SHADOW_SPLITS = 3;

    private static final Vector3f WORLD_LOCATION = new Vector3f(300, -20, 0);

    private static final Vector3f PLATFORM_LOCATION = new Vector3f(20, -18, -1);
    private static final Vector3f COMMANDER_LOCATION = new Vector3f(20, -10, -1f);
    private static final Vector3f RELATIVE_CARRIER_LOCATION = new Vector3f(-5f, -7.5f, 6f);

    private static final float COMMANDER_ROTATION = -1.5f;

    //This value is the time in milliseconds (1 second = 1000 ms).
    private static final long LOWER_BOUND_EVENT_TIME = 90000;

    //This value is in milliseconds.
    //It sort of sets the upper bound of the event time,
    //using formula: LOWER_BOUND_EVENT_TIME + RANGE_EVENT_TIME.
    private static final int RANGE_EVENT_TIME = 30000;

    private Application app;
    private Platform platform;
    private Commander commander;
    private DirectionalLight sun;
    private DirectionalLight sun2;

    private boolean flyCamActive;

    private CarrierAssigner carrierAssigner;
    private LevelGenerator levelGenerator;
    private EnemySpawner enemySpawner;

    private LinkedList<Enemy> enemies;


    private HashMap<Entity, CollisionResults> results;

    private ObstacleSpawner obstacleSpawner;
    private ScoreController scoreController;

    private HUDController hudController;

    private long randomEventTime;

    private CollisionHandler collisionHandler;

    private boolean constructed = false;

    /**
     * Constructor for MainEnvironment.
     *
     * @param carrierAssigner
     * 		The carrierAssigner that contains a map from all position to the addressed of the clients.
     */
    public MainEnvironment(CarrierAssigner carrierAssigner) {
        this.carrierAssigner = carrierAssigner;         
    }

    /**
     * First method that is called after the state has been created.
     * Handles all initialization of parameters needed for the Environment.
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = app;
        viewPort = app.getViewPort();
        flyObs = new FlyCamera();
        flyCamActive = false;

        viewPort.setBackgroundColor(BACKGROUNDCOLOR);
        scoreController = new ScoreController();          
        this.hudController = new HUDController(app);
        //creates the lights
        initLights();

        //creates shadowmap and attach it to the sun
        initShadows();

        //initializes all spatials and set the positions
        initSpatials();

        //Init the camera
        initCameras();
        
        
        //Init input
        initInput();
        constructed = true; 
        //Start the random event timer.
        setNewRandomEventTime();
        setPaused(true);
        
    }

    private void initInput() {
        if (isInitialized() && !constructed) {
            int[] keys = {KeyInput.KEY_R, KeyInput.KEY_P, KeyInput.KEY_C, KeyInput.KEY_M};
            for (int key : keys) {
                InputHandler.getInstance().register(this, key);
            }
        }
    }

    @Override
    public void update(float tpf) {
        if (!isPaused()) {
            super.update(tpf);
            checkRandomEvent();

            updateEnemies(tpf);
            checkGameOver();
            updateTestWorld();
            hudController.updateHUD();
            collisionHandler.checkObstacleCollision();
            collisionHandler.checkPathCollision();
        }
    }


    private void checkGameOver() {
        int count = 0;
        for (Carrier carrier: enemySpawner.getCarriers()) {
            if (carrier.isDead()) {
                count++;
            }
        }
        if (count >= 2) {
            hudController.gameOver();
            setPaused(true);
            if (!isGameOver()) {
                scoreController.addScore(new Score("placeholder", (int) hudController.getGameScore()));
            }
            setGameOver(true);
        }
    }




    /**
     * Checks every update if a random event should start.
     */
    private void checkRandomEvent() {
        //Time for a random event!
        if (System.currentTimeMillis() > randomEventTime) {
            StartBugEventMessage startMessage = new StartBugEventMessage(getRandomPosition(), getRandomPosition());
            Server server = getMain().getServer().getServer();
            //Message is send when:
            //The server is running.
            //There is no other bug event currently running
            //There are 4 people connected.
            if (server.isRunning() && !getMain().isBugEventRunning() && server.getConnections().size() > 0) {
                getMain().setBugEventRunning(true);
                server.broadcast(startMessage);
            }
            setNewRandomEventTime();
        }
    }

    /**
     * Retuns a random position.
     * @return a random platform position.
     */
    private PlatformPosition getRandomPosition() {
        int randomNumber = new Random().nextInt(4);
        return PlatformPosition.values()[randomNumber];
    }

    /**
     * Sets the randomEvent time to
     * LOWER_BOUND_EVENT_TIME to (LOWER_BOUND_EVENT_TIME + RANGE_EVENT_TIME)
     * seconds from the current time.
     */
    private void setNewRandomEventTime() {
        randomEventTime = System.currentTimeMillis() + new Random().nextInt(RANGE_EVENT_TIME) + LOWER_BOUND_EVENT_TIME;
    }

    /**
     * Initializes all lights of the scene.
     */
    private void initLights() {
        ((VRApplication) app).setBackgroundColors(ColorRGBA.Blue);

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

        enemies = new LinkedList<Enemy>();
        levelGenerator = new LevelGenerator(WORLD_LOCATION);
        platform = new Platform(PLATFORM_LOCATION, carrierAssigner);
        commander = new Commander(COMMANDER_LOCATION, platform);

        collisionHandler = new CollisionHandler(commander, platform, obstacleSpawner, this);
        collisionHandler.createWallBoundingBoxes();
        obstacleSpawner = new ObstacleSpawner(this);
        collisionHandler.setObstacleSpawner(obstacleSpawner);


        results = collisionHandler.getResults();



        //collisionHandler.setObstacleSpawner(obstacleSpawner);
        //attach all objects to the root pane
        for (LevelPiece levelPiece : levelGenerator.getLevelPieces(COMMANDER_LOCATION)) {
            addDisplayable(levelPiece);
        }
        for (Obstacle staticObstacle : obstacleSpawner.updateObstacles()) {
            addEntity(staticObstacle);
        }
        for (Path path : levelGenerator.getPathPieces(COMMANDER_LOCATION)) {
            addDisplayable(path);
        }
        for (PlatformPosition position : PlatformPosition.values()) {
            Carrier carrier = createCarrier(position);
            addEntity(carrier);
            platform.addCarrier(carrier);
        }

        addEntity(platform);
        addRotatable(platform);
        addEntity(commander);
        addRotatable(commander);
    }



    /**
     * Creates a carrier.
     *
     * @param position
     * 		The position under the platform to place the carrier.
     * @return
     * 		The created carrier.
     */
    public Carrier createCarrier(PlatformPosition position) {
        float x, y, z;
        x = RELATIVE_CARRIER_LOCATION.x;
        y = RELATIVE_CARRIER_LOCATION.y;
        z = RELATIVE_CARRIER_LOCATION.z;

        z *= position.getzFactor();
        x *= position.getxFactor();

        Vector3f relativeLocation = new Vector3f(x, y, z);

        Carrier newCarrier = new Carrier(relativeLocation, position, this);
        results.put(newCarrier, new CollisionResults());

        return newCarrier;
    }

    /**
     * Get the Commander.
     * @return commander
     */
    public Commander getCommander() {
        return commander;
    }

    /**
     * Gets the platform.
     * @return
     * 		The retrieved platform.
     */
    public Platform getPlatform() {
        return platform;
    }


    /**
     * Initializes the cameras and sets its location and rotation.
     * Starts with the VR camera.
     */
    private void initCameras() {
        commander.getModel().rotate(0f, COMMANDER_ROTATION, 0f);
        flyObs.getModel().setLocalTranslation(new Vector3f(COMMANDER_LOCATION.x, 0f, -16f));
        flyObs.getModel().setLocalRotation(new Quaternion(0f, 0f, 0f, 1f));

        commander.makeObserver();
        addEntity(flyObs);
        addRotatable(flyObs);
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
        flyObs.move(new Vector3f(-0.2f, 0, 0));
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
        for (Obstacle staticObstacle : obstacleSpawner.updateObstacles()) {
            addEntity(staticObstacle);
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

        for (Obstacle obstacle : obstacleSpawner.deleteObstacles()) {
            removeDisplayable(obstacle);
        }
    }

    private void updateEnemies(float tpf) {
        if (enemySpawner == null || enemySpawner.getCarriers().size() == 0) {
            enemySpawner = new EnemySpawner(commander, platform.getCarriers());
        } else {
            for (Enemy enemy : enemySpawner.generateEnemies()) {
                addEntity(enemy);
                enemies.add(enemy);
            }

            for (Enemy enemy : enemySpawner.deleteEnemies()) {
                removeEntity(enemy);
                enemies.remove(enemy);
            }

            for (Enemy enemy : enemies) {
                enemy.attack(tpf);
            }
        }
    }

    /**
     * Handles everything that happens when the MainEnvironment state is detached from the main application.
     */
    @Override
    public void cleanup() {        
        viewPort.clearProcessors();
        this.getRootNode().removeLight(sun);
        this.getRootNode().removeLight(sun2);
        if (enemySpawner != null) {
            for (Carrier carrier : enemySpawner.getCarriers()) {
                carrier.setHealth(3);
                ImmobilisedMessage message = new ImmobilisedMessage(false, carrier.getPosition());
                ServerWrapper serverWrapper = ((Main) app).getServer();
                Server server = serverWrapper.getServer();
                if (server.isRunning()) {
                    server.broadcast(message);
                }
            }
        }
        enemySpawner = null;
        this.setEnabled(false);
        super.cleanup();
    }

    /**
     * Returns the main application.
     * @return (Main) app.
     */
    public Main getMain() {
        return ((Main) app);
    }

    /**
     * Sets the results hashmap.
     * Used in testing ONLY.
     * @param newResults the new results.
     */
    public void setResults(HashMap<Entity, CollisionResults> newResults) {
        results = newResults;
    }

    /**
     * Getter for the left bounding box.
     * @return the left bounding box
     */
    public BoundingBox getLeftBound() {
        return collisionHandler.getBoundingBoxWallLeft();
    }

    /**
     * Getter for the right bounding box.
     * @return the right bounding box
     */
    public BoundingBox getRightBound() {
        return collisionHandler.getBoundingBoxWallRight();
    }

    /**
     * Sets the app.
     * @param app the app to set
     */
    public void setApp(Application app) {
        this.app = app;
    }

    @Override
    public void onKeyPressed(int key) {
        //System.out.println(isEnabled());
        if (isEnabled()) {
            switch (key) {
            case KeyInput.KEY_C:
                swapCamera();
                break;
            case KeyInput.KEY_P:
                setPaused(!isPaused());
                System.out.println(isPaused());
                break;
            case KeyInput.KEY_R:
                cleanup();
                ((Main) app).toLobby();
                break;
            case KeyInput.KEY_M:
                getAudioController().mute();
                break;
            default: 
            }
        }
    }

    @Override
    public void onKeyReleased(int key) { }
}
