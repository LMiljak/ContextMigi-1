package com.github.migi_1.Context.obstacle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.model.entity.Commander;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

/**TODO: UPDATE THIS JAVADOC WHEN DYNAMIC OBSTACLE SPAWNING IS IMPLEMENTED
 * Handles everything that has to do with the spawning of an obstacle.
 * An ObstacleSpawner has an staticObstaclefactory and a dynamicObstacleFactory, which makes it able to switch
 * between dynamic obstacle spawning and static obstacle spawning when the difficulty increases.
 * @author Marcel/Damian
 *
 */
public class ObstacleSpawner {

    private static final int NUMBER_OBSTACLES = 10;

    /** Location of next obstacle spawn location. **/
    private Vector3f location;

    /** Abstract factory. **/
    private MovingObstacleFactory movingObstacleFactory;
    private StaticObstacleFactory staticObstacleFactory;

    /** ArrayList of all geometry pieces. **/
    private ArrayList<Obstacle> obstacleList;

    private LinkedList<Obstacle> deleteList;

    private Commander commander;


    private float leftBound;

    private float rightBound;

    /**
     * Constructor for the obstacle spawner object.
     * @param environment needed for knowing where to spawn the obstacles.
     */
    public ObstacleSpawner(MainEnvironment environment) {
        this.commander = environment.getCommander();
        this.location = commander.getModel().getLocalTranslation();
        this.obstacleList = new ArrayList<Obstacle>();
        this.deleteList = new LinkedList<Obstacle>();
        this.movingObstacleFactory = new MovingObstacleFactory(environment);
        this.staticObstacleFactory = new StaticObstacleFactory();
        this.leftBound = getBound(environment.getLeftBound());
        this.rightBound = getBound(environment.getRightBound());
    }

    /**
     * Create list of obstacles that are to be spawned in the environment and return them.
     * @return Map with all obstacles, with as key value their Geometry in the environment.
     */
    public ArrayList<Obstacle> updateObstacles() {
        //call removeDamageDealer when an obstacle is too far away
        for (Obstacle obstacle : obstacleList) {
            if ((obstacle.getModel().getLocalTranslation().x - commander.getModel().getLocalTranslation().x) > 200) {
                deleteList.add(obstacle);

            }
        }
        obstacleList.removeAll(deleteList);
        while (obstacleList.size() < NUMBER_OBSTACLES) {
            Obstacle movingObstacle = movingObstacleFactory.produce();
            movingObstacle.scale(0.3f);
            
            Obstacle staticObstacle = staticObstacleFactory.produce();
            staticObstacle.scale(2);
            
            location = location.add(new Vector3f(-1 * getDistanceToNextObstacle(), 0, 0));            
            movingObstacle.move(location.add(new Vector3f(0, 0, getZLocation())));
            
            location = location.add(new Vector3f(-1 * getDistanceToNextObstacle(), 0, 0));
            staticObstacle.move(location.add(new Vector3f(0, 0, getZLocation())));
            
            obstacleList.add(movingObstacle);
            obstacleList.add(staticObstacle);
            
        }
        return obstacleList;
    }
    
    /**
     * @return
     * 		A random float value that represents the distance
     * 		between two obstacles.
     */
    private float getDistanceToNextObstacle() {
    	final Random rand = new Random();
    	final float minimumDistance = 60.f;
    	final float maximumDistance = 180.f;
    	
    	final float result = rand.nextFloat() * (maximumDistance - minimumDistance) + minimumDistance;
    	return result;
    }

    /**
     * Return a random location on the z-axis between the two bounding boxes.
     * @return random location
     */
    private float getZLocation() {
        Float rand = (float) Math.random();
        float zOrientation = rand * (leftBound - rightBound) - (leftBound - rightBound) / 2.f;
        return zOrientation;
    }

    /**
     * Remove a obstacle after collision.
     * @return obstacles that is deleted
     */
    public Obstacle removeDamageDealer() {
        float distance = Float.MAX_VALUE;
        Obstacle closest = obstacleList.get(0);
        for (Obstacle obstacle : obstacleList) {
            Vector3f checkLocation = obstacle.getModel().getLocalTranslation();
            float check = checkLocation.distance(commander.getModel().getLocalTranslation());
            if (check < distance) {
                distance = check;
                closest = obstacle;
            }
        }
        obstacleList.remove(closest);
        return closest;
    }

    /**
     * Get the z coordinate of the center of a bounding box.
     * @param boundingBox Bounding box to check
     * @return z coordinate of the center
     */
    private float getBound(BoundingBox boundingBox) {
        return boundingBox.getCenter().z;
    }

    /**
     * Return all obstacles that need to be deleted.
     * @return List of obstacles to be deleted.
     */
    public LinkedList<Obstacle> deleteObstacles() {
        LinkedList<Obstacle> temp = (LinkedList<Obstacle>) deleteList.clone();
        deleteList = new LinkedList<Obstacle>();
        return temp;
    }

    /**
     * @return the movingObstacleFactory
     */
    public MovingObstacleFactory getMovingObstacleFactory() {
        return movingObstacleFactory;
    }

    /**
     * @param movingObstacleFactory the movingObstacleFactory to set
     */
    public void setMovingObstacleFactory(MovingObstacleFactory movingObstacleFactory) {
        this.movingObstacleFactory = movingObstacleFactory;
    }

    /**
     * @return the staticObstacleFactory
     */
    public StaticObstacleFactory getStaticObstacleFactory() {
        return staticObstacleFactory;
    }

    /**
     * @param staticObstacleFactory the staticObstacleFactory to set
     */
    public void setStaticObstacleFactory(StaticObstacleFactory staticObstacleFactory) {
        this.staticObstacleFactory = staticObstacleFactory;
    }
    
    
}
