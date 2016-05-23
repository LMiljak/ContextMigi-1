package com.github.migi_1.Context.damageDealers;


/**
 * This class defines what functions all factories that create DamageDealer objects must implement.
 * @author Marcel
 *
 */
public abstract class DamageDealerFactory {

    /**
     * Generate a DamageDealer object.
     * @param assetManager AssetManager that supplies the model.
     * @return generated DamageDealer object
     */
    public abstract DamageDealer produce();

}
