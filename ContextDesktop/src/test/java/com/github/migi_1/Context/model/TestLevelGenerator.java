package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;

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
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Test class for the levelGenerator class.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class})
public class TestLevelGenerator {

    private LevelGenerator levelGenerator;
    private Vector3f vec = new Vector3f(0, 0, 0);
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;

    /**
     * Initialise all mock objects, static class responses and initialise the tested object.
     */
    @Before
    public void setUp() {
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model = Mockito.mock(Spatial.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(model.getWorldBound()).thenReturn(new BoundingBox(new Vector3f(0, 0, 0), 0, 0, 0));

        levelGenerator = new LevelGenerator(vec);
    }


    /**
     * Verify that the getLevelPieces method is working correctly.
     */
    @Test
    public void getLevelPiecesTest() {
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

    /**
     * Verify that the getLevelPieces method is working correctly.
     */
    @Test
    public void getLevelPiecesTwiceTest() {
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

    /**
     * Verify that the deleteLevelPieces method is working correctly.
     */
    @Test
    public void deleteLevelPiecesNoDeletionTest() {
        //Ensure level pieces do not get deleted when the player is nearby.
        assertEquals(0, levelGenerator.deleteLevelPieces(vec).size());
    }

    /**
     * Verify that the deleteLevelPieces method is working correctly in conjunction with the getLevelPieces method.
     */
    @Test
    public void deleteAndGetLevelPiecesTest() {
        levelGenerator.getLevelPieces(vec);
        //Ensure 1 level piece gets deleted when the player is far away.
        assertEquals(5, levelGenerator.deleteLevelPieces(new Vector3f(500, 500, 500)).size());
        //Make sure that the number of levelpieces gets back up to the regular amount.
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

    /**
     * Verify that the deleteLevelPieces method is working correctly in conjunction with the getLevelPieces method.
     */
    @Test
    public void deleteAllLevelPiecesGetTest() {
        levelGenerator.getLevelPieces(vec);
        //Ensure 1 level piece gets deleted when the player is far away.
        assertEquals(5, levelGenerator.deleteLevelPieces(new Vector3f(500, 500, 500)).size());
        //Make sure that the number of levelpieces gets back up to the regular amount.
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

}
