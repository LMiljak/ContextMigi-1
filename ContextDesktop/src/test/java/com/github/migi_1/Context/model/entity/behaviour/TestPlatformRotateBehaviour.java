package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.utility.DistanceVectorAggregator;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 * Tests everything that has to do with the PlatformRotateBehaviour.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DistanceVectorAggregator.class, PlatformRotateBehaviour.class})
public class TestPlatformRotateBehaviour {

    private PlatformRotateBehaviour rotateBehaviour;
    private Collection<AccelerometerMoveBehaviour> collection;
    private AccelerometerMoveBehaviour moveBehaviour;
    private DistanceVectorAggregator aggregator;

    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @Before
    public void setUp() throws Exception {
        aggregator = Mockito.mock(DistanceVectorAggregator.class);
        PowerMockito.whenNew(DistanceVectorAggregator.class).withNoArguments().thenReturn(aggregator);
        Mockito.when(aggregator.aggregate(Mockito.any())).thenReturn(new Vector3f(1, 1, 1));
        moveBehaviour = Mockito.mock(AccelerometerMoveBehaviour.class);
        collection = new ArrayList<AccelerometerMoveBehaviour>();
        collection.add(moveBehaviour);
        rotateBehaviour = new PlatformRotateBehaviour(collection, Quaternion.ZERO);
    }
    
    /**
     * Tests the if the updateRotateVector doesn't update the rotateVector, when no time is applied.
     */
    @Test
    public void updateRotateVectorTest() {
        float disSimilarity = rotateBehaviour.getDisSimilarityAttribute();
        rotateBehaviour.updateRotateVector();
        assertEquals(disSimilarity, rotateBehaviour.getDisSimilarityAttribute(), 0f);
    }
    
    /**
     * Test if the updateVector gets updated when a time of 10 is applied.
     */
    @Test
    public void updateRotateVectorOutOfTimeTest() {
        float disSimilarity = rotateBehaviour.getDisSimilarityAttribute();
        rotateBehaviour.setTime(10);
        rotateBehaviour.updateRotateVector();
        assertNotEquals(disSimilarity, rotateBehaviour.getDisSimilarityAttribute(), 0f);
    }
}
