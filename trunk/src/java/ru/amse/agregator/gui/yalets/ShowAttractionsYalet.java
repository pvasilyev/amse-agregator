package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ShowAttractionsYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        String tmp = req.getParameter(String.valueOf("findTextBox"));
        if (tmp != null && !"".equals(tmp)) {
            Vector<String> labels = setLabels(req);
            res.add(manager.getSearchResult(tmp, labels));
        }
    }

    private Vector<String> setLabels(InternalRequest req) {
        Vector<String> labels = new Vector<String>();
        if (req.getParameter(String.valueOf("countryCheckbox")) != null) {
            labels.add("Country");
        }
        if (req.getParameter(String.valueOf("cityCheckbox")) != null) {
            labels.add("City");
        }
        if (req.getParameter(String.valueOf("archAttractionCheckbox")) != null) {
            labels.add("ArchAttraction");
        }
        if (req.getParameter(String.valueOf("naturalAttractionCheckbox")) != null) {
            labels.add("NaturalAttraction");
        }
        if (req.getParameter(String.valueOf("museumCheckbox")) != null) {
            labels.add("Museum");
        }
        if (req.getParameter(String.valueOf("entertainmentCheckbox")) != null) {
            labels.add("Entertainment");
        }
        if (req.getParameter(String.valueOf("shoppingCheckbox")) != null) {
            labels.add("Shopping");
        }
        if (req.getParameter(String.valueOf("hotelCheckbox")) != null) {
            labels.add("Hotel");
        }
        if (req.getParameter(String.valueOf("cafeCheckbox")) != null) {
            labels.add("Cafe");
        }

        return labels;
    }
}
