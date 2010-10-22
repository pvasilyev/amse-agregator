package ru.amse.agregator.storage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class BasicStorageObject extends StorageObject {
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESC = "decription";
	public static final String FIELD_COORDS = "coordinates";
	public static final String FIELD_PHOTOS = "photos";
	public static final String FIELD_KEYWORDS = "keywords";
	
	BasicStorageObject(DBObject dbObject){
		super(dbObject);
		this.setAllFromDBObject(dbObject);
	}
	
	public BasicStorageObject() {
		this(new BasicDBObject());
	}	
	
	public void setAllFromDBObject(DBObject dbObject){
		copyField(dbObject,FIELD_NAME);
		copyField(dbObject,FIELD_DESC);
		copyField(dbObject,FIELD_COORDS);
		copyField(dbObject,FIELD_PHOTOS);
		copyField(dbObject,FIELD_KEYWORDS);
	}
	
	//----
	
	
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
	
	//----
	
	
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
		for(DBObject obj : coordsArray){
			coords.add(new Point2D.Double((Double)obj.get("x"),(Double)obj.get("y")));
		}		
		return coords;
	}
	
	public Point2D.Double getCoords(){
		return getCoordsArray().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getPhotosArray(){
		return (ArrayList<String>) myDBObj.get(FIELD_PHOTOS);
	}
	
}
