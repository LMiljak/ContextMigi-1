package com.github.migi_1.Context.main;

import jmevr.app.VRApplication;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import com.github.migi_1.Context.model.MainEnvironment;
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

	private static final InputHandler INSTANCE = new InputHandler();

    private HashMap<Integer, Collection<KeyInputListener>> observers = new HashMap<>();
    
    private boolean forwards, back, left, right, up, down = false;
    private Main main;
    private ActionListener actionListener;
    private InputManager inputManager;
    
    /**
     * @return
     * 		The instance of this class.
     */
    public static InputHandler getInstance() {
    	return INSTANCE;
    }
    
    /**
     * Initialises the InputHandler.
     * 
     * @param main
     * 		The main application.
     */
    public void initialise(Main main) {
    	this.main = main;
    	
    	initInputs(main);
    }
    
    /** Private empty constructor to prevent initialisation */
    private InputHandler() { }

    /**
     * Registers a KeyInputListener. It's onKeyPressed method will be
     * called when the given key is pressed.
     * 
     * @param observer
     * 		The observer to register.
     * @param key
     * 		The key to listen to (use public static final attributes of KeyInput).
     */
    public void register(KeyInputListener observer, int key) {
    	if (observers.get(key) == null) {
    		observers.put(key, new LinkedList<>());
    	}
    	
    	observers.get(key).add(observer);
    	inputManager.addMapping(key + "", new KeyTrigger(key));
    	inputManager.addListener(actionListener, key + "");
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
    private void initInputs(Main main) {
        inputManager = main.getInputManager();
        actionListener = new ActionListener() {

            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
        		Collection<KeyInputListener> keyObservers = observers.get(Integer.parseInt(name));
            	if (keyObservers != null) {
            		for (KeyInputListener observer : keyObservers) {
            			if (keyPressed) {
            				observer.onKeyPressed(Integer.parseInt(name));
            			} else {
            				observer.onKeyReleased(Integer.parseInt(name));
            			}
            		}
            	}
            }
        };
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

}
