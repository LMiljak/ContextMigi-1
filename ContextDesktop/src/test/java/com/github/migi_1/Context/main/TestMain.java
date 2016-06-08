package com.github.migi_1.Context.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.screens.MainMenu;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.network.serializing.Serializable;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * TestSuite for the Main class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
@Serializable
public class TestMain {

    private Main main;
    private InputManager inputManager;
    private InputHandler inputHandler;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;

    /**
     * Setup for the testsuite.
     * This method is called every time a test is started.
     */
    @Before
    public void setUp() {
        main = Mockito.spy(new Main());
        inputManager = Mockito.mock(InputManager.class);
        inputHandler = Mockito.mock(InputHandler.class);
        Mockito.when(main.getInputManager()).thenReturn(inputManager);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Main.setMain(main);
    }

    /**
     * Tests if the setup of the main is done properly.
     * @throws Exception when the invokeMethod calls
     * an unknown private method.
     */
    @Test
    public void testSetUpMain() throws Exception {
        Whitebox.invokeMethod(main, "setUpMain");
        //Verify all settings are set correctly.
        AppSettings settings = main.getSettings();
        assertEquals("Carried Away", settings.getTitle());
        assertEquals(1280, settings.getWidth());
        assertEquals(720, settings.getHeight());
        assertTrue(settings.isVSync());
    }

    /**
     * Tests if the getInstance method returns the main object.
     */
    @Test
    public void testGetInstance() {
        assertEquals(main, Main.getInstance());
    }

    /**
     * Tests if the simple init app behaves the way it should.
     * Also tests the private launchServer() method.
     * @throws Exception when verifyPrivate checks an unknown method.
     */
    @Test
    public void testSimpleInitApp() throws Exception {
        main.simpleInitApp();
        //Verify the inputs are all added.
        Mockito.verify(main).getInputManager();
        //Verify the main menu state is added to the statemanager.
        assertNotNull(main.getStateManager().getState(MainMenu.class));
        //Verify the MainEnvironment state is created.
        assertNotNull(main.getEnv());
        //Verify the MainEnvironment state is not added to the statemanager.
        assertNull(main.getStateManager().getState(MainEnvironment.class));
    }

    /**
     * Tests the simple update method.
     */
    @Test
    public void testSimpleUpdate() {
        //Calling this method to instantiate the inputHandler.
        main.setInputHandler(inputHandler);
        main.simpleUpdate(0);
        Mockito.verify(inputHandler).moveCamera(Mockito.anyFloat());
        //Verify that the environment state is not in the state manager yet.
        assertNull(main.getStateManager().getState(MainEnvironment.class));

        MainEnvironment mainEnvState = Mockito.mock(MainEnvironment.class);
        main.setEnvState(mainEnvState);
        //Attach a mainEnvironment state to the state manager.
        main.getStateManager().attach(mainEnvState);
        //Verify that the environment state added to the state manager.
        assertNotNull(main.getStateManager().getState(MainEnvironment.class));
        main.simpleUpdate(0);
        //Verify that the main environment state (when added) is updated.s
        Mockito.verify(mainEnvState).update(Mockito.anyFloat());
    }

    /**
     * Tests the simpleRender method.
     */
    @Test
    public void testSimpleRender() {
        RenderManager renderManager = Mockito.mock(RenderManager.class);
        main.simpleRender(renderManager);
        //Verify this method is not in use.
        Mockito.verifyZeroInteractions(renderManager);
    }

//    /**
//     * Tests the handleAccelerometerMessage.
//     */
//    @Test
//    public void testHandleAccelerometerMessage() {
//        MainEnvironment mainEnvState = Mockito.mock(MainEnvironment.class);
//        main.setEnvState(mainEnvState);
//        //Check if no steering works.
//        main.handleAccelerometerMessage(0);
//        Mockito.verify(mainEnvState).steer(0);
//        //Check if steering left works.
//        main.handleAccelerometerMessage(-1);
//        Mockito.verify(mainEnvState).steer(-1);
//        //Check if steering right works.
//        main.handleAccelerometerMessage(1);
//        Mockito.verify(mainEnvState).steer(1);
//    }

    /**
     * Tests if the getter for the main menu state works.
     */
    @Test
    public void testGetMainMenu() {
        MainMenu mainMenuState = Mockito.mock(MainMenu.class);
        main.setMainMenuState(mainMenuState);
        assertEquals(mainMenuState, main.getMainMenu());
    }

    /**
     * Tests if the getter for the root node works.
     */
    @Test
    public void testGetRootNode() {
        assertEquals(Node.class, main.getRootNode().getClass());
        //Verify this also works with a "regular" main
        Main newMain = new Main();
        assertEquals(newMain.getRootNode().getClass(), main.getRootNode().getClass());
    }

    /**
     * Tests if the getter for the gui node works.
     */
    @Test
    public void testGetGUINode() {
        assertEquals(Node.class, main.getGuiNode().getClass());
        //Verify this also works with a "regular" main
        Main newMain = new Main();
        assertEquals(newMain.getGuiNode().getClass(), main.getGuiNode().getClass());
    }

    /**
     * Tests if the getter for the settings works.
     * @throws Exception When invokeMethod tries to invoke an unknown method.
     */
    @Test
    public void testGetSettings() throws Exception {
        Whitebox.invokeMethod(main, "setUpMain");
        assertEquals(AppSettings.class, main.getSettings().getClass());
        //Verify this also works with a "regular" main
        Main newMain = new Main();
        assertEquals(newMain.getSettings().getClass(), main.getSettings().getClass());
    }
}
