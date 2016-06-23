package com.github.migi_1.Context.main;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

/**
 * Handles input from the Main class.
 * @author Nils
 */
public final class InputHandler {

	private static final InputHandler INSTANCE = new InputHandler();

    private HashMap<Integer, Collection<KeyInputListener>> observers = new HashMap<>();

    private ActionListener actionListener;
    private InputManager inputManager;
    private List<Integer> keys = Arrays.asList(
        KeyInput.KEY_W, KeyInput.KEY_A, KeyInput.KEY_S, KeyInput.KEY_D,
        KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_UP, KeyInput.KEY_DOWN
        );

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
    	initInputs(main);
    }

    /** Private empty constructor to prevent initialisation. */
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
                if(keyPressed && !keys.contains(Integer.parseInt(name))) {
                    main.getEnv().setPaused(!main.getEnv().isPaused());
                }
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
     * Returns the actionListener of the InputHandler.
     * @return the actionListener.
     */
    public ActionListener getActionListener() {
        return actionListener;
    }

}
