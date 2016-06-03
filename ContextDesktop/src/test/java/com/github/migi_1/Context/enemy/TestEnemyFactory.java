package com.github.migi_1.Context.enemy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


/**
 * Test class for the Enemyfactory class.
 * @author Damian
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestEnemyFactory {


    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private EnemyFactory enemyFactory;
    private BoundingBox boundingBox;
    private ArrayList<Carrier> carriers;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @Before
    public void setUp() {

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        
        boundingBox = Mockito.mock(BoundingBox.class);
        
        carriers = new ArrayList<Carrier>();
        for (int i = 0; i < 3; i++) {
            carriers.add(Mockito.mock(Carrier.class));
           // Mockito.when(carriers[i].getId()).thenReturn(i);
            Mockito.when(carriers.get(i).getModel()).thenReturn(model);
            Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));
        }     
        Mockito.when(boundingBox.getZExtent()).thenReturn(0f);
        enemyFactory = new EnemyFactory(boundingBox, 0, 0, carriers);
        
    }

    /**
     * Tests the create method of the first enemy.
     */
    @Test
    public void testCreateEnemy1() {
        assertEquals(enemyFactory.createEnemy1(0).getClass(), Enemy.class);
    }
    
    /**
     * Tests the create method of the second enemy.
     */
    @Test
    public void testCreateEnemy2() {
        assertEquals(enemyFactory.createEnemy2(0).getClass(), Enemy.class);
    }
    
    /**
     * Tests the create method of the third enemy.
     */
    @Test
    public void testCreateEnemy3() {
        assertEquals(enemyFactory.createEnemy3(0).getClass(), Enemy.class);
    }


}
