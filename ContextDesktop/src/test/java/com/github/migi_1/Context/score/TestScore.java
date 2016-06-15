package com.github.migi_1.Context.score;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestScore {

    private Iterator<String> iterator;
    private Score score;
    private String scoreName = "scoreName";
    private int scoreAmount = 0;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        iterator = Mockito.mock(Iterator.class);
        score = new Score(scoreName, scoreAmount);

        Mockito.when(iterator.next()).thenReturn("0");
    }

    @Test
    public void getAndSetNameTest() {
        assertEquals(scoreName, score.getName());
        score.setName("newname");
        assertEquals("newname", score.getName());
    }

    @Test
    public void getAndSetScoreTest() {
        assertEquals(0, score.getScore());
        score.setScore(42);
        assertEquals(42, score.getScore());
    }

    @Test
    public void readTest() {
        Score readScore = Score.read(iterator);
        assertEquals(0, readScore.getScore());
        assertEquals("0", readScore.getName());
    }

}
