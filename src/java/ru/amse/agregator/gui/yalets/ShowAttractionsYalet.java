package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import ru.amse.agregator.gui.model.AttractionManager;
import ru.amse.agregator.searcher.Searcher;

import java.io.File;
import java.io.IOException;

public class ShowAttractionsYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);

    @Override
	public void process(InternalRequest req, InternalResponse res) {
        String tmp = req.getParameter(String.valueOf("findTextBox"));
        if (tmp != null && !"".equals(tmp)) {
            if (tmp.equals("italy")) {
                res.add(manager.getAllAttraction());
            } else {
                res.add(manager.getSearchResult(tmp));
            }
        }

        boolean isCity = req.getParameter("cityCheckbox") != null;
        log.warn(req.getParameter("cityCheckbox") + " - isCity");
        if (isCity) {

        }

    }
}
