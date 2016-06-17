package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestEnableSprayToVRMessage {

    private EnableSprayToVRMessage enableSprayMessage;

    @Before
    public void setUp() throws Exception {
        enableSprayMessage = new EnableSprayToVRMessage(PlatformPosition.FRONTLEFT);
    }

    @Test
    public void getPositionTest() {
        assertEquals(PlatformPosition.FRONTLEFT, enableSprayMessage.getPosition());
    }

    @Test
    public void constructorTest() {
        assertEquals(EnableSprayToVRMessage.class, new EnableSprayToVRMessage().getClass());
        assertEquals(EnableSprayToVRMessage.class, new EnableSprayToVRMessage(PlatformPosition.FRONTRIGHT).getClass());
    }

}
