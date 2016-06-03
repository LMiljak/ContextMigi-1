package com.github.migi_1.Context.enemy;

import java.util.LinkedList;
import java.util.Random;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

/**
 * Class responsible for the behaviour of enemies.
 * @author Damian
 *
 */
public class EnemyMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;
    private float speed = 0.5f;
    private Vector3f localTranslation;
    private Carrier[] carriers;
    private EnemySpot targetSpot;
    private Enemy enemy;
    private boolean atSpot;
    
    /**
     * Constructor of the EnemyMoveBehaviour.
     * @param enemy The enemy to which the movebehaviour belongs.
     * @param carriers for finding the spot where the enemy will walk to.
     */
    public EnemyMoveBehaviour(Enemy enemy, Carrier[] carriers) {
        super();
        this.enemy = enemy;
        this.moveVector = new Vector3f(0, 0, 0);
        this.carriers = carriers;
        this.localTranslation = enemy.getModel().getLocalTranslation();
        atSpot = false;
        targetSpot = createTargetSpot();
    }
    
    /**
     * Finds the spot the enemy will walk to. 
     * The spot is randomly chosen from all spots of the carriers which are still unoccupied.
     * The spot becomes occupied as soon as the enemy starts walking towards it to prevent 2 enemies from
     * walking to the same spot.
     * @return The enemySpot, the spot where the enemy will walk towards.
     */
    private EnemySpot createTargetSpot() {
        LinkedList<EnemySpot> spots = new LinkedList<EnemySpot>();
        for (Carrier carrier : carriers) {
            for (EnemySpot location : carrier.getEnemySpots()) {
                if (!location.isOccupied()) {
                    spots.add(location);
                }
            }
        }

        if (spots.size() != 0) {
            int random = new Random().nextInt(spots.size());
            EnemySpot enemySpot = spots.get(random);
            enemySpot.setOccupied(true);
            return enemySpot;
        } else {
            return null;
        }
    }

    @Override
    public Vector3f getMoveVector() {
        updateMoveVector();
        return moveVector;
    }
    
    /**
     * Updates the moveVector.
     * If the enemy has a moveVector (if not all spots are occupied already) and it is within range
     * of this spot, the enemy will move towards it with a certain speed in the x and z direction.
     * As soon as the distance on the x as is less than the speed it will move on that spot equal to
     * that distance to prevent the enemy from overshooting that threshold. Overshooting results in 
     * stuttering of the enemy when it has reached the target spot. It also sets the atSpot to true when 
     * the enemy has reached the generated targetSpot at createTargetSpot.
     */
    @Override
    public void updateMoveVector() {
        if (targetSpot != null) { 
            if (targetSpot.getLocation().distance(localTranslation) < 50) {
                handleXmovement();
                handleZmovement();
            } 
            reachedSpot();
            
        }  
    }

    private void handleXmovement() {
        if (targetSpot.getLocation().x > localTranslation.getX()) {
            if (targetSpot.getLocation().subtract(localTranslation).x < speed) {
                moveVector.setX(targetSpot.getLocation().subtract(localTranslation).x);
            } else {
                moveVector.setX(speed);
            }
        } else if (targetSpot.getLocation().x < localTranslation.getX()) {
            if (targetSpot.getLocation().subtract(localTranslation).x < -speed) {
                moveVector.setX(-targetSpot.getLocation().subtract(localTranslation).x);
            } else {
                moveVector.setX(-speed);
            }
        }
    }

    private void handleZmovement() {
        if (targetSpot.getLocation().z > localTranslation.getZ()) {
            moveVector.setZ(speed);
        } else {
            moveVector.setZ(-speed);
        }
    }

    private void reachedSpot() {
        if (targetSpot.getLocation().distance(localTranslation) < speed 
                && !atSpot) {
            targetSpot.setEnemy(enemy);
            atSpot = true;
        }
    }

    /**
     * @return the atSpot
     */
    public boolean isAtSpot() {
        return atSpot;
    }

    /**
     * @return the targetSpot
     */
    public EnemySpot getTargetSpot() {
        return targetSpot;
    }

    /**
     * @param targetSpot the targetSpot to set
     */
    public void setTargetSpot(EnemySpot targetSpot) {
        this.targetSpot = targetSpot;
    }

    /**
     * @param atSpot the atSpot to set
     */
    public void setAtSpot(boolean atSpot) {
        this.atSpot = atSpot;
    }
    
}

