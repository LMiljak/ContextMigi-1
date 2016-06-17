package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestStopAllEventsMessage {

    private StopAllEventsMessage stopEventMessage;

    @Before
    public void setUp() throws Exception {
        stopEventMessage = new StopAllEventsMessage();
    }

    /**
     * Tests the constructors.
     */
    @Test
    public void constructorTest() {
        assertEquals(StopAllEventsMessage.class, stopEventMessage.getClass());
    }
}
