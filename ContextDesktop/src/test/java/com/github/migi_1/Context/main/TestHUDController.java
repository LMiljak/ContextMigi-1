package com.github.migi_1.Context.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapCharacterSet;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * Test suite for the HUDController class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AppSettings.class, HUDController.class})
public class TestHUDController {

    private HUDController hudController;
    private Main main;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private BitmapFont font;
    private BitmapText hudText;
    private BitmapCharacterSet characterSet;
    private AppSettings settings;
    private Node guiNode;
    private MainEnvironment mainEnv;
    private Commander commander;
    private Spatial model;
    private Material material;

    /**
     * Setup for the testsuite
     * @throws Exception when the BitmapText with any arguments is not
     */
    @Before
    public void setUp() throws Exception {
        main = Mockito.mock(Main.class);
        mainEnv = Mockito.mock(MainEnvironment.class);
        material = Mockito.mock(Material.class);
        PowerMockito.mockStatic(AppSettings.class);
        settings = PowerMockito.mock(AppSettings.class);
        commander = Mockito.mock(Commander.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        guiNode = Mockito.mock(Node.class);
        model =  Mockito.mock(Spatial.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        PowerMockito.whenNew(Material.class).withArguments(Mockito.any(), Mockito.anyString()).thenReturn(material);
        font = Mockito.mock(BitmapFont.class);
        characterSet = Mockito.mock(BitmapCharacterSet.class);
        Mockito.when(main.getSettings()).thenReturn(settings);
        Mockito.when(main.getEnv()).thenReturn(mainEnv);
        Mockito.when(main.getGuiNode()).thenReturn(guiNode);
        Mockito.when(mainEnv.getCommander()).thenReturn(commander);
        Mockito.when(commander.getModel()).thenReturn(model);
        Mockito.when(assetManager.loadFont(Mockito.anyString())).thenReturn(font);
        Mockito.when(font.getCharSet()).thenReturn(characterSet);
        Mockito.when(characterSet.getRenderedSize()).thenReturn(0);
        Mockito.when(settings.getWidth()).thenReturn(0);
        Mockito.when(settings.getHeight()).thenReturn(0);

        hudController = new HUDController(main);
        hudText = Mockito.mock(BitmapText.class);
        hudController.setHUDText(hudText);
    }

    /**
     * Tests the addScore method.
     */
    @Test
    public void addGameScoreTest() {
        float oldScore = hudController.getScore();
        hudController.addScore(10);
        assertEquals(hudController.getScore(), oldScore + 10f, 0);
    }

    /**
     * Tests the updateHUD method when the commander is at a checkpoint.
     */
    @Test
    public void updateHUD_AtCheckpoint_Test() {
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(-1000, -1000, -1000));
        float oldScore = hudController.getScore();
        String hudTextOld = hudText.getText();
        assertFalse(hudController.getCheckpointUpdated());
        hudController.updateHUD();
        assertEquals(hudController.getScore(), oldScore + 0.1f, 0);
        assertEquals(hudTextOld, hudText.getText());
        Mockito.verify(guiNode, Mockito.times(2)).attachChild(Mockito.any());
        assertFalse(hudController.getCheckpointUpdated());
    }

    /**
     * Tests the updateHUD method when the commander is not at a checkpoint.
     */
    @Test
    public void updateHUD_NotAtCheckpoint_Test() {
        Mockito.when(model.getLocalTranslation()).thenReturn(Vector3f.ZERO);
        String hudTextOld = hudText.getText();
        assertFalse(hudController.getCheckpointUpdated());
        float oldScore = hudController.getScore();
        hudController.updateHUD();
        assertEquals(hudController.getScore(), oldScore + 0.1f, 0);
        assertEquals(hudTextOld, hudText.getText());
        Mockito.verify(guiNode).attachChild(Mockito.any());
        assertTrue(hudController.getCheckpointUpdated());
    }

    /**
     * Tests the updateHUD method when the commander is moving towards a checkpoint.
     */
    @Test
    public void updateHUD_ReachingCheckpoint_Test() {
        //Set checkpoint updated to true
        updateHUD_NotAtCheckpoint_Test();
        //Make the commander reach a checkpoint.
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(-1000, -1000, -1000));
        hudController.updateHUD();
        assertFalse(hudController.getCheckpointUpdated());
    }

    /**
     * Tests the scaling of the score HUD element.
     */
    @Test
    public void updateHUD_HUDScoreScaling_Test() {
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(1, 1, 1));
        for (int i = 0; i < 1000; i++) {
            hudController.updateHUD();
        }
        Mockito.verify(hudText, Mockito.atLeastOnce()).setLocalTranslation(0f, 0f, 0f);

    }

}
