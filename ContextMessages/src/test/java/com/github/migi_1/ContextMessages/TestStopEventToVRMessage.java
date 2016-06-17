package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestStopEventToVRMessage {

    private StopEventToVRMessage stopEventMessage;

    @Before
    public void setUp() throws Exception {
        stopEventMessage = new StopEventToVRMessage();
    }

    /**
     * Tests the constructors.
     */
    @Test
    public void constructorTest() {
        assertEquals(StopEventToVRMessage.class, stopEventMessage.getClass());
    }
}
