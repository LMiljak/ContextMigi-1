package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.obstacle.AbstractObstacleFactory;
import com.github.migi_1.Context.obstacle.Obstacle;

/**
 * Test class for the DamageDealerFactory class.
 * @author Marcel
 *
 */
public abstract class TestDamageDealerFactory {

    private AbstractObstacleFactory testDamageDealerFactory;
    private Obstacle obstacle;

    /**
     * Initialise attributes.
     */
    @Before
    public abstract void setUp();


    /**
     * Test the produce method.
     */
    @Test
    public void testProduce() {
        assertTrue(testDamageDealerFactory.produce().equals(obstacle));
    }

    /**
     * Get the damageDealerFactory attribute.
     * @return damageDealerFactory
     */
    public AbstractObstacleFactory getTestDamageDealerFactory() {
        return testDamageDealerFactory;
    }

    /**
     * Set the damageDealerFactory attribute.
     * @param testDamageDealerFactory The damageDealerFactory to set
     */
    public void setTestDamageDealerFactory(
            AbstractObstacleFactory testDamageDealerFactory) {
        this.testDamageDealerFactory = testDamageDealerFactory;
    }


    /**
     * Get the damageDealer attribute.
     * @return damageDealer attribute
     */
    public Obstacle getDamageDealer() {
        return obstacle;
    }

    /**
     * Set the damageDealer attribute.
     * @param damageDealer The damageDealer to set
     */
    public void setDamageDealer(Obstacle damageDealer) {
        this.obstacle = damageDealer;
    }



}
