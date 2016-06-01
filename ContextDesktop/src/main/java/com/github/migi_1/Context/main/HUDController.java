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
public class HUDController {

    private float gameScore;

    private BitmapText hudText;

    private int threshold = 10;

    private AppSettings settings;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public HUDController(Application app) {
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/RockwellExtraBold.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        hudText.setColor(ColorRGBA.White);
        hudText.setText("0");
        settings = ((Main) app).getSettings();
        float width = settings.getWidth() - hudText.getLineWidth();
        float height = settings.getHeight();
        hudText.setLocalTranslation(width, height, 0);
        ((Main) app).getGuiNode().attachChild(hudText);
    }

    /**
     * Update the score. If the score becomes a digit longer, move it left.
     */
    public void updateHUD() {
        gameScore = gameScore + 0.1f;
        hudText.setText(Integer.toString(Math.round(gameScore)));
        if (Math.round(gameScore) >= threshold) {
            threshold *= 10;
            float width = settings.getWidth() - hudText.getLineWidth();
            float height = settings.getHeight();
            hudText.setLocalTranslation(width, height, 0);
        }
    }

    /**
     * Add a value to the score.
     * @param add value to add
     */
    public void addScore(float add) {
        gameScore += add;
    }

}
