package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.DataBase;

/**
 *
 * @author pavel
 */
public class SimpleMerger extends Merger {

    public DBWrapper merge(Cluster cluster) {
        if (cluster.getObjectList().get(0) != null)  {
            return DataBase.getDBObjectById(cluster.getObjectList().get(0));
        } else {
            return null;
        }
    }

}
