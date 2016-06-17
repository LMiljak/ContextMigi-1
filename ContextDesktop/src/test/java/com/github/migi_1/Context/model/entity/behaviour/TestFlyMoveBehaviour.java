package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.main.InputHandler;
import com.jme3.input.KeyInput;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import jmevr.app.VRApplication;

/**
 * Testsuite for the FlyMoveBehaviour class.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({InputHandler.class, VRApplication.class, Quaternion.class, Vector3f.class})
public class TestFlyMoveBehaviour {

    private FlyMoveBehaviour moveBehaviour;
    private InputHandler inputHandler;
    private Quaternion quaternion;
    private Vector3f vector;

    /**
     * Setup for the test suite.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(InputHandler.class);
        PowerMockito.mockStatic(VRApplication.class);
        PowerMockito.mockStatic(Quaternion.class);
        inputHandler = Mockito.mock(InputHandler.class);
        quaternion = Mockito.mock(Quaternion.class);
        vector = Mockito.mock(Vector3f.class);
        BDDMockito.given(InputHandler.getInstance()).willReturn(inputHandler);
        BDDMockito.given(VRApplication.getFinalObserverRotation()).willReturn(quaternion);
        Mockito.when(quaternion.getRotationColumn(2)).thenReturn(vector);

        moveBehaviour = new FlyMoveBehaviour();
    }

    /**
     * Verifies the onKeyPressed method works with input W.
     */
    @Test
    public void pressed_W_Test() {
        assertFalse(moveBehaviour.isForwards());
        moveBehaviour.onKeyPressed(KeyInput.KEY_W);
        assertTrue(moveBehaviour.isForwards());
    }

    /**
     * Verifies the onKeyPressed method works with input D.
     */
    @Test
    public void pressed_D_Test() {
        assertFalse(moveBehaviour.isBack());
        moveBehaviour.onKeyPressed(KeyInput.KEY_D);
        assertTrue(moveBehaviour.isBack());
    }

    /**
     * Verifies the onKeyReleased method works with input W.
     */
    @Test
    public void release_W_Test() {
        assertFalse(moveBehaviour.isForwards());
        moveBehaviour.onKeyPressed(KeyInput.KEY_W);
        assertTrue(moveBehaviour.isForwards());
        moveBehaviour.onKeyReleased(KeyInput.KEY_W);
        assertFalse(moveBehaviour.isForwards());
    }

    /**
     * Verifies the onKeyReleased method works with input D.
     */
    @Test
    public void release_D_Test() {
        assertFalse(moveBehaviour.isBack());
        moveBehaviour.onKeyPressed(KeyInput.KEY_D);
        assertTrue(moveBehaviour.isBack());
        moveBehaviour.onKeyReleased(KeyInput.KEY_D);
        assertFalse(moveBehaviour.isBack());
    }

    /**
     * Verifies the updateMoveVector method works correctly.
     */
    @Test
    public void updateMoveVector_Forw_Test() {
        moveBehaviour.onKeyPressed(KeyInput.KEY_W);
        moveBehaviour.updateMoveVector();
        Mockito.verify(vector).mult(0.1f);
    }

    /**
     * Verifies the updateMoveVector method works correctly.
     */
    @Test
    public void updateMoveVector_Back_Test() {
        moveBehaviour.onKeyPressed(KeyInput.KEY_D);
        moveBehaviour.updateMoveVector();
        Mockito.verify(vector).mult(-0.1f);
    }
}
