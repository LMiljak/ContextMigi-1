package com.github.migi_1.Context.enemy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.Context.model.entity.TestEntity;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class tests everything inside the Enemy class.
 * @author Damian
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestEnemy extends TestEntity {

    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private EnemyMoveBehaviour moveBehaviour;
    private Spatial model;
    private Enemy testEnemy;
    private EnemySpot targetSpot;
    private ArrayList<Carrier> carriers;

    /**
     * Initialises all mock objects, static class responses and initialises the tested object.
     */
    @Override
    @Before
    public void setUp() {

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        carriers = new ArrayList<Carrier>();
        for (int i = 0; i < 3; i++) {
            carriers.add(Mockito.mock(Carrier.class));
           // Mockito.when(carriers[i].getId()).thenReturn(i);
            Mockito.when(carriers.get(i).getModel()).thenReturn(model);
            Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));
        }

        targetSpot = Mockito.mock(EnemySpot.class);
        Mockito.when(targetSpot.getCarrier()).thenReturn(carriers.get(0));
        Mockito.doNothing().when(targetSpot).setOccupied(false);

        moveBehaviour = Mockito.mock(EnemyMoveBehaviour.class);
        Mockito.when(moveBehaviour.getTargetSpot()).thenReturn(targetSpot);
        Mockito.when(moveBehaviour.isAtSpot()).thenReturn(true);

        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(model.scale(Mockito.anyFloat())).thenReturn(model);

        testEnemy = new Enemy(new Vector3f(5, 0, 0), carriers);
        testEnemy.setSpot(targetSpot);
        testEnemy.setMoveBehaviour(moveBehaviour);
        setEntity(testEnemy);

    }

    /**
     * Tests the takeDamage method.
     */
    @Test
    public void takeDamageTest() {
        testEnemy.takeDamage(1);
        assertEquals(testEnemy.getHealth(), 0);
    }

    /**
     * Tests the onKilled method.
     */
    @Test
    public void onKilledTest() {
        testEnemy.onKilled();
        assertNull(testEnemy.getSpot().getEnemy());
    }

    /**
     * Tests the setHealth method.
     */
    @Test
    public void setHealthTest() {
        testEnemy.setHealth(42);
        assertEquals(testEnemy.getHealth(), 42);
    }

    /**
     * Tests the attack method.
     */
    @Test
    public void testAttack() {
        testEnemy.attack(1000);
        Mockito.verify(carriers.get(0), Mockito.times(1)).takeDamage(1);
    }
}
