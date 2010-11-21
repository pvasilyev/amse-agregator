package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.DataBase;

/**
 *
 * @author pavel
 */
public class SimpleMerger extends Merger {

    @Override
    public DBWrapper merge(Cluster cluster) {
        return DataBase.getAttractionById(cluster.getObjectList().get(0));
    }
}
