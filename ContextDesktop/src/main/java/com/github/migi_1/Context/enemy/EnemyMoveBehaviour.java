package com.github.migi_1.Context.enemy;

import java.util.LinkedList;
import java.util.Random;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

public class EnemyMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;
    private float speed = 0.5f;
    private Vector3f localTranslation;
    private Carrier[] carriers;
    private EnemySpot targetSpot;

    public EnemyMoveBehaviour(Enemy enemy, Carrier[] carriers) {
        super();
        this.moveVector = new Vector3f(0,0,0);
        this.carriers = carriers;
        this.localTranslation = enemy.getModel().getLocalTranslation();
        targetSpot = getTargetSpot();
    }

    private EnemySpot getTargetSpot() {
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
        } else return null;
    }

    @Override
    public Vector3f getMoveVector() {
        return moveVector;
    }

    @Override
    public void updateMoveVector() {
        if (targetSpot != null) { 
            if (targetSpot.getLocation().distance(localTranslation) < 50) {
                if (targetSpot.getLocation().x > localTranslation.getX()) {
                    if(targetSpot.getLocation().subtract(localTranslation).x < speed) {
                        moveVector.setX(targetSpot.getLocation().subtract(localTranslation).x);
                    } else moveVector.setX(speed);
                } else if (targetSpot.getLocation().x < localTranslation.getX()) {
                    if (targetSpot.getLocation().subtract(localTranslation).x < -speed) {
                        moveVector.setX(-targetSpot.getLocation().subtract(localTranslation).x);
                    } else moveVector.setX(-speed);
                }
                if (targetSpot.getLocation().z > localTranslation.getZ()) {
                    moveVector.setZ(speed);
                } else {
                    moveVector.setZ(-speed);
                }

            }  
        }
    }
}

