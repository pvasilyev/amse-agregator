package ru.amse.agregator.storage;

import java.net.UnknownHostException;
import java.util.Set;

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
	public static final String COLLECTION_NATURAL_ATTRACTIONS = "natural_attractions";
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
	
	
	//Add object - 'obj' in collection - 'collectionName'
	private static ObjectId addToCollection(String collectionName, DBObject obj){
		if(myDB != null){
			return (ObjectId) myDB.getCollection(collectionName).save(obj).getField("_id");
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
	
	//ArchitectualAttraction
	public static ObjectId add(ArchitectualAttraction storageObject){
		return addToCollection(COLLECTION_ATTRACTIONS, storageObject.toDBObject());
	}
	public static ArchitectualAttraction getArchitectualAttraction(ObjectId id){
		return (ArchitectualAttraction) findInCollectionById(COLLECTION_ATTRACTIONS, id);
	}
		
	//NaturalAttractions
	public static ObjectId add(NaturalAttraction storageObject){
		return addToCollection(COLLECTION_NATURAL_ATTRACTIONS, storageObject.toDBObject());
	}
	public static NaturalAttraction getNaturalAttraction(ObjectId id){
		return (NaturalAttraction) findInCollectionById(COLLECTION_NATURAL_ATTRACTIONS, id);
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
	
	
	
	
	
	
	
	/**
	//Это пока еще не реализовано.	
	public static ObjectId add(KurortAttraction storageObject){
		return addToCollection("attractions", storageObject.toDBObject());
	}
			
	*/
}
