package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;

public class ShowAttractionDescYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);
   
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        String tmp = req.getParameter("id");
        log.error("ID = " + tmp);
        res.add(manager.getAttractionById(tmp.toString()));

    }
}
