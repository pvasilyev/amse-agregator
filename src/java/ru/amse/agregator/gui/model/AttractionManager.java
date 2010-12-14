package ru.amse.agregator.gui.model;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.searcher.UserQuery;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;
import ru.amse.agregator.utils.HtmlTools;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class AttractionManager {
    Logger log = Logger.getLogger(AttractionManager.class);

    public static enum DatabaseEnum {
        mainDB,
        dirtyDB
    }

    public static DatabaseEnum databaseName = DatabaseEnum.mainDB;

    public static void connectToDatabase() {

        if (AttractionManager.databaseName == DatabaseEnum.dirtyDB) {
            Database.connectToDirtyBase();
        } else {
            Database.connectToMainBase();
        }
    }

    //Временный метод
    public List<Attraction> getAllAttraction() {
        List<Attraction> result = new ArrayList<Attraction>();

        for (int i = 1; i < 5; i++) {
            Attraction attraction = new Attraction();
            attraction.setType("City");
            attraction.setName("имя _ " + i);
            result.add(attraction);
        }
        return result;
    }

    //Получение данных из базы
    public List<Attraction> getSearchResult(String param, ArrayList<String> vector) {
        Searcher.setIndexDir(new File("index"));
        log.error("vector - " + vector);
        ArrayList<DBWrapper> dbwr;

        if (vector.size() > 0) {
            dbwr = Searcher.search(new UserQuery(param, vector));
        } else {
            dbwr = Searcher.search(new UserQuery(param));
        }
        List<Attraction> result = new ArrayList<Attraction>();
        if (dbwr.size() == 0) {
            Attraction attraction = new Attraction();
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }

        String tmp;
        log.error("length " + dbwr.size());
        for (int i = 0; i < dbwr.size(); ++i) {
            log.error("number " + i);
            log.error("name " + dbwr.get(i).getName());
            log.error("id " + dbwr.get(i).getId());
            log.error("type " + dbwr.get(i).getType());

            Attraction attraction = new Attraction();

            tmp = String.valueOf(dbwr.get(i).getId());
            if (tmp == null) {
                continue;
            }
            attraction.setId(tmp);

            tmp = dbwr.get(i).getType();
            if (tmp == null) {
                log.error("CONYINUE");
                continue;
            }
            attraction.setType(tmp);

            tmp = dbwr.get(i).getName();
            if (tmp == null) {
                continue;
            }
            attraction.setName(tmp);

            if (dbwr.get(i).getDescriptionArray() == null) {
                continue;
            }

            tmp = (String) dbwr.get(i).getDescriptionArray().get(0);

            if (tmp != null) {
            	String withoutTags = HtmlTools.clearString(tmp);
                if (withoutTags.length() > 300) {
                    attraction.setDescription(new String(withoutTags.substring(0, 300) + " ..."));
                } else {
                    attraction.setDescription(tmp);
                }
            }

            result.add(attraction);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
	public List<Attraction> getAttractionById(String id, String type) {
        AttractionManager.connectToDatabase();
        ObjectId selectedItem = new ObjectId(id);

        DBWrapper dbwr = Database.getDBObjectByIdAndTypeAndIncRating(selectedItem, type);

        List<Attraction> result = new ArrayList<Attraction>();

        if (dbwr == null) {
            Attraction attraction = new Attraction();
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }

        Attraction attraction = new Attraction();
        attraction.setId(dbwr.getId().toString());
        attraction.setType(type);
        attraction.setName(dbwr.getName());
        if (!type.equals("Continent")) {
        	StringBuffer sb = new StringBuffer();
        	ArrayList<String> descArray = dbwr.getDescriptionArray();
        	if(descArray != null){
            	for(String desc : descArray){
            		sb.append(desc + "<hr>");
            	}
        	}
            attraction.setDescription(sb.toString());

            ArrayList<String> imagesArray = dbwr.getImagesArray();
            if (imagesArray != null) {
                if (imagesArray.size() > 0) {
                    attraction.setImagesArray(imagesArray);
                }
            } 
//        attraction.setCoordinates(dbwr.getCoords().toString());
//        attraction.setKeywords(dbwr.getKeyWordsArray().get(0));
//        attraction.setDate_foundation(dbwr.getBuildDate().toString());
//        attraction.setArchitect(dbwr.getArchitect());
//        attraction.setCost(dbwr.getCost());
//        attraction.setAddress(dbwr.getAddress());
//        attraction.setMusic(dbwr.getMusic());
        	sb = new StringBuffer();
        	ArrayList<String> srcArray = dbwr.getSourceUrlArray();
        	if(srcArray != null){
        		int i=0;
            	for(String src : srcArray){
            		sb.append(src + ((srcArray.size()-1 == i)?(""):(", ")));
            		i++;
            	}
        	}
            attraction.setWebsite(sb.toString());
//        attraction.setRooms(dbwr.getRooms()); 
        }


        if (type.equals("City") || type.equals("Country") || type.equals("Continent")) {
            attraction.setAttractionList(addListOfAttractions(attraction));
        }
        result.add(attraction);

        return result;
    }

    private ArrayList<MenuItem> addListOfAttractions(Attraction attraction) {
        ArrayList<MenuItem> links = new ArrayList<MenuItem>();

        if (attraction.getType().equals("City")) {
            ArrayList<String> list = Database.getAllTypesOfObjectByCity(new ObjectId(attraction.getId()));
            for (String tmp : list) {
                ArrayList<DBWrapper> array = Database.getAllObjectOfSelectedTypeInCity(new ObjectId(attraction.getId()), tmp);
                for (DBWrapper dbwrArray : array) {
                    links.add(new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString(), dbwrArray.getType()));
                }
            }
        }

        if (attraction.getType().equals("Country")) {
            ArrayList<DBWrapper> array = Database.getAllCitiesByCountry(new ObjectId(attraction.getId()));
            for (DBWrapper dbwrArray : array) {
                links.add(new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString(), DBWrapper.TYPE_CITY));
            }
        }

        if (attraction.getType().equals("Continent")) {
            ArrayList<DBWrapper> array = Database.getAllCountriesByContinent(new ObjectId(attraction.getId()));
            for (DBWrapper dbwrArray : array) {
                links.add(new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString(), DBWrapper.TYPE_COUNTRY));
            }
        }

        return links;

    }
}
