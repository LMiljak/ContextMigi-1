package com.github.migi_1.Context.model.entity;



import java.util.ArrayList;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.EnemySpot.Direction;
import com.github.migi_1.Context.server.HealthMessenger;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.Context.server.AttackMessageHandler;
import com.github.migi_1.ContextMessages.PlatformPosition;

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
    private static final int INITIAL_HEALTH = 3;

    private Main main;
    private HealthMessenger healthMessenger;
    private AttackMessageHandler attackMessageHandler;
    
    private int health;

    private PlatformPosition position;
    
    private Vector3f relativeLocation;
    private ArrayList<EnemySpot> enemySpots;
    private MainEnvironment environment;
    
    /**
     * Constructor of the carrier.
     * @param relativeLocation location relative to the commander
     * @param position The position of the carrier under the platform.
     * @param environment The environment to follow
     */
    public Carrier(Vector3f relativeLocation, PlatformPosition position, 
            MainEnvironment environment) {
        super();

        enemySpots = new ArrayList<EnemySpot>();

        setModel(getDefaultModel());
        getModel().setLocalTranslation(environment.getCommander().getModel()
                .getLocalTranslation().add(relativeLocation));
        this.relativeLocation = relativeLocation;

        setMoveBehaviour(environment.getPlatform().getMoveBehaviour());

        health = INITIAL_HEALTH;
        main = environment.getMain();
        healthMessenger = new HealthMessenger(main);
        attackMessageHandler = new AttackMessageHandler(main, this, position);

        this.position = position;
        this.environment = environment;
        createEnemyLocations();
        
    }

    private void createEnemyLocations() {
        enemySpots.add(new EnemySpot(new Vector3f(-2, 0, 0), this, environment.getCommander(), Direction.NORTH));
        if (position.getzFactor() == 1) {
            enemySpots.add(new EnemySpot(new Vector3f(0, 0, 2), this, environment.getCommander(), Direction.EAST));
        }
        else {
            enemySpots.add(new EnemySpot(new Vector3f(0, 0, -2), this, environment.getCommander(), Direction.WEST));
        }
        enemySpots.add(new EnemySpot(new Vector3f(2, 0, 0), this, environment.getCommander(), Direction.SOUTH));

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int h) {
        health = h;
        healthMessenger.sendHealth(getHealth(), getPosition());
    }
    
    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    	if (getHealth() <= 0) {
    		onKilled();
        }
    }

    /**
     * Gets the position of this Carrier under the Platform.
     * 
     * @return
     * 		The position of this Carrier under the Platform.
     */
    public PlatformPosition getPosition() {
        return position;
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
    
    /**
     * Getter for healthMessenger.
     * @return healthMessenger HealthMessenger
     */
    public HealthMessenger getHealthMessenger() {
        return healthMessenger;
    }
    
    /**
     * Executes an attack using a player's position and direction of attack.
     * @param direction
     * 			the direction of the attack (String)
     */
    public void handleAttack(String direction) {
        if (position.equals(PlatformPosition.FRONTLEFT)
                || position.equals(PlatformPosition.BACKLEFT)) {
            attackLeft(direction);
        }
        else {
            attackRight(direction);
        }
    }
    
    public void attackLeft(String direction) {
        switch (direction) {
            case "left":
                attack(Direction.SOUTH);
                break;
            case "middle":
                attack(Direction.WEST);
                break;
            case "right":
                attack(Direction.NORTH);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public void attackRight(String direction) {
        switch (direction) {
            case "left":
                attack(Direction.NORTH);
                break;
            case "middle":
                attack(Direction.EAST);
                break;
            case "right":
                attack(Direction.SOUTH);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public void attack(Direction direction) {
        // TODO: execute attacks
    }

}
