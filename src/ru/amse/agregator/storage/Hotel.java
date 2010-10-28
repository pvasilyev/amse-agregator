package ru.amse.agregator.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Hotel extends CityElement {
	
	public static final String FIELD_ROOMS = "rooms";

	public Hotel(DBObject dbObject){
		super(dbObject);
		setAllFromDBObject(dbObject);
	}
	
	public Hotel(){
		this(new BasicDBObject());
	}
	
	@Override
	public void setAllFromDBObject(DBObject dbObject){
		super.setAllFromDBObject(dbObject);
		copyField(dbObject,FIELD_ROOMS);
	}
	
	//----
	
	public void setRooms(String rooms){
		myDBObj.put(FIELD_ROOMS,rooms);
	}
	
	//----
		
	public String getRooms(){
		return myDBObj.getString(FIELD_ROOMS);
	}
			
}


