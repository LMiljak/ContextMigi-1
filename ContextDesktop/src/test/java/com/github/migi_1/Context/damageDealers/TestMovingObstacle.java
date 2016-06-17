package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.model.entity.behaviour.MovingObstacleMoveBehaviour;
import com.github.migi_1.Context.obstacle.MovingObstacle;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.scene.Spatial;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class, MovingObstacle.class})
public class TestMovingObstacle {

    private MovingObstacle movingObstacle;
    private BoundingBox boundingBox;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private MovingObstacleMoveBehaviour moveBehaviour;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ProjectAssetManager.class);
        boundingBox = PowerMockito.mock(BoundingBox.class);
        model = Mockito.mock(Spatial.class);
        moveBehaviour = Mockito.mock(MovingObstacleMoveBehaviour.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);

        PowerMockito.whenNew(MovingObstacleMoveBehaviour.class).withArguments(Mockito.any(), Mockito.any(), Mockito.any()).
            thenReturn(moveBehaviour);

        Mockito.when(model.scale(Mockito.anyFloat())).thenReturn(model);
        Mockito.when(model.rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat())).thenReturn(model);
        Mockito.when(model.move(Mockito.any())).thenReturn(model);

        movingObstacle = Mockito.spy(new MovingObstacle(boundingBox, boundingBox));
        Mockito.when(movingObstacle.getModel()).thenReturn(model);
    }

    @Test
    public void getAndSetHealthTest() {
        int oldHealth = movingObstacle.getHealth();
        movingObstacle.setHealth(oldHealth + 10);
        assertNotEquals(oldHealth, movingObstacle.getHealth());
    }

    @Test
    public void takeDamageTest() {
        int oldHealth = movingObstacle.getHealth();
        movingObstacle.takeDamage(10);
        assertEquals(oldHealth - 10 , movingObstacle.getHealth());
    }

    @Test
    public void onKilledTest() {
        movingObstacle.onKilled();
        Mockito.verify(movingObstacle).onKilled();
        Mockito.verifyNoMoreInteractions(movingObstacle);
    }

}
