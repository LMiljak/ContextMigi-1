package com.github.migi_1.Context.main;

import jmevr.app.VRApplication;

import com.github.migi_1.Context.model.MainEnvironment;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;

/**
 * Handles input from the Main class.
 * @author Nils
 */
public final class InputHandler {

    private String[] actions = {"exit", "cam_switch", "forwards", "backwards", "left", "right",
                                "up", "down", "steer_left", "steer_right", "start",
                                "pause", "menu", "restart", "mute"};
    private int[] keyInputs = {KeyInput.KEY_ESCAPE, KeyInput.KEY_C, KeyInput.KEY_W, KeyInput.KEY_S,
            KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_SPACE, KeyInput.KEY_LSHIFT,
            KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_SPACE, KeyInput.KEY_P,
            KeyInput.KEY_E, KeyInput.KEY_R, KeyInput.KEY_M};

    private boolean forwards, back, left, right, up, down = false;
    private Main main;
    private ActionListener actionListener;
    private InputManager inputManager;
    private boolean inMenu = true;
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
            	MainEnvironment environment = main.getEnv();
            	
                if (!inMenu) {
                    if (name.equals("cam_switch") && keyPressed) {
                        environment.swapCamera();
                    } else if (name.equals("pause") && keyPressed) {
                        if (!environment.isPaused() || environment.isGameOver()) {
                        	environment.setPaused(true);
                        	environment.getAudioController().getBackgroundMusic().pause();
                        }
                        else {
                        	environment.setPaused(false);
                            if (environment.getAudioController().isPlaying()) {
                            	environment.getAudioController().getBackgroundMusic().play();
                            }
                        }
                    } else if (name.equals("menu") && keyPressed) {
                        if (!main.getInLobby()) {
                            inMenu = true;
                            main.toLobby();
                        }
                    } else if (name.equals("restart") && keyPressed && main.getStateManager().hasState(environment)) {
                    	environment.cleanup();
                        inMenu = true;
                        main.toLobby();
                    } else if (name.equals("mute") && keyPressed) {
                    	environment.getAudioController().mute();
                    }
                } else if (name.equals("start") && keyPressed) {
                    if (main.getInLobby()) {
                        inMenu = false;
                        main.toMainEnvironment();
                    }
                }
                if (name.equals("exit") && keyPressed) {
                    main.destroy();
                }

                //Controls that only work with flycam.
                if (environment.getFlyCamActive()) {
                    checkMovements(name, keyPressed);
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

    /**
     * Checks if the camera needs to move in a certain way.
     * @param name the name of the button that is pressed,
     *          as stated in the listeners.
     * @param keyPressed whether or not the key is pressed (true)
     *          or released (false).
     */
    private void checkMovements(String name, boolean keyPressed) {
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

    /**
     * Returns if the forwards key is pressed.
     * @return true when pressed.
     */
    public boolean isForwards() {
        return forwards;
    }

    /**
     * Returns if the backwards key is pressed.
     * @return true when pressed.
     */
    public boolean isBack() {
        return back;
    }

    /**
     * Returns if the left key is pressed.
     * @return true when pressed.
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Returns if the right key is pressed.
     * @return true when pressed.
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Returns if the up key is pressed.
     * @return true when pressed.
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Returns if the down key is pressed.
     * @return true when pressed.
     */
    public boolean isDown() {
        return down;
    }

    /**
     * Returns the actionListener of the InputHandler.
     * @return the actionListener.
     */
    public ActionListener getActionListener() {
        return actionListener;
    }

    /**
     * @return the inMenu
     */
    public boolean isInMenu() {
        return inMenu;
    }

    /**
     * @param inMenu the inMenu to set
     */
    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }




}
