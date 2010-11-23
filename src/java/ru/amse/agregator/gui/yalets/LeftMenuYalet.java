package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import ru.amse.agregator.gui.model.LeftMenuItem;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.util.ArrayList;

public class LeftMenuYalet extends AbstractAgregatorYalet{
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    public void process(InternalRequest req, InternalResponse res) {
        if (AbstractAgregatorYalet.menuItemArrayList.size() == 0) {
            Database.connectToDirtyBase();
            ArrayList<DBWrapper> continents = Database.getAllContinents();
            log.error(continents);

            for (DBWrapper continent : continents) {
                AbstractAgregatorYalet.menuItemArrayList.add(new LeftMenuItem(continent.getName(), continent.getId().toString()));
            }
        }
          log.error(AbstractAgregatorYalet.menuItemArrayList);
        res.add(AbstractAgregatorYalet.menuItemArrayList);
        log.error(res.getData());
        
    }
}