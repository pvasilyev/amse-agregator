package ru.amse.agregator.storage;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DataBase {
	private static Mongo myMongo = null;
	public static DB myDB = null;
	
	public static final String DB_SERVER_ADDRESS = "localhost";
	public static final int DB_SERVER_PORT = 27017;
	public static final String MAIN_DB_NAME = "mainDB";
	public static final String DIRTY_DB_NAME = "dirtyDB";
	
	public static final String COLLECTION_ATTRACTIONS = "attractions";
	public static final String COLLECTION_CITIES = "city";
	public static final String COLLECTION_CAFE = "cafe";
	public static final String COLLECTION_HOTELS = "hotels";


	//Connect to dirty database
	public static void connectToDirtyBase(){
		connect(DB_SERVER_ADDRESS, DB_SERVER_PORT,DIRTY_DB_NAME);
	}
	
	//Connect to main database
	public static void connectToMainBase(){
		connect(DB_SERVER_ADDRESS, DB_SERVER_PORT,MAIN_DB_NAME);
	}
	
	//Connect to server address:port, and database dbName
	public static void connect(String address, int port, String dbName){
		try {
			myMongo = new Mongo(address, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		myDB = myMongo.getDB(dbName);
	}
	
	public static void switchBaseTo(String dbName){
		myDB = myMongo.getDB(dbName);
	}
	
	
	public static void removeCollection(String collectionName) {
		if (myDB != null) {
			myDB.getCollection(collectionName).drop();
		}
	}
	//Print all collections of current database (myDB)
	public static void printAll(){
		Set<String> set = myDB.getCollectionNames();
		for(String collectionName : set){
			DBCollection coll = myDB.getCollection(collectionName);
			DBCursor cur = coll.find();
			System.out.println("\n Collection: " + collectionName + "\n");
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}
	}
	
	//Get All Attractions from DataBase as ArrayList<Attraction>
	public static ArrayList<Attraction> getAllAttractions(){
		ArrayList<Attraction> allCollection = new ArrayList<Attraction>();
		DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find();
		while(cur.hasNext()){
			allCollection.add((Attraction)cur.next());
		}
		return allCollection;
	}
	
	//Get All ArchitectualAttraction (only) from DataBase as ArrayList<ArchitectualAttraction>
	public static ArrayList<ArchitectualAttraction> getAllArchitectualAttractions(){
		ArrayList<ArchitectualAttraction> allArchitectualFromCollection = new ArrayList<ArchitectualAttraction>();		
		DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(new BasicDBObject(Attraction.FIELD_ATTRACTION_TYPE,Attraction.TYPE_ARCHITECTUAL_ATTRACTION));
		while(cur.hasNext()){
			allArchitectualFromCollection.add((ArchitectualAttraction)cur.next());
		}
		return allArchitectualFromCollection;
	}
	
	//Get All NaturalAttraction (only) from DataBase as ArrayList<NaturalAttraction>
	public static ArrayList<NaturalAttraction> getAllNaturalAttractions(){
		ArrayList<NaturalAttraction> allNaturalFromCollection = new ArrayList<NaturalAttraction>();		
		DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(new BasicDBObject(Attraction.FIELD_ATTRACTION_TYPE,Attraction.TYPE_NATURAL_ATTRACTION));
		while(cur.hasNext()){
			allNaturalFromCollection.add((NaturalAttraction)cur.next());
		}
		return allNaturalFromCollection;
	}
	
	public static ArrayList<City> getAllCities(){
		ArrayList<City> allCollection = new ArrayList<City>();
		DBCursor cur = myDB.getCollection(COLLECTION_CITIES).find();
		while(cur.hasNext()){
			allCollection.add((City)cur.next());
		}
		return allCollection;
	}
	
	public static ArrayList<Cafe> getAllCafes(){
		ArrayList<Cafe> allCollection = new ArrayList<Cafe>();
		DBCursor cur = myDB.getCollection(COLLECTION_CAFE).find();
		while(cur.hasNext()){
			allCollection.add((Cafe)cur.next());
		}
		return allCollection;
	}
	
	public static ArrayList<Hotel> getAllHotels(){
		ArrayList<Hotel> allCollection = new ArrayList<Hotel>();
		DBCursor cur = myDB.getCollection(COLLECTION_HOTELS).find();
		while(cur.hasNext()){
			allCollection.add((Hotel)cur.next());
		}
		return allCollection;
	}

	
	//Attraction
	public static ObjectId add(Attraction storageObject){
		return addToCollection(COLLECTION_ATTRACTIONS, storageObject.toDBObject());
	}
	
	//ArchitectualAttraction
	public static ArchitectualAttraction getArchitectualAttraction(ObjectId id){
		return (ArchitectualAttraction) findInCollectionById(COLLECTION_ATTRACTIONS, id);
	}
		
	//NaturalAttractions
	public static NaturalAttraction getNaturalAttraction(ObjectId id){
		return (NaturalAttraction) findInCollectionById(COLLECTION_ATTRACTIONS, id);
	}
	
	//City
	public static ObjectId add(City storageObject){
		return addToCollection(COLLECTION_CITIES, storageObject.toDBObject());
	}
	public static City getCity(ObjectId id){
		return (City) findInCollectionById(COLLECTION_CITIES, id);
	}
	public static ObjectId getCityIdByName(String cityName){
		DBObject obj = findInCollection(COLLECTION_CITIES,"name",cityName);
		if(obj != null){
			return (ObjectId) obj.get("_id");
		} else {
			return null;
		}
	}
	
	//Cafes
	public static ObjectId add(Cafe storageObject){
		return addToCollection(COLLECTION_CAFE, storageObject.toDBObject());
	}
	public static Cafe getCafe(ObjectId id){
		return (Cafe) findInCollectionById(COLLECTION_CAFE, id);
	}
	public static ObjectId getCafeIdByName(String name){
		DBObject obj = findInCollection(COLLECTION_CAFE,"name",name);
		if(obj != null){
			return (ObjectId) obj.get("_id");
		} else {
			return null;
		}
	}
	
	//Hotels
	public static ObjectId add(Hotel storageObject){
		return addToCollection(COLLECTION_HOTELS , storageObject.toDBObject());
	}
	public static ObjectId getHotelIdByName(String name){
		DBObject obj = findInCollection(COLLECTION_HOTELS,"name",name);
		if(obj != null){
			return (ObjectId) obj.get("_id");
		} else {
			return null;
		}
	}
	public static Hotel getHotel(ObjectId id){
		return (Hotel) findInCollectionById(COLLECTION_HOTELS, id);
	}
	
	
	//Add object - 'object' in collection - 'collectionName'
	
	private static ObjectId addToCollection(String collectionName, DBObject object){ 
		if(myDB != null){ 
		myDB.getCollection(collectionName).save(object); 
		return (ObjectId) myDB.getCollection(collectionName).findOne(object).get("_id"); 
		} else { 
		return null; 
		} 
		}
	//Replace in collection 'collectionName', object with id 'id' by 'newObj'
	private static ObjectId updateInCollectionById(String collectionName, ObjectId id, DBObject newObj){
		if(myDB != null){
			newObj.put("_id", id);
			return (ObjectId) myDB.getCollection(collectionName).save(newObj).getField("_id");
		} else {
			return null;
		}		
	}
		
	//Find one object in collection 'collectionName' where field 'key' == 'value'
	private static DBObject findInCollection(String collectionName, String key, Object value){
		if(myDB != null){
			BasicDBObject criteria = new BasicDBObject(key, value);
			return myDB.getCollection(collectionName).findOne(criteria);
		} else {
			return null;
		}
	}
	
	//Find one object in collection 'collectionName' where field id == 'id'
	private static DBObject findInCollectionById(String collectionName, ObjectId id){
		return findInCollection(collectionName,"_id", id);
	}
	public static Vector<String> getCollectionAttributes(String collectionName){
		Vector<String> res = new Vector<String>();
		try{
			Set <String> tempRes = new HashSet<String>();	
			DBCollection coll = myDB.getCollection(collectionName);
			DBCursor cur = coll.find();
			while (cur.hasNext()){
				DBObject temp= cur.next();
				Set<String> fieldsSet = temp.keySet();
				if ( !tempRes.containsAll(fieldsSet) ){
					for(String fieldName : fieldsSet){
						if (!tempRes.contains(fieldName))
							tempRes.add(fieldName);
					}
				}
			}
			for (String v : tempRes){
				res.add(v);
			}
		}
		catch (Exception e) {
			System.out.println("Epic Fail");
		}
		return res;
	}
	
	public static Vector<Vector<Object>> getCollectionValues(String collectionName,Vector<String> attributs){
		Vector<Vector<Object>> res = new Vector<Vector<Object>>();
		DBCollection coll = myDB.getCollection(collectionName);
		DBCursor cur = coll.find();
		
		while(cur.hasNext()){
			DBObject temp= cur.next();
			res.add(new Vector<Object>());
			for (Vector<Object> v : res){
				Set<String> fieldsSet = temp.keySet();
				for ( int i = 0; i < attributs.size();i++){
					if ( fieldsSet.contains(attributs.get(i))){
						v.add(temp.get(attributs.get(i)));
					}
					else
						v.add(temp.get(null));
				}
				
			}
		}
		
		return res;
	}
	
	
	
	
}
