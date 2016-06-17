package com.github.migi_1.Context.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.enemy.Enemy;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.ContextMessages.Direction;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class for testing everything that has to do with an EnemySpot.
 * @author Nils
 *
 */
public class TestEnemySpot {

    private EnemySpot enemySpot;
    private Carrier carrier;
    private Commander commander;
    private Spatial model;

    /**
     * Sets up everything needed for the tests. Happens before every test.   
     * @exception Exception Any exception that is thrown.
     */
    @Before
    public void setUp() throws Exception {
        carrier = Mockito.mock(Carrier.class);
        commander = Mockito.mock(Commander.class);
        model = Mockito.mock(Spatial.class);
        Mockito.when(commander.getModel()).thenReturn(model);
        Mockito.when(model.getLocalTranslation()).thenReturn(Vector3f.ZERO);
        Mockito.when(carrier.getRelativeLocation()).thenReturn(Vector3f.ZERO);
        Mockito.when(carrier.getPosition()).thenReturn(PlatformPosition.BACKLEFT);
        enemySpot = new EnemySpot(Vector3f.ZERO, carrier, commander, Direction.NORTH);
    }

    /**
     * Test the getter and setter of the occupied boolean.
     */
    @Test
    public void getAndSetOccupiedTest() {
        assertFalse(enemySpot.isOccupied());
        enemySpot.setOccupied(true);
        assertTrue(enemySpot.isOccupied());
    }
    
    /**
     * Tests the getter and setter of the carrier attribute.
     */
    @Test
    public void getAndSetCarrierTest() {
        Carrier oldCarrier = enemySpot.getCarrier();
        enemySpot.setCarrier(Mockito.mock(Carrier.class));
        assertNotEquals(oldCarrier, enemySpot.getCarrier());
    }
    
    /**
     * Tests the getLocation method.
     */
    @Test
    public void getLocationTest() {
        assertEquals(Vector3f.ZERO, enemySpot.getLocation());
    } 
    
    /**
     * Tests the getOffset method.
     */
    @Test
    public void getOffsetTest() {
        assertEquals(Vector3f.ZERO, enemySpot.getOffset());
    }
    
    /**
     * Tests the getCarrierId method.
     */
    @Test
    public void getCarrierIDTest() {
        assertEquals(PlatformPosition.BACKLEFT, enemySpot.getCarrierId());
    }
    
    /**
     * Tests the getDirection method.
     */
    @Test
    public void getDirectionTest() {
        assertEquals(Direction.NORTH, enemySpot.getDirection());
    }
    
    /**
     * Tests the getter and setter methods for the enemy.
     */
    @Test
    public void getAndSetEnemyTest() {
        Enemy oldEnemy =  enemySpot.getEnemy();
        enemySpot.setEnemy(Mockito.mock(Enemy.class));
        assertNotEquals(oldEnemy, enemySpot.getEnemy());
    }

}
