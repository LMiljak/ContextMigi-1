package com.github.migi_1.Context;

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

import com.github.migi_1.Context.model.MainEnvironment;
import com.jme3.input.InputManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import jmevr.app.VRApplication;

/**
 * Test suite for the InputHandler.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({VRApplication.class, Quaternion.class, Vector3f.class})
public class TestInputHandler {

    private InputHandler inputHandler;
    private InputManager inputManager;
    private MainEnvironment envState;
    private Quaternion quat;
    private Vector3f vec;
    private Main main;

    /**
     * Setup for this test class.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        main = Mockito.mock(Main.class);
        inputManager = Mockito.mock(InputManager.class);
        envState = Mockito.mock(MainEnvironment.class);
        quat = PowerMockito.mock(Quaternion.class);
        vec = Mockito.mock(Vector3f.class);
        PowerMockito.mockStatic(VRApplication.class);
        BDDMockito.given(VRApplication.getFinalObserverRotation()).willReturn(quat);
        BDDMockito.given(quat.getRotationColumn(Mockito.anyInt())).willReturn(vec);
        BDDMockito.given(vec.mult(Mockito.anyFloat())).willReturn(vec);

        Mockito.when(main.getInputManager()).thenReturn(inputManager);
        Mockito.when(main.getEnv()).thenReturn(envState);
        Mockito.when(envState.getFlyCamActive()).thenReturn(true);

        inputHandler = new InputHandler(main);
        inputHandler.initInputs(main);
    }

    /**
     * Tests if all keys are mapped and all listeners are bound.
     */
    @Test
    public void initInputTest() {
        //Verify all keys are mapped correctly.
        Mockito.verify(inputManager, Mockito.times(10)).addMapping(Mockito.anyString(), Mockito.any());
        //Verify all listeners are bound.
        Mockito.verify(inputManager, Mockito.times(10)).addListener(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void moveCamForwardsTest() {
        inputHandler.getActionListener().onAction("forwards", true, 0f);
        inputHandler.moveCamera(0f);
        assertTrue(inputHandler.isForwards());
        Mockito.verify(envState).moveCam(Mockito.any());
    }

    @Test
    public void moveCamBackwardsTest() {
        inputHandler.getActionListener().onAction("backwards", true, 0f);
        inputHandler.moveCamera(0f);
        assertTrue(inputHandler.isBack());
        Mockito.verify(envState).moveCam(Mockito.any());
    }

    @Test
    public void moveCamLeftTest() {
        inputHandler.getActionListener().onAction("left", true, 0f);
        inputHandler.moveCamera(0f);
        assertTrue(inputHandler.isLeft());
        Mockito.verify(envState).rotateCam(Mockito.any());
    }

    @Test
    public void moveCamRightTest() {
        inputHandler.getActionListener().onAction("right", true, 0f);
        inputHandler.moveCamera(0f);
        assertTrue(inputHandler.isRight());
        Mockito.verify(envState).rotateCam(Mockito.any());
    }

    @Test
    public void moveCamUpTest() {
        inputHandler.getActionListener().onAction("up", true, 0f);
        inputHandler.moveCamera(0f);
        assertTrue(inputHandler.isUp());
        Mockito.verify(envState).moveCam(Mockito.any());
    }

    @Test
    public void moveCamDownTest() {
        inputHandler.getActionListener().onAction("down", true, 0f);
        inputHandler.moveCamera(0f);
        assertTrue(inputHandler.isDown());
        Mockito.verify(envState).moveCam(Mockito.any());
    }

    //The following tests test the action listener.

    @Test
    public void testForwards() {
        assertFalse(inputHandler.isForwards());
        inputHandler.getActionListener().onAction("forwards", true, 0f);
        assertTrue(inputHandler.isForwards());
    }

    @Test
    public void testBackwards() {
        assertFalse(inputHandler.isBack());
        inputHandler.getActionListener().onAction("backwards", true, 0f);
        assertTrue(inputHandler.isBack());
    }

    @Test
    public void testLeft() {
        assertFalse(inputHandler.isLeft());
        inputHandler.getActionListener().onAction("left", true, 0f);
        assertTrue(inputHandler.isLeft());
    }

    @Test
    public void testRight() {
        assertFalse(inputHandler.isRight());
        inputHandler.getActionListener().onAction("right", true, 0f);
        assertTrue(inputHandler.isRight());
    }

    @Test
    public void testUp() {
        assertFalse(inputHandler.isUp());
        inputHandler.getActionListener().onAction("up", true, 0f);
        assertTrue(inputHandler.isUp());
    }

    @Test
    public void testDown() {
        assertFalse(inputHandler.isDown());
        inputHandler.getActionListener().onAction("down", true, 0f);
        assertTrue(inputHandler.isDown());
    }

    @Test
    public void testUnknownButton() {
        assertFalse(inputHandler.isDown());
        inputHandler.getActionListener().onAction("unusedName", true, 0f);
        assertFalse(inputHandler.isDown());
    }

    @Test
    public void testNoFlyCamButtonPress() {
        //Overwrite method stub for this method only.
        Mockito.when(envState.getFlyCamActive()).thenReturn(false);
        assertFalse(inputHandler.isDown());
        inputHandler.getActionListener().onAction("down", true, 0f);
        assertFalse(inputHandler.isDown());
    }

    @Test
    public void testExit() {
        Mockito.verify(main, Mockito.never()).destroy();
        inputHandler.getActionListener().onAction("exit", false, 0f);
        Mockito.verify(main, Mockito.never()).destroy();
        inputHandler.getActionListener().onAction("exit", true, 0f);
        Mockito.verify(main).destroy();
    }

    @Test
    public void testCamSwitch() {
        Mockito.verify(envState, Mockito.never()).swapCamera();
        inputHandler.getActionListener().onAction("cam_switch", false, 0f);
        Mockito.verify(envState, Mockito.never()).swapCamera();
        inputHandler.getActionListener().onAction("cam_switch", true, 0f);
        Mockito.verify(envState).swapCamera();
    }

    /**
     * Verifies steering left.
     */
    @Test
    public void testCheckSteerLeft() {
        Mockito.verify(envState, Mockito.never()).steer(Mockito.anyFloat());
        inputHandler.getActionListener().onAction("steer_left", false, 0f);
        Mockito.verify(envState, Mockito.never()).steer(-1.0f);
        inputHandler.getActionListener().onAction("steer_left", true, 0f);
        Mockito.verify(envState).steer(-1.0f);
    }

    /**
     * Verifies steering right.
     */
    @Test
    public void testCheckSteerRight() {
        Mockito.verify(envState, Mockito.never()).steer(Mockito.anyFloat());
        inputHandler.getActionListener().onAction("steer_right", false, 0f);
        Mockito.verify(envState, Mockito.never()).steer(1.0f);
        inputHandler.getActionListener().onAction("steer_right", true, 0f);
        Mockito.verify(envState).steer(1.0f);
    }

    /**
     * Verifies resetting the steering angle when not steering.
     */
    @Test
    public void testCheckSteerReset() {
        Mockito.verify(envState, Mockito.never()).steer(Mockito.anyFloat());
        inputHandler.getActionListener().onAction("steer_left", true, 0f);
        Mockito.verify(envState, Mockito.never()).steer(0.0f);
        inputHandler.getActionListener().onAction("steer_right", true, 0f);
        Mockito.verify(envState, Mockito.never()).steer(0.0f);
        inputHandler.getActionListener().onAction("steer_right", false, 0f);
        Mockito.verify(envState).steer(1.0f);
    }
}
