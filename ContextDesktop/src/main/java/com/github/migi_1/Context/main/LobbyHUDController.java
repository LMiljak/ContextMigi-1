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

    private AppSettings settings;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public LobbyHUDController(Application app) {
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        BitmapFont titleFont = assetManager.loadFont("Interface/Fonts/myfont.fnt");
        title = new BitmapText(titleFont, false);
        title.setSize(titleFont.getCharSet().getRenderedSize());
        title.setColor(ColorRGBA.White);
        title.setText("CarriedfAway");
        settings = ((Main) app).getSettings();
        setTextPosition(title, 0.5f, 0.8f);
        ((Main) app).getGuiNode().attachChild(title);
        
        addPlayers(assetManager, app);
    }
    
   private void addPlayers(AssetManager assetManager, Application app) {
        BitmapFont menuFont = assetManager.loadFont("Interface/Fonts/myfont2.fnt");
        
        player1 = setPlayer(menuFont, player1);
        setTextPosition(player1, 0.5f, 0.65f);
        ((Main) app).getGuiNode().attachChild(player1);
        
        player2 = setPlayer(menuFont, player2);
        setTextPosition(player2, 0.5f, 0.6f);
        ((Main) app).getGuiNode().attachChild(player2);
        
        player3 = setPlayer(menuFont, player3);
        setTextPosition(player3, 0.5f, 0.55f);
        ((Main) app).getGuiNode().attachChild(player3);
        
        player4 = setPlayer(menuFont, player4);
        setTextPosition(player4, 0.5f, 0.5f);
        ((Main) app).getGuiNode().attachChild(player4);
        
    }
    
    private BitmapText setPlayer(BitmapFont font, BitmapText player) {
        player = new BitmapText(font, false);
        player.setSize(font.getCharSet().getRenderedSize());
        player.setColor(ColorRGBA.White);
        player.setText("|");
        return player;
    }
    
    private void setTextPosition(BitmapText text, float widthFactor, 
            float heightFactor) {
        float width = widthFactor * settings.getWidth() - 0.5f * text.getLineWidth();
        float height = heightFactor * settings.getHeight();
        text.setLocalTranslation(width, height, 0);
    }

    /**
     * Update the players.
     */
    public void updateHUD() {
        
    }

}
