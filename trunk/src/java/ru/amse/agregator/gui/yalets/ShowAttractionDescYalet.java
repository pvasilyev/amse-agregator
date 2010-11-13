package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

public class ShowAttractionDescYalet extends AbstractAgregatorYalet {

    @Override
	public void process(InternalRequest req, InternalResponse res) {
        String tmp = req.getParameter(String.valueOf("id"));
        

        res.add(manager.getSomeAttractionById(tmp));

    }
}
