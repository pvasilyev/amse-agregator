package ru.amse.agregator.gui.model;

import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.UniqueId;

import java.util.ArrayList;
import java.util.Date;

public class Attraction {
    private UniqueId uid;
    private String type = "";
    private String name = "";
    private ArrayList<String> description = new ArrayList<String>();
    private String architect = "";
    private String adress = "";
    private String buildDate = "";
    private ArrayList<String> photoArray = new ArrayList<String>();

    boolean isCity = false;

    public UniqueId getUid() {
        return uid;
    }

    public void setUid(UniqueId uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public ArrayList<String> getPhotoArray() {
        return photoArray;
    }

    public void setPhotoArray(ArrayList<String> photoArray) {
        this.photoArray = photoArray;
    }

    public String getArchitect() {
        return architect;
    }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
