package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

public class ShowAttractionsYalet extends AbstractAgregatorYalet {

    public void process(InternalRequest req, InternalResponse res) {
        res.add(manager.getAllAttraction());
    }
}
