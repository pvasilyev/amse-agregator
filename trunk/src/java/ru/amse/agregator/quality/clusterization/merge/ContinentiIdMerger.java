package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
public class ContinentiIdMerger extends AttributeMerger {

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
        //
        String continentName = null;
        for (UniqueId id : cluster.getObjectList())
        {
            DBWrapper obj = Database.getByUniqueId(id);
            continentName = obj.getContinentNameFromDB();
            if (continentName != null) {
                break;
            }
        } 

        assert(continentName != null);
        // @todo do some logging
//        for (UniqueId id : cluster.getObjectList()) {
//            if ()
//            DBWrapper obj = Database.getByUniqueId(id);
//
//        }

        //@todo care about switching databases here, may cause errors
        Database.connectToMainBase();
        resultingObject.setContinentByName(continentName);
    }

}
