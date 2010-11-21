package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.storage.DBWrapper;

import ru.amse.agregator.storage.Database;

/**
 *
 * @author pavel
 */
final public class MergeProcess {

    static public void perform(ClusterMerger merger, ClusterStorage storage) {

        storage.startIterating();
        while (storage.hasNext()) {
            //use merging algorithm to create single object out of cluster
            Database.connectToDirtyBase();
            Cluster cluster = storage.getNextCluster();

            //logging error
            if (cluster.getObjectList().isEmpty()) {
                System.err.println("Encountered empty cluster while merging");
                continue;
            }

            DBWrapper obj = merger.mergeCluster(cluster);
            //put it in the storage
            Database.connectToMainBase();
            Database.add(obj);
        }
    }
}
