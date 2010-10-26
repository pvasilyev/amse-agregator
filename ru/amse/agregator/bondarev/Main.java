package ru.amse.agregator.bondarev;

import org.apache.lucene.queryParser.ParseException;
import ru.amse.agregator.searcher.*;
import ru.amse.agregator.indexer.*;
import ru.amse.agregator.storage.*;

import javax.xml.crypto.Data;
import java.awt.geom.Point2D;
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
        File indexDir = new File("index");
        //File dataDir = new File("data");

        //writeDataToDB();
        //DataBase.printAll();
        DataBase.connectToDirtyBase();
        try {
            Indexer.makeNewIndex(indexDir);
            Searcher.setIndexDir(indexDir);
            Searcher.search(new UserQuery("test"));
        } catch (IOException e) {
            System.out.println("IOException! Message: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ParseException! Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception! Message: " + e.getMessage());
        }

        System.out.println("Done!");
    }

    private static void writeDataToDB() {
        DataBase.connectToDirtyBase();

        ArchitectualAttraction x1 = new ArchitectualAttraction();
        x1.setName("korgov attraction");
        x1.setPhoto("img.jpg");
        x1.setCityByName("kllandia");
        x1.setCoordinates(new Point2D.Double(1220.32432, 10.234324));
        x1.setDescription("Who I am?");
        x1.setKeyWordsArray(new ArrayList<String>());
        x1.setAddress("kl address");
        x1.setArchitect("korgov a.a.");
        x1.setCost("100 $");
        //x1.setDate(new Date(1990,87,8));
        x1.setType("ìóçåé");

        DataBase.add(x1);
        NaturalAttraction x2 = new NaturalAttraction();
        x2.setName("natural attraction");
        x2.setPhoto("img.jpg");
        x2.setCityByName("kllandia");
        x2.setCoordinates(new Point2D.Double(1220.32432, 10.234324));
        x2.setDescription("Who I am?");
        x2.setKeyWordsArray(new ArrayList<String>());
        DataBase.add(x2);

        City x3 = new City();
        x3.setName("korgov city");
        x3.setPhoto("imgcity.jpg");
        x3.setCoordinates(new Point2D.Double(1220.32432, 10.234324));
        x3.setDescription("Who I am city?");
        x3.setKeyWordsArray(new ArrayList<String>());
        DataBase.add(x3);


        Cafe x4 = new Cafe();
        x4.setName("korgov cafe");
        x4.setPhoto("imgcitycafe.jpg");
        x4.setCoordinates(new Point2D.Double(1220.32432, 10.234324));
        x4.setDescription("Who I am cafe?");
        x4.setKeyWordsArray(new ArrayList<String>());
        x4.setCityByName("new new city");
        x4.setWebsite("www.korgov.ru");
        DataBase.add(x4);

        Hotel x6 = new Hotel();
        x6.setName("korgov hotel");
        x6.setPhoto("imghotel.jpg");
        x6.setCoordinates(new Point2D.Double(1220.32432, 10.234324));
        x6.setDescription("Who I am hotel?");
        x6.setKeyWordsArray(new ArrayList<String>());
        x6.setCityByName("super-city");
        x6.setWebsite("www.korgov.ru");
        x6.setRooms("super-luks");
        DataBase.add(x6);
    }
}

