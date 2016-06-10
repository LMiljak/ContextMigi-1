package com.git.migi_1.Context.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.behaviour.AccelerometerMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.github.migi_1.Context.utility.Filter;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Test class for the Commander class.
 * @author Marcel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestCommander extends TestEntity {

    private Commander testCommander;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private MoveBehaviour moveBehaviour;
    private Spatial model;
    private Filter<String> filter;



    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @SuppressWarnings("unchecked")
    @Override
    @Before
    public void setUp() {
        filter = Mockito.mock(Filter.class);
        try {
            PowerMockito.whenNew(AccelerometerMoveBehaviour.class)
            .withArguments(filter).thenReturn(Mockito.mock(AccelerometerMoveBehaviour.class));
        } catch (Exception e) {
            e.printStackTrace();
        }


        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        moveBehaviour = Mockito.mock(MoveBehaviour.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);

        testCommander = new Commander(new Vector3f(0, 0, 0), moveBehaviour);

        setMoveBehaviour(moveBehaviour);
        setEntity(testCommander);

    }

    /**
     * Tests the collideWith method.
     */
    @Test
    public void collideWithTest() {
        Spatial collider = Mockito.mock(Spatial.class);
        CollisionResults results = Mockito.mock(CollisionResults.class);
        testCommander.collideWith(collider, results);
        Mockito.verify(model, Mockito.times(1)).collideWith(collider, results);
    }

}
