package com.github.migi_1.Context.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import com.github.migi_1.Context.model.LobbyEnvironment;
import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

/**
 * TestSuite for the Main class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestMain {

    private Main main;
    private InputManager inputManager;
    private InputHandler inputHandler;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private ServerWrapper serverWrapper;
    private Server server;
    private MainEnvironment mainEnv;
    private LobbyEnvironment lobbyEnv;

    /**
     * Setup for the testsuite.
     * This method is called every time a test is started.
     * @throws Exception when a newly created object has other arguments.
     */
    @Before
    public void setUp() throws Exception {
        main = Mockito.spy(new Main());
        mainEnv = Mockito.mock(MainEnvironment.class);
        lobbyEnv = Mockito.mock(LobbyEnvironment.class);
        inputManager = Mockito.mock(InputManager.class);
        inputHandler = Mockito.mock(InputHandler.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);
        Mockito.when(main.getInputManager()).thenReturn(inputManager);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.doNothing().when(mainEnv).cleanup();
        Mockito.doNothing().when(lobbyEnv).cleanup();
        Mockito.when(serverWrapper.getServer()).thenReturn(server);
        main.setServerWrapper(serverWrapper);
        main.setEnvState(mainEnv);
        main.setLobby(lobbyEnv);
        Mockito.when(server.isRunning()).thenReturn(true);
        Main.setMain(main);
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
        assertNotNull(main.getStateManager().getState(LobbyEnvironment.class));
        //Verify the MainEnvironment state is created.
        assertNotNull(main.getEnv());
        //Verify the MainEnvironment state is not yet added to the statemanager.
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

    /**
     * Tests if the getter for the main menu state works.
     */
    @Test
    public void testGetMainMenu() {
        LobbyEnvironment lobbyState = Mockito.mock(LobbyEnvironment.class);
        main.setLobby(lobbyState);
        assertEquals(lobbyState, main.getLobby());
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
     * Tests if enabling the spray message functions correctly.
     */
    @Test
    public void testHandleEnableSprayMessage() {
        main.handleEnableSprayMessage(PlatformPosition.FRONTLEFT);
        Mockito.verify(server).broadcast(Mockito.any());
    }

    /**
     * Tests if stopping the bug event functions correctly.
     */
    @Test
    public void testHandleStopBugEvent() {
        main.setBugEventRunning(true);
        main.handleStopBugEvent();
        Mockito.verify(server).broadcast(Mockito.any());
    }

    /**
     * Tests setter and getter of inLobby boolean.
     */
    @Test
    public void testGetAndSetInLobby() {
        assertFalse(main.getInLobby());
        main.setInLobby(true);
        assertTrue(main.getInLobby());
    }

    /**
     * tests getter and setter for the bugEventRunning boolean.
     */
    @Test
    public void testGetAndSetBugEventRunning() {
        assertFalse(main.isBugEventRunning());
        main.setBugEventRunning(true);
        assertTrue(main.isBugEventRunning());
    }

    /**
     * Test going to the lobby functions correctly.
     */
    @Test
    public void testToLobby() {
        main.toLobby();
        //Verify the lobby is entered.
        Mockito.verify(main).setInLobby(true);
    }
}
