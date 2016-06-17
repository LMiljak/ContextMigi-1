package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestHitMissMessage {

    private HitMissMessage hitMissMessage;

    @Before
    public void setUp() throws Exception {
        hitMissMessage = new HitMissMessage(true, PlatformPosition.BACKLEFT);
    }

    @Test
    public void getHitTest() {
        assertTrue(hitMissMessage.getHit());
    }

    @Test
    public void getPositionTest() {
        assertEquals(PlatformPosition.BACKLEFT, hitMissMessage.getPos());
    }

    @Test
    public void constructorTest() {
        assertEquals(HitMissMessage.class, new HitMissMessage().getClass());
        assertEquals(HitMissMessage.class, new HitMissMessage(true, PlatformPosition.FRONTRIGHT).getClass());
    }

}
