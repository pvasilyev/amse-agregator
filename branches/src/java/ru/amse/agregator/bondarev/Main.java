package ru.amse.agregator.bondarev;

import org.apache.lucene.queryParser.ParseException;
import ru.amse.agregator.searcher.*;
import ru.amse.agregator.indexer.*;
import ru.amse.agregator.storage.*;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/*
 * Author: Bondarev Timofey
 * Date: Oct 20, 2010
 * Time: 1:22:03 AM
 */

public class Main {
    public static void main(String[] args) {
        File indexDir = new File("index");

        DataBase.connectToDirtyBase();
        //DataBase.printAll();
        try {
            Indexer.makeNewIndex(indexDir);
        } catch (IOException e) {
            System.out.println("IOException! Message: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ParseException! Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception! Message: " + e.getMessage());
        }

        System.out.println("Done!");
    }
}

