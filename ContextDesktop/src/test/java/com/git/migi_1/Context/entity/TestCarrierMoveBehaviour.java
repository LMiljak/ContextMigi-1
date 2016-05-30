package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.CarrierMoveBehaviour;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, AssetManager.class})
public class TestCarrierMoveBehaviour extends TestEntityMoveBehaviour {

    private CarrierMoveBehaviour testMoveBehaviour;
    private Carrier carrier;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private Commander commander;


    @Override
    @Before
    public void setUp() {
        commander = Mockito.mock(Commander.class);
        carrier = Mockito.mock(Carrier.class);
        model =  Mockito.mock(Spatial.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        setMoveVector(new Vector3f(1, 2, 3));
        testMoveBehaviour = new CarrierMoveBehaviour(getMoveVector(), carrier);
        testMoveBehaviour.setCommander(commander);
        setMoveBehaviour(testMoveBehaviour);
        testMoveBehaviour.setRelativeLocation(new Vector3f(0, 0, 0));
        PowerMockito.mockStatic(ProjectAssetManager.class);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(commander.getModel()).thenReturn(model);
        Mockito.when(carrier.getModel()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(new Vector3f(0, 0, 0));

    }

    @Override
    @Test
    public void collidedTest() {
        assertEquals(testMoveBehaviour.getImmobalised(), 0);
        testMoveBehaviour.collided();
        assertEquals(testMoveBehaviour.getImmobalised(), 120);


    }



}
