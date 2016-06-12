package com.github.migi_1.Context.model;

import com.github.migi_1.Context.main.LobbyHUDController;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;

/**
 * The LobbyEnvironment is a 3D environment that functions
 * as the lobby screen.
 */
public class LobbyEnvironment extends Environment {
    
    private ViewPort viewPort;
    
    private static final ColorRGBA BACKGROUNDCOLOR = ColorRGBA.Black;
    
    private Application app;
    private LobbyHUDController lobbyHUDController;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = app;
        viewPort = app.getViewPort();

        lobbyHUDController = new LobbyHUDController(app);
        viewPort.setBackgroundColor(BACKGROUNDCOLOR);
    }
    
    @Override
    public void update(float tpf) {
        lobbyHUDController.updateHUD();

    }
    
    @Override
    public void cleanup() {
        // viewPort.clearProcessors();
        super.cleanup();
    }
    
}
