package com.github.migi_1.Context.main;

import java.io.IOException;
import java.util.concurrent.Executors;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.screens.MainMenu;
import com.github.migi_1.Context.server.AttackMessageHandler;
import com.github.migi_1.Context.server.ClientFinder;
import com.github.migi_1.Context.server.EnableSprayToVRMessageHandler;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.server.StopEventMessageHandler;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.EnableSprayToAppMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

import jmevr.app.VRApplication;

/**
 * Creates the main desktop application. It initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */

public class Main extends VRApplication {
    //the main menu state
    private MainMenu mainMenuState;

    //the game state
    private MainEnvironment environmentState;

    //the main application
    private static Main main;

    private InputHandler inputHandler;

    private static AppSettings settings;

    private ServerWrapper server;

    private boolean bugEventRunning = false;

    private AttackMessageHandler attackMessageHandler;
    private EnableSprayToVRMessageHandler enableSprayReceiveHandler;
    private StopEventMessageHandler stopEventHandler;


    /**
     * main function of the application, sets some meta-parameters of the application
     * and starts it.
     *
     * @param args
     * 		ignored.
     */
    public static void main(String[] args) {
        main = new Main();
        main.setUpMain();
        main.start();
    }

    /**
     * Configuration for main.
     * In this method the AppSettings and VRConfiguration are done.
     */
    private void setUpMain() {
        settings = new AppSettings(true);
        settings.setTitle("Carried Away");
        settings.setResolution(1280, 720);
        settings.setVSync(true);

        super.setSettings(settings);
        VRConfigurer.configureVR(this);
        super.setPauseOnLostFocus(true);

    }

    /**
     * First method that is called when the application launches.
     * Sets the input, initializes all states and loads the main menu.
     */
    @Override
    public void simpleInitApp() {
        inputHandler = new InputHandler(main);
        inputHandler.initInputs(main);

        launchServer();

        mainMenuState = new MainMenu();
        environmentState = new MainEnvironment();
        ProjectAssetManager.getInstance().setAssetManager(getAssetManager());
        this.getStateManager().attachAll(mainMenuState);
        // Probably not the right spot, but I'll put this here for now.
        attackMessageHandler = new AttackMessageHandler(this);
        enableSprayReceiveHandler = new EnableSprayToVRMessageHandler(this);
        stopEventHandler = new StopEventMessageHandler(this);
    }

    /**
     * Creates, initialises and starts the server.
     */
    private void launchServer() {
    	try {
			this.server = new ServerWrapper();
			ClientFinder.getInstance().findClients(Executors.newFixedThreadPool(10));
			server.startServer();
		} catch (IOException e) {
			this.stop();
		}
    }

    /**
     * handles all updates.
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (getStateManager().hasState(environmentState)) {
            getStateManager().getState(MainEnvironment.class).update(tpf);
        }
        inputHandler.moveCamera(tpf);
    }

    /**
     * handles everything that needs rendering.
     * @param rm the rendermanager
     */
    @Override
    public void simpleRender(RenderManager rm) { }

    /**
     * Steers the platform depending on the orientation of an accelerometer.
     *
     * @param orientation
     *      The acceleration force along the z axis (including gravity).
     */
    public void handleAccelerometerMessage(float orientation) {
        environmentState.steer(orientation);
    }

    /**
     * Sends the enable spray message.
     * Method called in the EnableSprayToVRMessageHandler
     * @param pos the position the spray should be activated on.
     */
    public void handleEnableSprayMessage(PlatformPosition pos) {
        Server sendServer = server.getServer();
        EnableSprayToAppMessage enableSprayMsg = new EnableSprayToAppMessage(pos);
        if (sendServer.isRunning()) {
            //Send a message which enables the spray on the send location.
            //This happens in the app.
            sendServer.broadcast(enableSprayMsg);
        }
    }

    /**
     * Sends the stop bug event message.
     * Method called in the StopEventMessageHandler.
     */
    public void handleStopBugEvent() {
        Server sendServer = server.getServer();
        StopAllEventsMessage stopMsg = new StopAllEventsMessage();
        if (sendServer.isRunning()) {
            sendServer.broadcast(stopMsg);
            bugEventRunning = false;
        }
    }

    /**
     * Returns the main menu state.
     * @return the mainMenu
     */
    public MainMenu getMainMenu() {
        return mainMenuState;
    }

    /**
     * Returns the environment state.
     * @return the environment
     */
    public MainEnvironment getEnv() {
        return environmentState;
    }

    /**
     * Returns the rootnode.
     * @return
     * 		The root node.
     */
    @Override
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * Returns the GUInode.
     * @return The GUI node.
     */
    @Override
    public Node getGuiNode() {
        return guiNode;
    }

    /**
     * Returns the only instance of main.
     * @return the main instance.
     */
    public static Main getInstance() {
        return main;
    }

    /**
     * Returns the settings of the main menu.
     * Used in testing.
     * @return AppSettings
     */
    public AppSettings getSettings() {
        return settings;
    }
    /**
     * Gets the server on which this application is running.
     *
     * @return
     * 		The server on which this server is running.
     */
    public ServerWrapper getServer() {
    	return server;
    }

    /**
     * Executes an attack using a player's position and direction of attack.
     * @param pos
     * 			the PlatformPosition of the attacking player
     * @param dir
     * 			the direction of the attack (String)
     */
    public void handleAttack(PlatformPosition pos, String dir) {
        // TODO: EXECUTE ATTACKS
    }

    /**
     * Sets the current main.
     * Used for testing ONLY.
     * @param newMain the new main.
     */
    public static void setMain(Main newMain) {
        main = newMain;
    }

    /**
     * Sets the current inputHandler.
     * Used for testing ONLY.
     * @param newInputHandler the new inputHandler.
     */
    public void setInputHandler(InputHandler newInputHandler) {
        inputHandler = newInputHandler;
    }

    /**
     * Sets the current environment state.
     * Used for testing ONLY.
     * @param newEnvState the new environment state.
     */
    public void setEnvState(MainEnvironment newEnvState) {
        environmentState = newEnvState;
    }

    /**
     * Sets the current mainmenu state.
     * Used for testing ONLY.
     * @param newMainMenuState the new main menu state.
     */
    public void setMainMenuState(MainMenu newMainMenuState) {
        mainMenuState = newMainMenuState;
    }

    /**
     * Checks if the bug event is running.
     * @return true when running.
     */
    public boolean isBugEventRunning() {
        return bugEventRunning;
    }

    /**
     * Sets whether or not the bug event is running.
     * @param isRunning the new state of the bug event (true/false).
     */
    public void setBugEventRunning(boolean isRunning) {
        bugEventRunning = isRunning;

    }
}
