package ru.amse.agregator.quality.clusterization.declusterization;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;

/**
 *
 * @author pavel
 */
final public class SimpleDeclusterizer extends Declusterizer {

    public boolean isCorrect(ClusterStorage.Cluster cluster) {
        return true;
    }

}
