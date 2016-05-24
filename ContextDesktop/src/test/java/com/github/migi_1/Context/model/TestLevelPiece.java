package com.github.migi_1.Context.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.scene.Node;

public class TestLevelPiece {

    private LevelPiece lvlPiece;

    @Before
    public void setUp() throws Exception {
        ProjectAssetManager.getInstance().setAssetManager(AssetManagerCreator.getInstance());
        lvlPiece = new LevelPiece();
    }

    @Test
    public void getAndSetModelTest() {
        assertNull(lvlPiece.getModel());
        lvlPiece.setModel(new Node());
        assertNotNull(lvlPiece.getModel());
    }

}
