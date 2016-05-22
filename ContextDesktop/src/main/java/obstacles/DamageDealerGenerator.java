package obstacles;

import java.util.HashMap;

import com.github.migi_1.Context.model.Environment;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class DamageDealerGenerator {

    private static DamageDealerGenerator instance;

    private Environment environment;

    private static final int NUMBER_OBSTACLES = 10;

    private Vector3f location;

    private DamageDealerFactory obstacleFactory;

    private HashMap<Geometry, DamageDealer> obstacleList;

    public DamageDealerGenerator(Environment environment){
        this.environment = environment;
        this.location = environment.getCommanderLocation();
        this.obstacleFactory = new StaticObstacleFactory();
        this.obstacleList = new HashMap<Geometry, DamageDealer>();
    }

    public static DamageDealerGenerator getInstance(Environment environment) {
        if (instance == null) {
            instance = new DamageDealerGenerator(environment);
        }
        return instance;
    }

    public HashMap<Geometry, DamageDealer> getObstacles() {
        while (obstacleList.size() < NUMBER_OBSTACLES){
            DamageDealer obs = obstacleFactory.produce(environment.getAssetManager());
            obs.scale(0.3f);
            location = location.add(new Vector3f(-50.f, 0, 0.0f));


            obs.move(location);
            obstacleList.put((Geometry) obs.getModel(),obs);
        }
        return obstacleList;

    }


}
