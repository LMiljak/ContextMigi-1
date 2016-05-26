package com.github.migi_1.Context.model.Enemy;

import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

public class EnemyMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;

    public EnemyMoveBehaviour() {
        super();
        moveVector = new Vector3f(0, 0, 0);
    }

    @Override
    public Vector3f getMoveVector() {
          return moveVector;
      }

    public void update(Vector3f localTranslation) {
  
    }
}
