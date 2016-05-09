/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.Context.clientside;

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
public class MainMenuFunctionsAndroid extends AbstractAppState implements ScreenController {
    
    private Nifty nifty;
    private Screen screen;
    private Application app;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
    }
    
    @Override
    public void update(float tpf) {
        if (screen.getScreenId().equals("host")) {
            // execute functions to update the lobby screen.
        }
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
 
    public void onStartScreen() {
    }
 
    public void onEndScreen() {
    }
    
    /**custom methods*/
    public void toScreen(String scr) {
        nifty.gotoScreen(scr);
    }
    
    public void quitGame() {
        // app.stop();
        System.exit(0);
    }

}
