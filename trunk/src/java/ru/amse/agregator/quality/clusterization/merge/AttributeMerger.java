package ru.amse.agregator.quality.clusterization.merge;

import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 *
 * Abstract class that merges a
 */
abstract public class AttributeMerger {

    abstract public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject);
}
