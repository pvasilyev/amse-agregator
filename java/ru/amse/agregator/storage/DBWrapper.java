package ru.amse.agregator.storage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBWrapper extends StorageObject{	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESC = "decription";
	public static final String FIELD_COORDS = "coordinates";
	public static final String FIELD_PHOTOS = "photos";
	public static final String FIELD_KEYWORDS = "keywords";
	public static final String FIELD_DATE_FOUNDATION = "date_foundation";
	public static final String FIELD_ARCHITECT = "architect"; 
	public static final String FIELD_COST = "cost";
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELD_CITY_ID = "city_id";
	public static final String FIELD_MUSIC = "music";
	public static final String FIELD_WEBSITE = "website";
	public static final String FIELD_ROOMS = "rooms";
	
	public static final String TYPE_CITY = "City";
	public static final String TYPE_ARCH_ATTRACTION = "ArchAttraction";
	public static final String TYPE_NATURAL_ATTRACTION = "NaturalAttraction";		
	public static final String TYPE_CAFE = "Cafe";	
	public static final String TYPE_HOTEL = "Hotel";
	public static final String TYPE_USER = "User";		
	public static final String TYPE_COMMENT = "Comment";
			
	public DBWrapper(DBObject dbObject){
		super(dbObject);
	}
			
	public DBWrapper() {
		this(new BasicDBObject());
	}	
	
	//----------
			
	public void setType (String type){
		myDBObj.put(FIELD_TYPE,type);
	}
	
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
	
	public void setWebsite(String name){
		myDBObj.put(FIELD_WEBSITE,name);
	}
	
	public void setName(String name){
		myDBObj.put(FIELD_NAME,name);
	}
	
	public void setDescription(String desc){
		myDBObj.put(FIELD_DESC,desc);
	}
	
	public void setCoordsArray(ArrayList<Point2D.Double> coordsArray){
		ArrayList<DBObject> coords = new ArrayList<DBObject>();
		for(Point2D.Double p : coordsArray){
			coords.add(new BasicDBObject("x",p.x).append("y", p.y));
		}
		myDBObj.put(FIELD_COORDS,coords);
	}
	
	public void setCoordinates(Point2D.Double coords){
		ArrayList<Point2D.Double> coordsArray = new ArrayList<Point2D.Double>();
		coordsArray.add(coords);
		setCoordsArray(coordsArray);
	}
	
	public void setPhotosArray(ArrayList<String> photosArray){
		myDBObj.put(FIELD_PHOTOS,photosArray);
	}
	
	public void setPhoto(String photo){
		ArrayList<String> photosArray = new ArrayList<String>();
		photosArray.add(photo);
		setPhotosArray(photosArray);
	}
	
	public void setKeyWordsArray(ArrayList<String> keyWordsArray){
		myDBObj.put(FIELD_KEYWORDS,keyWordsArray);
	}
	
	public void setCityById(ObjectId id){
		myDBObj.put(FIELD_CITY_ID, id);
	}	
	
	public void setCityByName(String name){
		ObjectId id = DataBase.getCityIdByName(name);
		if(id == null){
			DBWrapper a = new DBWrapper();
			a.setName(name);
			a.setType(TYPE_CITY);
			id = DataBase.add(a);
		}
		myDBObj.put(FIELD_CITY_ID, id);
	}
	
	public void setRooms(String rooms){
		myDBObj.put(FIELD_ROOMS,rooms);
	}
	
	public void setMusic(String music){
		myDBObj.put(FIELD_MUSIC,music);
	}
		
	//--------
	
	
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
	
	public String getRooms(){
		return myDBObj.getString(FIELD_ROOMS);
	}
	
	public String getMusic(){
		return myDBObj.getString(FIELD_MUSIC);
	}
	
	public String getType(){
		return myDBObj.getString(FIELD_TYPE);
	}	
	
	public ObjectId getCityId(){
		return (ObjectId) myDBObj.get(FIELD_CITY_ID);
	}
	
	public String getWebsite(){
		return myDBObj.getString(FIELD_WEBSITE);
	}	
	
	public String getName(){
		return myDBObj.getString(FIELD_NAME);
	}
	
	public String getDescription(){
		return myDBObj.getString(FIELD_DESC);
	}
	
	@SuppressWarnings("unchecked")		
	public ArrayList<Point2D.Double> getCoordsArray(){
		ArrayList<DBObject> coordsArray = (ArrayList<DBObject>) myDBObj.get(FIELD_COORDS);
		ArrayList<Point2D.Double> coords = new ArrayList<Point2D.Double>();
		if(coordsArray != null){
			for(DBObject obj : coordsArray){
				coords.add(new Point2D.Double((Double)obj.get("x"),(Double)obj.get("y")));
			}
		}
		return coords;
	}		
	
	public Point2D.Double getCoords(){
		ArrayList<Point2D.Double> coordsAr = getCoordsArray();
		if(!coordsAr.isEmpty()){
			return coordsAr.get(0);
		} else {
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getPhotosArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_PHOTOS);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getKeyWordsArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_KEYWORDS);
	}
}