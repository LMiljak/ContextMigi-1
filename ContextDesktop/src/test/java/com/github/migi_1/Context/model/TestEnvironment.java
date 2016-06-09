package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.audio.AudioController;
import com.github.migi_1.Context.main.HUDController;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IDisplayable;
import com.github.migi_1.Context.model.entity.behaviour.ConstantSpeedMoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Test suite for the Environment class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.github.migi_1.Context.*")
public class TestEnvironment {

	protected Environment environment;

	protected Node root;
	protected AssetManager assetManager;
	private HUDController hudController;
	private AudioController audioController;

	/**
	 * Initialises the environment field for testing.
	 * @throws Exception exception that is thrown.
	 */
	@Before
	public void setUp() throws Exception {
		this.environment = new Environment();

		hudController = Mockito.mock(HUDController.class);
		audioController = Mockito.mock(AudioController.class);
		AppStateManager manager = mock(AppStateManager.class);
		ProjectAssetManager projectAssetManager = mock(ProjectAssetManager.class);
		PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.whenNew(HUDController.class).withAnyArguments().thenReturn(hudController);
        PowerMockito.whenNew(AudioController.class).withAnyArguments().thenReturn(audioController);
		when(ProjectAssetManager.getInstance()).thenReturn(projectAssetManager);
		this.assetManager = Mockito.mock(AssetManager.class);
		when(projectAssetManager.getAssetManager()).thenReturn(assetManager);

		Main app = mock(Main.class);
		this.root = mock(Node.class);
		when(app.getRootNode()).thenReturn(root);
		AudioNode backgroundMusic = Mockito.mock(AudioNode.class);
        when(audioController.getBackgroundMusic()).thenReturn(backgroundMusic);
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

	/**
	 * Tests the assetManager method.
	 */
	@Test
	public void testGetAssetManager() {
		assertEquals(assetManager, environment.getAssetManager());
	}

	/**
	 * Tests the addEntity method.
	 */
	@Test
	public void testAddEntity() {
		Entity entity = mock(Entity.class);
		Spatial model = mock(Spatial.class);
		when(entity.getModel()).thenReturn(model);

		environment.addEntity(entity);

		verify(root, times(1)).attachChild(model);
	}

	/**
	 * Tests the removeEntity method.
	 */
	@Test
	public void testRemoveEntity() {
		Entity entity = mock(Entity.class);
		Spatial model = mock(Spatial.class);
		when(entity.getModel()).thenReturn(model);

		environment.removeEntity(entity);

		verify(root, times(1)).detachChild(model);
	}

	/**
	 * Tests the moveMovables method.
	 */
	@Test
	public void testMoveMovables() {
		Entity entity = mock(Entity.class);

		Vector3f moveVector = new Vector3f(1, 2, 3);
		when(entity.getMoveBehaviour()).thenReturn(new ConstantSpeedMoveBehaviour(moveVector));
		System.out.println(entity.getMoveBehaviour());

		environment.addEntity(entity);
		environment.update(0);

		verify(entity, times(1)).move(moveVector);
	}

	/**
	 * Tests if the getter and setter for paused work correctly.
	 */
	@Test
	public void testIsAndSetPaused() {
	    assertFalse(environment.isPaused());
	    environment.setPaused(true);
	    assertTrue(environment.isPaused());
	}

	/**
	 * Tests if the update method behaves well when the game is paused.
	 */
	@Test
	public void testUpdateWhenPaused() {
	    //Verify that when not paused,
	    //The HUDcontroller is updated.
	    environment.update(0);
	    Mockito.verify(hudController).updateHUD();
	    environment.setPaused(true);
	    //Verify that when paused,
        //The HUDcontroller is not updated.
	    environment.update(0);
	    Mockito.verifyNoMoreInteractions(hudController);
	}
}
