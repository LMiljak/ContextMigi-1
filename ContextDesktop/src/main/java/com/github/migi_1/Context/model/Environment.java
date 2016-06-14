package com.github.migi_1.Context.model;

import java.util.ArrayList;
import java.util.Collection;

import com.github.migi_1.Context.audio.AudioController;
import com.github.migi_1.Context.main.HUDController;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IDisplayable;
import com.github.migi_1.Context.model.entity.IMovable;
import com.github.migi_1.Context.model.entity.IRotatable;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 * An Environment represents a world that can contain all kinds
 * of objects that can be added/removed.
 */
public class Environment extends AbstractAppState {

	private Node rootNode;
	private AssetManager assetManager;
	private Collection<IMovable> movables;
	private Collection<IRotatable> rotatables;
	private HUDController hudController;
	private AudioController audioController;
	private boolean paused;
	private Application app;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.app = app;
		this.paused = false;
		this.rootNode = ((Main) app).getRootNode();
		this.movables = new ArrayList<>();
		this.rotatables = new ArrayList<>();
		this.assetManager = ProjectAssetManager.getInstance().getAssetManager();
		hudController = new HUDController(app);
		audioController = new AudioController(app);
	}

	@Override
	public void update(float tpf) {
		super.update(tpf);
		    hudController.updateHUD();
		    moveMovables();
		    rotateRotatables();
	}

	/**
	 * Adds a Displayable object to the world.
	 * Note: Do not add Entities using this method.
	 *
	 * @param displayable
	 * 		The displayable to add.
	 */
	public void addDisplayable(IDisplayable displayable) {
		rootNode.attachChild(displayable.getModel());
	}

	/**
	 * Removes a Displayable object from the world.
	 *
	 * @param displayable
	 * 		The displayable to remove.
	 */
	public void removeDisplayable(IDisplayable displayable) {
		rootNode.detachChild(displayable.getModel());
	}

	/**
	 * Gets the root node of the application.
	 *
	 * @return
	 * 		The root node of the application.
	 */
	public Node getRootNode() {
		return rootNode;
	}

	/**
	 * Gets the asset manager.
	 *
	 * @return
	 * 		The asset manager.
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Adds an Entity to the world.
	 *
	 * @param entity
	 * 		The entity to add.
	 */
	public void addEntity(Entity entity) {
		addDisplayable(entity);
		if (!(movables.contains(entity))) {
		    movables.add(entity);
		}
	}

	public void addRotatable(IRotatable rotatable) {
		rotatables.add(rotatable);
	}

	/**
	 * Removes an Entity from the world.
	 *
	 * @param entity
	 * 		The entity to remove.
	 */
	public void removeEntity(Entity entity) {
		removeDisplayable(entity);
		movables.remove(entity);
	}

	/**
	 * Moves all Movable objects in the world using the MoveBehaviours.
	 */
	private void moveMovables() {
		for (IMovable movable : movables) {
		    movable.getMoveBehaviour().updateMoveVector();
			movable.move(movable.getMoveBehaviour().getMoveVector());
		}
	}

	private void rotateRotatables() {
		for (IRotatable rotatable : rotatables) {
			rotatable.getRotateBehaviour().updateRotateVector();
			rotatable.rotate();
		}
	}

	/**
	 * Check whether game is paused.
	 * @return paused or not paused
	 */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Pause or unpause the game.
     * @param paused pause or unpause
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Delete all visual elements.
     */
    @Override
    public void cleanup() {
        super.cleanup();
        this.assetManager.clearAssetEventListeners();
        this.assetManager.clearCache();
        this.rootNode.detachAllChildren();
        ((Main) this.app).getGuiNode().detachAllChildren();
        audioController.getBackgroundMusic().pause();
    }

    /**
     * Getter for the AudioController.
     * @return The AudioController.
     */
    public AudioController getAudioController() {
        return audioController;
    }

    /**
     * Setter for the AudioController.
     * @param audioController AudioController to set
     */
    public void setAudioController(AudioController audioController) {
        this.audioController = audioController;
    }

}
