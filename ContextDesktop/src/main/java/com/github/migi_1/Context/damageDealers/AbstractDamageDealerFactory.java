package com.github.migi_1.Context.damageDealers;


/**
 * This class defines what functions all factories that create DamageDealer objects must implement.
 * @author Marcel
 *
 */
public abstract class AbstractDamageDealerFactory {

    /**
     * Generate a DamageDealer object.
     * @return generated DamageDealer object
     */
    public abstract DamageDealer produce();

}
