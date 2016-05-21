package com.github.migi_1.Context.screens;

import com.github.migi_1.Context.Main;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
/**
 * The main menu for the pc (server).
 * @author Remi & Nils
 */
public class MainMenu extends AbstractAppState {

    private Main app;

    /**
     * A private variable to be able to split up the creation of the screen in separate methods.
     */
    private Nifty nifty;

    private AssetManager assetManager;

    private InputManager inputManager;

    private AudioRenderer audioRenderer;

    private ViewPort guiViewPort;

    private MainMenuFunctions mainMenuController;

    private static final String MAIN_SCREEN = "Screens/main_screen.xml";

    private static final String HOST_SCREEN = "Screens/host_screen.xml";

     /**
     * Creates the start screen.
     */
     private void createStartScreen() {
         nifty.fromXml(MAIN_SCREEN, "start", mainMenuController);
     }
     /**
      * Creates the host screen.
      */
     private void createHostScreen() {
         nifty.addXml(HOST_SCREEN);
     }

    /**
     * Initializes and sets all attributes that nifty needs in order to function.
     * Creates the start and host screens.
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (Main) app;

        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
        audioRenderer = app.getAudioRenderer();
        guiViewPort = app.getGuiViewPort();

        mainMenuController = new MainMenuFunctions(this.app);

        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        //flyCam.setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        //Create the start menu
        createStartScreen();
        //Create the join menu.
        createHostScreen();

        nifty.gotoScreen("start"); //Go to the start screen.

    }

    /**
     * Method called when the stateManager detaches this states.
     * Removes the current screen.
     */
    @Override
    public void cleanup() {
        super.cleanup();
        nifty.removeScreen(nifty.getCurrentScreen().getScreenId());
    }

    ////////////////////////////////////Below methods might be used later on when the pause screen is introduced.
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInitialized() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setEnabled(boolean arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(float arg0) {
        // TODO Auto-generated method stub

    }
}