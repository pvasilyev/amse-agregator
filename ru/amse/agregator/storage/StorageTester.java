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
		DataBase.removeCollection(DataBase.COLLECTION_CAFE);
		DataBase.removeCollection(DataBase.COLLECTION_ATTRACTIONS);
		DataBase.removeCollection(DataBase.COLLECTION_CITIES);
		DataBase.removeCollection(DataBase.COLLECTION_HOTELS);
		
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
		x1.setDate(new Date(1990,87,8));
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

		DataBase.printAll();
		///*
		ArrayList<Attraction> cities21 = DataBase.getAllAttractions();
		for(Attraction city : cities21){
			System.out.println(city.toDBObject());
		}
		
		//*/
	}
}