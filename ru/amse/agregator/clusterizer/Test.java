package ru.amse.agregator.clusterizer;

//Подключаем все необходимые классы хранилища.
import ru.amse.agregator.storage.ArchitectualAttraction;
import ru.amse.agregator.storage.City;
import ru.amse.agregator.storage.DataBase;
import ru.amse.agregator.clusterizer.Clusterizer;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import org.bson.types.ObjectId;

public class Test {
    public static void main(String[] args) {
        //Подключаемся к сырой базе.
        try {
            DataBase.connectToDirtyBase();
        } catch (Exception e) {
            System.out.println("Error connecting to db");
        }

        //Создаём новый город.
        City someCity = new City();

        someCity.setName("Рим");
        someCity.setPhoto("images/rim.jpg");
        someCity.setCoordinates(new Point2D.Double(1220.32432,10.234324));
        someCity.setDescription("Описание Рима");
        //ключевые слова
        ArrayList<String> stringsArray = new ArrayList<String>();
        stringsArray.add("beautiful");
        stringsArray.add("city");
        stringsArray.add("rim");
        stringsArray.add("Рим");
        someCity.setKeyWordsArray(stringsArray);

        //Добавляем созданный город в базу и запоминаем его id на всякий случай.
        ObjectId someCityId = DataBase.add(someCity);

        System.out.print(someCityId);

        //---------------

        //Создаём новую архитектурную достопримечательность.
        ArchitectualAttraction someArchAttr = new ArchitectualAttraction();
        someArchAttr.setName("Колизей");

        //Несколько фотографий.
        ArrayList<String> photosArray = new ArrayList<String>();
        photosArray.add("images/kolizey_im1.jpg");
        photosArray.add("images/kolizey_im2.jpg");
        photosArray.add("images/kolizey_im3.jpg");
        photosArray.add("images/kolizey_im4.jpg");
        someArchAttr.setPhotosArray(photosArray);

        //указываем город.
        //someArchAttr.setCityById(someCityId);
        someArchAttr.setCityByName("Рим");// - так тоже можно. но если известно id то лучше через него.

        someArchAttr.setCoordinates(new Point2D.Double(10.324234,1022.24234));
        someArchAttr.setDescription("Kolizey description");

        //ключевые слова
        ArrayList<String> keyWordsArray = new ArrayList<String>();
        keyWordsArray.add("rim");
        keyWordsArray.add("kolizey");
        keyWordsArray.add("keyword3");
        someArchAttr.setKeyWordsArray(keyWordsArray);

        someArchAttr.setAddress("Kolizey Street, 55");
        someArchAttr.setArchitect("Kirill K.");
        someArchAttr.setCost("100 $");
        someArchAttr.setDate(new Date(1990,7,8));
        someArchAttr.setType("музей");

        //DataBase.

        //добавляем арх дост. в базу.
        DataBase.add(someArchAttr);

        System.out.println("before:");
        DataBase.printAll();


        Clusterizer.clusterize();


        System.out.println("after:");
        DataBase.connectToMainBase();
        DataBase.printAll();

    }
}