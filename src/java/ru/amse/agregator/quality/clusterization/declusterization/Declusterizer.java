package ru.amse.agregator.quality.clusterization.declusterization;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;

/**
 *
 * @author pavel
 *
 * Prototype for declusterization function.
 * Tells whether the cluster should be divided or not.
 * Input: cluster. Output: objects in
 */
abstract public class Declusterizer {

    abstract public boolean isCorrect(ClusterStorage.Cluster cluster);

}
