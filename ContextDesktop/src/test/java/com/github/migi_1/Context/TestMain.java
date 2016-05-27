package com.github.migi_1.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jme3.system.AppSettings;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AppSettings.class})
public class TestMain {

    private Main main;
    private AppSettings settings;
    //private InputManager inputManager;

    @Before
    public void setUp() throws Exception {
        //inputManager = PowerMockito.mock(InputManager.class);
        main = new Main();
//        main.setUpMain();
//        main.simpleInitApp();
    }

    @Test
    public void simpleInitTest() {
//        Mockito.verify(settings).setTitle(Mockito.anyString());
//        Mockito.verify(settings).setResolution(Mockito.anyInt(), Mockito.anyInt());
    }

}
