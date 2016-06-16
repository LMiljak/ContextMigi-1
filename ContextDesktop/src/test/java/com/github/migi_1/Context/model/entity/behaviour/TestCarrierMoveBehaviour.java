package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Carrier;
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
        ArrayList<Carrier> carriers = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
        	Carrier c = Mockito.mock(Carrier.class);
            carriers.add(c);
            Mockito.when(c.getModel()).thenReturn(model);
        }
        commander = Mockito.mock(Commander.class);
        Mockito.when(commander.getModel()).thenReturn(model);
        environment = Mockito.mock(MainEnvironment.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        setMoveVector(new Vector3f(1, 2, 3));
        Mockito.when(environment.getCommander()).thenReturn(commander);
        testMoveBehaviour = new CarrierMoveBehaviour(carriers.get(0), getMoveVector(), environment);
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
        assertEquals(testMoveBehaviour.getImmobilized(), 0);
        testMoveBehaviour.collided();
        assertEquals(testMoveBehaviour.getImmobilized(), CarrierMoveBehaviour.getNumberFrames());
    }

    /**
     * Tests if the getMoveVector method works when the carrier is doing nothing special.
     */
    @Test
    public void getMoveVectorRegularTest() {
        assertEquals(getMoveVector(), testMoveBehaviour.getMoveVector());
    }

    /**
     * Tests if the getMoveVector method works when the carrier is immobilized.
     */
    @Test
    public void getMoveVectorImmobilizedTest() {
        assertEquals(0, testMoveBehaviour.getImmobilized());
        testMoveBehaviour.collided();
        assertEquals(testMoveBehaviour.getImmobilized(), CarrierMoveBehaviour.getNumberFrames());
        assertEquals(new Vector3f(0, 0, 0), testMoveBehaviour.getMoveVector());
    }

    /**
     * Tests if the getMoveVector method works when the carrier is catching up.
     */
    @Test
    public void getMoveVectorCatchUpTest() {
        Vector3f oldMoveVector = getMoveVector();
        assertEquals(oldMoveVector, testMoveBehaviour.getMoveVector());
        testMoveBehaviour.setCatchUp(true);
        oldMoveVector.setX(oldMoveVector.getX() - testMoveBehaviour.getAcceleratingFactor());
        //Rounding errors, so therefore testing if the values differ practically nothing.
        assertTrue(Math.abs(oldMoveVector.mult(2.0f).x - testMoveBehaviour.getMoveVector().x) < 0.01);
        testMoveBehaviour.setCatchUp(false);
        assertEquals(getMoveVector(), testMoveBehaviour.getMoveVector());
    }

    /**
     * Tests if the getMoveVector method works when the carrier is null.
     */
    @Test
    public void getMoveVectorCarrierNullTest() {
        testMoveBehaviour.setCarrier(null);
        assertEquals(getMoveVector(), testMoveBehaviour.getMoveVector());
    }

    /**
     * Tests if the updateMoveVector method works when the carrier is immobilized.
     */
    @Test
    public void updateMoveVectorImmobilizedTest() {
        assertEquals(0, testMoveBehaviour.getImmobilized());
        testMoveBehaviour.collided();
        assertEquals(testMoveBehaviour.getImmobilized(), CarrierMoveBehaviour.getNumberFrames());
        testMoveBehaviour.updateMoveVector();
        assertEquals(testMoveBehaviour.getImmobilized(), CarrierMoveBehaviour.getNumberFrames() - 1);
    }

    /**
     * Tests the getter for the catch up attribute.
     */
    @Test
    public void getCatchUpTest() {
        assertFalse(testMoveBehaviour.getCatchUp());
        testMoveBehaviour.setCatchUp(true);
        assertTrue(testMoveBehaviour.getCatchUp());
        testMoveBehaviour.setCatchUp(false);
        assertFalse(testMoveBehaviour.getCatchUp());
    }

    /**
     * Tests the getter for the relativeLocation attribute.
     */
    @Test
    public void getRelativeLocationTest() {
        assertEquals(new Vector3f(0, 0, 0), testMoveBehaviour.getRelativeLocation());
    }

    /**
     * Tests the getter for the carrier.
     */
    @Test
    public void getAndSetCarrierTest() {
        Carrier oldCarrier = testMoveBehaviour.getCarrier();
        testMoveBehaviour.setCarrier(Mockito.mock(Carrier.class));
        assertNotEquals(oldCarrier, testMoveBehaviour.getCarrier());
    }

    /**
     * Tests if the carrier move behaviour stays correct
     * when the carrier overshoots the platform
     */
    @Test
    public void updateMoveVector_overshot_Test() {
        Carrier carrier = Mockito.mock(Carrier.class);
        assertFalse(testMoveBehaviour.getCatchUp());
        Mockito.when(carrier.getRelativeLocation()).thenReturn(new Vector3f(100, 100, 100));
        Mockito.when(carrier.getModel()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(10, 0, 0));
        testMoveBehaviour = new CarrierMoveBehaviour(carrier, getMoveVector(), environment);
        testMoveBehaviour.setCarrier(carrier);
        testMoveBehaviour.updateMoveVector();
        assertFalse(testMoveBehaviour.getCatchUp());

    }
}
