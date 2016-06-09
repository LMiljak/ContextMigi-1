package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.github.migi_1.Context.obstacle.AbstractObstacleFactory;
import com.github.migi_1.Context.obstacle.ObstacleSpawner;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.network.Server;
import com.jme3.scene.Spatial;

/**
 * Test suite for the ObstacleSpawner class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class, Main.class})
public class TestObstacleSpawner {

    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private ObstacleSpawner obstacleSpawner;
    private AbstractObstacleFactory obstacleFactory;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;

    /**
     * Setup for the tests.
     */
    @Before
    public void setUp() {
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.mockStatic(Main.class);
        BDDMockito.given(Main.getInstance()).willReturn(main);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);

        obstacleSpawner = new ObstacleSpawner(new Commander(new Vector3f(0, 0, 0), Mockito.mock(Platform.class)));
    }

    /**
     * Tests the setter for the ObstacleFactory.
     */
    @Test
    public void setObstacleFactoryTest() {
        AbstractObstacleFactory oldFactory = obstacleSpawner.getObstacleFactory();
        obstacleSpawner.setObstacleFactory(obstacleFactory);
        assertNotEquals(oldFactory, obstacleSpawner.getObstacleFactory());
    }

}
