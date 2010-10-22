package ru.amse.agregator.storage;

import java.util.ArrayList;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ArchitectualAttraction extends Attraction {
	public static final String FIELD_DATE_FOUNDATION = "date_foundation"; //дата основания
	public static final String FIELD_ARCHITECT = "architect"; //архитектор
	public static final String FIELD_TYPES = "types";  //Museum, Monument..
	public static final String FIELD_COST = "cost";  //price?
	public static final String FIELD_ADDRESS = "address";
	
	public ArchitectualAttraction(DBObject dbObject){
		super(dbObject);
		this.setAllFromDBObject(dbObject);
		myDBObj.put(FIELD_ATTRACTION_TYPE, TYPE_ARCHITECTUAL_ATTRACTION);
	}
		
	public ArchitectualAttraction(){	
		this(new BasicDBObject());
	}
		
	public void setAllFromDBObject(DBObject dbObject){
		super.setAllFromDBObject(dbObject);
		copyField(dbObject,FIELD_DATE_FOUNDATION);
		copyField(dbObject,FIELD_ARCHITECT);
		copyField(dbObject,FIELD_TYPES);
		copyField(dbObject,FIELD_COST);
		copyField(dbObject,FIELD_ADDRESS);
	}
	
	//----
	
	
	public void setAddress(String address){
		myDBObj.put(FIELD_ADDRESS,address);
	}
		
	public void setDate(Date date){
		myDBObj.put(FIELD_DATE_FOUNDATION, date);
	}
	
	public void setArchitect(String autor){
		myDBObj.put(FIELD_ARCHITECT, autor);
	}
	
	public void setCost(String cost){
		myDBObj.put(FIELD_COST, cost);
	}
	
	public void setTypesArray(ArrayList<String> typesArray){
		myDBObj.put(FIELD_TYPES, typesArray);
	}
	
	public void setType(String oneType){
		ArrayList<String> array = new ArrayList<String>();
		array.add(oneType);
		setTypesArray(array);
	}
	
	
	//----
	
	
	public String getAddress(){
		return myDBObj.getString(FIELD_ADDRESS);
	}	
	
	public String getArchitect(){
		return (String) myDBObj.get(FIELD_ARCHITECT);
	}
	
	public String getCost(){
		return (String) myDBObj.get(FIELD_COST);
	}
	
	public Date getBuildDate(){
		return (Date) myDBObj.get(FIELD_DATE_FOUNDATION);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getTypesArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_TYPES);
	}
}
