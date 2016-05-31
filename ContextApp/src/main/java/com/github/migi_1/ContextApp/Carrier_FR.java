/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

/**
 *
 * @author Nils
 */
public class Carrier_FR extends CarrierPosition {
    
    private static final Carrier_FR INSTANCE = new Carrier_FR();
    
    private boolean hasSpray = false;
    private boolean hasBug = false;
    
    private Carrier_FL c_FL;
    private Carrier_BR c_BR;
    
    private Carrier_FR() {}
    
    public static Carrier_FR getInstance() {
        return INSTANCE;
    }
    
    @Override
    public boolean hasSpray() {
        return hasSpray;
    }
    
    @Override
    public boolean hasBug() {
        return hasBug;
    }
    
    @Override
    public void rotateClockwise() {
        hasSpray = false;
        c_BR.setSpray(true);
    }
    
    @Override
    public void rotateCounterClockwise() {
        hasSpray = false;
        c_FL.setSpray(true);
    }
    
}
