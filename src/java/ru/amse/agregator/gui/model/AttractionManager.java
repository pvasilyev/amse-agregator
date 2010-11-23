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

    public static List<Attraction> ARRAY;

    //Временный метод
    public List<Attraction> getAllAttraction() {
        List<Attraction> result = new ArrayList<Attraction>();


        for (int i = 1; i < 5; i++) {
            Attraction attraction = new Attraction();
            attraction.setType("City");
            attraction.setName("имя _ " + i);
            result.add(attraction);
        }
        saveResult(result);
        return result;
    }

    //Получение данных из базы
    public List<Attraction> getSearchResult(String param, Vector<String> vector) {
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
            attraction.setType("City");
            attraction.setName("по вашему запросу ничего не найдено");
            attraction.setAdress(" Санкт Петербург");
            attraction.setArchitect("sldkjfljsd");
            attraction.setBuildDate("10/11/11");
            result.add(attraction);
            saveResult(result);
            log.error("SECOND");
            
            return result;
        }

        for (int i = 0; i < dbwr.size(); ++i) {
            Attraction attraction = new Attraction();
            attraction.setType(dbwr.get(i).getType());
            attraction.setName(dbwr.get(i).getName());
            attraction.setDescription(dbwr.get(i).getDescriptionArray().get(0));
            attraction.setAdress(dbwr.get(i).getAddress());
            attraction.setArchitect(dbwr.get(i).getArchitect());
            attraction.setType(dbwr.get(i).getCityNameFromDB());
            attraction.setUid(dbwr.get(i).getUniqueId());
            result.add(attraction);
        }
        saveResult(result);
        return result;
    }

    public List<Attraction> getSearchResult(String param) {
        Searcher.setIndexDir(new File("../index"));
        List<DBWrapper> dbwr = Searcher.search(new UserQuery(param));

        List<Attraction> result = new ArrayList<Attraction>();
        if (dbwr.size() == 0) {
            Attraction attraction = new Attraction();
            attraction.setType("City");
            attraction.setName("по вашему запросу ничего не найдено");
            attraction.setAdress(" Санкт Петербург");
            attraction.setArchitect("sldkjfljsd");
            attraction.setBuildDate("10/11/11");
            result.add(attraction);
            saveResult(result);
            return result;
        }

        for (int i = 0; i < dbwr.size(); ++i) {
            Attraction attraction = new Attraction();
            attraction.setType(dbwr.get(i).getType());
            attraction.setName(dbwr.get(i).getName());
            attraction.setDescription(dbwr.get(i).getDescriptionArray().get(0));
            attraction.setAdress(dbwr.get(i).getAddress());
            attraction.setArchitect(dbwr.get(i).getArchitect());
            attraction.setType(dbwr.get(i).getCityNameFromDB());
            attraction.setUid(dbwr.get(i).getUniqueId());
            result.add(attraction);
        }
        saveResult(result);
        return result;
    }


    //Схоранение найденного, чтобы вывести по щелчку,
    private void saveResult(List<Attraction> array) {
        ARRAY = new ArrayList<Attraction>();
        for (int i = 0; i < array.size(); i++) {
            ARRAY.add(array.get(i));
        }
    }


    public List<Attraction> getAttractionById(String id) {
        Database.connectToDirtyBase();
        ObjectId selectedCity = new ObjectId(id);

        DBWrapper city = Database.getDBObjectByIdAndType(selectedCity, DBWrapper.TYPE_CITY);


        List<Attraction> result = new ArrayList<Attraction>();

        Attraction attraction = new Attraction();
        attraction.setType(city.getType());
        attraction.setName(city.getName());
        attraction.setDescription(city.getDescriptionArray().get(0));
        attraction.setAdress(city.getAddress());
        attraction.setArchitect(city.getArchitect());
        attraction.setType(city.getCityNameFromDB());
        attraction.setUid(city.getUniqueId());
        result.add(attraction);
        return result;

    }
}
