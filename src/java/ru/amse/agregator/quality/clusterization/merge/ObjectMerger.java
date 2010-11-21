package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.HashSet;
import ru.amse.agregator.storage.DBWrapper;

import java.util.Map;
import java.util.TreeMap;
import ru.amse.agregator.storage.UniqueId;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.Database;


/**
 *
 * @author pavel
 */
public class ObjectMerger extends ClusterMerger {

    final private Map<String, AttributeMerger> attMergers;
    final private AttributeMerger defaultMerger;

    public ObjectMerger() {

        defaultMerger = new StringMerger();

        attMergers = new TreeMap<String, AttributeMerger>();

        attMergers.put(DBWrapper.FIELD_KEYWORDS, new StringListMerger());
        attMergers.put(DBWrapper.FIELD_PHOTOS, new StringListMerger());
        attMergers.put(DBWrapper.FIELD_UNIQUE_ID, new DoNothingMerger());
        attMergers.put(DBWrapper.FIELD_ID, new DoNothingMerger());
        attMergers.put(DBWrapper.FIELD_CITY_ID, new DoNothingMerger());
        attMergers.put(DBWrapper.FIELD_COUNTRY_ID, new DoNothingMerger());
        attMergers.put(DBWrapper.FIELD_CONTINENT_ID, new DoNothingMerger());
        attMergers.put(DBWrapper.FIELD_COORDS, new DoNothingMerger());
    }


    @Override
    public DBWrapper mergeCluster(Cluster cluster) {
        DBWrapper resultingObject = new DBWrapper();

        // retrieve a list of attributeNames that are possesed
        // by at least one object of the cluster
        ArrayList<String> attributeNames = getAttributeNames(cluster);

        //merge all attributes using corresponding attribute mergers
        for (String attributeName : attributeNames) {
            AttributeMerger merger = attMergers.get(attributeName);
            if (merger == null) {
                merger = defaultMerger;
            }
            merger.mergeAttributes(attributeName, cluster, resultingObject);
        }

        return resultingObject;
        
    }

    ArrayList<String> getAttributeNames(Cluster cluster) {
        // hash set is used to eliminate duplicates
        HashSet<String> attributeNames = new HashSet<String>();

        for (UniqueId id : cluster.getObjectList()) {
            DBWrapper obj =  Database.getByUniqueId(id);
            attributeNames.addAll(obj.getKeySet());
        }

        return new ArrayList<String>(attributeNames);
    }

}
