package com.github.migi_1.Context.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestLevelPiece {

    private LevelPiece lvlPiece;

    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;

    @Before
    public void setUp() throws Exception {

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);


        lvlPiece = new LevelPiece();
    }

    @Test
    public void getAndSetModelTest() {
        assertNull(lvlPiece.getModel());
        lvlPiece.setModel(new Node());
        assertNotNull(lvlPiece.getModel());
    }

}
