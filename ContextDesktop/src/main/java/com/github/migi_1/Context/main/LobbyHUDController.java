package com.github.migi_1.Context.main;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;


/**
 * This class controls the score and displays it.
 *
 * @author Marcel
 *
 */
public class LobbyHUDController {

    private BitmapText title, player1, player2, player3, player4;
    
    private Main main;
    private AssetManager assetManager;
    private AppSettings settings;
    private ExitButtonListener exitButtonListener;
    private Node exitNode;
    private Node playNode;

    /**
     * Constructor. Generates initial score and displays it.
     * @param app Application
     */
    public LobbyHUDController(Application app) {
        this.main = (Main) app;
        assetManager = ProjectAssetManager.getInstance().getAssetManager();
        BitmapFont titleFont = assetManager.loadFont("Interface/Fonts/myfont.fnt");
        title = new BitmapText(titleFont, false);
        title.setSize(titleFont.getCharSet().getRenderedSize());
        title.setColor(ColorRGBA.White);
        title.setText("CarriedfAway");
        settings = main.getSettings();
        setTextPosition(title, 0.5f, 0.8f);
        main.getGuiNode().attachChild(title);
        
        addPlayers();
        
        playNode = new Node("play");
        main.getGuiNode().attachChild(playNode);
        playNode = addPlayButton(playNode);
        
        exitNode = new Node("exit");
        main.getGuiNode().attachChild(exitNode);
        exitNode = addExitButton(exitNode);
    }
    
    /**
     * Adds the text fields for the players.
     */
    private void addPlayers() {
        BitmapFont menuFont = assetManager.loadFont("Interface/Fonts/myfont2.fnt");
        
        player1 = setPlayer(menuFont, player1);
        setTextPosition(player1, 0.5f, 0.65f);
        main.getGuiNode().attachChild(player1);
        
        player2 = setPlayer(menuFont, player2);
        setTextPosition(player2, 0.5f, 0.6f);
        main.getGuiNode().attachChild(player2);
        
        player3 = setPlayer(menuFont, player3);
        setTextPosition(player3, 0.5f, 0.55f);
        main.getGuiNode().attachChild(player3);
        
        player4 = setPlayer(menuFont, player4);
        setTextPosition(player4, 0.5f, 0.5f);
        main.getGuiNode().attachChild(player4);
        
    }
    
    public BitmapText setPlayer(BitmapFont font, BitmapText player) {
        player = new BitmapText(font, false);
        player.setSize(font.getCharSet().getRenderedSize());
        player.setColor(ColorRGBA.White);
        player.setText("|");
        return player;
    }
    
    /**
     * Sets the position of a text field.
     * @param text
     *              The text field.
     * @param widthFactor
     *              The width value at which the center of the text field
     *              will be placed.
     * @param heightFactor 
     *              The height value at which the text field will be placed.
     */
    private void setTextPosition(BitmapText text, float widthFactor, 
            float heightFactor) {
        float width = widthFactor * settings.getWidth() - 0.5f * text.getLineWidth();
        float height = heightFactor * settings.getHeight();
        text.setLocalTranslation(width, height, 0);
    }
    
    /**
     * Adds the play button to the lobby.
     */
    private Node addPlayButton(Node node) {
        Picture playButton = new Picture("play");
        playButton.setImage(assetManager, "Interface/Buttons/play_button_inactive.png", true);
        playButton.setHeight(settings.getHeight()/8);
        float width = settings.getWidth()/8;
        playButton.setWidth(width);
        playButton.setPosition(0.7f * settings.getWidth() - 0.5f * width, 
                0.2f * settings.getHeight());
        
        node.attachChild(playButton);
        return node;
    }
    
    /**
     * Adds the exit button to the lobby.
     */
    private Node addExitButton(Node node) {
        Picture exitButton = new Picture("exit");
        exitButton.setImage(assetManager, "Interface/Buttons/exit_button.png", true);
        exitButton.setHeight(settings.getHeight()/8);
        float width = settings.getWidth()/8;
        exitButton.setWidth(width);
        exitButton.setPosition(0.3f * settings.getWidth() - 0.5f * width, 
                0.2f * settings.getHeight());
        
        node.attachChild(exitButton);
        return node;
    }

    /**
     * Update the players.
     */
    public void updateHUD() {
        
    }

}
