package com.github.migi_1.Context.main;

import java.util.HashMap;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;


/**
 * Controls the GUI elements of the LobbyEnvironment.
 */
public class LobbyHUDController {

    private BitmapText title, instruction;
    private HashMap<PlatformPosition, BitmapText> players = new HashMap<>(4);

    private Main main;
    private AssetManager assetManager;
    private AppSettings settings;

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
        addInstruction();
    }

    /**
     * Adds the text fields for the players.
     */
    private void addPlayers() {
        BitmapFont menuFont = assetManager.loadFont("Interface/Fonts/myfont2.fnt");

        float textPosition = 0.65f;
        final float distanceBetweenText = 0.05f;

        for (PlatformPosition position : PlatformPosition.values()) {
        	BitmapText player = new BitmapText(menuFont);
            player.setSize(menuFont.getCharSet().getRenderedSize());
            player.setColor(ColorRGBA.White);
            setTextPosition(player, 0.5f, textPosition);
            player.setText("|");

        	players.put(position, player);
        	main.getGuiNode().attachChild(player);

        	textPosition -= distanceBetweenText;
        }
    }

    /**
     * Adds the "press space to start" instruction.
     */
    private void addInstruction() {
        BitmapFont titleFont = assetManager.loadFont("Interface/Fonts/myfont.fnt");
        instruction = new BitmapText(titleFont, false);
        instruction.setSize(titleFont.getCharSet().getRenderedSize() * 0.75f);
        instruction.setColor(ColorRGBA.White);
        instruction.setText("Pressfspaceftofstart");
        settings = main.getSettings();
        setTextPosition(instruction, 0.5f, 0.3f);
        main.getGuiNode().attachChild(instruction);
    }

    /**
     * Sets the text for a certain player.
     *
     * @param position
     * 		The position the player has assigned.
     * @param text
     * 		The text.
     */
    public void setPlayerText(PlatformPosition position, String text) {
    	players.get(position).setText(text);
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
     * Getter for the players.
     * Used in testing ONLY.
     * @return the players hashmap.
     */
    public HashMap<PlatformPosition, BitmapText> getPlayers() {
        return players;
    }
}
