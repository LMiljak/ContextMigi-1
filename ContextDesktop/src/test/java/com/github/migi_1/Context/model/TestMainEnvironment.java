package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.github.migi_1.Context.audio.AudioController;
import com.github.migi_1.Context.main.HUDController;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Camera;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.model.entity.behaviour.AccelerometerMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.EntityMoveBehaviour;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResults;
import com.jme3.material.MatParamTexture;
import com.jme3.material.MaterialDef;
import com.jme3.math.Vector3f;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Test class that tests the MainEnvironment class.
 * @author Nils
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestMainEnvironment {

    private MainEnvironment env;
    private AppStateManager stateManager;
    private Main app;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private ViewPort viewPort;
    private Node rootNode, guiNode;
    private MaterialDef matDef;
    private MatParamTexture matParam;
    private Spatial model;
    private RenderManager renderManager;
    private Camera cam;
    private HUDController hudController;
    private AudioController audioController;
    private Entity entity;
    private EntityMoveBehaviour moveBehaviour;
    private Path path;
    private AudioNode backgroundMusic;
    private CarrierAssigner carrierAssigner;
    private Platform platform;



    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        try {
            AccelerometerMoveBehaviour amb = Mockito.mock(AccelerometerMoveBehaviour.class);
            Mockito.when(amb.getMoveVector()).thenReturn(Vector3f.ZERO);
            PowerMockito.whenNew(AccelerometerMoveBehaviour.class)
            .withNoArguments().thenReturn(amb);
        } catch (Exception e) {
            e.printStackTrace();
        }



        entity = Mockito.mock(Entity.class);
        hudController = Mockito.mock(HUDController.class);
        audioController = Mockito.mock(AudioController.class);
        stateManager = Mockito.mock(AppStateManager.class);
        app = Mockito.mock(Main.class);
        viewPort = Mockito.mock(ViewPort.class);
        rootNode = Mockito.mock(Node.class);
        guiNode = Mockito.mock(Node.class);
        matDef = Mockito.mock(MaterialDef.class);
        matParam = Mockito.mock(MatParamTexture.class);
        model =  Mockito.mock(Spatial.class);
        renderManager = Mockito.mock(RenderManager.class);
        cam = Mockito.mock(Camera.class);
        moveBehaviour = Mockito.mock(EntityMoveBehaviour.class);
        path = Mockito.mock(Path.class);
        backgroundMusic = Mockito.mock(AudioNode.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        carrierAssigner = Mockito.mock(CarrierAssigner.class);
        platform = Mockito.mock(Platform.class);

        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.whenNew(HUDController.class).withAnyArguments().thenReturn(hudController);
        PowerMockito.whenNew(AudioController.class).withAnyArguments().thenReturn(audioController);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(cam.getModel()).thenReturn(model);
        Mockito.when(app.getViewPort()).thenReturn(viewPort);
        Mockito.when(app.getRootNode()).thenReturn(rootNode);
        Mockito.when(assetManager.loadAsset(Mockito.any(AssetKey.class))).thenReturn(matDef);
        Mockito.when(matDef.getMaterialParam(Mockito.anyString())).thenReturn(matParam);
        Mockito.when(model.getWorldBound()).thenReturn(new BoundingBox(new Vector3f(0, 0, 0), 0, 0, 0));
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(500, 500, 500));
        Mockito.when(entity.getModel()).thenReturn(model);
        Mockito.when(entity.getMoveBehaviour()).thenReturn(moveBehaviour);
        Mockito.when(app.getGuiNode()).thenReturn(guiNode);
        Mockito.when(path.getModel()).thenReturn(model);
        Mockito.when(model.center()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));
        Mockito.when(audioController.getBackgroundMusic()).thenReturn(backgroundMusic);
        ServerWrapper wrapper = Mockito.mock(ServerWrapper.class);
        PowerMockito.mockStatic(ServerWrapper.class);
        Mockito.when(app.getServer()).thenReturn(wrapper);
        Mockito.when(wrapper.getServer()).thenReturn(Mockito.mock(Server.class));
        PowerMockito.whenNew(Platform.class).withAnyArguments().thenReturn(platform);
        PowerMockito.whenNew(CarrierAssigner.class).withAnyArguments().thenReturn(carrierAssigner);
        env = PowerMockito.spy(new MainEnvironment());
    }

    /**
     * Test for the initialize method.
     * NOTE: The initialize method will be used in other tests as well.
     */
    @Test
    public void intializeTest() {
        env.initialize(stateManager, app);
        Mockito.verify(rootNode, Mockito.atLeastOnce()).attachChild(Mockito.<Spatial>any());
    }

    /**
     * Test for the render method.
     */
    @Test
    public void renderTest() {
        env.render(renderManager);
        Mockito.verifyZeroInteractions(renderManager);
    }

    /**
     * Test for the moveCam method.
     */
    @Test
    public void moveCamTest() {
        env.initialize(stateManager, app);
        env.setFlyCam(cam);
        env.moveCam(new Vector3f(-1, 1, 1));
        Mockito.verify(model, Mockito.atLeastOnce()).move(Mockito.<Vector3f>any());
    }

    /**
     * Test for the rotateCam method.
     */
    @Test
    public void rotateCamTest() {
        env.initialize(stateManager, app);
        env.setFlyCam(cam);
        env.rotateCam(new Vector3f(-1, 1, 1));
        Mockito.verify(model, Mockito.atLeastOnce()).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
    }

    /**
     * Test for the swapCamera and getFlyCamActive method.
     */
    @Test
    public void swapCamTest() {
        env.initialize(stateManager, app);
        assertFalse(env.getFlyCamActive());
        env.swapCamera();
        assertTrue(env.getFlyCamActive());
        env.swapCamera();
        assertFalse(env.getFlyCamActive());
    }

    /**
     * Test for the steer method.
     */
    @Test
    public void steerTest() {
        env.initialize(stateManager, app);
        env.steer(-1.0f);
        assertEquals(-1.0f, env.getSteering(), 0.0);
        env.steer(1.0f);
        assertEquals(1.0f, env.getSteering(), 0.0);
    }

    /**

     * Test for the updateTestWorld method.
     * @throws Exception when the invokeMethod() method can't find the method specified in its parameters.
     */
    @Test
    public void updateTestWorldTest() throws Exception {
        env.initialize(stateManager, app);
        Whitebox.invokeMethod(env, "updateTestWorld");
        //Verify that everything is still in the right place.
        Mockito.verify(rootNode, Mockito.atLeastOnce()).attachChild(Mockito.<Spatial>any());
        Mockito.verify(rootNode, Mockito.times(0)).detachChild(Mockito.<Spatial>any());
    }

    /**
     * Test for the updateTestWorld method.
     * @throws Exception when the invokeMethod() method can't find the method specified in its parameters.
     */
    @Test
    public void updateTestWorldUpdateTwiceTest() throws Exception {
        env.initialize(stateManager, app);
        Whitebox.invokeMethod(env, "updateTestWorld");
        //Verify that everything is still in the right place.
        Mockito.verify(rootNode, Mockito.atLeastOnce()).attachChild(Mockito.any());
        Mockito.verify(rootNode, Mockito.times(0)).detachChild(Mockito.any());

        Whitebox.invokeMethod(env, "updateTestWorld");
        //Verify that everything is still in the right place.
        Mockito.verify(rootNode, Mockito.atLeastOnce()).attachChild(Mockito.any());
        Mockito.verify(rootNode, Mockito.times(0)).detachChild(Mockito.any());
    }

    /**
     * Tests the getCarriers method.
     */
    //    @Test
    //    public void getCarriersTest() {
    //        env.initialize(stateManager, app);
    //        assertEquals(4, env.getCarriers().size());
    //    }

    /**
     * Verifies the checkCollision method works the way it should.
     * @throws Exception when the invokeMethod() method can't find the specified method.
     */
    @Test
    public void checkCollisionCollidingTest() throws Exception {
        env.initialize(stateManager, app);
        //Add a mocked results hashmap to simulate the collision.
        HashMap<Entity, CollisionResults> newResults = new HashMap<Entity, CollisionResults>();
        //Add collisionResults to trigger the removal of the object.
        CollisionResults collisionResults = new CollisionResults();
        collisionResults.addReusedCollision(0, 0, 0, 0);
        newResults.put(entity, collisionResults);
        //Set the mocked results as results for now.
        env.setResults(newResults);
        //Call the checkCollision method.
        Whitebox.invokeMethod(env, "checkObstacleCollision");
        //Verify the mocked object has collided.
        Mockito.verify(moveBehaviour).collided();
    }

    /**
     * Tests the cleanup method.
     */
    @Test
    public void cleanupTest() {
        env.initialize(stateManager, app);
        env.cleanup();
        Mockito.verify(rootNode, Mockito.times(2)).removeLight(Mockito.any());
        Mockito.verify(viewPort).clearProcessors();
    }
}
