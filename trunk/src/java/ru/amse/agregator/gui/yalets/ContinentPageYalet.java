package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import ru.amse.agregator.gui.model.DescriptionModel;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.util.ArrayList;

public class ContinentPageYalet implements Yalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    public void process(InternalRequest req, InternalResponse res) {
        Database.connectToMainBase();

        ObjectId selectedContinent = new ObjectId(req.getParameter("id"));
        DBWrapper continent = Database.getDBObjectByIdAndType(selectedContinent, DBWrapper.TYPE_CONTINENT);

        ArrayList<DBWrapper> countrytmp = Database.getAllCountriesByContinent(continent.getId());
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        for (DBWrapper country : countrytmp) {
            menuItems.add(new MenuItem(country.getName(), country.getId().toString()));
        }
        res.add(menuItems);
    }
}