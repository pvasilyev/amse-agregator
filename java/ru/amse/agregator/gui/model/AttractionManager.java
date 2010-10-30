package ru.amse.agregator.gui.model;

import com.sun.java.util.jar.pack.*;
import com.sun.org.apache.xerces.internal.xni.XMLString;

import java.io.*;
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
            results.add(attraction);
        }

//        return convertToXml(results);
        return results;
    }

    private String convertToXml(List<Attraction> attractionList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<attractions>\n");

        for (int i = 0; i < 5; ++i) {
            stringBuffer.append("<attraction>\n");
            stringBuffer.append("<name>\n");
            stringBuffer.append(attractionList.get(i).getName());
            stringBuffer.append("\n</name>\n");

            stringBuffer.append("<description>\n");
            stringBuffer.append(attractionList.get(i).getDescription());
            stringBuffer.append("\n</description>\n");

            stringBuffer.append("</attraction>\n");

        }
        stringBuffer.append("</attractions>");

        return stringBuffer.toString();
    }
}
