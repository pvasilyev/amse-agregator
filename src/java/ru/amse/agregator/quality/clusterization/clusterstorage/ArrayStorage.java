package ru.amse.agregator.quality.clusterization.clusterstorage;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author pavel
 */
final public class ArrayStorage extends ClusterStorage {

    public ArrayList<Cluster> clusters = new ArrayList<Cluster>();
    private Iterator iterator = null;

    public void addCluster(Cluster cluster) {
        clusters.add(cluster);
    }

    public void startIterating() {
        iterator = clusters.iterator();
    }

    public Cluster getNextCluster() {
        return (Cluster)iterator.next();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public int getClusterCount() {
        return clusters.size();
    }
}
