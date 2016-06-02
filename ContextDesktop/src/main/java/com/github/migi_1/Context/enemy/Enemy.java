package com.github.migi_1.Context.enemy;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.IKillable;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Enemy extends Entity implements IKillable {
    
    private static final String PATH_NAME = "Models/ninja.j3o";
    private EnemyMoveBehaviour moveBehaviour;
    private int health;
    
    public Enemy (Vector3f startLocation, Carrier[] carriers) {
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
        // TODO Auto-generated method stub        
    }
    
    public MoveBehaviour getMoveBehaviour() {
        moveBehaviour.updateMoveVector();
        return moveBehaviour;
        
    }
    

}
