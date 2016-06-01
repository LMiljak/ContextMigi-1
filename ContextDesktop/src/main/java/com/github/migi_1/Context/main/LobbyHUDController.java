package com.github.migi_1.Context.main;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;

/**
 * This class controls the score and displays it.
 *
 * @author Marcel
 *
 */
public class LobbyHUDController {

    private BitmapText title, player1, player2, player3, player4;

    private int threshold = 10;

    private AppSettings settings;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public LobbyHUDController(Application app) {
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/myfont.fnt");
        title = new BitmapText(guiFont, false);
        title.setSize(guiFont.getCharSet().getRenderedSize());
        title.setColor(ColorRGBA.White);
        title.setText("CarriedfAway");
        settings = ((Main) app).getSettings();
        float width = 0.5f * settings.getWidth() - 0.5f * title.getLineWidth();
        float height = 0.8f * settings.getHeight();
        title.setLocalTranslation(width, height, 0);
        ((Main) app).getGuiNode().attachChild(title);
    }

    /**
     * Update the players.
     */
    public void updateHUD() {
        
    }

}
