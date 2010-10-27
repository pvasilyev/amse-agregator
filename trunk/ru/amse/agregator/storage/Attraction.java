package ru.amse.agregator.storage;

import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Attraction extends BasicStorageObject{
	public static final String TYPE_ARCHITECTUAL_ATTRACTION = "ArchitectualAttraction";
	public static final String TYPE_NATURAL_ATTRACTION = "NaturalAttraction";
	
	public static final String FIELD_CITY_ID = "city_id";
	public static final String FIELD_ATTRACTION_TYPE = "attraction_type";
	
	public Attraction(DBObject dbObject){
		super(dbObject);
		myDBObj = (BasicDBObject)dbObject;
		setAllFromDBObject(dbObject);
	}
		
	public Attraction(){
		this(new BasicDBObject());
	}
	
	@Override
	public void setAllFromDBObject(DBObject dbObject){
		super.setAllFromDBObject(dbObject);
		copyField(dbObject,FIELD_CITY_ID);
		copyField(dbObject,FIELD_ATTRACTION_TYPE);
	}
	
	//----
	
	public void setCityById(ObjectId id){
		myDBObj.put(FIELD_CITY_ID, id);
	}	
	
	public void setCityByName(String name){
		ObjectId id = DataBase.getCityIdByName(name);
		if (id != null){
		       myDBObj.put(FIELD_CITY_ID, id);
		}
		else{
			City a = new City();
			a.setName(name);
			id = DataBase.add(a);
			myDBObj.put(FIELD_CITY_ID, id);
		}
	}
	
	//----
		
	public ObjectId getCityId(){
		return (ObjectId) myDBObj.get(FIELD_CITY_ID);
	}
	
	public String getAttractionType(){
		return myDBObj.getString(FIELD_ATTRACTION_TYPE);
	}
	
	

}
