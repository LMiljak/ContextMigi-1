package com.github.migi_1.Context.model.entity;

import java.util.ArrayList;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.EnemySpot.Direction;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Class that handles everything that have to do with the carrier.
 * It implements collidable to register collisions.
 * It implements IMovable to register a movable behaviour. A movable behaviour
 * Sets how the carrier moves after every tick.
 * @author Damian
 *
 */
public class Carrier extends Entity implements IKillable {

    //String of the path to the carrier model
    private static final String PATHNAME = "Models/ninja.j3o";
    private static final Vector3f MOVE_VECTOR = new Vector3f(-0.2f, 0, 0);

    private int health;
    private int id; //Represents the location of the carrier under the platform.
    private Vector3f relativeLocation;
    private ArrayList<EnemySpot> enemySpots;
    private MainEnvironment environment;
    
    /**
     * constructor of the carrier.
     * @param relativeLocation location relative to the commander
     * @param id to keep the 4 carriers apart
     * @param environment The environment to follow
     */
    public Carrier(Vector3f relativeLocation, int id, MainEnvironment environment) {
        super();
        enemySpots = new ArrayList<EnemySpot>();
        setModel(getDefaultModel());
        getModel().setLocalTranslation(environment.getCommander().getModel()
                .getLocalTranslation().add(relativeLocation));
        this.relativeLocation = relativeLocation;
        CarrierMoveBehaviour moveBehaviour = new CarrierMoveBehaviour(this, MOVE_VECTOR, environment);
        moveBehaviour.setRelativeLocation(relativeLocation);
        setMoveBehaviour(new CarrierMoveBehaviour(this, MOVE_VECTOR, environment));
        this.id = id;
        this.environment = environment;
        createEnemyLocations();
        health = 2;
        
    }

    private void createEnemyLocations() {
        enemySpots.add(new EnemySpot(new Vector3f(-2, 0, 0), this, environment.getCommander(), Direction.NORTH));
        if (id % 2 == 0) {
            enemySpots.add(new EnemySpot(new Vector3f(0, 0, 2), this, environment.getCommander(),Direction.EAST));
        }
        else {
            enemySpots.add(new EnemySpot(new Vector3f(0, 0, -2), this, environment.getCommander(),Direction.WEST));
        }
        enemySpots.add(new EnemySpot(new Vector3f(2, 0, 0), this, environment.getCommander(),Direction.SOUTH));

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int h) {
        health = h;
    }

    /**
     * Gets the id that represents the location of the Carrier under the platform.
     *
     * @return
     * 		the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onKilled() {

    }

    @Override
    public Spatial getDefaultModel() {
        return ProjectAssetManager.getInstance().getAssetManager().loadModel(PATHNAME);
    }


    /**
     * Get relativeLocation attribute.
     * @return relativeLocation attribute
     */
    public Vector3f getRelativeLocation() {
        return relativeLocation;
    }



    /**
     * @return the enemySpots
     */
    public ArrayList<EnemySpot> getEnemySpots() {
        return enemySpots;
    }



    /**
     * @param enemySpots the enemySpots to set
     */
    public void setEnemySpots(ArrayList<EnemySpot> enemySpots) {
        this.enemySpots = enemySpots;
    }
    
    
    
    



}
