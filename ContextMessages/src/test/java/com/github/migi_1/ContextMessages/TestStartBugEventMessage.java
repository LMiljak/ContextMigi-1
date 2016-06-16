package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestStartBugEventMessage {

    private StartBugEventMessage startBugEventMessage;

    @Before
    public void setUp() throws Exception {
        startBugEventMessage = new StartBugEventMessage(PlatformPosition.FRONTLEFT, PlatformPosition.BACKRIGHT);
    }

    @Test
    public void getBugPositionTest() {
        assertEquals(PlatformPosition.FRONTLEFT, startBugEventMessage.getBugPosition());
    }

    @Test
    public void getSprayPosition() {
        assertEquals(PlatformPosition.BACKRIGHT, startBugEventMessage.getSprayPosition());
    }

    /**
     * Tests the constructors.
     */
    @Test
    public void constructorTest() {
        assertEquals(StartBugEventMessage.class, new StartBugEventMessage().getClass());
        assertEquals(StartBugEventMessage.class, new StartBugEventMessage(
                PlatformPosition.FRONTRIGHT, PlatformPosition.FRONTRIGHT).getClass());
    }

}
