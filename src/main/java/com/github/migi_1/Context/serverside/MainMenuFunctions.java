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
    
    Nifty nifty;
    Application app;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
    
    public void bind(Nifty nifty, Screen screen) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
 
    public void onStartScreen() {
       // throw new UnsupportedOperationException("Not supported yet.");
    }
 
    public void onEndScreen() {
       // throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**custom methods*/
    public void toScreen(String scr) {
        nifty.gotoScreen(scr);
    }
    
    public void quitGame() {
        app.stop();
    }
}
