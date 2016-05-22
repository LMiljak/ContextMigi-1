package obstacles;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This interface provides some methods that all DamageDealers must declare.
 * @author Marcel
 *
 */
public interface DamageDealer {

    /**
     * Return the model of the object.
     * @return the model
     */
    public Spatial getModel();

    /**
     * Set the model of the object.
     */
    public void setModel(Spatial model);

    /**
     * Move the object.
     * @param x Translation in x direction
     * @param y Translation in y direction
     * @param z Translation in z direction
     */
    public void move(float x, float y, float z);

    /**
     * Scale the object.
     * @param f scale factor
     */
    public void scale(float f);

    /**
     * Move the object.
     * @param add Translation vector
     */
    public void move(Vector3f add);

    /**
     * Handle collisions between the model of the damage dealer and the platform
     * @param platform The platform
     * @param results Collision results
     */
    public void collideWith(Spatial platform, CollisionResults results);

}
