package com.github.migi_1.Context.enemy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.model.LevelPiece;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * TestSuite for the EnemySpawner class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BoundingBox.class, ProjectAssetManager.class})
public class TestEnemySpawner {

    private EnemySpawner enemySpawner;
    private Commander commander;
    private ArrayList<Carrier> carriers;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private LevelPiece levelPiece;
    private BoundingBox boundingBox;
    private Carrier carrier;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ProjectAssetManager.class);

        assetManager = Mockito.mock(AssetManager.class);
        boundingBox = PowerMockito.mock(BoundingBox.class);
        carrier = Mockito.mock(Carrier.class);
        carriers = new ArrayList<Carrier>(4);
        commander = Mockito.mock(Commander.class);
        levelPiece = Mockito.mock(LevelPiece.class);
        model = Mockito.mock(Spatial.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        PowerMockito.whenNew(LevelPiece.class).withNoArguments().thenReturn(levelPiece);

        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(commander.getModel()).thenReturn(model);
        Mockito.when(levelPiece.getModel()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(Vector3f.ZERO);
        Mockito.when(model.getWorldBound()).thenReturn(boundingBox);
        Mockito.when(boundingBox.getCenter()).thenReturn(Vector3f.ZERO);
        Mockito.when(carrier.getModel()).thenReturn(model);

        carrier.setHealth(0);
        carriers.add(carrier);
        carriers.add(carrier);
        carriers.add(carrier);
        carriers.add(carrier);

        enemySpawner = new EnemySpawner(commander, carriers);
    }

    @Test
    public void generateEnemiesTest() {
        LinkedList<Enemy> enemies = enemySpawner.generateEnemies();
        assertFalse(enemies.isEmpty());
        //Verify the first enemy is not rotates (so createEnemy3 has succeeded).
        assertNotEquals(Quaternion.ZERO, enemies.getFirst().getModel().getLocalRotation());
    }

    @Test
    public void getCarriersTest() {
        ArrayList<Carrier> carriers = enemySpawner.getCarriers();
        assertFalse(carriers.isEmpty());
        assertEquals(4, carriers.size());
        assertEquals(ArrayList.class, carriers.getClass());
    }

    @Test
    public void deleteEnemiesTest() {
        enemySpawner.generateEnemies();
        LinkedList<Enemy> enemies = enemySpawner.getEnemies();
        enemies.getFirst().setHealth(0);
        enemySpawner.setEnemies(enemies);
        LinkedList<Enemy> deletedEnemies = enemySpawner.deleteEnemies();
        assertFalse(deletedEnemies.isEmpty());
    }

    @Test
    public void deleteEnemiesTooFarAwayTest() {
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(100, 100, 100));
        enemySpawner.generateEnemies();
        LinkedList<Enemy> deletedEnemies = enemySpawner.deleteEnemies();
        assertFalse(deletedEnemies.isEmpty());
    }
}
