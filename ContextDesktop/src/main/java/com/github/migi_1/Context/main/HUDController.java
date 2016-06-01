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


    private static final int CHECKPOINT_DISTANCE = 200;
    private static final float DISPLAY_DISTANCE = 20f;

    private float gameScore;
    private BitmapText hudText;
    private BitmapText checkpointAlertText;
    private int threshold = 10;

    private BitmapFont guiFont;
    private int checkpointCounter = 0;
    private boolean checkpointUpdated = false;


    private AppSettings settings;

    private Main main;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public HUDController(Application app) {
        this.main = (Main) app;
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        guiFont = assetManager.loadFont("Interface/Fonts/RockwellExtraBold.fnt");
        settings = main.getSettings();

        initScoreText();
        initCheckPointText();

        main.getGuiNode().attachChild(hudText);
    }

    private void initScoreText() {
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        hudText.setColor(ColorRGBA.White);
        hudText.setText("0");
        float width = settings.getWidth() - hudText.getLineWidth();
        float height = settings.getHeight();
        hudText.setLocalTranslation(width, height, 0);
    }

    private void initCheckPointText() {
        checkpointAlertText = new BitmapText(guiFont, false);
        checkpointAlertText.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        checkpointAlertText.setColor(ColorRGBA.Red);
        checkpointAlertText.setText("CHECKPOINT " + Integer.toString(checkpointCounter) + " REACHED");

        checkpointAlertText.setLocalTranslation(0, settings.getHeight(), 0);
    }

    /**
     * Update the score. If the score becomes a digit longer, move it left.
     */
    public void updateHUD() {
        updateScoreElementHUD();
        updateCheckpointElementHUD();
    }

    /**
     * Update the score element in the HUD.
     */
    private void updateScoreElementHUD() {
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
     * Update the checkpoint element in the HUD.
     */
    private void updateCheckpointElementHUD() {
        //Get the current location of the commander in the world.
        Vector3f commanderLoc = main.getEnv().getCommander().getModel().getLocalTranslation();

        /**
         * Check if the commander location is at a checkpoint, so that the HUD has to be updated.
         * Since the commander won't be exactly at the checkpoint,
         * there is a buffer called DISPLAY_DISTANCE.
         * When the commander is within this range, the checkpoint message will be displayed.
         * This way the HUD will also show long enough to see the checkpoint you have reached.
         *
         * The second part of this condition is added since the commander starts at a positive x-coordinate
         * and moves in a negative x direction. If this condition wasn't added,
         * the player would see the checkpoint reached message at the start of the game.
         */
        if (Math.abs(commanderLoc.x) % CHECKPOINT_DISTANCE < DISPLAY_DISTANCE && commanderLoc.x < 0) {
            main.getGuiNode().attachChild(checkpointAlertText);
            if (checkpointUpdated) {
                checkpointUpdated = false;
            }
        } else {
            //A boolean flag to force the counter being updated only once.
            if (!checkpointUpdated) {
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
