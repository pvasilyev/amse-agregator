package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.apache.log4j.Logger;
import ru.amse.agregator.gui.model.LeftMenuItem;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.util.ArrayList;

public class LeftMenuYalet implements Yalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);


    public void process(InternalRequest req, InternalResponse res) {

        Database.connectToDirtyBase();
        ArrayList<DBWrapper> continents = Database.getAllContinents();
        ArrayList<LeftMenuItem> leftMenuItem = new ArrayList<LeftMenuItem>();

        for (DBWrapper continent : continents) {
            leftMenuItem.add(new LeftMenuItem(continent.getName(), continent.getId().toString()));
        }
        res.add(leftMenuItem);
    }
}