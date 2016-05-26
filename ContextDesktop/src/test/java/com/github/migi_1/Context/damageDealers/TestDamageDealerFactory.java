package com.github.migi_1.Context.damageDealers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public abstract class TestDamageDealerFactory {

    private DamageDealerFactory testDamageDealerFactory;

    private DamageDealer damageDealer;


    @Before
    public abstract void setUp();



    @Test
    public void testProduce() {
        assertTrue(testDamageDealerFactory.produce().equals(damageDealer));
    }



    public DamageDealerFactory getTestDamageDealerFactory() {
        return testDamageDealerFactory;
    }



    public void setTestDamageDealerFactory(
            DamageDealerFactory testDamageDealerFactory) {
        this.testDamageDealerFactory = testDamageDealerFactory;
    }



    public DamageDealer getDamageDealer() {
        return damageDealer;
    }



    public void setDamageDealer(DamageDealer damageDealer) {
        this.damageDealer = damageDealer;
    }



}
