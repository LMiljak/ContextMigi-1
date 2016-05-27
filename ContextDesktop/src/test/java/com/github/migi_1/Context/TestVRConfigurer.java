package com.github.migi_1.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Tests VRConfigurer class.
 * @author Nils.
 *
 */
public class TestVRConfigurer {

    private Main main;

    @Before
    public void setUp() throws Exception {
        main = Mockito.mock(Main.class);
    }

    @Test
    public void configureVRTest() {
        VRConfigurer.configureVR(main);
        Mockito.verify(main, Mockito.times(8)).preconfigureVRApp(Mockito.any(), Mockito.anyBoolean());
        Mockito.verify(main).preconfigureFrustrumNearFar(Mockito.anyFloat(), Mockito.anyFloat());
    }

}
