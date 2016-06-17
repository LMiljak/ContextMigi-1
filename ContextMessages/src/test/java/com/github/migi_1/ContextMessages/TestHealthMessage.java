package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestHealthMessage {

    private HealthMessage healthMessage;

    @Before
    public void setUp() throws Exception {
        healthMessage = new HealthMessage(0, PlatformPosition.FRONTRIGHT);
    }

    @Test
    public void getPositionTest() {
        assertEquals(PlatformPosition.FRONTRIGHT, healthMessage.getPos());
    }

    @Test
    public void getHealthTest() {
        assertEquals(0, healthMessage.getHealth());
    }

    @Test
    public void constructorTest() {
        assertEquals(HealthMessage.class, new HealthMessage().getClass());
        assertEquals(HealthMessage.class, new HealthMessage(0, PlatformPosition.FRONTRIGHT).getClass());
    }

}
