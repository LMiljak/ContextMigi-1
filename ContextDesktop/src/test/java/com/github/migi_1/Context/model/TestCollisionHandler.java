package com.github.migi_1.Context.model;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.audio.AudioController;
import com.github.migi_1.Context.main.HUDController;
import com.github.migi_1.Context.model.entity.CarrierAssigner;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.obstacle.ObstacleSpawner;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Test suite for the Environment class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestCollisionHandler {

    protected MainEnvironment environment;

    protected Node root;
    protected AssetManager assetManager;
    private HUDController hudController;
    private AudioController audioController;

    private CollisionHandler collisionHandler;
    /**
     * Initialises the environment field for testing.
     * @throws Exception exception that is thrown.
     */
    @Before
    public void setUp() throws Exception {   
        Commander commander = Mockito.mock(Commander.class);
        Platform platform = Mockito.mock(Platform.class);
        ObstacleSpawner obstacleSpawner = Mockito.mock(ObstacleSpawner.class);
        CarrierAssigner carrierAssigner = Mockito.mock(CarrierAssigner.class);
        environment = PowerMockito.spy(new MainEnvironment(carrierAssigner));
        Spatial model = Mockito.mock(Spatial.class);
        Path path = Mockito.mock(Path.class); 
        
        
        hudController = Mockito.mock(HUDController.class);
        audioController = Mockito.mock(AudioController.class);
        ProjectAssetManager projectAssetManager = mock(ProjectAssetManager.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.whenNew(HUDController.class).withAnyArguments().thenReturn(hudController);
        PowerMockito.whenNew(AudioController.class).withAnyArguments().thenReturn(audioController);
        when(ProjectAssetManager.getInstance()).thenReturn(projectAssetManager);
        this.assetManager = Mockito.mock(AssetManager.class);
        when(projectAssetManager.getAssetManager()).thenReturn(assetManager);
        when(path.getModel()).thenReturn(model);
        when(model.center()).thenReturn(model);
        when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));
        collisionHandler = new CollisionHandler(commander, platform, obstacleSpawner, environment);
    }
    
    /**
     * Tests the creation of boundingBoxes.
     */
    @Test
    public void testCreateBoundingBoxes() {
        assertNull(collisionHandler.getBoundingBoxWallLeft());
        assertNull(collisionHandler.getBoundingBoxWallRight());
    }
    
    
}
