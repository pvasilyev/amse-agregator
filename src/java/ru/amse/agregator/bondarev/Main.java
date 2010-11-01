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
        writeDateToDB();
        DataBase.printAll();
        try {
            Indexer.makeNewIndex(indexDir);
            Searcher.setIndexDir(indexDir);
            Searcher.search(new UserQuery("Колизей"));
        } catch (IOException e) {
            System.out.println("IOException! Message: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ParseException! Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception! Message: " + e.getMessage());
        }

        System.out.println("Done!");
    }

    private static void writeDateToDB() {
        DataBase.removeCollection(DataBase.COLLECTION_MAIN);
              
        DBWrapper someCity = new DBWrapper();

        someCity.setType(DBWrapper.TYPE_CITY);
        someCity.setName("Рим");
        someCity.setPhoto("images/rim.jpg");
        someCity.setCoordinates(new Point2D.Double(1220.32432,10.234324));
        someCity.setDescription("Описание Рима");

        ArrayList<String> stringsArray = new ArrayList<String>();
        stringsArray.add("beautiful");
        stringsArray.add("city");
        stringsArray.add("rim");
        stringsArray.add("Рим");
        someCity.setKeyWordsArray(stringsArray);

        DataBase.add(someCity);

        DBWrapper someArchAttr = new DBWrapper();
        someArchAttr.setType(DBWrapper.TYPE_ARCH_ATTRACTION);
        someArchAttr.setName("Колизей");

        ArrayList<String> photosArray = new ArrayList<String>();
        photosArray.add("images/kolizey_im1.jpg");
        photosArray.add("images/kolizey_im2.jpg");
        photosArray.add("images/kolizey_im3.jpg");
        photosArray.add("images/kolizey_im4.jpg");
        someArchAttr.setPhotosArray(photosArray);
        
        someArchAttr.setCityById(someCity.getId());
        someArchAttr.setCoordinates(new Point2D.Double(10.324234,1022.24234));
        someArchAttr.setDescription("Kolizey description");

        ArrayList<String> keyWordsArray = new ArrayList<String>();
        keyWordsArray.add("rim");
        keyWordsArray.add("kolizey");
        keyWordsArray.add("keyword3");
        someArchAttr.setKeyWordsArray(keyWordsArray);

        someArchAttr.setAddress("Kolizey Street, 55");
        someArchAttr.setArchitect("Kirill K.");
        someArchAttr.setCost("100 $");
        someArchAttr.setDate(new Date(1990,7,8));

        DataBase.add(someArchAttr);
    }
}

