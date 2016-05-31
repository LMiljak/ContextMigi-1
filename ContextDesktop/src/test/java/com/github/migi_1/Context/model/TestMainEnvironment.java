package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.github.migi_1.Context.main.HUDController;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Camera;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.MatParamTexture;
import com.jme3.material.MaterialDef;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Test class that tests the Environment class.
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
    private Node rootNode;
    private MaterialDef matDef;
    private MatParamTexture matParam;
    private Spatial model;
    private RenderManager renderManager;
    private Camera cam;
    private HUDController hudController;

    /**
     * This method starts every time a new test case starts.
     */
    @Before
    public void setUp() throws Exception {
        env = PowerMockito.spy(new MainEnvironment());

        hudController = Mockito.mock(HUDController.class);
        stateManager = Mockito.mock(AppStateManager.class);
        app = Mockito.mock(Main.class);
        viewPort = Mockito.mock(ViewPort.class);
        rootNode = Mockito.mock(Node.class);
        matDef = Mockito.mock(MaterialDef.class);
        matParam = Mockito.mock(MatParamTexture.class);
        model =  Mockito.mock(Spatial.class);
        renderManager = Mockito.mock(RenderManager.class);
        cam = Mockito.mock(Camera.class);

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.whenNew(HUDController.class).withAnyArguments().thenReturn(hudController);
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
    }

    /**
     * Test for the initialize method.
     * NOTE: The initialize method will be used in other tests as well.
     */
    @Test
    public void intializeTest() {
        env.initialize(stateManager, app);
        Mockito.verify(rootNode, Mockito.atLeastOnce()).attachChild(Mockito.any());
    }

    /**
     * Test for the update method.
     */
    @Test
    public void updateTest() {
        env.initialize(stateManager, app);
        env.update(0.1f);
        Mockito.verify(model, Mockito.atLeastOnce()).move(Mockito.any());
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
        Mockito.verify(model, Mockito.atLeastOnce()).move(Mockito.any());
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
     * Test for the cleanup method.
     */
    @Test
    public void cleanupTest() {
        env.cleanup();
        Mockito.verifyNoMoreInteractions(app);
        Mockito.verifyNoMoreInteractions(stateManager);
        Mockito.verifyNoMoreInteractions(assetManager);
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
        Mockito.verify(rootNode, Mockito.atLeastOnce()).attachChild(Mockito.any());
        Mockito.verify(rootNode, Mockito.times(0)).detachChild(Mockito.any());
    }

}