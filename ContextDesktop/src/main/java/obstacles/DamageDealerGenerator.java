package obstacles;

import java.util.HashMap;

import com.github.migi_1.Context.model.Environment;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 * This class handles how often and what types of enemies spawn.
 * TODO: Create better obstacle spawn routine.'
 *
 * SINGLETON class.
 * @author Marcel
 *
 */
public final class DamageDealerGenerator {

    /** Class instance. **/
    private static DamageDealerGenerator instance;

    /** Environment instance. **/
    private Environment environment;

    private static final int NUMBER_OBSTACLES = 10;

    /** Location of next obstacle spawn location. **/
    private Vector3f location;

    /** Abstract factory. **/
    private DamageDealerFactory damageDealerFactory;

    /** HashMap of all geometry pieces. **/
    private HashMap<Geometry, DamageDealer> obstacleList;

    /**
     * Private constructor.
     * Instantiate local variables.
     * @param environment The environment app state.
     */
    private DamageDealerGenerator(Environment environment) {
        this.environment = environment;
        this.location = environment.getCommanderLocation();
        this.damageDealerFactory = new StaticObstacleFactory();
        this.obstacleList = new HashMap<Geometry, DamageDealer>();
    }

    /** Access method of singleton class.
     * @param environment Environment state.
     * @return Instance of this object.
     */
    public static DamageDealerGenerator getInstance(Environment environment) {
        if (instance == null) {
            instance = new DamageDealerGenerator(environment);
        }
        return instance;
    }

    /**
     * Create list of damage dealers that are to be spawned in the environment.
     * @return Map with all DamageDealer objects, with as key value their Geometry in the environment.
     */
    public HashMap<Geometry, DamageDealer> getObstacles() {
        while (obstacleList.size() < NUMBER_OBSTACLES) {
            DamageDealer obs = damageDealerFactory.produce(environment.getAssetManager());
            obs.scale(0.3f);
            location = location.add(new Vector3f(-50.f, 0, 0.0f));


            obs.move(location);
            obstacleList.put((Geometry) obs.getModel(), obs);
        }
        return obstacleList;

    }


}
