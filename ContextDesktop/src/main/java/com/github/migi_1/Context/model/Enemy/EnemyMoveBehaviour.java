package com.github.migi_1.Context.model.Enemy;

import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

public class EnemyMoveBehaviour extends MoveBehaviour {

    private Commander commander;
    private Vector3f moveVector;
    private float speed = 0.001f;

    public EnemyMoveBehaviour(Commander commander) {
        super();
        this.commander = commander;
        moveVector = new Vector3f(0, 0, 0);
    }

    @Override
    public Vector3f getMoveVector() {
        return moveVector;
    }

    public void update(Vector3f localTranslation) {
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
