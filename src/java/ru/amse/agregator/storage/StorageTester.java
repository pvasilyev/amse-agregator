package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.awt.geom.Point2D;
public class StorageTester {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DataBase.connectToDirtyBase();  
        
        System.out.println(DataBase.getAllDBObjects().size());
        //DataBase.printAll();
        //DataBase.removeCollection(DataBase.COLLECTION_MAIN);
    }
}
