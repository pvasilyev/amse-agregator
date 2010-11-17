package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.awt.geom.Point2D;
import java.io.IOException;
public class StorageTester {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        DataBase.connectToDirtyBase();  
        
        ArrayList<DBWrapper> list = DataBase.getAllWithKeyValue(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_CITY);
        
        for(DBWrapper item : list){
        	System.out.println(item.getKeySetWithoutNull());
        	System.out.println(item.getMapWithoutNull());
        	System.in.read();
        }
        
        //System.out.println(DataBase.getAllDBObjects().size());
        //DataBase.printAll();
        //DataBase.removeCollection(DataBase.COLLECTION_MAIN);
    }
}
