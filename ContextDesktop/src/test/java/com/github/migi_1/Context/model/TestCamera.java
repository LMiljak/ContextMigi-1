package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class TestCamera {

    private Camera cam;
    private Spatial model;

    @Before
    public void setUp() throws Exception {
        cam = new Camera();
        model = Mockito.mock(Spatial.class);
    }

    /**
     * Verify that the getModel method returns a model.
     */
    @Test
    public void getModelTest() {
        assertEquals(cam.getModel().getClass(), new Node().getClass());
    }


    /**
     * Check if the setModel method sets the correct node.
     */
    @Test
    public void setAndGetModelTest() {
        //Verify that "node" is not the current model of the camera.
        assertNotEquals(cam.getModel(), model);
        cam.setModel(model);
        assertEquals(cam.getModel(), model);
    }

}
