package com.github.migi_1.Context.main;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
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

    private BitmapFont guiFont;

    private int threshold = 10;

    private AppSettings settings;

    private Application app;

    private Geometry gameOver;

    private BitmapText gameOverText;

    private BitmapText gameOverScore;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public HUDController(Application app) {
        this.app = app;
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        guiFont = assetManager.loadFont("Interface/Fonts/RockwellExtraBold.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        hudText.setColor(ColorRGBA.White);
        hudText.setText("0");
        settings = ((Main) app).getSettings();
        float width = settings.getWidth() - hudText.getLineWidth();
        float height = settings.getHeight();
        hudText.setLocalTranslation(width, height, 0);
        ((Main) app).getGuiNode().attachChild(hudText);
        createGameOverScreen();
    }

    private void createGameOverScreen() {
        Material mat = new Material(ProjectAssetManager.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(0, 1, 1, 0.5f));
        float width = (float) (settings.getWidth() / 1.5);
        float height = (float) (settings.getHeight() / 1.5);

        gameOver = new Geometry("Rect", new Quad(width, height));
        float leftBound = (settings.getWidth() - width)/ 2;
        float upperBound = (settings.getHeight() - height)/ 2;
        gameOver.setLocalTranslation(leftBound, upperBound, 0);
        gameOver.setMaterial(mat);

        gameOverText =  new BitmapText(guiFont, false);
        gameOverText.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        gameOverText.setColor(ColorRGBA.White);
        gameOverText.setText("Game Over!");
        gameOverText.setLocalTranslation((settings.getWidth() - gameOverText.getLineWidth()) / 2, settings.getHeight() - upperBound, 1);

        gameOverScore =  new BitmapText(guiFont, false);
        gameOverScore.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        gameOverScore.setColor(ColorRGBA.White);
        gameOverScore.setLocalTranslation(settings.getWidth() / 2, height, 1);


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

    public void gameOver() {
        gameOverScore.setText(Integer.toString(Math.round(gameScore)));
        ((Main) app).getGuiNode().attachChild(gameOver);
        ((Main) app).getGuiNode().attachChild(gameOverText);
        ((Main) app).getGuiNode().attachChild(gameOverScore);

    }

}
