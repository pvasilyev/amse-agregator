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

public class Database {
	private static Mongo myMongo = null;
	private static DB myDB = null;
	
	private static String myCurrentAddress;
	private static int myCurrentPort;
	
	private static ArrayList<String> myCollections;
	
	private static String myLastContinentName = null;
	private static String myLastCityName = null;
	private static String myLastCountryName = null;
	private static ObjectId myLastContinentId = null;
	private static ObjectId myLastCityId = null;
	private static ObjectId myLastCountryId = null;
	
	public static final String 	DB_SERVER_ADDRESS = "localhost";
	public static final int 	DB_SERVER_PORT = 27017;
	public static final String 	MAIN_DB_NAME = "mainDB";
	public static final String 	DIRTY_DB_NAME = "dirtyDB";
	
	public static final String 	COLLECTION_HOTELS = "hotels";
	public static final String 	COLLECTION_CONTINENTS = "continents";
	public static final String 	COLLECTION_COUNTRIES = "countries";
	public static final String 	COLLECTION_CITIES = "cities";
	public static final String 	COLLECTION_CAFE = "cafe";
	public static final String 	COLLECTION_USERS = "users";
	public static final String 	COLLECTION_COMMENTS = "comments";
	public static final String 	COLLECTION_ATTRACTIONS = "attractions";
	

	
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
			
			myCollections = new ArrayList<String>();
			myCollections.add(COLLECTION_ATTRACTIONS);
			myCollections.add(COLLECTION_CITIES);
			myCollections.add(COLLECTION_COUNTRIES);
			myCollections.add(COLLECTION_CONTINENTS);
			myCollections.add(COLLECTION_CAFE);
			myCollections.add(COLLECTION_HOTELS);
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

    public static void removeAllCollections() {
        Set<String> set = myDB.getCollectionNames();
        //@todo remove constant from code
        for (String collectionName : set) {
            if (!collectionName.equals("system.indexes")) {
                removeCollection(collectionName);
            }
        }
    }
	
	//Print all collections of current database (myDB)
	public static void printAll() {
		Set<String> set = myDB.getCollectionNames();
        System.out.println("There are "  + set.size() + " collections in database");
        for (String collectionName : set) {
            System.out.println(collectionName + " has " +
                    myDB.getCollection(collectionName).count() + " elements");
        }
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
		return getAllCollection(COLLECTION_CITIES);
	}
	
	public static ArrayList<DBWrapper> getAllAttractions(){
		return getAllCollection(COLLECTION_ATTRACTIONS);
	}
	
