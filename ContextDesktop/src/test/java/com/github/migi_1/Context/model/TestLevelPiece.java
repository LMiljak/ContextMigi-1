package com.github.migi_1.Context.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Test class for the LevelPiece class.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestLevelPiece {

    private LevelPiece lvlPiece;

    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;

    private Spatial model;

    private BoundingVolume boundingBox;

    /**
     * Initialise all mock objects, static class responses and initialise the tested object.
     */
    @Before
    public void setUp() {
        
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        
        lvlPiece = new LevelPiece();
    }

    /**
     * Test the getters and setters.
     */
    @Test
    public void getAndSetModelTest() {
        assertNull(lvlPiece.getModel());
        lvlPiece.setModel(new Node());
        assertNotNull(lvlPiece.getModel());
    }

}
