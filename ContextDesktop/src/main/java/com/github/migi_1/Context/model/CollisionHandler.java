package com.github.migi_1.Context.model;

import java.util.HashMap;
import java.util.Map.Entry;

import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.Platform;
import com.github.migi_1.Context.model.entity.behaviour.EntityMoveBehaviour;
import com.github.migi_1.Context.obstacle.Obstacle;
import com.github.migi_1.Context.obstacle.ObstacleSpawner;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;

/**
 * This class handles all collisions, these include collisions with static and 
 * moving obstacles and collisions with the boundingboxes on the sides of the path.
 * @author Damian
 *
 */
public class CollisionHandler {

    private BoundingBox boundingBoxWallLeft;
    private BoundingBox boundingBoxWallRight;
    private Platform platform;
    private HashMap<Entity, CollisionResults> results;
    private Commander commander;
    private ObstacleSpawner obstacleSpawner;
    private Environment environment;
    
    /**
     * Constructor for the CollisionHandler.
     * @param commander commander of the game, needed for location checking.
     * @param platform platform of the game, needed for location checking.
     * @param obstacleSpawner used to check where the obstacles are.
     * @param environment used to create boundingBoxes.
     */
    public CollisionHandler(Commander commander, Platform platform,
            ObstacleSpawner obstacleSpawner, MainEnvironment environment) {        
        this.commander = commander;
        this.platform = platform;
        this.obstacleSpawner = obstacleSpawner;
        this.environment = environment;
        results = new HashMap<Entity, CollisionResults>();
        createWallBoundingBoxes();
    }
    
    /**
     * Creates the bounding boxes on the left and right side of the path.
     */
    private void createWallBoundingBoxes() {
        Path path = new Path();
        boundingBoxWallLeft = new BoundingBox(
                new Vector3f(0, 0, path.getModel().center().getLocalTranslation().z
                        + ((BoundingBox) path.getModel().getWorldBound()).getZExtent()),
                        Float.MAX_VALUE,
                        100f, 1f);

        final float offset = -10;
        boundingBoxWallRight = new BoundingBox(
                new Vector3f(0, 0, path.getModel().center().getLocalTranslation().z + offset
                        -  ((BoundingBox) path.getModel().getWorldBound()).getZExtent()),
                        Float.MAX_VALUE,
                        100f, 1f);
    }
    
    /**
     * Handles collision checking of the obstacles. Makes the carriers take 1 damage when
     * they collide with any kind of obstacle.
     */
    public void checkObstacleCollision() {
        //add collision check for all obstacles
        for (Obstacle staticObstacle : obstacleSpawner.updateObstacles()) {
            for (Entry<Entity, CollisionResults> entry: results.entrySet()) {
                staticObstacle.collideWith(entry.getKey().getModel().getWorldBound(), entry.getValue());
            }
        }

        //check whether a collision has taken place.
        //only one object can collide each update, to prevent two objects from taking damage.
        Boolean collided  = false;
        for (Entry<Entity, CollisionResults> entry: results.entrySet()) {
            if (entry.getValue().size() > 0 && !collided) {
                collided = true;
                for (Carrier carrier : platform.getCarriers()) {
                    carrier.takeDamage(1);
                }
                environment.removeDisplayable(obstacleSpawner.removeDamageDealer());
                entry.setValue(new CollisionResults());
                if (entry.getKey().getMoveBehaviour() instanceof EntityMoveBehaviour) {
                    ((EntityMoveBehaviour) entry.getKey().getMoveBehaviour()).collided();
                }

            }
        }

        //reset all CollisionResults.
        for (Entry<Entity, CollisionResults> entry: results.entrySet()) {
            entry.setValue(new CollisionResults());
        }

    }
    
    /**
     * Checks the collision of the carriers with edges of the path.
     * Moves the commander/carriers and platform in the opposite direction of where they were moving
     * in the z direction by a vector the size of their previous speed.
     */
    public void checkPathCollision() {
        for (Carrier carrier : platform.getCarriers()) {
            if (boundingBoxWallLeft.intersects(carrier.getModel().getWorldBound())
                    || boundingBoxWallRight.intersects(carrier.getModel().getWorldBound())) {
                Vector3f antiMoveVector = platform.getMoveBehaviour().getMoveVector().mult(new Vector3f(0, 0, -1.1f));

                commander.move(antiMoveVector);
                platform.move(antiMoveVector);
                for (Carrier carr : platform.getCarriers()) {
                    carr.move(antiMoveVector);
                }
                break;
            }
        }
    }

    /**
     * @return the boundingBoxWallLeft
     */
    public BoundingBox getBoundingBoxWallLeft() {
        return boundingBoxWallLeft;
    }

    /**
     * @return the boundingBoxWallRight
     */
    public BoundingBox getBoundingBoxWallRight() {
        return boundingBoxWallRight;
    }

    /**
     * @return the results
     */
    public HashMap<Entity, CollisionResults> getResults() {
        return results;
    }

    /**
     * @param obstacleSpawner the obstacleSpawner to set
     */
    public void setObstacleSpawner(ObstacleSpawner obstacleSpawner) {
        this.obstacleSpawner = obstacleSpawner;
    }       
    
} 
