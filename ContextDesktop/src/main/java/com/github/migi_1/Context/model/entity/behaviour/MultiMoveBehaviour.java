package com.github.migi_1.Context.model.entity.behaviour;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.github.migi_1.Context.utility.IVectorAggregator;
import com.jme3.math.Vector3f;

/**
 * A MoveBehaviour class for every behaviour that is composed
 * of multiple other behaviours.
 */
public class MultiMoveBehaviour extends MoveBehaviour {

    private IVectorAggregator aggregator;
    private List<MoveBehaviour> behaviours;

    /**
     * Constructor for MultiMoveBehaviour.
     * 
     * @param aggregator
     * 		The aggregator that should aggregate the MoveVectors into one MoveVector.
     * @param behaviours
     * 		The behaviours of which this MultiMoveBehaviour is composed of.
     */
    public MultiMoveBehaviour(IVectorAggregator aggregator, MoveBehaviour... behaviours) {
        this.aggregator = aggregator;
        this.behaviours = Arrays.asList(behaviours);
    }

    @Override
    public void updateMoveVector() {
        Collection<Vector3f> vectors = new LinkedList<>();
        for (MoveBehaviour behaviour : getBehaviours()) {
            behaviour.updateMoveVector();
            vectors.add(behaviour.getMoveVector());
        }    
        setMoveVector(aggregator.aggregate(vectors));
    }	

    /**
     * Gets the list of subbehaviours of this MultiMoveBehaviour.
     * 
     * @return
     * 		The list of subbehaviours of this MultiMoveBehaviour.
     */
    public List<MoveBehaviour> getBehaviours() {
        return behaviours;
    }
}
