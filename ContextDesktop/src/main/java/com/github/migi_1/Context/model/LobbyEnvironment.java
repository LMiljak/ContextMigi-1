package com.github.migi_1.Context.model;

import com.github.migi_1.Context.main.LobbyHUDController;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.ContextMessages.PlatformPosition;
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

    private LobbyHUDController lobbyHUDController;

    private CarrierAssigner carrierAssigner;

    /**
     * Constructor for LobbyEnvironment.
     *
     * @param carrierAssigner
     * 		The CarrierAssigner (the same on the MainEnvironment should also get).
     */
    public LobbyEnvironment(CarrierAssigner carrierAssigner) {
    	this.carrierAssigner = carrierAssigner;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        viewPort = app.getViewPort();

        lobbyHUDController = new LobbyHUDController(app);

        viewPort.setBackgroundColor(BACKGROUNDCOLOR);
    }

    @Override
    public void update(float tpf) {
    	super.update(tpf);

    	updatePlayerText();
    }

    /**
     * Checks if the CarrierAssigner has assigned a carrier
     * to a position and updates the position in the HUD if
     * that's the case.
     */
    private void updatePlayerText() {
    	for (PlatformPosition position : PlatformPosition.values()) {
    		String address = carrierAssigner.getAddress(position);
    		if (!address.isEmpty()) {
    			lobbyHUDController.setPlayerText(position, address);
    		} else {
    			lobbyHUDController.setPlayerText(position, "|");
    		}
    	}
    }

    @Override
    public void cleanup() {
        // viewPort.clearProcessors();
        super.cleanup();
    }

}
