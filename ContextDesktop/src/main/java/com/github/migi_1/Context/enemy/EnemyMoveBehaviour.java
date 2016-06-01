package com.github.migi_1.Context.enemy;

import java.util.Random;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.EnemySpot;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

public class EnemyMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;
    private float speed = 0.001f;
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
        EnemySpot[] spots = new EnemySpot[12];
        int i = 0;
        for (Carrier carrier : carriers) {
            for (EnemySpot location : carrier.getEnemySpots()) {
                if (!location.isOccupied()) {
                    spots[i] = location;
                    i++;
                }
            }
        }

        int random = new Random().nextInt(spots.length);
        return spots[random];
    }

    @Override
    public Vector3f getMoveVector() {
        return moveVector;
    }

    @Override
    public void updateMoveVector() {
        if (targetSpot.getLocation().distance(localTranslation) < 50) {
            if (targetSpot.getLocation().x > localTranslation.getX()) {
                moveVector.setX(speed);
            } else {
                moveVector.setX(-speed);
            }
           // System.out.println("target: " + targetSpot.getCarrierId() + " " + targetSpot.getLocation().x);
            for(Carrier carrier : carriers) {
                if (targetSpot.getCarrierId() == carrier.getId()) {
                   // System.out.println("carrier location" + carrier.getId() + carrier.getModel().getLocalTranslation());
                }
            }
            

            //            if (Math.abs(Math.abs(commander.getModel().getWorldBound().getCenter().z) - Math.abs(localTranslation.getZ())) < 30 &&
            //                    Math.abs(Math.abs(commander.getModel().getWorldBound().getCenter().z) - Math.abs(localTranslation.getZ())) > 3) {
            //                if (commander.getModel().getLocalTranslation().getZ() > localTranslation.getZ()) {
            //                    moveVector.setZ(speed);
            //                } else {
            //                    moveVector.setZ(-speed);
            //                }
            //            } else {
            //                moveVector.setZ(0);
            //            }
            //if (targetSpot.getLocation().distance(localTranslation) < 30) {
                if (targetSpot.getLocation().z > localTranslation.getZ()) {
                    moveVector.setZ(speed);
                } else {
                    moveVector.setZ(-speed);
                }
//            } else {
//                moveVector.setZ(0);
//            }

        }
    }
}

