/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp;

/**
 *
 * @author Nils
 */
public class Carrier_FL extends CarrierPosition {
    
    private static final Carrier_FL INSTANCE = new Carrier_FL();
    
    private boolean hasSpray = false;
    private boolean hasBug = false;
    
    private Carrier_FR c_FR;
    private Carrier_BL c_BL;
    
    private Carrier_FL() {}
    
    public static Carrier_FL getInstance() {
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
    public void setSpray(boolean newSpray) {
        this.hasSpray = newSpray;
    }
    
    @Override
    public void rotateClockwise() {
        hasSpray = false;
        c_FR.setSpray(true);
    }
    
    @Override
    public void rotateCounterClockwise() {
        hasSpray = false;
        c_BL.setSpray(true);
    }
    
}
