package com.github.migi_1.Context.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

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
import com.github.migi_1.Context.model.entity.behaviour.AccelerometerMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.PlatformRotateBehaviour;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
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
    private HashMap<PlatformPosition, Carrier> carriers;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    @Before
    public void setUp() throws Exception {
    	try {
 			PowerMockito.whenNew(AccelerometerMoveBehaviour.class)
 				.withAnyArguments().thenReturn(Mockito.mock(AccelerometerMoveBehaviour.class));
		} catch (Exception e) {
		        e.printStackTrace();
		}

    	carriers = Mockito.mock(HashMap.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        moveBehaviour = Mockito.mock(MoveBehaviour.class);
        Main main = Mockito.mock(Main.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.mockStatic(Main.class);
        Mockito.when(Main.getInstance()).thenReturn(main);
        Mockito.when(main.getServer()).thenReturn(Mockito.mock(ServerWrapper.class));
        PowerMockito.whenNew(CarrierAssigner.class).withAnyArguments().thenReturn(Mockito.mock(CarrierAssigner.class));
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(model.getLocalRotation()).thenReturn(new Quaternion(0, 0, 0, 0));
        platform = new Platform(new Vector3f(0, 0, 0),
        		Mockito.mock(MainEnvironment.class),
        		Mockito.mock(CarrierAssigner.class));
        platform.setCarriers(carriers);

        setMoveBehaviour(moveBehaviour);
        setEntity(platform);

    }

    /**
     * Tests getDefaultModel method.
     */
    @Test
    public void getDefaultModelTest() {
        assertEquals(platform.getDefaultModel(), model);
    }
    
    /**
     * Tests the addCarrier method.
     */
    @Test
    public void addCarrierTest() {
        platform.addCarrier(Mockito.mock(Carrier.class));
        Mockito.verify(carriers).put(Mockito.any(), Mockito.any());
    }
    
    /**
     * Tests the getter for the carrier attribute.
     */
    @Test
    public void getCarrierTest() {
        Carrier carrier = Mockito.mock(Carrier.class);
        Mockito.when(carrier.getPosition()).thenReturn(PlatformPosition.FRONTLEFT);
        platform.addCarrier(carrier);
        platform.getCarrier(PlatformPosition.FRONTLEFT);
    }
    
    /**
     * Tests if the platform has all 4 carriers.
     */
    @Test
    public void isFullTest() {
        assertFalse(platform.isFull());
    }
    
    /**
     * Tests the getCarriers method, which returns all carriers.
     */
    @Test
    public void getCarriersTest() {
        assertTrue(platform.getCarriers().isEmpty());
    }
    
    /**
     * Tests the getter for the getMoveBehaviour.
     */
    @Test
    public void getRotateBehaviourTest() {
        assertEquals(PlatformRotateBehaviour.class, platform.getRotateBehaviour().getClass());
    }
}
