package ru.amse.agregator.gui.model;

import java.util.ArrayList;
import java.util.List;

public class AttractionManager {

    public List<Attraction> getAllAttraction() {
        List<Attraction> results = new ArrayList<Attraction>();
        //TODO temporary.

        for (int i = 0; i < 5; ++i) {
            Attraction attraction = new Attraction();
            attraction.setUid(i);
            attraction.setName("name_" + i);
            attraction.setDescription("description_" + i);
            attraction.setImage("images/image.jpg");
            results.add(attraction);
        }
        return results;
    }

    public List<Attraction> getSomeAttraction(String param) {
        List<Attraction> results = new ArrayList<Attraction>();

        //TODO temporary.
        if (param.equals("italy")) {
            for (int i = 0; i < 5; ++i) {
                Attraction attraction = new Attraction();
                attraction.setUid(i);
                attraction.setName("имя_" + i);
                attraction.setDescription("описание_" + i);
                attraction.setImage("images/image.jpg");
                results.add(attraction);
            }
        } else {
            Attraction attraction = new Attraction();
            attraction.setUid(0);
            attraction.setName("ничего не найднео");
            attraction.setDescription("");
            attraction.setImage("images/image.jpg");
            results.add(attraction);
        }
        return results;
    }

    public Attraction getSomeAttractionById(String param) {
        if (param != null && !"".equals(param)) {
             Attraction attraction = new Attraction();
        attraction.setUid(66666);
        attraction.setName("some_name_" + 666);
        attraction.setDescription("some_description_" + 666);
        attraction.setImage("images/image.jpg");
        }
        Attraction attraction = new Attraction();
        attraction.setUid(10000000);
        attraction.setName("some_name_" + 1000000);
        attraction.setDescription("some_description_" + 1000000);
        attraction.setImage("images/image.jpg");
        return attraction;
    }
}
