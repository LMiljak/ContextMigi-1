package com.github.migi_1.Context.enemy;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IKillable;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class responsible for the enemy, has an EnemyMoveBehaviour.
 * @author Damian
 *
 */
public class Enemy extends Entity implements IKillable {
    
    private static final String PATH_NAME = "Models/ninja.j3o";
    private EnemyMoveBehaviour moveBehaviour;
    private int health;
    private float currentTime = 0;
    private float attackThreshHold = 3;
    
    /**
     * Constructor of the Enemy.
     * @param startLocation Location where the enemy will spawn.
     * @param carriers will be used to determine the movebehaviour.
     */
    public Enemy(Vector3f startLocation, Carrier[] carriers) {
        super();
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        moveBehaviour = new EnemyMoveBehaviour(this, carriers);
        health = 1;
    }
    
    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATH_NAME);
    }
    
    @Override
    public int getHealth() {
        return health;
    }
    
    @Override
    public void setHealth(int health) {
        this.health = health;        
    }
    
    @Override
    public void onKilled() {
        
    }
    
    /**
     * Returns the moveBehaviour of the enemy.
     * @return the movebeahviour of the enemy.
     */
    public MoveBehaviour getMoveBehaviour() {
        moveBehaviour.updateMoveVector();
        return moveBehaviour;
        
    }
    
    /**
     * Attack the carrier for 1 damage every 3 seconds. Can only attack if the 
     * enemy is at a spot next to a carrier.
     * @param tpf deltatime required for calulating when to attack again.
     */
    public void attack(float tpf) {
        if (moveBehaviour.isAtSpot()) {
            currentTime += tpf;
            if (currentTime >= attackThreshHold) {
                moveBehaviour.getTargetSpot().getCarrier().takeDamage(1);
                currentTime -= attackThreshHold;
            }
        }
    }
    

}
