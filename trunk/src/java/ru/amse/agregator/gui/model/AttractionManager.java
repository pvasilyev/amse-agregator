package ru.amse.agregator.gui.model;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.searcher.UserQuery;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

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

        for (int i = 0; i < dbwr.size(); ++i) {
            Attraction attraction = new Attraction();
            attraction.setType(dbwr.get(i).getType());
            attraction.setName(dbwr.get(i).getName());
            String desc = dbwr.get(i).getDescriptionArray().get(0);
            if (desc.length() > 300) {
                attraction.setDescription(new String(desc.substring(0, 300) + " ..."));
            } else {
                attraction.setDescription(desc);
            }
//            attraction.setType(dbwr.get(i).getCityNameFromDB());
            attraction.setId(dbwr.get(i).getId().toString());

            log.error("Other ID = " + attraction.getId());
            result.add(attraction);
        }
        return result;
    }

    public List<Attraction> getAttractionById(String id, String type) {
        AttractionManager.connectToDatabase();
        ObjectId selectedItem = new ObjectId(id);

        DBWrapper dbwr = Database.getDBObjectByIdAndType(selectedItem, type);
        log.error("DBWR AM " + dbwr);

        List<Attraction> result = new ArrayList<Attraction>();

        if (dbwr == null) {
            Attraction attraction = new Attraction();
            attraction.setType("Error");
            result.add(attraction);
            return result;
        }

        Attraction attraction = new Attraction();
        attraction.setId(dbwr.getId().toString());
        attraction.setType(dbwr.getType());
        attraction.setName(dbwr.getName());
        attraction.setDescription(dbwr.getDescriptionArray().get(0));
//        attraction.setCoordinates(dbwr.getCoords().toString());
//        attraction.setKeywords(dbwr.getKeyWordsArray().get(0));
//        attraction.setDate_foundation(dbwr.getBuildDate().toString());
//        attraction.setArchitect(dbwr.getArchitect());
//        attraction.setCost(dbwr.getCost());
//        attraction.setAddress(dbwr.getAddress());
//        attraction.setMusic(dbwr.getMusic());
//        attraction.setWebsite(dbwr.getWebsite());
//        attraction.setRooms(dbwr.getRooms());

        if (attraction.getType().equals("City")) {
            ArrayList<String> list = Database.getAllTypesOfObjectByCity(new ObjectId(attraction.getId()));

            ArrayList<MenuItem> links = new ArrayList<MenuItem>();

            for (String tmp : list) {
                ArrayList<DBWrapper> array = Database.getAllObjectOfSelectedTypeInCity(new ObjectId(id), tmp);
                for (DBWrapper dbwrArray : array) {
                    MenuItem menuItem = new MenuItem(dbwrArray.getName(), dbwrArray.getId().toString());
                    menuItem.setType(dbwrArray.getType());
                    links.add(menuItem);
                }
            }
            attraction.setAttractionList(links);
        }
        result.add(attraction);
        
        return result;
    }
}
