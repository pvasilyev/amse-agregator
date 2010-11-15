package ru.amse.agregator.searcher;

import ru.amse.agregator.storage.DBWrapper;

import java.io.File;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Nov 15, 2010
 * Time: 9:37:48 PM
 */

public class MainClassForSearcher {
    public static void main(String[] args) {
        Searcher.setIndexDir(new File("index"));
        ArrayList<DBWrapper> output = Searcher.search(new UserQuery("Рйм~"));
        for (DBWrapper currentObject: output) {
            System.out.println(currentObject.getName());
            System.out.println(currentObject.getDescription());
        }
    }
}
