package ru.amse.agregator.quality.clusterization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.quality.clusterization.clusterstorage.*;
import ru.amse.agregator.quality.clusterization.simgraph.*;
import ru.amse.agregator.quality.clusterization.metric.*;
import ru.amse.agregator.quality.clusterization.merge.*;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.quality.clusterization.merge.Fingerprint;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
public class ClusterizationHelper {

    static public void main(String[] args) throws Exception {
        Database.connectToDirtyBase();
        String fileName = "myDescriptions.txt";
        BufferedWriter outputStream = null;
        try {
            outputStream =
                new BufferedWriter(new FileWriter(fileName));

            ArrayList<DBWrapper> cities = Database.getAllWithType(DBWrapper.TYPE_CONTINENT);
            for (DBWrapper city : cities) {
                List<String> descriptions = city.getDescriptionArray();
                for (String description : descriptions) {
                    outputStream.write(description);
                }

            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

}
