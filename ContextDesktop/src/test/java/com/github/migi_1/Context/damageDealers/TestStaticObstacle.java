package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.git.migi_1.Context.entity.TestEntity;
import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.obstacle.StaticObstacle;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;


/**
 * Test class for the StaticObstacle class.
 * @author Marcel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestStaticObstacle extends TestEntity {

    private StaticObstacle staticObstacle;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private MoveBehaviour moveBehaviour;
    private Spatial model;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @Override
    public void setUp() {

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        moveBehaviour = Mockito.mock(MoveBehaviour.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);

        staticObstacle = new StaticObstacle();

        setMoveBehaviour(moveBehaviour);
        setEntity(staticObstacle);

    }

    /**
     * Tests the collideWith method.
     */
    @Test
    public void collideWithTest() {
        Spatial collider = Mockito.mock(Spatial.class);
        CollisionResults results = Mockito.mock(CollisionResults.class);
        staticObstacle.collideWith(collider, results);
        Mockito.verify(model, Mockito.times(1)).collideWith(collider, results);
    }

    /**
     * Test the getHealth method.
     */
    @Test
    public void getHealthTest() {
       assertEquals(staticObstacle.getHealth(), 1);
    }

    /**
     * Test the setHealth method.
     */
    @Test
    public void setHealthTest() {

        staticObstacle.setHealth(10);
        assertEquals(staticObstacle.getHealth(), 10);

    }

    /**
     * Test the takeDamage method.
     */
    @Test
    public void takeDamageTest() {

        staticObstacle.takeDamage(1);
        assertEquals(staticObstacle.getHealth(), 0);

    }

    /**
     * Test the onKilled method.
     */
    @Test
    public void onKilledTest() {
        staticObstacle.onKilled();
    }
}
