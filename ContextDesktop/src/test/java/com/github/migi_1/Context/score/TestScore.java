package com.github.migi_1.Context.score;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests everything that has to with the Score class.
 * @author Nils
 *
 */
public class TestScore {

    private Iterator<String> iterator;
    private Score score;
    private String scoreName = "scoreName";
    private int scoreAmount = 0;
    
    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        iterator = Mockito.mock(Iterator.class);
        score = new Score(scoreName, scoreAmount);

        Mockito.when(iterator.next()).thenReturn("0");
    }
    
    /**
     * Tests the getter and setter of the name attribute.
     */
    @Test
    public void getAndSetNameTest() {
        assertEquals(scoreName, score.getName());
        score.setName("newname");
        assertEquals("newname", score.getName());
    }
    
    /**
     * Tests the getter and setter of score.
     */
    @Test
    public void getAndSetScoreTest() {
        assertEquals(0, score.getScore());
        score.setScore(42);
        assertEquals(42, score.getScore());
    }
    
    /**
     * Tests the read method.
     */
    @Test
    public void readTest() {
        Score readScore = Score.read(iterator);
        assertEquals(0, readScore.getScore());
        assertEquals("0", readScore.getName());
    }

}
