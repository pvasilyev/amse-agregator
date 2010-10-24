package ru.amse.agregator.indexer;

import ru.amse.agregator.storage.*;

/*
 * Author: Bondarev Timofey
 * Date: Oct 24, 2010
 * Time: 12:35:28 AM
 */

public class DataToIndex {
    public static void writeDataToFile() {
        connectToDB();

        
    }

    private static void connectToDB() {
        DataBase.connectToMainBase();
        // TODO убрать после того, как появятся данные в базе
        City someCity = new City();
        someCity.setName("Rome");
        someCity.setDescription("wqe ertty ytryertwer wertwertwete tyuerwewq rteryrty rtwert ewrtwertrwey trr");
        someCity.setPhoto("RomeInPhoto");
        DataBase.add(someCity);
        // TODO до этого места
    }
}
