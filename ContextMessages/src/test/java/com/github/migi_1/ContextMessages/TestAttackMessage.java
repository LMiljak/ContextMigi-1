package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestAttackMessage {

    private AttackMessage attackMessage;

    @Before
    public void setUp() throws Exception {
        attackMessage = new AttackMessage(PlatformPosition.BACKLEFT, Direction.NORTH);
    }

    @Test
    public void getPositionTest() {
        assertEquals(PlatformPosition.BACKLEFT, attackMessage.getPosition());
    }

    @Test
    public void getDirectionTest() {
        assertEquals(Direction.NORTH, attackMessage.getDirection());
    }

    @Test
    public void emptyConstructorTest() {
        assertEquals(AttackMessage.class, new AttackMessage().getClass());
        assertEquals(AttackMessage.class, new AttackMessage(PlatformPosition.BACKLEFT, Direction.NORTH).getClass());
    }

}
