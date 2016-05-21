package obstacles;

import com.jme3.asset.AssetManager;

public class StaticObstacleFactory extends DamageDealerFactory {


    @Override
    public StaticObstacle produce(AssetManager assetManager) {
        return new StaticObstacle(assetManager);
    }

}
