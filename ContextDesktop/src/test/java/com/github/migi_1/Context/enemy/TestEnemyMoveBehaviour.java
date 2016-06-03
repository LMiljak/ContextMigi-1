package com.github.migi_1.Context.enemy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.git.migi_1.Context.entity.TestMoveBehaviour;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class for testing the enemy movement behaviour.
 * @author Damian
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestEnemyMoveBehaviour extends TestMoveBehaviour {
    
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private Carrier[] carriers;
    private Enemy enemy;
    private EnemyMoveBehaviour enemyMoveBehaviour;
    private ArrayList<EnemySpot> enemySpots;
    private EnemySpot enemySpot;
    
    @Override
    @Before
    public void setUp() {
        
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        
        enemy = Mockito.mock(Enemy.class);
        enemySpots = new ArrayList<EnemySpot>();
        enemySpot = Mockito.mock(EnemySpot.class);
        enemySpots.add(enemySpot);
        
        carriers = new Carrier[4];
        for (int i = 0; i < carriers.length; i++) {
            carriers[i] = Mockito.mock(Carrier.class);
            Mockito.when(carriers[i].getId()).thenReturn(i);
            Mockito.when(carriers[i].getModel()).thenReturn(model);
            Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));
            Mockito.when(carriers[i].getEnemySpots()).thenReturn(enemySpots);
        }        
                
        Mockito.when(enemy.getModel()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(-100, -100, -100));
        Mockito.when(enemySpot.getLocation()).thenReturn(new Vector3f(0, 0, 0));
        enemyMoveBehaviour = new EnemyMoveBehaviour(enemy, carriers);
    }
    
    /**
     * Tests creating a enemy spot when not all are occupied yet.
     */
    @Test
    public void testCreateTargetSpot1() {
        Mockito.when(enemySpots.get(0).isOccupied()).thenReturn(false);
        enemyMoveBehaviour = new EnemyMoveBehaviour(enemy, carriers);
        assertTrue(enemyMoveBehaviour.getTargetSpot() != null);
    }
    
    /**
     * Tests creating a enemy spot when all are occupied.
     */
    @Test
    public void testCreateTargetSpot2() {
        Mockito.when(enemySpots.get(0).isOccupied()).thenReturn(true);
        enemyMoveBehaviour = new EnemyMoveBehaviour(enemy, carriers);
        assertTrue(enemyMoveBehaviour.getTargetSpot() == null);
    }
    
    /**
     * Tests updating the move vector when the enemy is behind its target spot.
     */
    @Test
    public void testUpdateMoveVector1() {
        Mockito.when(enemySpots.get(0).isOccupied()).thenReturn(false);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(-200f, 0, 0));
        enemyMoveBehaviour = new EnemyMoveBehaviour(enemy, carriers);
        float previousX = enemyMoveBehaviour.getMoveVector().x;
        try {
            ((EnemyMoveBehaviour)enemyMoveBehaviour).updateMoveVector();
            Whitebox.invokeMethod(enemyMoveBehaviour, "handleXmovement");
            assertTrue(enemyMoveBehaviour.getMoveVector().x > previousX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tests updating the move vector when the enemy is in front of its target spot.
     */
    @Test
    public void testUpdateMoveVector2() {
        Mockito.when(enemySpots.get(0).isOccupied()).thenReturn(false);        
        enemyMoveBehaviour = new EnemyMoveBehaviour(enemy, carriers);
        float previousX = enemyMoveBehaviour.getMoveVector().x;
        try {
            ((EnemyMoveBehaviour)enemyMoveBehaviour).updateMoveVector();
            Whitebox.invokeMethod(enemyMoveBehaviour, "handleXmovement");
            assertEquals(enemyMoveBehaviour.getMoveVector().x,
                    previousX + ((EnemyMoveBehaviour) enemyMoveBehaviour).getSpeed(),
                    0.001f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    @Test
    public void testGetAndSetMoveVector() {
        
    }

    
    
    
}
