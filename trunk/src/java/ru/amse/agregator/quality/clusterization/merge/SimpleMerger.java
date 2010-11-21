package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import java.util.HashSet;

/**
 *
 * @author pavel
 */
public class SimpleMerger extends ClusterMerger {

    @Override
    public DBWrapper mergeCluster(Cluster cluster) {
        return Database.getByUniqueId(cluster.getObjectList().get(0));
    }
}
