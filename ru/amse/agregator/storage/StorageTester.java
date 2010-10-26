package ru.amse.agregator.storage;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.awt.geom.Point2D;
public class StorageTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBase.connectToDirtyBase();
//		DataBase.removeCollection(DataBase.COLLECTION_CAFE);
//		DataBase.removeCollection(DataBase.COLLECTION_ATTRACTIONS);
//		DataBase.removeCollection(DataBase.COLLECTION_CITIES);
//		DataBase.removeCollection(DataBase.COLLECTION_HOTELS);
		ArchitectualAttraction x1 = new ArchitectualAttraction();
		x1.setName("korgov attraction");
		x1.setPhoto("img.jpg");
		x1.setCityByName("kllandia");
		x1.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x1.setDescription("Who I am?");
		x1.setKeyWordsArray(new ArrayList<String>());
		x1.setAddress("kl address");
		x1.setArchitect("korgov a.a.");
		x1.setCost("100 $");
		//x1.setDate(new Date(1990,87,8));
		x1.setType("музей");
		
		DataBase.add(x1);
		NaturalAttraction x2 = new NaturalAttraction();
		x2.setName("natural attraction");
		x2.setPhoto("img.jpg");
		x2.setCityByName("kllandia");
		x2.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x2.setDescription("Who I am?");
		x2.setKeyWordsArray(new ArrayList<String>());
		DataBase.add(x2);
		
		City x3 = new City();
		x3.setName("korgov city");
		x3.setPhoto("imgcity.jpg");
		x3.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x3.setDescription("Who I am city?");
		x3.setKeyWordsArray(new ArrayList<String>());
		DataBase.add(x3);
		
		
		Cafe x4 = new Cafe();
		x4.setName("korgov cafe");
		x4.setPhoto("imgcitycafe.jpg");
		x4.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x4.setDescription("Who I am cafe?");
		x4.setKeyWordsArray(new ArrayList<String>());
		x4.setCityByName("new new city");
		x4.setWebsite("www.korgov.ru");
		DataBase.add(x4);
		
		Hotel x6 = new Hotel();
		x6.setName("korgov hotel");
		x6.setPhoto("imghotel.jpg");
		x6.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x6.setDescription("Who I am hotel?");
		x6.setKeyWordsArray(new ArrayList<String>());
		x6.setCityByName("super-city");
		x6.setWebsite("www.korgov.ru");
		x6.setRooms("super-luks");	
		DataBase.add(x6);
		DataBase.printAll();
//		
//		
//		DataBase.connectToMainBase();
//		DataBase.removeCollection(DataBase.COLLECTION_CAFE);
//		DataBase.removeCollection(DataBase.COLLECTION_ATTRACTIONS);
//		DataBase.removeCollection(DataBase.COLLECTION_CITIES);
//		DataBase.removeCollection(DataBase.COLLECTION_HOTELS);
		ArchitectualAttraction x11 = new ArchitectualAttraction();
		x11.setName("korgov attraction");
		x11.setPhoto("img.jpg");
		x11.setCityByName("kllandia");
		x11.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x11.setDescription("Who I am?");
		x11.setKeyWordsArray(new ArrayList<String>());
		x11.setAddress("kl address");
		x11.setArchitect("korgov a.a.");
		x11.setCost("100 $");
		//x1.setDate(new Date(1990,87,8));
		x11.setType("музей");
//		
//		DataBase.add(x11);
//		NaturalAttraction x22 = new NaturalAttraction();
//		x22.setName("natural attraction");
//		x22.setPhoto("img.jpg");
//		x22.setCityByName("kllandia");
//		x22.setCoordinates(new Point2D.Double(1220.32432,10.234324));
//		x22.setDescription("Who I am?");
//		x22.setKeyWordsArray(new ArrayList<String>());
//		DataBase.add(x22);
//		
//		City x33 = new City();
//		x33.setName("korgov city");
//		x33.setPhoto("imgcity.jpg");
//		x33.setCoordinates(new Point2D.Double(1220.32432,10.234324));
//		x33.setDescription("Who I am city?");
//		x33.setKeyWordsArray(new ArrayList<String>());
//		DataBase.add(x33);
//		
//		
//		Cafe x44 = new Cafe();
//		x44.setName("korgov cafe");
//		x44.setPhoto("imgcitycafe.jpg");
//		x44.setCoordinates(new Point2D.Double(1220.32432,10.234324));
//		x44.setDescription("Who I am cafe?");
//		x44.setKeyWordsArray(new ArrayList<String>());
//		x44.setCityByName("new new city");
//		x44.setWebsite("www.korgov.ru");
//		DataBase.add(x44);
//		
//		DataBase.connectToDirtyBase();
//		DataBase.printAll();
		
		ArrayList<City> cities = DataBase.getAllCities();
		for(City city : cities){
			System.out.println(city.toDBObject());
		}
		
		ArrayList<Cafe> cities1 = DataBase.getAllCafes();
		for(Cafe city : cities1){
			System.out.println(city.toDBObject());
		}
		
		ArrayList<ArchitectualAttraction> cities21 = DataBase.getAllArchitectualAttractions();
		for(ArchitectualAttraction city : cities21){
			System.out.println(city.toDBObject());
		}
		
		
	}
}