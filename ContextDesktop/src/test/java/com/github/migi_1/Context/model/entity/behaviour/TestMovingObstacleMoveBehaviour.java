package com.github.migi_1.Context.model.entity.behaviour;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.migi_1.Context.obstacle.MovingObstacle;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Tests everything that has to with the MovingObstacleBehaviour.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BoundingBox.class})
public class TestMovingObstacleMoveBehaviour {

    private MovingObstacleMoveBehaviour moveBehaviour;
    private MovingObstacle movingObstacle;
    private BoundingBox boundingBox;
    private Spatial model;

    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @Before
    public void setUp() throws Exception {
        boundingBox = PowerMockito.mock(BoundingBox.class);
        model = Mockito.mock(Spatial.class);
        movingObstacle = Mockito.mock(MovingObstacle.class);
        Mockito.when(boundingBox.getCenter()).thenReturn(Vector3f.ZERO);
        Mockito.when(movingObstacle.getModel()).thenReturn(model);
        Mockito.when(model.getWorldBound()).thenReturn(boundingBox);
        Mockito.when(model.getLocalTranslation()).thenReturn(Vector3f.ZERO);
        Mockito.when(boundingBox.getXExtent()).thenReturn(5f);
        moveBehaviour = Mockito.spy(
                        new MovingObstacleMoveBehaviour(movingObstacle, boundingBox, boundingBox)
                        );
    }

    /**
     * Tests if the moveVector gets updated when updateMoveVector method gets called.
     */
    @Test
    public void testUpdateMoveVectors() {
        moveBehaviour.updateMoveVector();
        Mockito.verify(moveBehaviour, Mockito.times(2)).setMoveVector(Mockito.any());
    }

}
