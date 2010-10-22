package ru.amse.agregator.storage;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class CityElement extends BasicStorageObject {
	public static final String FIELD_WEBSITE = "website";
	public static final String FIELD_CITY_ID = "city_id";
		
	public CityElement(DBObject dbObject){
		super(dbObject);
		this.setAllFromDBObject(dbObject);
	}
	
	public CityElement(){
		this(new BasicDBObject());
	}
	
	public void setAllFromDBObject(DBObject dbObject){
		copyField(dbObject,FIELD_WEBSITE);
		copyField(dbObject,FIELD_CITY_ID);
	}

	
	//---
	
	public void setCityById(ObjectId id){
		myDBObj.put(FIELD_CITY_ID, id);
	}
	
	public void setCityByName(String name){
		ObjectId id = DataBase.getCityIdByName(name);
		if (id != null){
		       myDBObj.put(FIELD_CITY_ID, id);
		}
		else{
			City newCity = new City();
			newCity.setName(name);
			id = DataBase.add(newCity);
			myDBObj.put(FIELD_CITY_ID, id);
			
		}
	}

	public void setWebsite(String name){
		myDBObj.put(FIELD_WEBSITE,name);
	}
	
	
	//---
	
	public ObjectId getCityId(){
		return (ObjectId) myDBObj.get(FIELD_CITY_ID);
	}
	
	public String getWebsite(){
		return myDBObj.getString(FIELD_WEBSITE);
	}
	
}
