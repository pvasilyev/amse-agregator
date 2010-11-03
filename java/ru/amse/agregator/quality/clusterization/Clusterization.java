package ru.amse.agregator.quality.clusterization;


import java.util.ArrayList;
import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DataBase;
import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.*;
import ru.amse.agregator.quality.clusterization.metric.*;
import ru.amse.agregator.quality.clusterization.merge.*;
import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 */
final public class Clusterization {

    static public void perform() {
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

        System.out.println("Resulting graph has "
                + String.valueOf(similatityGraph.getEdgeCount()) + " edges");

        System.out.println("Performing clusterization process");

        clusterizer.clusterize(allCities, similatityGraph, storage);

        System.out.println("Checking consistency");
        storage.startIterating();
        while (storage.hasNext()) {
            ClusterStorage.Cluster cl = storage.getNextCluster();
            if (cl == null) {
                System.out.println("fail");
            }
            if (cl.getObjectList() == null) {
                System.out.println("fail");
            }
            if (cl.getObjectList().isEmpty()) {
                System.out.println("fail");
            }
            if (cl.getObjectList().get(0) == null) {
                System.out.println("fail");
            }
            DataBase.getDBObjectById(cl.getObjectList().get(0));
        }

//        DataBase.connectToMainBase();
//        DataBase.printAll();
//        DataBase.connectToDirtyBase();
//        DataBase.printAll();

        System.out.println("Clusterisation process created " 
                + String.valueOf(storage.getClusterCount()) + " clusters out of "
                + String.valueOf(allCities.size()) + " objects");

        System.out.println("Merging and adding to main base");

        MergeProcess.perform(new SimpleMerger(), storage);

        System.out.println("Finished clusterization process");

    }

    public static void main(String[] args) {
        perform();
    }
}
