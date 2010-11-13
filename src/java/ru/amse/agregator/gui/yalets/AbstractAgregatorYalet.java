package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.springframework.beans.factory.annotation.Required;
import ru.amse.agregator.gui.model.Attraction;
import ru.amse.agregator.gui.model.AttractionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAgregatorYalet implements Yalet {
    

    protected AttractionManager manager = new AttractionManager();

    @Required
    public void setManager(AttractionManager manager) {
        this.manager = manager;
    }
}
