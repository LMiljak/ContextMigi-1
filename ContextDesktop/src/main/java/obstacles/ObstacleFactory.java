package obstacles;

import com.jme3.asset.AssetManager;

public abstract class ObstacleFactory {

    public abstract Obstacle produce(AssetManager assetManager);

}
