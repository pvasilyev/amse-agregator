package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.util.Date;
import java.awt.geom.Point2D;

import org.bson.types.ObjectId;
public class StorageTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBase.connectToDirtyBase();
		DataBase.removeCollection("attractions");
		DataBase.removeCollection(DataBase.COLLECTION_MAIN);
		
		DBWrapper x1 = new DBWrapper();
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
		x1.setType(DBWrapper.TYPE_ARCH_ATTRACTION);
		
		ObjectId id1 = DataBase.add(x1);
		System.out.println("id1: " + id1);
		
		DBWrapper x2 = new DBWrapper();
		x2.setName("natural attraction");
		x2.setPhoto("img.jpg");
		x2.setCityByName("kllandia");
		x2.setCoordinates(new Point2D.Double(1220.32432,10.234324));
		x2.setDescription("Who I am?");
		x2.setKeyWordsArray(new ArrayList<String>());
		x2.setType(DBWrapper.TYPE_NATURAL_ATTRACTION);
		
		ObjectId id2 = DataBase.add(x2);
		System.out.println("id2: " + id2);

		DataBase.printAll();
		///*
		ArrayList<DBWrapper> all = DataBase.getAllDBObjects();
		for(DBWrapper city : all){
			System.out.println("+" + city.toDBObject());
			System.out.println("+" + city.getType());
		}
		
		//*/
	}
}