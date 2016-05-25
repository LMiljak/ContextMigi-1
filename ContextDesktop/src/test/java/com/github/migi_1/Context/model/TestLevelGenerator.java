package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

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
    private Vector3f vec;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private BoundingBox boundingBox;
    private Vector3f localTranslation;
    private LevelPiece levelPiece;
    private LinkedList<LevelPiece> levelPieces;

    /**
     * Initialise all mock objects, static class responses and initialise the tested object.
     */
    @Before
    public void setUp() {


        vec = new Vector3f(0, 0, 0);

        levelPieces = new LinkedList<LevelPiece>();
        levelPiece = Mockito.mock(LevelPiece.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model = Mockito.mock(Spatial.class);
        boundingBox = Mockito.mock(BoundingBox.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(model.getWorldBound()).thenReturn(boundingBox);
        Mockito.when(model.getLocalTranslation()).thenReturn(vec);
        Mockito.when(boundingBox.getXExtent()).thenReturn(20.0f);
        Mockito.when(levelPiece.getModel()).thenReturn(model);


        levelGenerator = new LevelGenerator(vec);
    }


    /**
     * Verify that the getLevelPieces method is working correctly.
     */
    @Test
    public void getLevelPiecesTest() {
        localTranslation = new Vector3f(0, 0, 0);
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

    /**
     * Verify that the getLevelPieces method is working correctly.
     */
    @Test
    public void getLevelPiecesTwiceTest() {
        localTranslation = new Vector3f(0, 0, 0);
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
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        localTranslation = new Vector3f(101.0f, 0.0f, 0.0f);

        levelGenerator.setLevelPieces(levelPieces);

        //Ensure all level piece gets deleted when the player is far away.
        assertEquals(5, levelGenerator.deleteLevelPieces(localTranslation).size());
        //Make sure that the number of levelpieces gets back up to the regular amount.
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

    /**
     * Verify that the deleteLevelPieces method is working correctly in conjunction with the getLevelPieces method.
     */
    @Test
    public void deleteAllLevelPiecesGetTest() {

        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelPieces.add(levelPiece);
        levelGenerator.setLevelPieces(levelPieces);

        levelGenerator.getLevelPieces(vec);
        localTranslation = new Vector3f(-121.0f, 0.0f, 0.0f);
        //Ensure 1 level piece gets deleted when the player is far away.
        assertEquals(5, levelGenerator.deleteLevelPieces(localTranslation).size());
        //Make sure that the number of levelpieces gets back up to the regular amount.
        assertEquals(levelGenerator.getLevelPieces(vec).size(), levelGenerator.getNumberOfLevelPieces());
    }

}
