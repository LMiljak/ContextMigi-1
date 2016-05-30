package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.obstacle.StaticObstacle;
import com.github.migi_1.Context.obstacle.StaticObstacleFactory;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;


/**
 * Test class for the StaticObstacleFactory class.
 * @author Marcel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestStaticObstacleFactory extends TestDamageDealerFactory {


    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @Override
    public void setUp() {

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);

        setDamageDealer(new StaticObstacle());
        setTestDamageDealerFactory(new StaticObstacleFactory());
    }

    /**
     * Test the produce method.
     */
    @Override
    @Test
    public void testProduce() {
        assertEquals(getTestDamageDealerFactory().produce().getClass(), StaticObstacle.class);
    }


}
