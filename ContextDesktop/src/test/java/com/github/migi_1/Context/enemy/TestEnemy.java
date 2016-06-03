package com.github.migi_1.Context.enemy;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.git.migi_1.Context.entity.TestEntity;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestEnemy extends TestEntity {

    private Commander testCommander;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private EnemyMoveBehaviour moveBehaviour;
    private Spatial model;
    
    /**
     * Initialises all mock objects, static class responses and initialise the tested object.
     */
    @Override
    @Before
    public void setUp() {

        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        model =  Mockito.mock(Spatial.class);
        moveBehaviour = Mockito.mock(EnemyMoveBehaviour.class);
        Carrier[] carriers = new Carrier[4];
        for (int i = 0; i < carriers.length; i++) {
            carriers[i] = Mockito.mock(Carrier.class);
            Mockito.when(carriers[i].getId()).thenReturn(i);
            Mockito.when(carriers[i].getModel()).thenReturn(model);
        }
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        
        Enemy testEnemy = new Enemy(new Vector3f(5, 0, 0), carriers);

        setMoveBehaviour(moveBehaviour);
        setEntity(testEnemy);

    }
    
//    @Override
//    @Before
//    public void testGetMoveBehaviour() {
//        
//    }
}
