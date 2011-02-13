package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.storage.DBWrapper;

import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
final public class MergeProcess {

    static public void perform(ClusterMerger merger, ClusterStorage storage) {

        merger.preprocess(storage);

        storage.startIterating();
        while (storage.hasNext()) {
            //use merging algorithm to create single object out of cluster
            Database.connectToDirtyBase();
            Cluster cluster = storage.getNextCluster();

            //logging an error
            if (cluster.size() == 0) {
                //not critical
                System.out.println("Encountered empty cluster while merging");
                continue;
            }

            DBWrapper obj = merger.mergeCluster(cluster);
            if(obj.getName() != null && !obj.getName().isEmpty()){
                // add or update an object
                UniqueId objectToUpdateId = cluster.objectFromMainDB();
                if (objectToUpdateId != null) {
                    // update the correct object
                    Database.connectToMainBase();
                    obj.setId(objectToUpdateId.getId());
                    Database.update(obj);
                } else {
                    // add a new object
                    Database.connectToMainBase();
                    Database.add(obj);
                }
            }
            

        }
        storage.finishIterating();

        merger.postprocess(storage);
    }
}
