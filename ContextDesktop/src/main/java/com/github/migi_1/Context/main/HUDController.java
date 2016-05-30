package com.github.migi_1.Context.main;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public class HUDController {

    private Application app;

    private float count = 0;

    private BitmapText hudText;

    private int threshold;

    private AppSettings settings;

    public HUDController(Node guiNode, Application app) {
        threshold = 10;
        this.app = app;
        BitmapFont guiFont = ProjectAssetManager.getInstance().getAssetManager().loadFont("Interface/Fonts/RockwellExtraBold.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize() * 4);      // font size
        hudText.setColor(ColorRGBA.White);
        hudText.setText("0");             // the text
        settings = ((Main) app).getSettings();
        hudText.setLocalTranslation(settings.getWidth() - hudText.getLineWidth(), settings.getHeight(), 0); // position
        guiNode.attachChild(hudText);
    }

    public void updateHUD() {
        count = count + 0.1f;
        hudText.setText(Integer.toString(Math.round(count)));
        if (Math.round(count) >= threshold) {
            threshold *= 10;
            hudText.setLocalTranslation(settings.getWidth() - hudText.getLineWidth(), settings.getHeight(), 0); // position
        }


    }

}
