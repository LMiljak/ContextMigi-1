package com.github.migi_1.Context.screens;

import com.github.migi_1.Context.Main;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
/**
 * This class contains all the functions for the MainMenu screen.
 * @author Remi & Nils
 */
public class MainMenuFunctions implements ScreenController {

    private Nifty nifty;
//    private Screen screen;
    private Main main;

    /**
     * This function initializes MainMenuFunctions and sets app.
     * @param main
     * 		The main instance.
     */
    public MainMenuFunctions(Main main) {
        this.main = main;
    }
    /**
     * This function executes code every unit of time according to tpf to update
     * the gui.
     * Is currently not used.
     * @param tpf float
     */
    public void update(float tpf) {
//        if (screen.getScreenId().equals("host")) {
//            // TODO: execute functions to update the lobby screen.
//        }
    }

    /**
     * Binds the nifty and the screen that is shown to the functions.
     * @param nifty Nifty
     * @param screen Screen
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
//        this.screen = screen;
    }

    /**
     * Function needs to exist to be called when a screen is started.
     * Doesn't currently have any other functions.
     */
    @Override
    public void onStartScreen() {
    }

    /**
     * Function needs to exist to be called when a screen is ended.
     * Doesn't currently have any other functions.
     */
    @Override
    public void onEndScreen() {
    }

    /**
     * This function makes the program switch to a different screen.
     * @param scr String
     */
    public void toScreen(String scr) {
        nifty.gotoScreen(scr);
    }

    /**
     * Activates when the start button is pressed in the host screen.
     * Detaches the menu and attaches the environment to the app.
     */
    public void startGame() {
        main.getStateManager().detach(main.getMainMenu());
        main.getStateManager().attach(main.getEnv());
        main.getEnv().getPhysicsSpace().addCollisionListener(main);
    }

    /**
     * Quits the game.
     * @NOTE For now this exits the game using a System.exit(0),
     * @NOTE in the end app.stop() will probably be a bit nicer (but it is not possible to do so yet).
     */
    public void quitGame() {
        System.exit(0);
    }
}