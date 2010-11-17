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
	private static DB myDB = null;
	
	private static String myCurrentAddress;
	private static int myCurrentPort;
	
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
		if(!address.equals(myCurrentAddress) || myCurrentPort != port){
			try {
				myMongo = new Mongo(address, port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}
			myCurrentAddress = address;
			myCurrentPort = port;
		}
		
		if(myMongo != null){
			myDB = myMongo.getDB(dbName);
		}
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
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				allCollection.add(dbWrapper);
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllWithKeyValue(String key, String value){
		ArrayList<DBWrapper> collection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(COLLECTION_MAIN).find(new BasicDBObject(key,value));
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				collection.add(dbWrapper);
			}
		}
		return collection;
	}
		
	public static ObjectId add(DBWrapper storageObject){
		if(storageObject != null){
			storageObject.setId(addToCollection(COLLECTION_MAIN, storageObject.toDBObject()));
			return storageObject.getId();
		} else {
			return null;
		}
	}
	
	public static DBWrapper getDBObjectById(ObjectId id) {
		DBObject retObj = findInCollectionById(COLLECTION_MAIN, id);
		if(retObj != null){
			DBWrapper dbWrapper = new DBWrapper(retObj);
			dbWrapper.initFromDB();
			return dbWrapper;
		} else {
			return null;
		}
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
	
	public static ObjectId getCountryIdByName(String countryName){
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_TYPE,DBWrapper.TYPE_COUNTRY);
		criteria.put(DBWrapper.FIELD_NAME,countryName);
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
	@SuppressWarnings("unused")
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
	public static Set<String> getTypesNames(String nameCollection){
		Set <String> tempRes = new HashSet<String>();	
		try{
			
			DBCollection coll = myDB.getCollection(nameCollection);
			DBCursor cur = coll.find();
			String typeName;
			while (cur.hasNext()){
				DBWrapper temp= new DBWrapper(cur.next());
				typeName = temp.getType();
			    if ( !(tempRes.contains(typeName)) ){
					tempRes.add(typeName);
				}
			}
			
		}
		catch (Exception e) {
			System.out.println("Epic Fail here");
		}
		return  tempRes;
	}
	
	
	public static Set<String> getAttributsNames(String nameType){
		Set <String> tempRes = new HashSet<String>();	
		tempRes.add("_id");
		tempRes.add(DBWrapper.FIELD_TYPE);
		tempRes.add(DBWrapper.FIELD_NAME);
		tempRes.add(DBWrapper.FIELD_KEYWORDS);
		if (nameType.equalsIgnoreCase("User")){}
		else if (nameType.equalsIgnoreCase("Comment")){}
		else {
			tempRes.add(DBWrapper.FIELD_DESC);
			tempRes.add(DBWrapper.FIELD_COORDS);
			tempRes.add(DBWrapper.FIELD_PHOTOS);
			if (!nameType.equalsIgnoreCase("City")){
				tempRes.add(DBWrapper.FIELD_COST);
				tempRes.add(DBWrapper.FIELD_ADDRESS);
				tempRes.add(DBWrapper.FIELD_CITY_ID);
				tempRes.add(DBWrapper.FIELD_WEBSITE);
				if (nameType.equalsIgnoreCase("ArchAttraction")){
					tempRes.add(DBWrapper.FIELD_ARCHITECT);
					tempRes.add(DBWrapper.FIELD_DATE_FOUNDATION);
				}
				else if(nameType.equalsIgnoreCase("Cafe")){
					tempRes.add(DBWrapper.FIELD_MUSIC);
				}
				else if(nameType.equalsIgnoreCase("Hotel")){
					tempRes.add(DBWrapper.FIELD_ROOMS);
				}
			}
		}
			
		return  tempRes;
	}
	

	public static Vector<Vector<Object>> getCollectionValues(String nameType, Set<String> attr){
		Vector<Vector<Object>> res = new Vector<Vector<Object>>();
		Vector<DBWrapper> allCollection = new Vector<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(COLLECTION_MAIN).find(new BasicDBObject(DBWrapper.FIELD_TYPE,nameType));
			while(cur.hasNext()){
				allCollection.add(new DBWrapper(cur.next()));
			}
		}
		for (int i = 0; i < allCollection.size();++i){
			Vector<Object> v = new Vector<Object>();
			for(String str1 : attr){
			  v.add(allCollection.get(i).myDBObj.get(str1));
			}
			res.add(v);
		}
		return res;
	}
	public static int getTypesCount(String nameCollection){
		int count = 0;
		try{
			Set <String> tempRes = new HashSet<String>();	
			DBCollection coll = myDB.getCollection(nameCollection);
			DBCursor cur = coll.find();
			String typeName;
			
			while (cur.hasNext()){
				DBWrapper temp= (DBWrapper)cur.next();
				typeName = temp.getType(); 
			    if ( !(tempRes.contains(typeName)) ){
					tempRes.add(typeName);
				}
			}
			for (@SuppressWarnings("unused") String v : tempRes){			
				count++;
			}
		}
		catch (Exception e) {
			System.out.println("Epic Fail");
		}
		return count;
	}
	public static void setAttribut(String nameAttribut, String valueAttribut, Object id){
		DBWrapper a = getDBObjectById((ObjectId)id);
		a.setAttribut(nameAttribut,valueAttribut);
		myDB.getCollection(COLLECTION_MAIN).save(a.toDBObject());
	}
}