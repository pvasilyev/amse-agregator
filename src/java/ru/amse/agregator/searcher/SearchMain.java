package ru.amse.agregator.searcher;

import ru.amse.agregator.storage.DBWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

/*
 * Author: Bondarev Timofey
 * Date: Nov 15, 2010
 * Time: 9:37:48 PM
 */

public class SearchMain {
    public static void main(String[] args) {
        Calendar begin = Calendar.getInstance();
        Searcher.setIndexDir(new File("index"));
        long timeForSearch = Calendar.getInstance().getTimeInMillis() - begin.getTimeInMillis();
        ArrayList<DBWrapper> output = Searcher.search(new UserQuery("остров"));
        if (output == null) {
            System.out.println("Ничего не найдено");
            return;
        }
        for (DBWrapper currentObject: output) {
            System.out.println(currentObject.getName());
            System.out.println(currentObject.getDescription());
        }

        System.out.println("Searching by " + timeForSearch + " millis") ;
        test();
    }

    private static void test() {
        Vector<String> ls = new Vector<String>();
        ls.add("0");
        ls.add("1");

        UserQuery q = new UserQuery("1",ls);
        System.out.println(q.getLabels().toString());
    }
}
