package ru.amse.agregator.quality.clusterization.clusterstorage;

import java.util.ArrayList;

import org.bson.types.ObjectId;

/**
 *
 * @author pavel
 */
abstract public class ClusterStorage {

    static public class Cluster {

        private ArrayList<ObjectId> objects = new ArrayList<ObjectId>();

        public Cluster() {
        }

        public Cluster(ObjectId obj) {
            objects.add(obj);
        }

        public Cluster(ArrayList<ObjectId> objs) {
            objects.addAll(objs);
        }

        public void addObject(ObjectId obj) {
            objects.add(obj);
        }

        public void addObjects(ArrayList<ObjectId> objs) {
            objects.addAll(objs);
        }

        public ArrayList<ObjectId> getObjectList() {
            return objects;
        }

        static public Cluster mergeClusters(Cluster cluster1, Cluster cluster2) {
            if (cluster1 != cluster2) {
                cluster1.addObjects(cluster2.objects);
            }
            return cluster1;
        }
    }

    abstract public void addCluster(Cluster cluster);

    abstract public void startIterating();

    abstract public boolean hasNext();

    abstract public Cluster getNextCluster();

    abstract public int getClusterCount();
}
