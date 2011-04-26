package ru.amse.agregator.ranking;

import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: nepank
 * Date: 23.03.11
 * Time: 13:23
 */

public class CategoriesTest {
    public void test() {
        Database.connectToDirtyBase();
        DBWrapper redFort = Database.getDBObjectByIdAndType(new ObjectId("4db5f37b2420b0864ee35ec0"), DBWrapper.TYPE_ARCH_ATTRACTION);
        System.out.println(redFort.getKeyWordsArray());
        System.out.println("redFort in ATTRACTION category : " + redFort.isInCategory(Categories.ATTRACTION));
        System.out.println("redFort in BUILDING category : " + redFort.isInCategory(Categories.BUILDING));
        System.out.println("redFort in BRIDGE category : " + redFort.isInCategory(Categories.BRIDGE));

//        final ArrayList<String> tops = Database.getTopNCategoriesInContinent(10, new ObjectId("4db5f3742420b08648e35ec0"));
//        for (String top : tops) {
//            System.out.println(top);
//        }

        final ArrayList<String> tops = Database.getTopNCategories(10);
        for (String top : tops) {
            System.out.println(top);
        }
//        final ArrayList<DBWrapper> allCities = Database.getAllCitiesByCountry(new ObjectId("4db5f4a72420b08697e45ec0"));
//        Database.getAllDBObjectsWithKeyValue(DBWrapper.FIELD_COUNTRY_ID, "4db5f4a72420b08697e45ec0");
//        System.out.println(Database.getAllDBObjectsWithKeyValue(DBWrapper.FIELD_COUNTRY_ID, "4db5f4a72420b08697e45ec0").get(0).getName());
    }

    public static void main(String[] args) throws IOException {
        CategoriesTest test = new CategoriesTest();
        test.test();
    }
}
