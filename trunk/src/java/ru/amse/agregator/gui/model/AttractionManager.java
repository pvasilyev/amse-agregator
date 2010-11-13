package ru.amse.agregator.gui.model;

import org.apache.log4j.Logger;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.searcher.UserQuery;
import ru.amse.agregator.storage.DBWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AttractionManager {
    Logger log = Logger.getLogger(AttractionManager.class);

    public static List<Attraction> ARRAY;

    //Временный метод
    public List<Attraction> getAllAttraction() {
        List<Attraction> result = new ArrayList<Attraction>();
        //TODO temporary.

        for (int i = 0; i < 5; ++i) {
            Attraction attraction = new Attraction();
            attraction.setUid(i);
            attraction.setName("name_" + i);
            attraction.setDescription("description_" + i);
            attraction.setImage("images/image.jpg");
            result.add(attraction);
        }
        saveResult(result);
        return result;
    }

    //Временный метод
    public List<Attraction> getSomeAttraction(String param) {
        List<Attraction> result = new ArrayList<Attraction>();

        //TODO temporary.
        if (param.equals("italy")) {
            for (int i = 0; i < 5; ++i) {
                Attraction attraction = new Attraction();
                attraction.setUid(i);
                attraction.setName("имя" + i);
                attraction.setDescription("описание_" + i);
                attraction.setImage("images/image.jpg");
                result.add(attraction);
            }
        } else {
            Attraction attraction = new Attraction();
            attraction.setUid(0);
            attraction.setName("по вашему запросу ничего не найдено");
            attraction.setDescription("");
            attraction.setImage("images/image.jpg");
            result.add(attraction);
        }
        saveResult(result);
        return result;
    }

    //Получение attraction из загруженных
    public List<Attraction> getSomeAttractionById(String param) {
        List<Attraction> result = new ArrayList<Attraction>();
        result.add(ARRAY.get(new Integer(param)));
        saveResult(result);
        return result;
    }

    //Получение данных из базы
    public List<Attraction> getSearchResult(String param) {
        Searcher.setIndexDir(new File("/home/natalia/DEVELOPMENT/JAVA/IDEA/AgregatorSource/amse-agregator/index"));
        ArrayList<DBWrapper> dbwr = Searcher.search(new UserQuery(param));

        List<Attraction> result = new ArrayList<Attraction>();

        if (dbwr.size() == 0) {
            Attraction attraction = new Attraction();
            attraction.setUid(0);
            attraction.setName("по вашему запросу ничего не найдено");
            attraction.setDescription("");
            attraction.setImage("images/image.jpg");
            result.add(attraction);
            saveResult(result);
            return result;
        }

        for (int i = 0; i < dbwr.size(); ++i) {
            Attraction attraction = new Attraction();
            attraction.setDescription("описание");
            attraction.setUid(i);
            attraction.setImage("images/image.jpg");
            attraction.setName(dbwr.get(i).getName());
            result.add(attraction);
        }
        saveResult(result);
        return result;
    }

    

    //Схоранение найденного, чтобы вывести по щелчку,
    private void saveResult(List<Attraction> array) {
        ARRAY = new ArrayList<Attraction>();
        for (int i = 0; i < array.size(); ++i) {
            ARRAY.add(array.get(i));
        }
    }
}