	public static ArrayList<DBWrapper> getAllCollection(String collectionName){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(collectionName).find();
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				allCollection.add(dbWrapper);
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllDBObjects(){
		ArrayList<DBWrapper> allObjects = new ArrayList<DBWrapper>();
		if(myDB != null){
			for(String collectionName : myCollections){
				DBCursor cur = myDB.getCollection(collectionName).find();
				while(cur.hasNext()){
					DBWrapper dbWrapper = new DBWrapper(cur.next());
					dbWrapper.initFromDB();
					allObjects.add(dbWrapper);
				}	
			}
		}
		return allObjects;
	}
	
	public static ArrayList<DBWrapper> getAllWithType(String type){
		ArrayList<DBWrapper> allCollection = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(new BasicDBObject(DBWrapper.FIELD_TYPE,type));
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				allCollection.add(dbWrapper);
			}
		}
		return allCollection;
	}
	
	public static ArrayList<DBWrapper> getAllDBObjectsWithKeyValue(String key, String value){
		ArrayList<DBWrapper> collection = new ArrayList<DBWrapper>();
		if(myDB != null){
			for(String collectionName : myCollections){
				DBCursor cur = myDB.getCollection(collectionName).find(new BasicDBObject(key,value));
				while(cur.hasNext()){
					DBWrapper dbWrapper = new DBWrapper(cur.next());
					dbWrapper.initFromDB();
					collection.add(dbWrapper);
				}
			}
		}
		return collection;
	}
		
	public static ObjectId add(DBWrapper storageObject){
		if(storageObject != null){
			String collectionName = typeCollection(storageObject.getType());
			storageObject.setId(addToCollection(collectionName, storageObject.toDBObject()));
			return storageObject.getId();
		} else {
			return null;
		}
	}
	
	private static String typeCollection(String type){
		String collectionName;
		if(type.equals(DBWrapper.TYPE_CONTINENT)){
			collectionName = COLLECTION_CONTINENTS;
		} else if(type.equals(DBWrapper.TYPE_COUNTRY)) {
			collectionName = COLLECTION_COUNTRIES;
		} else if(type.equals(DBWrapper.TYPE_CITY)) {
			collectionName = COLLECTION_CITIES;
		} else if(type.equals(DBWrapper.TYPE_HOTEL)) {
			collectionName = COLLECTION_HOTELS;
		} else if(type.equals(DBWrapper.TYPE_CAFE)) {
			collectionName = COLLECTION_CAFE;
		} else if(type.equals(DBWrapper.TYPE_USER)) {
			collectionName = COLLECTION_USERS;
		} else if(type.equals(DBWrapper.TYPE_COMMENT)) {
			collectionName = COLLECTION_COMMENTS;
		} else {
			collectionName = COLLECTION_ATTRACTIONS;
		}
		return collectionName;
	}
	
	public static DBWrapper getDBObjectByIdAndType(ObjectId id, String type) {
		DBObject retObj = findInCollectionById(typeCollection(type), id);
		if(retObj != null){
			DBWrapper dbWrapper = new DBWrapper(retObj);
			dbWrapper.initFromDB();
			return dbWrapper;
		} else {
			return null;
		}
	}
	
	public static DBWrapper getAttractionById(ObjectId id) {
		DBObject retObj = findInCollectionById(COLLECTION_ATTRACTIONS, id);
		if(retObj != null){
			DBWrapper dbWrapper = new DBWrapper(retObj);
			dbWrapper.initFromDB();
			return dbWrapper;
		} else {
			return null;
		}
	}
	
	public static ObjectId getCityIdByName(String cityName){
		if(cityName != null && cityName.equals(myLastCityName)){
			return myLastCityId;
		}
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_NAME,cityName);
		DBObject obj = findInCollection(COLLECTION_CITIES,criteria);
		if(obj != null){
			myLastCityName = cityName;
			myLastCityId = (ObjectId) obj.get(DBWrapper.FIELD_ID);
			return myLastCityId;
		} else {
			return null;
		}
	}
	
	public static ObjectId getCountryIdByName(String countryName){
		if(countryName != null && countryName.equals(myLastCountryName)){
			return myLastCountryId;
		}
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_NAME,countryName);
		DBObject obj = findInCollection(COLLECTION_COUNTRIES,criteria);
		if(obj != null){
			myLastCountryName = countryName;
			myLastCountryId = (ObjectId) obj.get(DBWrapper.FIELD_ID);
			return myLastCountryId;
		} else {
			return null;
		}
	}
	
	public static ObjectId getContinentIdByName(String continentName){
		if(continentName != null && continentName.equals(myLastContinentName)){
			return myLastContinentId;
		}
		BasicDBObject criteria = new BasicDBObject();
		criteria.put(DBWrapper.FIELD_NAME,continentName);
		DBObject obj = findInCollection(COLLECTION_CONTINENTS,criteria);
		if(obj != null){
			myLastContinentName = continentName;
			myLastContinentId = (ObjectId) obj.get(DBWrapper.FIELD_ID);
			return myLastContinentId;
		} else {
			return null;
		}
	}
	
	public static ArrayList<ObjectId> getAllIdByType(String type){
		ArrayList<ObjectId> allCollection = new ArrayList<ObjectId>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(typeCollection(type)).find(new BasicDBObject(DBWrapper.FIELD_TYPE,type),new BasicDBObject(DBWrapper.FIELD_ID,1));
			while(cur.hasNext()){
				allCollection.add((ObjectId)cur.next().get(DBWrapper.FIELD_ID));
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
	
	//Find all objects in collection 'collectionName' where are present fields with values as in the 'criteria' 
	private static ArrayList<DBWrapper> findAllInCollection(String collectionName, DBObject criteria){
		ArrayList<DBWrapper> objects = new ArrayList<DBWrapper>();
		if(myDB != null){
			DBCursor cur = myDB.getCollection(collectionName).find(criteria);
			while(cur.hasNext()){
				DBWrapper dbWrapper = new DBWrapper(cur.next());
				dbWrapper.initFromDB();
				objects.add(dbWrapper);
			}
		}
		return objects;		
	}
	
	public static DB getDB(){
		return myDB;
	}
	
	//------------------------
	//Methods for Gui
	
	public static ArrayList<DBWrapper> getAllContinents(){
		return findAllInCollection(COLLECTION_CONTINENTS,new BasicDBObject(DBWrapper.FIELD_TYPE,DBWrapper.TYPE_CONTINENT));		
	}
	
	public static ArrayList<DBWrapper> getAllCountriesByContinent(ObjectId continentId){
		BasicDBObject criteria = new BasicDBObject();
		//criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_COUNTRY);
		criteria.put(DBWrapper.FIELD_CONTINENT_ID,continentId);
		
		return findAllInCollection(COLLECTION_COUNTRIES,criteria);		
	}
	
	public static ArrayList<DBWrapper> getAllCitiesByCountry(ObjectId countryId){
		BasicDBObject criteria = new BasicDBObject();
		//criteria.put(DBWrapper.FIELD_TYPE, DBWrapper.TYPE_CITY);
		criteria.put(DBWrapper.FIELD_COUNTRY_ID,countryId);
		return findAllInCollection(COLLECTION_CITIES,criteria);	
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getAllTypesOfObjectByCity(ObjectId cityId){
		if(myDB != null){
			ArrayList<String> types = (ArrayList<String>) myDB.getCollection(COLLECTION_ATTRACTIONS).distinct(DBWrapper.FIELD_TYPE,new BasicDBObject(DBWrapper.FIELD_CITY_ID, cityId));
			return types;
		} else{
			return null;
		}
	}
	
	public static ArrayList<DBWrapper> getAllObjectOfSelectedTypeInCity(ObjectId cityId, String type){
		ArrayList<DBWrapper> objects = new ArrayList<DBWrapper>();
		if(myDB != null){
			BasicDBObject criteria = new BasicDBObject();
			criteria.put(DBWrapper.FIELD_CITY_ID,cityId);
			criteria.put(DBWrapper.FIELD_TYPE,type);
			DBCursor cur = myDB.getCollection(COLLECTION_ATTRACTIONS).find(criteria);
			while(cur.hasNext()){
				objects.add(new DBWrapper(cur.next()));
			}
		}
		return objects;	
	}

	
	//-------------------------------------------------------------

	
	//---------------------------------
	//--Methods for Private GUI-start--
	//---------------------------------
	
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
			tempRes.add(DBWrapper.FIELD_IMAGES);
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
			DBCursor cur = myDB.getCollection(typeCollection(nameType)).find(new BasicDBObject(DBWrapper.FIELD_TYPE,nameType));
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
	/*
	public static void setAttribut(String nameAttribut, String valueAttribut, Object id){
		DBWrapper a = getDBObjectById((ObjectId)id);
		a.setAttribut(nameAttribut,valueAttribut);
		myDB.getCollection(COLLECTION_MAIN).save(a.toDBObject());
	}
	*/

	//---------------------------------
	//--Methods for Private GUI end----
	//---------------------------------

	//-------------------------------------------------------------
	
	//---------------------------------
	//--Methods for Clasterization-----
	//---------------------------------
	
    public static DBWrapper getByUniqueId(UniqueId uniqueId) {
        switchBaseTo(uniqueId.getDatabaseName());
        return getDBObjectByIdAndType(uniqueId.getId(),
                uniqueId.getCollectionName());
    }
    
	//---------------------------------
	//--Methods for Clasterization end-
	//---------------------------------
    
    
}