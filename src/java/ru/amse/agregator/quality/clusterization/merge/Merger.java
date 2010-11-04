package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 */
abstract public class Merger {

    abstract public DBWrapper merge(ClusterStorage.Cluster cluster);

}
