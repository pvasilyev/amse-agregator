package ru.amse.agregator.storage;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class StorageObject {
	protected BasicDBObject myDBObj;
	
	StorageObject(DBObject dbObject){
		//myDBObj = (BasicDBObject) dbObject;
		myDBObj = new BasicDBObject();
		setAllFromDBObject(dbObject);
	}
	
	StorageObject(){
		this(new BasicDBObject());
	}
	
	public void setAllFromDBObject(DBObject dbObject){
		copyField(dbObject,"_id");
	}
	
	protected void copyField(DBObject dbObj, String key){
		myDBObj.put(key,dbObj.get(key));
	}
	
	public ObjectId getId(){
		return (ObjectId) myDBObj.get("_id");
	}
	
	public DBObject toDBObject(){
		return (DBObject) myDBObj.clone();
	}
	
	public String toString(){
		return myDBObj.toString();
	}
}