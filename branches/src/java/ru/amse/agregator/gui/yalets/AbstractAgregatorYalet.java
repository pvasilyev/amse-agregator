package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.Yalet;
import org.springframework.beans.factory.annotation.Required;
import ru.amse.agregator.gui.model.AttractionManager;

public abstract class AbstractAgregatorYalet implements Yalet {

    protected AttractionManager manager;

    @Required
    public void setManager(AttractionManager manager) {
        this.manager = manager;
    }
}
