package com.github.migi_1.Context.model;

import java.util.ArrayList;
import java.util.Collection;

import com.github.migi_1.Context.Main;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IDisplayable;
import com.github.migi_1.Context.model.entity.IMovable;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

/**
 * An Environment represents a world that can contain all kinds
 * of objects that can be added/removed.
 */
public class Environment extends AbstractAppState {

	private Node rootNode;
	private Collection<IMovable> movables;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		
		this.rootNode = ((Main) app).getRootNode();
		this.movables = new ArrayList<>();
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		
		moveMovables();
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
	 * Adds an Entity to the world.
	 * 
	 * @param entity
	 * 		The entity to add.
	 */
	public void addEntity(Entity entity) {
		addDisplayable(entity);
		movables.add(entity);
	}
	
	/**
	 * Moves all Movable objects in the world using the MoveBehaviours.
	 */
	private void moveMovables() {
		for (IMovable movable : movables) {
			movable.move(movable.getMoveBehaviour().getMoveVector());
		}
	}
}
