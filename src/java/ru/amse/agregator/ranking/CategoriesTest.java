package ru.amse.agregator.ranking;

import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.io.IOException;

/**
 * User: nepank
 * Date: 23.03.11
 * Time: 13:23
 */

public class CategoriesTest {
    public void test() {
        Database.connectToDirtyBase();
        DBWrapper redFort = Database.getDBObjectByIdAndType(new ObjectId("4d9835b0bc76b0864869d2b5"), DBWrapper.TYPE_ATTRACTION);
        System.out.println(redFort.getKeyWordsArray());
        System.out.println("redFort in ATTRACTION category : " + redFort.isInCategory(Categories.ATTRACTION));
        System.out.println("redFort in BUILDING category : " + redFort.isInCategory(Categories.BUILDING));
        System.out.println("redFort in BRIDGE category : " + redFort.isInCategory(Categories.BRIDGE));
    }

    public static void main(String[] args) throws IOException {
        CategoriesTest test = new CategoriesTest();
        test.test();
    }
}
