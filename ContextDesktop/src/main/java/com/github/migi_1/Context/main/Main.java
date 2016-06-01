package com.github.migi_1.Context.main;

import java.io.IOException;
import java.util.concurrent.Executors;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.screens.MainMenu;
import com.github.migi_1.Context.server.ClientFinder;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

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


    /**
     * main function of the appication, sets some meta-parameters of the application
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

        main.setSettings(settings);
        VRConfigurer.configureVR(main);
        main.setPauseOnLostFocus(true);
    }

    /**
     * Gets the instance of this Application.
     *
     * @return
     * 		The instance of this Application.
     */
    public static Main getMain() {
    	return main;
    }

    /**
     * First method that is called when the application launches.
     * Sets the input, initializes all states and loads the main menu.
     */
    @Override
    public void simpleInitApp() {
        inputHandler = new InputHandler(main);
        inputHandler.initInputs(main);

        mainMenuState = new MainMenu();
        environmentState = new MainEnvironment();
        ProjectAssetManager.getInstance().setAssetManager(getAssetManager());
        this.getStateManager().attach(mainMenuState);

        launchServer();
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
     * 		The acceleration force along the z axis (including gravity).
     */
    public void handleAccelerometerMessage(float orientation) {
        environmentState.steer(orientation);
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
     * @return the env
     */
    public MainEnvironment getEnv() {
        return environmentState;
    }

    /**
     * @return
     * 		The root node.
     */
    @Override
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * @return The GUI node.
     */
    @Override
    public Node getGuiNode() {
        return guiNode;
    }

    /**
     * Returns the only instance of main.
     * @return main.
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
}
