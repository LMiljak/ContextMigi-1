package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.Main;
import com.github.migi_1.Context.model.Environment;
import com.github.migi_1.Context.model.entity.IDisplayable;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Test suite for the Environment class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class})
public class TestEnvironment {

	private Environment environment;
	
	private Node root;
	private AssetManager assetManager;
	
	/**
	 * Initialises the environment field for testing.
	 */
	@Before
	public void setUp() {
		this.environment = new Environment();
		
		AppStateManager manager = mock(AppStateManager.class);
		ProjectAssetManager projectAssetManager = mock(ProjectAssetManager.class);
		PowerMockito.mockStatic(ProjectAssetManager.class);
		when(ProjectAssetManager.getInstance()).thenReturn(projectAssetManager);
		this.assetManager = Mockito.mock(AssetManager.class);
		when(projectAssetManager.getAssetManager()).thenReturn(assetManager);
		
		Main app = mock(Main.class);
		this.root = mock(Node.class);
		when(app.getRootNode()).thenReturn(root);
		
		
		this.environment.initialize(manager, app);
	}
	
	/**
	 * Tests the getRootNode method.
	 */
	@Test
	public void testGetRootNode() {
		assertEquals(root, environment.getRootNode());
	}
	
	/**
	 * Tests the addDisplayable method.
	 */
	@Test
	public void testAddDisplayable() {
		IDisplayable displayable = mock(IDisplayable.class);
		Spatial model = mock(Spatial.class);
		when(displayable.getModel()).thenReturn(model);
		
		environment.addDisplayable(displayable);
		
		verify(root, times(1)).attachChild(model);
	}
	
	/**
	 * Tests the removeDisplayabl method.
	 */
	@Test
	public void testRemoveDisplayable() {
		IDisplayable displayable = mock(IDisplayable.class);
		Spatial model = mock(Spatial.class);
		when(displayable.getModel()).thenReturn(model);
		
		environment.removeDisplayable(displayable);
		
		verify(root, times(1)).detachChild(model);
	}
	
	@Test
	public void testGetAssetManager() {
		assertEquals(assetManager, environment.getAssetManager());
	}
}
