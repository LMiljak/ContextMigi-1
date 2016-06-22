package com.github.migi_1.Context.model.entity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.github.migi_1.Context.enemy.Enemy;
import com.github.migi_1.Context.enemy.EnemyMoveBehaviour;
import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.server.AttackMessageHandler;
import com.github.migi_1.Context.server.HealthMessenger;
import com.github.migi_1.Context.server.HitMissMessenger;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.github.migi_1.ContextMessages.Direction;
import com.github.migi_1.ContextMessages.ImmobilisedMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.math.Vector3f;
import com.jme3.network.Server;
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
    private static final long  IMMOBILISATION_TIME = 10000;

    private Main main;
    private HealthMessenger healthMessenger;

    @SuppressWarnings("unused")
    private AttackMessageHandler attackMessageHandler;
    private HitMissMessenger hitMissMessenger;

    private int health;

    private PlatformPosition position;

    private Vector3f relativeLocation;
    private ArrayList<EnemySpot> enemySpots;
    private MainEnvironment environment;

    private Timer immobalisedTimer;

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
        getModel().rotate(0, (float) Math.PI, 0);
        this.relativeLocation = relativeLocation;

        setMoveBehaviour(environment.getPlatform().getMoveBehaviour());

        health = INITIAL_HEALTH;
        main = environment.getMain();
        healthMessenger = new HealthMessenger(main);
        attackMessageHandler = new AttackMessageHandler(main, this, position);
        hitMissMessenger = new HitMissMessenger(main);

        this.position = position;
        this.environment = environment;
        this.immobalisedTimer = new Timer();
        createEnemyLocations();
    }

    private void createEnemyLocations() {
        enemySpots.add(new EnemySpot(new Vector3f(-4, 0, 0), this, environment.getCommander(), Direction.NORTH));
        if (position.getzFactor() == 1) {
            enemySpots.add(new EnemySpot(new Vector3f(0, 0, 4), this, environment.getCommander(), Direction.WEST));
        }
        else {
            enemySpots.add(new EnemySpot(new Vector3f(0, 0, -4), this, environment.getCommander(), Direction.EAST));
        }
        enemySpots.add(new EnemySpot(new Vector3f(4, 0, 0), this, environment.getCommander(), Direction.SOUTH));
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
        getModel().setMaterial(ProjectAssetManager.getInstance().getAssetManager().loadMaterial("Materials/ninjaRed.j3m"));
        
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) {
            onKilled();
        }
    }

    /**
     * Gets the position of this Carrier under the Platform.
     *
     * @return
     *      The position of this Carrier under the Platform.
     */
    public PlatformPosition getPosition() {
        return position;
    }

    @Override
    public void onKilled() {
        immobalisedTimer.schedule(new ImmobilisedTimerTask(), IMMOBILISATION_TIME);
        ImmobilisedMessage message = new ImmobilisedMessage(true, position);
        Server server = main.getServer().getServer();
        if (server.isRunning()) {
            server.broadcast(message);
        }
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
     * 			the direction of the attack
     */
    public void handleAttack(Direction direction) {
        for (EnemySpot enemySpot : enemySpots) {
            if (direction.equals(enemySpot.getDirection())) {
                Enemy enemy = enemySpot.getEnemy();
                if (enemy == null || !((EnemyMoveBehaviour) enemy.getMoveBehaviour()).isAtSpot()) {
                    hitMissMessenger.sendHitMiss(false, position);
                }
                else {
                    hitMissMessenger.sendHitMiss(true, position);
                    enemy.takeDamage(1);
                }
            }
        }
    }

    /**
     * Task that gets executed when the carrier stops being immobilised.
     * @author Marcel
     *
     */
    class ImmobilisedTimerTask extends TimerTask {
        /**
         * When the carrier is not immobilised anymore, give his health back.
         */
        @Override
        public void run() {
            setHealth(3);
            ImmobilisedMessage message = new ImmobilisedMessage(false, position);
            Server server = main.getServer().getServer();
            if (server.isRunning()) {
                server.broadcast(message);
            }
        }
    }
}
