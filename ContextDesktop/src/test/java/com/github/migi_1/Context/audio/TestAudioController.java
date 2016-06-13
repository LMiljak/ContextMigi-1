package com.github.migi_1.Context.audio;

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

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 * Testsuite for the Audio Controller class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestAudioController {

    private AudioController audioController;
    private Main main;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Node rootNode;

    @Before
    public void setUp() {
        main = Mockito.mock(Main.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        rootNode = Mockito.mock(Node.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);

        Mockito.when(main.getRootNode()).thenReturn(rootNode);
        audioController = new AudioController(main);
    }

    @Test
    public void isPlayingTest() {
        assertFalse(audioController.isPlaying());
        audioController.setPlaying(true);
        assertTrue(audioController.isPlaying());
    }

    @Test
    public void getBackGroundMusicTest() {
        assertEquals(AudioNode.class, audioController.getBackgroundMusic().getClass());
    }

}
