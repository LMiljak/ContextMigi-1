package com.git.migi_1.Context.entity;

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

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Test class for the Carrier class.
 * @author Marcel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestCarrier extends TestEntity {

    private Carrier testCarrier;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private MoveBehaviour moveBehaviour;
    private Spatial model;
    private MainEnvironment environment;
    private Commander commander;
    private Main main;
    private ServerWrapper serverWrapper;

    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @Override
    @Before
    public void setUp() {
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        commander = Mockito.mock(Commander.class);
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);

        ArrayList<Carrier> carriers = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
        	Carrier carrier = Mockito.mock(Carrier.class);
        	carriers.add(carrier);
        	Mockito.when(carrier.getModel()).thenReturn(model);
        }
        moveBehaviour = Mockito.mock(MoveBehaviour.class);
        environment = Mockito.mock(MainEnvironment.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(environment.getCarriers()).thenReturn(carriers);
        Mockito.when(environment.getCommander()).thenReturn(commander);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));
        Mockito.when(environment.getMain()).thenReturn(main);
        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(null);


        Mockito.when(commander.getModel()).thenReturn(model);
        testCarrier = new Carrier(new Vector3f(0, 0, 0), PlatformPosition.BACKLEFT, environment);

        setMoveBehaviour(moveBehaviour);
        setEntity(testCarrier);

    }

    /**
     * Tests the collideWithMethod.
     */
    @Test
    public void collideWithTest() {
        Spatial collider = Mockito.mock(Spatial.class);
        CollisionResults results = Mockito.mock(CollisionResults.class);
        testCarrier.collideWith(collider, results);
        Mockito.verify(model, Mockito.times(1)).collideWith(collider, results);
    }

    /**
     * Tests the takeDamage method.
     */
    @Test
    public void takeDamageTest() {
        testCarrier.takeDamage(1);
        assertEquals(2, testCarrier.getHealth());
    }

    /**
     * Tests the onKilled method.
     */
    @Test
    public void onKilledTest() {
        testCarrier.onKilled();
    }

    /**
     * Tests the setHealth method.
     */
   @Test
    public void setHealthTest() {
        testCarrier.setHealth(42);
        assertEquals(testCarrier.getHealth(), 42);
    }

    /**
     * Tests the getPosition method.
     */
    @Test
    public void getPositionTest() {
        assertEquals(testCarrier.getPosition(), PlatformPosition.BACKLEFT);
    }

}
