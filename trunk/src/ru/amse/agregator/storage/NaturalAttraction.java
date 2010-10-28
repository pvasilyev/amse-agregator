package ru.amse.agregator.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class NaturalAttraction extends  Attraction {

	public NaturalAttraction(DBObject dbObject){
		super(dbObject);
		//если добавятся свои поля то нужно добавить собственную функцию:
		//this.setAllFromDBObject(dbObject);
		myDBObj.put(FIELD_ATTRACTION_TYPE, TYPE_NATURAL_ATTRACTION);
	}
	
	public NaturalAttraction(){
		this(new BasicDBObject());
	}
	
	//----
	
}
