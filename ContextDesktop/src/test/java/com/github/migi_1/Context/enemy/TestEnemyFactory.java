package com.github.migi_1.Context.enemy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.LevelPiece;
import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.network.Server;
import com.jme3.scene.Spatial;

/**
 * TestSuite for the EnemyFactory class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectAssetManager.class, Main.class, BoundingBox.class})
public class TestEnemyFactory {

    private EnemyFactory enemyFactory;
    private Platform platform;
    private MainEnvironment mainEnv;
    private ProjectAssetManager pAssetManager;
    private AssetManager assetManager;
    private Spatial model;
    private Carrier carrier;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;
    private LevelPiece levelPiece;
    private BoundingBox boundingBox;
    private Vector3f vector;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ProjectAssetManager.class);
        PowerMockito.mockStatic(Main.class);
        pAssetManager = PowerMockito.mock(ProjectAssetManager.class);
        assetManager = Mockito.mock(AssetManager.class);
        boundingBox = PowerMockito.mock(BoundingBox.class);
        carrier = Mockito.mock(Carrier.class);
        levelPiece = Mockito.mock(LevelPiece.class);
        main = Mockito.mock(Main.class);
        mainEnv = Mockito.mock(MainEnvironment.class);
        model = Mockito.mock(Spatial.class);
        server = Mockito.mock(Server.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        vector = Vector3f.ZERO;
        PowerMockito.whenNew(LevelPiece.class).withNoArguments().thenReturn(levelPiece);
        BDDMockito.given(ProjectAssetManager.getInstance()).willReturn(pAssetManager);
        BDDMockito.given(pAssetManager.getAssetManager()).willReturn(assetManager);
        BDDMockito.given(Main.getInstance()).willReturn(main);
        Mockito.when(assetManager.loadModel(Mockito.anyString())).thenReturn(model);
        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);
        platform = Mockito.mock(Platform.class);
        Mockito.when(levelPiece.getModel()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(vector);
        Mockito.when(model.getWorldBound()).thenReturn(boundingBox);
        Mockito.when(boundingBox.getCenter()).thenReturn(vector);
        Mockito.when(carrier.getModel()).thenReturn(model);

        ArrayList<Carrier> carriers = new ArrayList<Carrier>(4);
        carriers.add(carrier);
        carriers.add(carrier);
        carriers.add(carrier);
        carriers.add(carrier);
        enemyFactory = new EnemyFactory(carriers);
    }

    @Test
    public void createEnemy1test() {
        Enemy enemy1 = enemyFactory.createEnemy1(0);
        assertEquals(Vector3f.ZERO, enemy1.getModel().getLocalTranslation());
        Mockito.verify(model).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
    }

    @Test
    public void createEnemy2test() {
        Enemy enemy2 = enemyFactory.createEnemy2(0);
        assertEquals(Vector3f.ZERO, enemy2.getModel().getLocalTranslation());
        Mockito.verify(model).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
    }

    @Test
    public void createEnemy3test() {
        Enemy enemy3 = enemyFactory.createEnemy3(0);
        assertEquals(Vector3f.ZERO, enemy3.getModel().getLocalTranslation());
        Mockito.verify(model, Mockito.never()).rotate(Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
    }

}
