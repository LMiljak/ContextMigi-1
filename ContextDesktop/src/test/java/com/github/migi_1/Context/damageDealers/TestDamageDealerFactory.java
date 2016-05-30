package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the DamageDealerFactory class.
 * @author Marcel
 *
 */
public abstract class TestDamageDealerFactory {

    private AbstractObstacleFactory testDamageDealerFactory;
    private DamageDealer damageDealer;

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
        assertTrue(testDamageDealerFactory.produce().equals(damageDealer));
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
    public DamageDealer getDamageDealer() {
        return damageDealer;
    }

    /**
     * Set the damageDealer attribute.
     * @param damageDealer The damageDealer to set
     */
    public void setDamageDealer(DamageDealer damageDealer) {
        this.damageDealer = damageDealer;
    }



}
