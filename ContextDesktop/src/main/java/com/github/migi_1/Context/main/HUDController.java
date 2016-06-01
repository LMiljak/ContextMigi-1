package com.github.migi_1.Context.main;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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

    private BitmapText checkpointAlertText;

    private static final int CHECKPOINT_DISTANCE = 200;
    private int checkpointCounter = 0;
    private boolean checkpointUpdated = false;

    private int threshold = 10;

    private AppSettings settings;

    private Main main;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public HUDController(Application app) {
        this.main = (Main) app;
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

        checkpointAlertText = new BitmapText(guiFont, false);
        checkpointAlertText.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        checkpointAlertText.setColor(ColorRGBA.Red);
        checkpointAlertText.setText("CHECKPOINT " + Integer.toString(checkpointCounter) + " REACHED");

        checkpointAlertText.setLocalTranslation(0, height, 0);
        main.getGuiNode().attachChild(hudText);
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
        Vector3f commanderLoc = main.getEnv().getCommander().getModel().getLocalTranslation();
        if(commanderLoc.x % CHECKPOINT_DISTANCE > -10 && commanderLoc.x < 0) {
            main.getGuiNode().attachChild(checkpointAlertText);
            if(checkpointUpdated) {
                checkpointUpdated = false;
            }
        } else {
            if(!checkpointUpdated) {
                checkpointCounter += 1;
                checkpointAlertText.setText("CHECKPOINT " + Integer.toString(checkpointCounter) + " REACHED");
                checkpointUpdated = true;
            }
            main.getGuiNode().detachChild(checkpointAlertText);
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
