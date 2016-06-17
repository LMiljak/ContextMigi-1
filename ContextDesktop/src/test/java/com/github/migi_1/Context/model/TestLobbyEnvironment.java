package com.github.migi_1.Context.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.main.LobbyHUDController;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

/**
 * Tests everything that has to do with the LobbyEnvironment.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestLobbyEnvironment {

    private LobbyEnvironment lobbyEnv;
    private CarrierAssigner carrierAssigner;
    private AppStateManager stateManager;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Main main;
    private AudioData audioData;
    private LobbyHUDController lobbyHUDController;
    private Node rootNode;
    private ViewPort viewPort;
    
    /**
     * Sets up everything needed for the tests. Happens before every test.   
     * @exception Exception any Exception if it occurs.
     */
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ProjectAssetManager.class);
        audioData = Mockito.mock(AudioData.class);
        rootNode = Mockito.mock(Node.class);
        viewPort = Mockito.mock(ViewPort.class);
        lobbyHUDController = Mockito.mock(LobbyHUDController.class);
        main = Mockito.mock(Main.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        stateManager = Mockito.mock(AppStateManager.class);
        carrierAssigner = Mockito.mock(CarrierAssigner.class);

        PowerMockito.whenNew(LobbyHUDController.class).withArguments(Mockito.any()).thenReturn(lobbyHUDController);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadAudio(Mockito.anyString())).thenReturn(audioData);
        Mockito.when(main.getRootNode()).thenReturn(rootNode);
        Mockito.when(main.getViewPort()).thenReturn(viewPort);
        Mockito.when(carrierAssigner.getAddress(Mockito.any())).thenReturn("");

        lobbyEnv = new LobbyEnvironment(carrierAssigner);
        lobbyEnv.initialize(stateManager, main);
    }
    
    /**
     * Tests setting the player text with an empty adress.
     */
    @Test
    public void update_EmptyAdress_Test() {
        lobbyEnv.update(0);
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.FRONTLEFT, "|");
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.FRONTRIGHT, "|");
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.BACKLEFT, "|");
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.BACKRIGHT, "|");
    }
    
    /**
     * Tests setting the player text with a non-empty adress.
     */
    @Test
    public void update_NonEmptyAdress_Test() {
        Mockito.when(carrierAssigner.getAddress(Mockito.any())).thenReturn("adress");
        lobbyEnv.update(0);
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.FRONTLEFT, "adress");
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.FRONTRIGHT, "adress");
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.BACKLEFT, "adress");
        Mockito.verify(lobbyHUDController).setPlayerText(PlatformPosition.BACKRIGHT, "adress");
    }

}
