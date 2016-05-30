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

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.CarrierMoveBehaviour;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Test class for CarrierMoveBehaviour.
 * @author Marcel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestCarrierMoveBehaviour extends TestEntityMoveBehaviour {

    private CarrierMoveBehaviour testMoveBehaviour;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private Commander commander;
    private MainEnvironment environment;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @Override
    @Before
    public void setUp() {
        model =  Mockito.mock(Spatial.class);
        Carrier[] carriers = new Carrier[4];
        for (int i = 0; i < carriers.length; i++) {
            carriers[i] = Mockito.mock(Carrier.class);
            Mockito.when(carriers[i].getId()).thenReturn(i);
            Mockito.when(carriers[i].getModel()).thenReturn(model);
        }
        commander = Mockito.mock(Commander.class);
        Mockito.when(commander.getModel()).thenReturn(model);
        environment = Mockito.mock(MainEnvironment.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        setMoveVector(new Vector3f(1, 2, 3));
        Mockito.when(environment.getCarriers()).thenReturn(carriers);
        Mockito.when(environment.getCommander()).thenReturn(commander);
        testMoveBehaviour = new CarrierMoveBehaviour(carriers[0], getMoveVector(), environment);
        testMoveBehaviour.setRelativeLocation(new Vector3f(0, 0, 0));
        setMoveBehaviour(testMoveBehaviour);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));

    }

    /**
     * Test the collided method.
     */
    @Override
    @Test
    public void collidedTest() {
        assertEquals(testMoveBehaviour.getImmobalised(), 0);
        testMoveBehaviour.collided();
        assertEquals(testMoveBehaviour.getImmobalised(), 120);


    }



}
