package ru.amse.agregator.indexer;

import java.io.File;
import java.util.Calendar;

/*
 * Author: Bondarev Timofey
 * Date: Nov 4, 2010
 * Time: 8:06:41 PM
 */

public class Main {
    public static void main(String[] args) {

        // todo Разобрать вывод в XML файла-состояния.

        try {
            Calendar time = Calendar.getInstance();
            long beginIndexTime = time.getTimeInMillis();
            Indexer.makeNewIndex(new File("index1"));
            time = Calendar.getInstance();
            long endIndexTime = time.getTimeInMillis();
            long timeForIndexing = endIndexTime - beginIndexTime;
            System.out.println(Indexer.countIndexedFiles + " files indexed by " + timeForIndexing + " milli seconds.");
        } catch (Exception e) {
            System.out.println("Error in function makeNewIndex. Message: " + e.getMessage());            
        }
    }
}
