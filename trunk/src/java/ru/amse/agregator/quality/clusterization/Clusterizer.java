package ru.amse.agregator.quality.clusterization;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.quality.clusterization.simgraph.Graph;

import java.util.ArrayList;

import org.bson.types.ObjectId;

/**
 *
 * @author pavel
 */
abstract public class Clusterizer {

    //abstract algorithm that takes similarity graph and forms the list of resulting clusters
    abstract public void clusterize(ArrayList<ObjectId> objects,
            Graph similarityGraph, ClusterStorage clusterStorage);
}
