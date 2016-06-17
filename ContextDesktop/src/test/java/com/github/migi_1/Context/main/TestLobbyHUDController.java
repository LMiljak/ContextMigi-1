package com.github.migi_1.Context.main;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapCharacterSet;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AppSettings.class})
public class TestLobbyHUDController {

    private LobbyHUDController lobbyHUDController;
    private Main main;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private BitmapFont font;
    private BitmapText title;
    private BitmapCharacterSet charSet;
    private AppSettings settings;
    private Node guiNode;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ProjectAssetManager.class);

        guiNode = Mockito.mock(Node.class);
        settings = Mockito.mock(AppSettings.class);
        charSet = Mockito.mock(BitmapCharacterSet.class);
        title = Mockito.mock(BitmapText.class);
        main = Mockito.mock(Main.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        font = Mockito.mock(BitmapFont.class);

        PowerMockito.whenNew(BitmapText.class).withArguments(font, false).thenReturn(title);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadFont(Mockito.anyString())).thenReturn(font);
        Mockito.when(font.getCharSet()).thenReturn(charSet);
        Mockito.when(main.getSettings()).thenReturn(settings);
        Mockito.when(main.getGuiNode()).thenReturn(guiNode);

        Mockito.doNothing().when(title).setSize(Mockito.anyFloat());
        Mockito.doNothing().when(title).setColor(Mockito.any());
        Mockito.doNothing().when(title).setText(Mockito.anyString());

        lobbyHUDController = new LobbyHUDController(main);
    }

    @Test
    public void setPlayerTextTest() {
        String oldText =
                lobbyHUDController.getPlayers().get(PlatformPosition.FRONTLEFT).getText();
        lobbyHUDController.setPlayerText(PlatformPosition.FRONTLEFT, "example");
        String newText =
                lobbyHUDController.getPlayers().get(PlatformPosition.FRONTLEFT).getText();
        assertNotEquals(oldText, newText);

    }

}
