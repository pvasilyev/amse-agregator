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
        //Database.connectToMainBase();
        //Database.unificationNames();
        Database.connectToDirtyBase();

        System.out.println(Database.getDBObjectByIdAndType(new ObjectId("4d98356bbc76b0863969d2b5"), DBWrapper.TYPE_ATTRACTION));
    }

    public static void main(String[] args) throws IOException {
        CategoriesTest test = new CategoriesTest();
        test.test();
    }
}
