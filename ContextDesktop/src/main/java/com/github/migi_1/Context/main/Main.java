package com.github.migi_1.Context.main;

import java.io.IOException;
import java.util.concurrent.Executors;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.model.LobbyEnvironment;
import com.github.migi_1.Context.server.ClientFinder;
import com.github.migi_1.Context.server.EnableSprayToVRMessageHandler;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.server.StopEventMessageHandler;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.EnableSprayToAppMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.StopAllEventsMessage;

import com.jme3.input.KeyInput;
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

public class Main extends VRApplication implements KeyInputListener {

    //the game state
    private MainEnvironment environmentState;
    
    //the game's lobby
    private LobbyEnvironment lobbyState;

    //the main application
    private static Main main;

    private InputHandler inputHandler;

    private static AppSettings settings;

    private ServerWrapper server;
    
    private boolean inLobby;

    private boolean bugEventRunning = false;


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
        inputHandler = InputHandler.getInstance();
        inputHandler.initialise(main);
        inputHandler.register(this, KeyInput.KEY_SPACE);
        
        launchServer();

        CarrierAssigner carrierAssigner = new CarrierAssigner(server);
        lobbyState = new LobbyEnvironment(carrierAssigner);
        environmentState = new MainEnvironment(carrierAssigner);
        ProjectAssetManager.getInstance().setAssetManager(getAssetManager());
        this.getStateManager().attach(lobbyState);
        inLobby = true;

        new EnableSprayToVRMessageHandler(this);
        new StopEventMessageHandler(this);
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
        if (sendServer.isRunning() && bugEventRunning) {
            sendServer.broadcast(stopMsg);
            bugEventRunning = false;
        }
    }

    /**
     * Returns the main menu state.
     * @return the mainMenu
     */
    public LobbyEnvironment getLobby() {
        return lobbyState;
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
     * Sets the boolean inLobby.
     * @param inLobby used to check whether or not the program is in the lobby.
     */
    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }
    
    /**
     * Getter for inLobby.
     * @return a boolean value that tells whether or not the program is in the lobby.
     */
    public boolean getInLobby() {
        return inLobby;
    }
    
    /**
     * Makes the game switch to the level.
     */
    public void toMainEnvironment() {
        if (inLobby) {
        	setInLobby(false);
        	lobbyState.cleanup();
            this.getStateManager().detach(lobbyState);
            this.getStateManager().attach(environmentState);
        }
    }
    
    /**
     * Makes the game switch to the lobby.
     */
    public void toLobby() {
        if (!inLobby) {
        	setInLobby(true);
        	environmentState.cleanup();
            this.getStateManager().attach(lobbyState);
            this.getStateManager().detach(environmentState);
        }
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
     * Sets the current lobby state.
     * Used for testing ONLY.
     * @param newLobbyState the new lobby state.
     */
    public void setLobby(LobbyEnvironment newLobbyState) {
        lobbyState = newLobbyState;
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

	@Override
	public void onKeyPressed(int key) {
		switch (key) {
		case KeyInput.KEY_SPACE:
			toMainEnvironment();
			break;
		}
	}

	@Override
	public void onKeyReleased(int key) { }
}
