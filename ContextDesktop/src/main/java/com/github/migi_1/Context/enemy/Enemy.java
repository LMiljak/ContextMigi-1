package com.github.migi_1.Context.enemy;

import java.util.ArrayList;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IKillable;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class responsible for the enemy, has an EnemyMoveBehaviour.
 * @author Damian
 *
 */
public class Enemy extends Entity implements IKillable {

    private static final String PATH_NAME = "Models/slime.j3o";
    private int health;
    private float currentTime = 0;
    private static final float ATTACK_THRESHOLD = 3;
    private EnemySpot spot;

    /**
     * Constructor of the Enemy.
     * @param startLocation Location where the enemy will spawn.
     * @param carriers will be used to determine the movebehaviour.
     */
    public Enemy(Vector3f startLocation, ArrayList<Carrier> carriers) {
        super();
        setModel(getDefaultModel());
        getModel().setLocalTranslation(startLocation);
        setMoveBehaviour(new EnemyMoveBehaviour(this, carriers));
        health = 1;
    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATH_NAME).scale(2f);
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
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    	if (getHealth() <= 0) {
    		onKilled();
    	}
    }

    @Override
    public void onKilled() {
        if (spot != null) {
            spot.setEnemy(null);
            spot.setOccupied(false);
            spot = null;
        }
    }

    /**
     * Attack the carrier for 1 damage every 3 seconds. Can only attack if the
     * enemy is at a spot next to a carrier.
     * @param tpf deltatime required for calulating when to attack again.
     */
    public void attack(float tpf) {
        if (((EnemyMoveBehaviour) getMoveBehaviour()).isAtSpot()) {
            currentTime += tpf;
            if (currentTime >= ATTACK_THRESHOLD) {
                ((EnemyMoveBehaviour) getMoveBehaviour()).getTargetSpot().getCarrier().takeDamage(1);
                currentTime = 0;
            }
        }
    }

    /**
     * Setter for the enemy's EnemySpot.
     * @param spot the spot where the enemy attacks a carrier from
     */
    public void setSpot(EnemySpot spot) {
        this.spot = spot;
    }

    /**
     * Getter for the enemy's EnemySpot.
     * @return
     *          The EnemySpot from which the enemy attacks a carrier
     */
    public EnemySpot getSpot() {
        return spot;
    }

}
