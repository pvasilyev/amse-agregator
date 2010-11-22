package ru.amse.agregator.quality.clusterization;

import java.util.ArrayList;

import ru.amse.agregator.storage.Database;
import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.*;
import ru.amse.agregator.quality.clusterization.metric.*;
import ru.amse.agregator.quality.clusterization.merge.*;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 *
 */
public class ClusterizationTester {


    public static void Test() {
        double threshold = 0.0;

        System.out.println("Deleting main base");

//        Database.connectToMainBase();
//        Database.removeAllCollections();

        ArrayList<String> types = DBWrapper.getTypeNames();

        String type = DBWrapper.TYPE_ARCH_ATTRACTION;

        Clusterizer clusterizer = new PartitionClusterizer();
        Metric metric = new NameMetric();
        Graph similatityGraph = new ArrayGraph(metric, threshold);
        ClusterStorage storage = new ArrayStorage();

        System.out.println("**************\n\nProcessing " + type + "s");
        System.out.println("Retrieving data from dirty base");

        Database.connectToDirtyBase();

        ArrayList<DBWrapper> allOfType = Database.
                getAllWithType(type);
        ClusterizationProcess.setUniqueIdsForObjects(allOfType, Database.DIRTY_DB_NAME);

        System.out.println("There are " + allOfType.size() + " " + type + "s in dirty base");

        System.out.println("Building similarity graph\nObjects processed:");
        similatityGraph.build(allOfType);

        System.out.println(String.valueOf(allOfType.size()));

        System.out.println("Resulting graph has "
                + String.valueOf(similatityGraph.getEdgeCount()) + " edges");

        System.out.println("Performing clusterization process");

        clusterizer.clusterize(allOfType, similatityGraph, storage);
        System.out.println("Printing non single object clusters:");

        storage.startIterating();
        while (storage.hasNext()) {
            Cluster cluster = storage.getNextCluster();
            if (cluster.size() > 1) {
                ClusterMerger merger = new ObjectMerger();
                System.out.println("Cluster");
                cluster.print();
                System.out.println("Merged object:");

                DBWrapper resultingObject = merger.mergeCluster(cluster);
                System.out.println(resultingObject);
            }
        }
        storage.finishIterating();

        System.out.println(String.valueOf(allOfType.size()));

        System.out.println("Clusterisation process created "
                + String.valueOf(storage.getClusterCount()) + " clusters out of "
                + String.valueOf(allOfType.size()) + " objects");

        System.out.println("Merging and adding to main base");

        MergeProcess.perform(new ObjectMerger(), storage);

        System.out.println("Finished clusterization process successfully");
    }

    public static void main(String[] args) {
        Test();
    }

}
