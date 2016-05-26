package com.git.migi_1.Context.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.Main;
import com.github.migi_1.Context.model.Environment;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;

/**
 * Test suite for the Environment class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class})
public class TestEnvironment {

	private Environment environment;
	
	/**
	 * Initialises the environment field for testing.
	 */
	@Before
	public void setUp() {
		this.environment = new Environment();
		
		AppStateManager manager = Mockito.mock(AppStateManager.class);
		Application app = Mockito.mock(Main.class);
		ProjectAssetManager projectAssetManager = Mockito.mock(ProjectAssetManager.class);
		
		PowerMockito.mockStatic(ProjectAssetManager.class);
		Mockito.when(ProjectAssetManager.getInstance()).thenReturn(projectAssetManager);
		
		Mockito.when(projectAssetManager.getAssetManager()).thenReturn(Mockito.mock(AssetManager.class));
		
		this.environment.initialize(manager, app);
	}
	
	@Test
	public void test() {
		
	}
	
}
