package ru.amse.agregator.bondarev;

import ru.amse.agregator.indexer.Indexer;
import ru.amse.agregator.searcher.*;
import ru.amse.agregator.storage.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Oct 20, 2010
 * Time: 1:22:03 AM
 */

public class Main {
    public static void main(String[] args) {
        try {
            Indexer.makeNewIndex(new File("index"));
        } catch (IOException e) {
            System.out.println("Error.");
        }

        Searcher.setIndexDir(new File("index1"));
        ArrayList<DBWrapper> list = Searcher.search(new UserQuery("Кализей~"));
        for (DBWrapper currentObject : list) {
            System.out.println(currentObject.getName());
            System.out.println(currentObject.getDescription());
        }
        System.out.println("Done!");
    }
}

