package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.io.IOException;



public class StorageTester {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
    	
   
        Database.connectToDirtyBase();

        ArrayList<DBWrapper> continents = Database.getAllContinents();
        for(DBWrapper continent : continents){
        	System.out.println(continent.getName() + " айдишник континента: " + continent.getId());
        	//вывели список континентов с сылочками в которых хранится айдишник.
        }
        
        //выводим список стран первого континента
        DBWrapper continentPerviy = continents.get(2);
       System.out.println(continentPerviy.getId());
        ArrayList<DBWrapper> countries = Database.getAllCountriesByContinent(continentPerviy.getId());
        for(DBWrapper country : countries){
        	System.out.println("континент страны: "+ country.getStaticContinentName()+ " "+ country.getName() + " айдишник страны: " + country.getId());
        	//вывели список стран первого континента с сылочками в которых хранится айдишник.
        	
            //выводим список городов первой страны первого континента 
            ArrayList<DBWrapper> cities = Database.getAllCitiesByCountry(country.getId());
            System.out.println(cities);
        }
        
        for(DBWrapper city : Database.getAllCities()){
        	if(countries.get(1).getId().equals(city.getCountryId())){
        		System.out.println(city);
        	}
        	//System.out.println(city.getCountryId());
        }
        
        

//    	DBWrapper wr = new DBWrapper();
//    	wr.setType(DBWrapper.TYPE_CONTINENT);
//    	wr.setName("Австралия и Океания");
//    	Database.add(wr);
//    	wr.setName("Азия");
//    	wr.removeId();
//    	Database.add(wr);
//    	wr.setName("Африка");
//    	wr.removeId();
//    	Database.add(wr);
//    	wr.setName("Европа");
//    	wr.removeId();
//    	Database.add(wr);
//    	wr.setName("Северная Америка");
//    	wr.removeId();
//    	Database.add(wr);
//    	wr.setName("Южная Америка");
//    	wr.removeId();
//    	Database.add(wr);
        
        
        //System.out.println(Database.getAllContinents());
        //System.out.println(Database.getAllCountriesByContinent(continentId)());
        //System.out.println(Database.getAllObjectOfSelectedTypeInCity(new ObjectId("4ce1c0ae078edf4cf9b87cfc"), DBWrapper.TYPE_MUSEUM));
        
        
        //Database.printAll();
        //System.out.println(Database.getAllTypesOfObjectByCity(new ObjectId("4ce1c096078edf4cd5b87cfc")));
    }
}

//4ce1bf9b078edf4c5bb77cfc