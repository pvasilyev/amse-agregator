package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.io.IOException;

import org.bson.types.ObjectId;



public class StorageTester {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
    	
   
        Database.connectToMainBase();  
        
        System.out.println(Database.getDBObjectByIdAndType(new ObjectId("4cf59db19f5edb2f5e12070f"), DBWrapper.TYPE_COUNTRY));
        System.out.println(Database.getTopNWithKeyValue(100, DBWrapper.TYPE_ATTRACTION, DBWrapper.FIELD_COUNTRY_ID, new ObjectId("4cf59db19f5edb2f5e12070f")));

        
//        ObjectId nullContinent = Database.getContinentIdByName("");
//        System.out.println(nullContinent);
//        ///*
//        ArrayList<DBWrapper> countries = Database.getAllCountriesByContinent(nullContinent);
//        for(DBWrapper country : countries){
//        	ArrayList<DBWrapper> cities = Database.getAllCitiesByCountry(country.getId());
//        	for(DBWrapper city : cities){
//        		Database.removeAllWithKeyValue(DBWrapper.TYPE_ATTRACTION, DBWrapper.FIELD_CITY_ID, city.getId());
//        	}
//    		Database.removeAllWithKeyValue(DBWrapper.TYPE_CITY, DBWrapper.FIELD_COUNTRY_ID, country.getId());
//
//        }
//		Database.removeAllWithKeyValue(DBWrapper.TYPE_COUNTRY, DBWrapper.FIELD_CONTINENT_ID, nullContinent);
//		Database.removeAllWithKeyValue(DBWrapper.TYPE_CONTINENT, DBWrapper.FIELD_ID, nullContinent);
        // */
        
        
    }
}

//4cf1e7253c65df4ca5b43186