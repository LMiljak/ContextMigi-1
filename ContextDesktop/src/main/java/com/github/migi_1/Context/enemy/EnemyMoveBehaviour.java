package com.github.migi_1.Context.enemy;

import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

public class EnemyMoveBehaviour extends MoveBehaviour {

    private Commander commander;
    private Vector3f moveVector;
    private float speed = 0.001f;
    private Vector3f localTranslation;

    public EnemyMoveBehaviour(Commander commander, Enemy enemy) {
        super();
        this.commander = commander;
        this.localTranslation = enemy.getModel().getLocalTranslation();
        moveVector = new Vector3f(0, 0, 0);
    }

    @Override
    public Vector3f getMoveVector() {
        return moveVector;
    }

    @Override
    public void updateMoveVector() {
        if (commander.getModel().getWorldBound().distanceTo(localTranslation) < 20) {
            if (commander.getModel().getLocalTranslation().getX() - localTranslation.getX() < 30) {
                if (commander.getModel().getLocalTranslation().getX() > localTranslation.getX()) {
                    moveVector.setX(speed);
                } else {
                    moveVector.setX(-speed);
                }
            }

            if (Math.abs(Math.abs(commander.getModel().getWorldBound().getCenter().z) - Math.abs(localTranslation.getZ())) < 10 &&
                    Math.abs(Math.abs(commander.getModel().getWorldBound().getCenter().z) - Math.abs(localTranslation.getZ())) > 3) {
                if (commander.getModel().getLocalTranslation().getZ() > localTranslation.getZ()) {
                    moveVector.setZ(speed);
                } else {
                    moveVector.setZ(-speed);
                }
            } else {
                moveVector.setZ(0);
            }

        }
    }
}

