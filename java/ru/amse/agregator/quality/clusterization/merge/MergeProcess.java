package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;

import ru.amse.agregator.storage.DataBase;

/**
 *
 * @author pavel
 */
final public class MergeProcess {

    static public void perform(Merger merger, ClusterStorage storage) {

        DataBase.connectToMainBase();

        storage.startIterating();
        while (storage.hasNext()) {
           merger.merge(storage.getNextCluster());

        }
    }

}
