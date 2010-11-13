package ru.amse.agregator.gui.model;

import ru.amse.agregator.storage.DBWrapper;

import java.util.ArrayList;

public class Attraction {
    private long uid;
    private String name;
    private String description;
    private String image;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
//ArrayList<DBWrapper> array = new ArrayList<DBWrapper>();
//        DBWrapper db = new DBWrapper();
//        db.setName("qqq");
//        array.add(db);
//        return array;
