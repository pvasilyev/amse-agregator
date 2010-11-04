package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.storage.DBWrapper;

import ru.amse.agregator.storage.DataBase;

/**
 *
 * @author pavel
 */
final public class MergeProcess {

    static public void perform(Merger merger, ClusterStorage storage) {

        storage.startIterating();
        while (storage.hasNext()) {
            //use merging algorithm to create single object out of cluster
            DataBase.connectToDirtyBase();
            DBWrapper obj = merger.merge(storage.getNextCluster());
            //put it in the storage
            DataBase.connectToMainBase();
            DataBase.add(obj);
        }
    }
}
