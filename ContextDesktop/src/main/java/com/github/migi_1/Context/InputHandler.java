package com.github.migi_1.Context;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;

import jmevr.app.VRApplication;

/**
 * Handles input from the Main class.
 * @author Nils
 */
public final class InputHandler {

    private String[] actions = {"exit", "cam_switch", "forwards", "backwards", "left", "right",
                                "up", "down", "steer_left", "steer_right"};
    private int[] keyInputs = {KeyInput.KEY_ESCAPE, KeyInput.KEY_C, KeyInput.KEY_W, KeyInput.KEY_S,
                               KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_SPACE, KeyInput.KEY_LSHIFT,
                               KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT};
    private boolean forwards, back, left, right, up, down = false;
    private Main main;
    private ActionListener actionListener;
    private InputManager inputManager;

    /**
     * Constructor for the InputHandler.
     * @param main the Main menu from which the input needs to be handled.
     */
    public InputHandler(Main main) {
        this.main = main;
    }

    /**
     * Initialises the game inputs (keys).
     *
     * Key bindings:
     * Escape key: Exit the game
     * c: switches camera
     * When in flycam:
     * w: move forwards
     * s: move backwars
     * a: move left
     * d: move right
     * Lshift: move down
     * space: move up
     * ---MORE CAN BE ADDED IF NEEDED---
     * @param main the Main menu the inputs have to be configured for.
     */
    public void initInputs(Main main) {
        inputManager = main.getInputManager();
        addMappings();
        actionListener = new ActionListener() {
            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
                if (name.equals("exit") && keyPressed) {
                    main.destroy();
                } else if (name.equals("cam_switch") && keyPressed) {
                    main.getEnv().swapCamera();
                }

                //Controls that only work with flycam.
                if (main.getEnv().getFlyCamActive()) {
                    switch (name) {
                        case "forwards":
                            forwards = keyPressed;
                            break;
                        case "backwards":
                            back = keyPressed;
                            break;
                        case "left":
                            left = keyPressed;
                            break;
                        case "right":
                            right = keyPressed;
                            break;
                        case "up":
                            up = keyPressed;
                            break;
                        case "down":
                            down = keyPressed;
                            break;
                        default: //Do nothing when an unknown button is pressed.
                    }
                }
                checkSteering(name, keyPressed);
            }
        };
        addListeners();
    }

    /**
     * Adds all the mappings for the different function names to the different keys.
     * @param inputManager the inputmanager for which these keymappings are set.
     */
    private void addMappings() {
        for (int i = 0; i < actions.length; i++) {
            inputManager.addMapping(actions[i], new KeyTrigger(keyInputs[i]));
        }
    }

    /**
     * Adds key-input event listeners to the inputmanager.
     * @param inputManager The input manager to which these listeners are added.
     * @param acl The actionlistener that listens to the key-input events.
     */
    private void addListeners() {
        for (String action : actions) {
            inputManager.addListener(actionListener, action);
        }
    }

    /**
     * Key binding for steering.
     * left: move left
     * right: move right
     * @param name Name of key action.
     * @param keyPressed True when pressed, false when released.
     */
    private void checkSteering(String name, boolean keyPressed) {
        if (keyPressed) {
            if (name.equals("steer_left")) {
                main.getEnv().steer(-1.f);
            }
            else if (name.equals("steer_right")) {
               main.getEnv().steer(1.f);
           }
       }
       if (!keyPressed && (name.equals("steer_left") || name.equals("steer_right"))) {
           main.getEnv().steer(0.f);
       }
    }

    /**
     * Makes the flyCamera move around in the environment.
     * @param tpf the update factor, determines the speed of rotation/movement.
     */
    public void moveCamera(float tpf) {
        if (forwards) {
            main.getEnv().moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf * 8f));
        }
        if (back) {
            main.getEnv().moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf * 8f));
        }
        if (left) {
            main.getEnv().rotateCam(new Vector3f(0f, 0.75f * tpf, 0f));
        }
        if (right) {
            main.getEnv().rotateCam(new Vector3f(0, -0.75f * tpf, 0));
        }
        if (up) {
            main.getEnv().moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(tpf * 8f));
        }
        if (down) {
            main.getEnv().moveCam(VRApplication.getFinalObserverRotation().getRotationColumn(1).mult(-tpf * 8f));
        }
    }

    public boolean isForwards() {
        return forwards;
    }

    public boolean isBack() {
        return back;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }
}
