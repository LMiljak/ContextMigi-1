package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestPlatformPosition {

    private PlatformPosition platformPosition;

    @Before
    public void setUp() throws Exception {
        platformPosition = PlatformPosition.BACKLEFT;
    }

    @Test
    public void getXtest() {
        assertEquals(-1, platformPosition.getxFactor());
    }

    @Test
    public void getZtest() {
        assertEquals(1, platformPosition.getzFactor());
    }

    @Test
    public void getPositiontest() {
        assertEquals("Location: back/left", platformPosition.getPosition());
    }

}
