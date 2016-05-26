package com.github.migi_1.Context.damageDealers;

import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import com.git.migi_1.Context.entity.TestEntity;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class TestStaticObstacle extends TestEntity {

    private Commander testCommander;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private MoveBehaviour moveBehaviour;
    private Spatial model;


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

        testCommander = new Commander(new Vector3f(0, 0, 0));

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
