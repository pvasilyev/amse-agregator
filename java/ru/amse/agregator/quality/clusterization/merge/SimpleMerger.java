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
        
        //return DataBase.getDBObjectById(cluster.getObjectList().get(0));
        return null;

    }



}
