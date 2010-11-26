package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

public class ShowAttractionDescYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);
   
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(manager.getAttractionById(req.getParameter("id"), req.getParameter("type")));
    }
}
