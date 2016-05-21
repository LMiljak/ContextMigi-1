package obstacles;

import java.util.LinkedList;

import com.github.migi_1.Context.model.Environment;
import com.jme3.math.Vector3f;

public class ObstacleGenerator {

    private static ObstacleGenerator instance;

    private Environment environment;

    private static final int NUMBER_OBSTACLES = 10;

    private Vector3f location;

    private ObstacleFactory obstacleFactory;

    private LinkedList<Obstacle> obstacleList;

    public ObstacleGenerator(Environment environment){
        this.environment = environment;
        this.location = environment.getCommanderLocation();
        this.obstacleFactory = new StaticObstacleFactory();
        this.obstacleList = new LinkedList<Obstacle>();
    }

    public static ObstacleGenerator getInstance(Environment environment) {
        if (instance == null) {
            instance = new ObstacleGenerator(environment);
        }
        return instance;
    }

    public LinkedList<Obstacle> getObstacles() {
        while (obstacleList.size() < NUMBER_OBSTACLES){
            Obstacle obs = obstacleFactory.produce(environment.getAssetManager());
            location = location.add(new Vector3f(-50.f, 0.0f, 0.0f));
            System.out.println(location);
            obs.move(location);
            obstacleList.add(obs);
        }
        return obstacleList;

    }


}
