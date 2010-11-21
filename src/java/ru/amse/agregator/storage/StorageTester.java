package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.awt.geom.Point2D;
import java.io.IOException;

import org.bson.types.ObjectId;

public class StorageTester {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
    	
   
        DataBase.connectToDirtyBase();  

        ArrayList<DBWrapper> continents = DataBase.getAllContinents();
        for(DBWrapper continent : continents){
        	System.out.println(continent.getName() + " айдишник: " + continent.getId());
        	//вывели список континентов с сылочками в которых хранится айдишник.
        }
        
        //выводим список стран первого континента
        DBWrapper continentPerviy = continents.get(0);
       System.out.println(continentPerviy.getId());
        ArrayList<DBWrapper> countries = DataBase.getAllCountriesByContinent(continentPerviy.getId());
        for(DBWrapper country : countries){
        	System.out.println(country.getName() + " айдишник: " + country.getId());
        	//вывели список стран первого континента с сылочками в которых хранится айдишник.
        }
        
//    	DBWrapper wr = new DBWrapper();
//    	wr.setType(DBWrapper.TYPE_CONTINENT);
//    	wr.setName("Австралия и Океания");
//    	DataBase.add(wr);
//    	wr.setName("Азия");
//    	wr.removeId();
//    	DataBase.add(wr);
//    	wr.setName("Африка");
//    	wr.removeId();
//    	DataBase.add(wr);
//    	wr.setName("Европа");
//    	wr.removeId();
//    	DataBase.add(wr);
//    	wr.setName("Северная Америка");
//    	wr.removeId();
//    	DataBase.add(wr);
//    	wr.setName("Южная Америка");
//    	wr.removeId();
//    	DataBase.add(wr);
        
        
        //System.out.println(DataBase.getAllContinents());
        //System.out.println(DataBase.getAllCountriesByContinent(continentId)());
        //System.out.println(DataBase.getAllObjectOfSelectedTypeInCity(new ObjectId("4ce1c0ae078edf4cf9b87cfc"), DBWrapper.TYPE_MUSEUM));
        
        
        //DataBase.printAll();
        //System.out.println(DataBase.getAllTypesOfObjectByCity(new ObjectId("4ce1c096078edf4cd5b87cfc")));
    }
}

//4ce1bf9b078edf4c5bb77cfc