package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestEnableSprayToAppMessage {

    private EnableSprayToAppMessage enableSprayMessage;

    @Before
    public void setUp() throws Exception {
        enableSprayMessage = new EnableSprayToAppMessage(PlatformPosition.FRONTRIGHT);
    }

    @Test
    public void getPositionTest() {
        assertEquals(PlatformPosition.FRONTRIGHT, enableSprayMessage.getPosition());
    }

    @Test
    public void constructorTest() {
        assertEquals(EnableSprayToAppMessage.class, new EnableSprayToAppMessage().getClass());
        assertEquals(EnableSprayToAppMessage.class, new EnableSprayToAppMessage(PlatformPosition.FRONTRIGHT).getClass());
    }

}
