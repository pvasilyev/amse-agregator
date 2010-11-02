package ru.amse.agregator.quality.clusterization.declusterization;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;

/**
 *
 * @author pavel
 */
final public class Declusterization {

    static public ArrayList<ObjectId> perform(Declusterizer declusterizer, ClusterStorage storage) {
        //@TODO work on the declusterizer

        storage.startIterating();
        while (storage.hasNext()) {
            ClusterStorage.Cluster cluster = storage.getNextCluster();
            if (!declusterizer.isCorrect(cluster)) {
                //remove cluster from storage, divide the cluster and add objects to the resulting arraylist
            }
        }

        return null;
    }

}
