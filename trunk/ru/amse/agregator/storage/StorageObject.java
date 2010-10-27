package ru.amse.agregator.storage;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class StorageObject {
	protected BasicDBObject myDBObj;
	
	StorageObject(DBObject dbObject){
		//System.out.println("kl" + dbObject);
		myDBObj = new BasicDBObject();
		setAllFromDBObject(dbObject);
	}
	
	StorageObject(){
		this(new BasicDBObject());
	}
	
	public void setAllFromDBObject(DBObject dbObject){
		//System.out.println("kl23" + myDBObj);
		copyField(dbObject,"_id");
	}
	
	protected void copyField(DBObject dbObj, String key){
		myDBObj.put(key,dbObj.get(key));
		//System.out.println("kl2" + myDBObj);
		//System.out.println("kl2" + key);
	}
	
	public ObjectId getId(){
		return (ObjectId) myDBObj.get("_id");
	}
	
	public DBObject toDBObject(){
		return new BasicDBObject(myDBObj);
	}
}