package ru.amse.agregator.clusterizer;

import java.util.ArrayList;
import java.util.TreeMap;
import ru.amse.agregator.storage.DataBase;
import ru.amse.agregator.storage.City;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author pavel
 */
public class Clusterizer {

    public static void clusterize() {

        try {
            DataBase.connectToDirtyBase();
        } catch (Exception e) {
            System.out.println("Error connecting to db");
        }

        ArrayList<City> allCities = DataBase.getAllCities();

        TreeMap<String, ArrayList<City>> clusterMap
                = new TreeMap<String, ArrayList<City>>();

        //form clusters
        //each cluster corresponds to exactly one name
        for (City city : allCities) {
            ArrayList<City> cluster = clusterMap.get(city.getName());
            if (cluster == null) {
                cluster = new ArrayList<City>();
            }
            cluster.add(city);
            clusterMap.put(city.getName(), cluster);
        }

        
        try {
            DataBase.connectToMainBase();
        } catch (Exception e) {
            System.out.println("Error connecting to db");
        }

        //iterate through map and the first city from each cluster
        for (Map.Entry<String, ArrayList<City>> e : clusterMap.entrySet()) {
            DataBase.add(e.getValue().get(0));
        }

    }

}
