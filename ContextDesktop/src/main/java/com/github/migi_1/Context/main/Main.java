package com.github.migi_1.Context.main;

import java.io.IOException;
import java.util.concurrent.Executors;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.LobbyEnvironment;
import com.github.migi_1.Context.screens.MainMenu;
import com.github.migi_1.Context.server.AttackMessageHandler;
import com.github.migi_1.Context.server.ClientFinder;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;

import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * Creates the main desktop application. It initializes the main menu on startup,
 * all things that have to do with the application as a whole can be found in this class
 * @author Damian
 */

public class Main extends VRApplication {

    //the game state
    private MainEnvironment environmentState;
    
    //the game's lobby
    private LobbyEnvironment lobbyState;

    //the main application
    private static Main main;

    private InputHandler inputHandler;

    private static AppSettings settings;

    private ServerWrapper server;

    private AttackMessageHandler attackMessageHandler;
    
    private boolean inLobby;


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

        lobbyState = new LobbyEnvironment();
        environmentState = new MainEnvironment();
        ProjectAssetManager.getInstance().setAssetManager(getAssetManager());
        this.getStateManager().attach(lobbyState);
        inLobby = true;

        // Probably not the right spot, but I'll put this here for now.
        attackMessageHandler = new AttackMessageHandler(this);
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
     * Returns the lobby state.
     * @return the lobby
     */
    public LobbyEnvironment getLobby() {
        return lobbyState;
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
    
    /**
     * Sets the boolean inLobby.
     * @param inLobby used to check whether or not the program is in the lobby.
     */
    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }
    
    /**
     * Getter for inLobby
     * @return a boolean value that tells whether or not the program is in the lobby.
     */
    public boolean getInLobby() {
        return inLobby;
    }
    
    /**
     * Makes the game switch to the level.
     */
    public void toMainEnvironment() {
        setInLobby(false);
        lobbyState.cleanup();
        this.getStateManager().attach(environmentState);
        this.getStateManager().detach(lobbyState);
    }
    
    /**
     * Makes the game switch to the lobby.
     */
    public void toLobby() {
        setInLobby(true);
        environmentState.cleanup();
        this.getStateManager().attach(lobbyState);
        this.getStateManager().detach(environmentState);
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

    // TESTS NEED TO BE ALTERED, SINCE THE MENU IS GOING TO BE REPLACED.
    
    /**
     * Sets the current lobby state.
     * Used for testing ONLY.
     * @param newLobbyState the new lobby state.
     */
    public void setLobby(LobbyEnvironment newLobbyState) {
        lobbyState = newLobbyState;
    }
}
