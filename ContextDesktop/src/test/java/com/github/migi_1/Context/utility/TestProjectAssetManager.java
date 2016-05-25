package com.github.migi_1.Context.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.asset.AssetManager;

/**
 * Test class for the ProjectAssetManager class.
 * @author Nils
 *
 */
public class TestProjectAssetManager {

    private AssetManager assetManager;
    private ProjectAssetManager projAssetManager;

    /**
     * Initialise all mock objects, static class responses and initialise the tested object.
     */
    @Before
    public void setup() {
        assetManager = Mockito.mock(AssetManager.class);
        projAssetManager = ProjectAssetManager.getInstance();
    }

    /**
     * Verify the instance returned by the getInstance method is a projectAssetManager.
     */
    @Test
    public void getInstanceTest() {
        assertEquals(projAssetManager.getClass(), ProjectAssetManager.class);
    }

    /**
     * Verify the get and set AssetManager methods work correctly.
     */
    @Test
    public void getAndSetAssetManager() {
        //Verify there is no assetManager set.
        assertNotEquals(projAssetManager.getAssetManager(), assetManager);
        projAssetManager.setAssetManager(assetManager);
        assertEquals(projAssetManager.getAssetManager(), assetManager);
    }

}
