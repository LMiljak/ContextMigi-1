package com.github.migi_1.Context.model;

public interface IKillable {
    
    int getHealth();
    void setHealth(int health);
    /**
     * 
     * @param damage
     * @return
     */
    void takeDamage(int damage);
    
}
