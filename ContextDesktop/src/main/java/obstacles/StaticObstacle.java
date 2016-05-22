package obstacles;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class StaticObstacle implements DamageDealer {

    private static final String MODEL_FILE = "Models/testCube2.j3o";

    private Spatial model;

    public StaticObstacle(AssetManager assetManager) {
        setModel(assetManager.loadModel(MODEL_FILE));
        model.move(0.0f, -2.0f, 0.0f);
    }

    @Override
    public Spatial getModel() {
        return model;
    }

    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }

    @Override
    public void scale(float f) {
        model.scale(f);

    }

    @Override
    public void move(Vector3f add) {
        model.move(add);

    }

    @Override
    public void move(float f, float g, float h) {
        model.move(f, g, h);

    }

    @Override
    public void collideWith(Spatial testPlatform, CollisionResults results) {
       this.getModel().collideWith(testPlatform.getWorldBound(), results);

    }




}
