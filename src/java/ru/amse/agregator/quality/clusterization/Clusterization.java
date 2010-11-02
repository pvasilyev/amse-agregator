package ru.amse.agregator.quality.clusterization;


import java.util.ArrayList;
import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DataBase;
import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.*;
import ru.amse.agregator.quality.clusterization.metric.*;
import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 */
final public class Clusterization {

    static public void performClusterization() {
        Clusterizer clusterizer = new PartitionClusterizer();
        Metric metric = new CityMetric();
        Graph similatityGraph = new ArrayGraph(metric, 0.0);
        ClusterStorage storage = new ArrayStorage();

        System.out.println("Deleting main base");
        DataBase.connectToMainBase();
        DataBase.removeCollection(DataBase.COLLECTION_MAIN);

        System.out.println("Retrieving dirty base");
        DataBase.connectToDirtyBase();

        ArrayList<ObjectId> allCities = DataBase.getAllIdByType(DBWrapper.TYPE_CITY);

        System.out.println("Building similarity graph");
        similatityGraph.build(allCities);

        System.out.println("Resulting graph has " + String.valueOf(similatityGraph.getEdgeCount()) + " edges");

        System.out.println("Performing clusterization process");
        clusterizer.clusterize(allCities, similatityGraph, storage);
//        DataBase.connectToMainBase();
//        DataBase.printAll();
//        DataBase.connectToDirtyBase();
//        DataBase.printAll();

        System.out.println("Clusterisation process created " + String.valueOf(storage.getClusterCount()) + " clusters out of " + String.valueOf(allCities.size()) + " objects");

    }

    public static void main(String[] args) {
        performClusterization();
    }
}
