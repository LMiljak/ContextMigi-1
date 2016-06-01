package com.github.migi_1.Context.model;

import com.github.migi_1.Context.main.LobbyHUDController;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * The LobbyEnvironment is a 3D environment that functions
 * as the lobby screen.
 */
public class LobbyEnvironment extends Environment {
    
    private LobbyHUDController lobbyHUDController;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        lobbyHUDController = new LobbyHUDController(app);
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);

    }
    
}
