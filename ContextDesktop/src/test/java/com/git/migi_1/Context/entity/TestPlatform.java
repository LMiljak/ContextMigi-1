package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.model.entity.behaviour.AccelerometerMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Test class for the Platform class.
 * @author Marcel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestPlatform extends TestEntity {

    private Platform platform;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private MoveBehaviour moveBehaviour;
    private Spatial model;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     * @throws Exception 
     */
    @Override
    @Before
    public void setUp() throws Exception {
    	try {
 			PowerMockito.whenNew(AccelerometerMoveBehaviour.class)
 				.withAnyArguments().thenReturn(Mockito.mock(AccelerometerMoveBehaviour.class));
 			
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
    	
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        moveBehaviour = Mockito.mock(MoveBehaviour.class);
        Main main = Mockito.mock(Main.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.mockStatic(Main.class);
        Mockito.when(Main.getMain()).thenReturn(main);
        Mockito.when(main.getServer()).thenReturn(Mockito.mock(ServerWrapper.class));
        PowerMockito.whenNew(CarrierAssigner.class).withAnyArguments().thenReturn(Mockito.mock(CarrierAssigner.class));
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);

        platform = new Platform(new Vector3f(0, 0, 0), Mockito.mock(MainEnvironment.class));

        setMoveBehaviour(moveBehaviour);
        setEntity(platform);

    }

    /**
     * Tests getDefaultModel method.
     */
    @Test
    public void testGetDefaultModel() {
        assertEquals(platform.getDefaultModel(), model);
    }
}
