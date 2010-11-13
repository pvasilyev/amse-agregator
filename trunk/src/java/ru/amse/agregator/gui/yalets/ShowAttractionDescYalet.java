package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.log4j.Logger;

public class ShowAttractionDescYalet extends AbstractAgregatorYalet {
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);
   
    public void process(InternalRequest req, InternalResponse res) {
        String tmp = req.getParameter(String.valueOf("id"));
        res.add(manager.getSomeAttractionById(tmp.toString()));
        
    }
}
