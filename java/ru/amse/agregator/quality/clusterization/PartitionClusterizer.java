package ru.amse.agregator.quality.clusterization;


import ru.amse.agregator.quality.clusterization.clusterstorage.ClusterStorage;
import ru.amse.agregator.quality.clusterization.simgraph.Graph;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashSet;

import org.bson.types.ObjectId;

/**
 *
 * @author pavel
 */
public class PartitionClusterizer extends Clusterizer {

    //simple implementation of partition algorithm
    public void clusterize(ArrayList<ObjectId> objects,
            Graph similarityGraph, ClusterStorage clusterStorage) {

        Map<ObjectId, ClusterStorage.Cluster> clusterMap
                = new TreeMap<ObjectId, ClusterStorage.Cluster>();

        //init map with single element clusters
        for (ObjectId obj : objects) {
            clusterMap.put(obj, new ClusterStorage.Cluster(obj));
        }

        //iterate through the edges of graph
        similarityGraph.startIterating();
        while (similarityGraph.hasNext()) {
            Graph.Edge e = similarityGraph.getNextEdge();

            //get objects
            ObjectId obj1 = e.obj1;
            ObjectId obj2 = e.obj2;

            //retrieve corresponding cluster from map
            ClusterStorage.Cluster cluster1 = clusterMap.get(obj1);
            ClusterStorage.Cluster cluster2 = clusterMap.get(obj2);

            //merge clusters
            ClusterStorage.Cluster mergedCluster
                    = ClusterStorage.Cluster.mergeClusters(cluster1, cluster2);
            clusterMap.put(obj1, mergedCluster);
            clusterMap.put(obj2, mergedCluster);
        }

        //eleminate duplicate and get unique clusters from our map
        Set<ClusterStorage.Cluster> uniqueClusters
                = new HashSet<ClusterStorage.Cluster>(clusterMap.values());

        // add resulting clusters to storage
        for (ClusterStorage.Cluster cluster : uniqueClusters) {
            clusterStorage.addCluster(cluster);
        }

    }
}
