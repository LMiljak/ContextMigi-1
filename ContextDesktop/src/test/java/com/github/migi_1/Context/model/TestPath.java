package com.github.migi_1.Context.model;

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

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 * Testsuite for the path class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class})
public class TestPath {
    private Path path;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model, model2;

    /**
     * Setup for the test suite.
     */
    @Before
    public void setUp() {
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model = Mockito.mock(Spatial.class);
        model2 = Mockito.mock(Spatial.class);
        PowerMockito.mockStatic(ProjectAssetManager.class);
        //Loads "model" as model for path.
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        path = new Path();
    }

    /**
     * Tests the getModel and setModel method.
     */
    @Test
    public void testGetAndSetModel() {
        assertNotEquals(model2, path.getModel());
        path.setModel(model2);
        assertEquals(model2, path.getModel());
    }

}
