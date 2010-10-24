package ru.amse.bondarev;


import ru.amse.agregator.Searcher.Searcher;
import ru.amse.agregator.indexer.Indexer;

import java.io.File;

/*
 * Author: Bondarev Timofey
 * Date: Oct 20, 2010
 * Time: 1:22:03 AM
 */

public class Main {
    public static void main(String[] args) {
        File indexDir = new File("index");
        File dataDir = new File("data");

        cleanDirectory(indexDir);

        Indexer.makeIndex(indexDir, dataDir);

        Searcher.search(indexDir, "uses");// "ведь достойной");        

        System.out.println("Done!");
    }

    private static void cleanDirectory(File indexDir) {
        File[] files = indexDir.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (!files[i].isDirectory()) {
                files[i].delete();
            } else {
                cleanDirectory(files[i]);
                files[i].delete();
            }
        }
    }
}

