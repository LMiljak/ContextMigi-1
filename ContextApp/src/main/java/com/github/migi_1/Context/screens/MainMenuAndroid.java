package com.github.migi_1.Context.screens;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
/**
 * The main menu for the pc (server)
 * @author Remi & Nils
 */
public class MainMenuAndroid {

    /**
     * A private variable to be able to split up the creation of the screen in separate methods.
     */
    private Nifty nifty;

    /**
     * initializes the main menu and everything it needs to function, is the first screen
     * that shows.
     * @param assetManager manages all assets
     * @param inputManager manages all input of the main menu
     * @param audioRenderer handles all audio of the main menu
     * @param guiViewPort port where all visual elements are added to.
     */
    public void initMenu(AssetManager assetManager, InputManager inputManager
            , AudioRenderer audioRenderer, ViewPort guiViewPort) {
       NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
               assetManager, inputManager, audioRenderer, guiViewPort);
       nifty = niftyDisplay.getNifty();
       guiViewPort.addProcessor(niftyDisplay);

       nifty.loadStyleFile("nifty-default-styles.xml");
       nifty.loadControlFile("nifty-default-controls.xml");

       //Create the start menu
       createStartScreen();
       //Create the join menu.
       createJoinScreen();

       nifty.gotoScreen("start"); //Go to the start screen.
    }

     /**
     * Creates the start screen.
     */
     private void createStartScreen() {
         nifty.fromXml("com/github/migi_1/Context/screens/main_screen_android.xml", "start", new MainMenuFunctionsAndroid());
     }
     /**
      * Creates the host screen.
      */
     private void createJoinScreen() {
         nifty.addXml("com/github/migi_1/Context/screens/joim_screen_android.xml");
     }
}