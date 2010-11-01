package ru.amse.agregator.storage;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
	private static DB myDB = null;
	
	public static final String 	DB_SERVER_ADDRESS = "localhost";
	public static final int 	DB_SERVER_PORT = 27017;
	public static final String 	MAIN_DB_NAME = "mainDB";
	public static final String 	DIRTY_DB_NAME = "dirtyDB";
	
	public static final String 	COLLECTION_MAIN = "main";

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
		if(myMongo != null){
			myDB = myMongo.getDB(dbName);
		}
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
		
	public static ArrayList<DBWrapper> getAllCities(){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(COLLECTION_MAIN).find(new BasicDBObject(DBWrapper.FIELD_TYPE,DBWrapper.TYPE_CITY));
			while(cur.hasNext()){
				allCollection.add(new DBWrapper(cur.next()));
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllDBObjects(){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(COLLECTION_MAIN).find();
			while(cur.hasNext()){
				allCollection.add(new DBWrapper(cur.next()));
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllWithType(String type){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(COLLECTION_MAIN).find(new BasicDBObject(DBWrapper.FIELD_TYPE,type));
			while(cur.hasNext()){
				allCollection.add(new DBWrapper(cur.next()));
			}
		}
		return allCollection;
	}
		
	public static ObjectId add(DBWrapper storageObject){
		storageObject.setId(addToCollection(COLLECTION_MAIN, storageObject.toDBObject()));
		return storageObject.getId();
	}
	
	public static DBWrapper getDBObjectById(ObjectId id){
		return new DBWrapper(findInCollectionById(COLLECTION_MAIN, id));
	}
	
	public static ObjectId getCityIdByName(String cityName){
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_TYPE,DBWrapper.TYPE_CITY);
		criteria.put(DBWrapper.FIELD_NAME,cityName);
		DBObject obj = findInCollection(COLLECTION_MAIN,criteria);
		if(obj != null){
			return (ObjectId) obj.get(DBWrapper.FIELD_ID);
		} else {
			return null;
		}
	}

	public static ArrayList<ObjectId> getAllIdByType(String type){
		ArrayList<ObjectId> allCollection = new ArrayList<ObjectId>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(COLLECTION_MAIN).find(new BasicDBObject(DBWrapper.FIELD_TYPE,type),new BasicDBObject(DBWrapper.FIELD_ID,1));
			while(cur.hasNext()){
				allCollection.add( (ObjectId) cur.next().get(DBWrapper.FIELD_ID));
			}
		}
		return allCollection;
	}
		
	private static ObjectId addToCollection(String collectionName, DBObject object){ 
		if(myDB != null){
			myDB.getCollection(collectionName).save(object); 
			return (ObjectId) object.get(DBWrapper.FIELD_ID); 
		} else { 
			return null; 
		}  
	}
	
	//Replace in collection 'collectionName', object with id 'id' by 'newObj'
	private static ObjectId updateInCollectionById(String collectionName, ObjectId id, DBObject newObj){
		if(myDB != null){
			newObj.put(DBWrapper.FIELD_ID, id);
			return (ObjectId) myDB.getCollection(collectionName).save(newObj).getField(DBWrapper.FIELD_ID);
		} else {
			return null;
		}		
	}
	
	
	//Find one object in collection 'collectionName' where field id == 'id'
	private static DBObject findInCollectionById(String collectionName, ObjectId id){
		return findInCollection(collectionName,DBWrapper.FIELD_ID, id);
	}
	
	//Find one object in collection 'collectionName' where field 'key' == 'value'
	private static DBObject findInCollection(String collectionName, String key, Object value){
			return findInCollection(collectionName, new BasicDBObject(key, value));
	}
	
	//Find one object in collection 'collectionName' where are present fields with values as in the 'criteria' 
	private static DBObject findInCollection(String collectionName, DBObject criteria){
		if(myDB != null){
			return myDB.getCollection(collectionName).findOne(criteria);
		} else {
			return null;
		}	
	}
	
	public static DB getDB(){
		return myDB;
	}
}