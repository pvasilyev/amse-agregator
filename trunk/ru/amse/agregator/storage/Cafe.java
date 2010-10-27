package ru.amse.agregator.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Cafe extends CityElement{
	public static final String FIELD_MUSIC = "music";
	
	public Cafe(DBObject dbObject){
		super(dbObject);
		setAllFromDBObject(dbObject);
	}
	
	public Cafe(){
		this(new BasicDBObject());
	}
			
	@Override
	public void setAllFromDBObject(DBObject dbObject){
		super.setAllFromDBObject(dbObject);
		copyField(dbObject,FIELD_MUSIC);
	}
	
	//----
	
	public void setMusic(String music){
		myDBObj.put(FIELD_MUSIC,music);
	}
	
	//----

	public String getMusic(){
		return myDBObj.getString(FIELD_MUSIC);
	}
		
}

