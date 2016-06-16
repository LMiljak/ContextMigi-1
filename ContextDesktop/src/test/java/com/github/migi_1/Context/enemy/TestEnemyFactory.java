package com.github.migi_1.Context.enemy;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * TestSuite for the EnemyFactory class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BoundingBox.class, ProjectAssetManager.class})
public class TestEnemyFactory {

    private EnemyFactory enemyFactory;
    private ArrayList<Carrier> carriers;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private LevelPiece levelPiece;
    private Spatial model;
    private BoundingBox boundingBox;
    private Enemy enemy;
    private Vector3f vector;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ProjectAssetManager.class);

        assetManager = Mockito.mock(AssetManager.class);
        vector = Vector3f.ZERO;
        boundingBox = PowerMockito.mock(BoundingBox.class);
        carriers = new ArrayList<Carrier>(4);
        enemy = Mockito.mock(Enemy.class);
        levelPiece = Mockito.mock(LevelPiece.class);
        model = Mockito.mock(Spatial.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);


        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        PowerMockito.whenNew(LevelPiece.class).withNoArguments().thenReturn(levelPiece);
        PowerMockito.whenNew(Enemy.class).withArguments(vector, carriers).thenReturn(enemy);
        Mockito.when(levelPiece.getModel()).thenReturn(model);
        Mockito.when(model.getWorldBound()).thenReturn(boundingBox);
        Mockito.when(model.scale(Mockito.anyFloat())).thenReturn(model);
        Mockito.doNothing().when(model).setLocalTranslation(Mockito.any());
        Mockito.when(boundingBox.getCenter()).thenReturn(Vector3f.ZERO);
        Mockito.when(enemy.getModel()).thenReturn(model);
        Mockito.when(enemy.getDefaultModel()).thenReturn(model);
        enemyFactory = new EnemyFactory(carriers);
    }

    @Test
    public void createEnemy1test() {
        Enemy enemy1 = enemyFactory.createEnemy1(0);
        Mockito.verify(model).getLocalTranslation();
        Mockito.verify(model, Mockito.times(0)).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
        assertNotNull(enemy1);
    }

    @Test
    public void createEnemy2test() {
        Enemy enemy2 = enemyFactory.createEnemy2(0);
        Mockito.verify(model).getLocalTranslation();
        Mockito.verify(model, Mockito.times(0)).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
        assertNotNull(enemy2);
    }

    @Test
    public void createEnemy3test() {
        Enemy enemy3 = enemyFactory.createEnemy3(0);
        Mockito.verify(model).getLocalTranslation();
        Mockito.verify(model).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
        assertNotNull(enemy3);
    }
}
