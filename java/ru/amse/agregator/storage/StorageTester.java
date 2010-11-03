package ru.amse.agregator.storage;
import java.util.ArrayList;
import java.util.Date;
import java.awt.geom.Point2D;
public class StorageTester {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DataBase.connectToDirtyBase();

        //Создаём новый объект-контейнер.
        DBWrapper someCity = new DBWrapper();
        
        //Обязательно указываем тип сущности!
        someCity.setType(DBWrapper.TYPE_CITY);
        
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
        
        //Добавляем созданный город в базу и объекту автоматически добавляется поле id
        DataBase.add(someCity);
        
        //---------------
        
        //Создаём новую архитектурную достопримечательность.
        DBWrapper someArchAttr = new DBWrapper();
        
        //Обязательно указываем тип сущности!
        someArchAttr.setType(DBWrapper.TYPE_ARCH_ATTRACTION);
        
        someArchAttr.setName("Колизей");
        
        //Несколько фотографий.
        ArrayList<String> photosArray = new ArrayList<String>();
        photosArray.add("images/kolizey_im1.jpg");
        photosArray.add("images/kolizey_im2.jpg");
        photosArray.add("images/kolizey_im3.jpg");
        photosArray.add("images/kolizey_im4.jpg");
        someArchAttr.setPhotosArray(photosArray);
        
        //указываем город.
        System.out.println("::::::::"+someCity.getId());
        someArchAttr.setCityById(someCity.getId());
        //someArchAttr.setCityByName("Рим"); - так тоже можно. но если известно id то лучше через него.
        
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
        
        //добавляем в базу.
        DataBase.add(someArchAttr);
        
        //Получаем все объекты базы:
        ArrayList<DBWrapper> all = DataBase.getAllDBObjects();
        for(DBWrapper obj : all){
            System.out.println("type: " + obj.getType());
            System.out.println("name:" + obj.getName());
            System.out.println("id: " + obj.getId());
            //........
            System.out.println("-------------");
        }
        
        System.out.println("-------------------------------");
        
        //Получаем все города:
        ArrayList<DBWrapper> cities = DataBase.getAllCities();
        for(DBWrapper city : cities){
            System.out.println("type: " + city.getType());
            System.out.println("name:" + city.getName());
            System.out.println("coords: " + city.getCoords());
            //........
            System.out.println("-------------");
        }
        
        System.out.println("-------------------------------");
        
        //Получаем все объекты указанного типа:
        ArrayList<DBWrapper> objsWithSelectedType = DataBase.getAllWithType(DBWrapper.TYPE_ARCH_ATTRACTION);
        for(DBWrapper obj : objsWithSelectedType){
            System.out.println("type: " + obj.getType());
            System.out.println("name:" + obj.getName());
            System.out.println("architect: " + obj.getArchitect());
            System.out.println("address: " + obj.getAddress());
            System.out.println("address: " + obj.getCost());
            System.out.println("address: " + obj.getDescription());
            System.out.println("address: " + obj.getMusic());
            System.out.println("address: " + obj.getRooms());
            System.out.println("address: " + obj.getWebsite());
            System.out.println("address: " + obj.getBuildDate());
            System.out.println("cityId: " + obj.getCityId());
            System.out.println("address: " + obj.getKeyWordsArray());
            System.out.println("address: " + obj.getPhotosArray());
            System.out.println("address: " + obj.getCoordsArray());
            //........
            System.out.println("-------------");
        }
    }
}