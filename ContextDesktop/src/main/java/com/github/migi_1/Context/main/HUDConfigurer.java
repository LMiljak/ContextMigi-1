package com.github.migi_1.Context.main;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public final class HUDConfigurer {

    public static final void initializeHUD(Node guiNode, Application app) {
        BitmapText hudText;

        BitmapFont guiFont = ProjectAssetManager.getInstance().getAssetManager().loadFont("Interface/Fonts/Console.fnt");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Red);                             // font color
        hudText.setText("You can write any string here");             // the text

        hudText.setLocalTranslation(((Main) app).getSettings().getWidth() - hudText.getLineWidth(), ((Main) app).getSettings().getHeight() - hudText.getLineHeight(), 0); // position
        guiNode.attachChild(hudText);

    }

}
