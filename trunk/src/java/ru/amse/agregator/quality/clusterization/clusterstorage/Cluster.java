package ru.amse.agregator.quality.clusterization.clusterstorage;

import java.util.ArrayList;

import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
final public class Cluster {

    private ArrayList<UniqueId> objects = new ArrayList<UniqueId>();

    public Cluster() {
    }

    public Cluster(UniqueId obj) {
        objects.add(obj);
    }

    public Cluster(ArrayList<UniqueId> objs) {
        objects.addAll(objs);
    }

    public void addObject(UniqueId obj) {
        objects.add(obj);
    }

    public void addObjects(ArrayList<UniqueId> objs) {
        objects.addAll(objs);
    }

    public ArrayList<UniqueId> getObjectList() {
        return objects;
    }

    static public Cluster mergeClusters(Cluster cluster1, Cluster cluster2) {
        if (cluster1 != cluster2) {
            cluster1.addObjects(cluster2.objects);
        }
        return cluster1;
    }
}
