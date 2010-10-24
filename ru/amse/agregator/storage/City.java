package ru.amse.agregator.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class City extends BasicStorageObject {
	
	public City(DBObject dbObject){
		super(dbObject);
		//если добавятся свои поля то нужно добавить собственную функцию:
		//this.setAllFromDBObject(dbObject);
	}
	
	public City(){
		this(new BasicDBObject());
	}

}
