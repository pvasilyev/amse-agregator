package ru.amse.agregator.bondarev;

import ru.amse.agregator.searcher.*;
import ru.amse.agregator.indexer.*;

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

        Indexer.makeIndex(indexDir, dataDir);
        Searcher.search(new UserQuery("uses"));

        System.out.println("Done!");
    }

   
}

