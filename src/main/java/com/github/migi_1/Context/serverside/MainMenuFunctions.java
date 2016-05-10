/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.Context.serverside;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
/**
 *
 * @author Remi
 */
public class MainMenuFunctions extends AbstractAppState implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private Application app;

    /**
     * This function initializes MainMenuFunctions and sets app.
     * @param stateManager AppStateManager
     * @param app Application
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
    }

    /**
     * This function executes code every unit of time according to tpf to update
     * the gui.
     * Is currently not used.
     * @param tpf float
     */
    @Override
    public void update(float tpf) {
        if (screen.getScreenId().equals("host")) {
            // execute functions to update the lobby screen.
        }
    }

    /**
     * Binds the nifty and the screen that is shown to the functions.
     * @param nifty Nifty
     * @param screen Screen
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
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
     * This function quits the game
     */
    public void quitGame() {
        System.exit(0);
    }
}
