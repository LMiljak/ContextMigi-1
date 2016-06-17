package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.obstacle.MovingObstacleFactory;
import com.github.migi_1.Context.obstacle.ObstacleSpawner;
import com.github.migi_1.Context.obstacle.StaticObstacleFactory;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.network.Server;
import com.jme3.scene.Spatial;

/**
 * Test suite for the ObstacleSpawner class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class, Main.class, BoundingVolume.class, BoundingBox.class})
public class TestObstacleSpawner {

    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private ObstacleSpawner obstacleSpawner;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;
    private MainEnvironment environment;
    private Commander commander;
    private BoundingBox leftBound;
    private BoundingBox rightBound;

    /**
     * Setup for the tests.
     * @throws Exception something went wrong
     */
    @Before
    public void setUp() throws Exception {
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);
        environment = Mockito.mock(MainEnvironment.class);
        commander = Mockito.mock(Commander.class);
        leftBound =  new BoundingBox(
                new Vector3f(0, 0, 0),
                        Float.MAX_VALUE,
                        100f, 1f);
        rightBound =  new BoundingBox(
                new Vector3f(0, 0, 0),
                        Float.MAX_VALUE,
                        100f, 1f);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.mockStatic(Main.class);
        BDDMockito.given(Main.getInstance()).willReturn(main);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);
        Mockito.when(environment.getCommander()).thenReturn(commander);
        Mockito.when(commander.getModel()).thenReturn(model);
        Mockito.when(environment.getLeftBound()).thenReturn(leftBound);
        Mockito.when(environment.getRightBound()).thenReturn(rightBound);
        obstacleSpawner = new ObstacleSpawner(environment);
    }

    /**
     * Tests the getters/setter for the StaticObstacleFactory.
     */
    @Test
    public void getSetStaticObstacleFactory() {
        obstacleSpawner.setStaticObstacleFactory(new StaticObstacleFactory());
        assertEquals(StaticObstacleFactory.class, obstacleSpawner.getStaticObstacleFactory().getClass());
        obstacleSpawner.setStaticObstacleFactory(null);
        assertNull(obstacleSpawner.getStaticObstacleFactory());        
    }
    
    /**
     * Tests the getters/setters for the MovingObstacleFactory.
     */
    @Test
    public void getSetMovingObstacleFactory() {
        obstacleSpawner.setMovingObstacleFactory(new MovingObstacleFactory(environment));
        assertEquals(MovingObstacleFactory.class, obstacleSpawner.getMovingObstacleFactory().getClass());
        obstacleSpawner.setMovingObstacleFactory(null);
        assertNull(obstacleSpawner.getMovingObstacleFactory());        
    }

}
