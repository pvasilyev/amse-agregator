package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import ru.amse.agregator.gui.model.Attraction;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;

public class AddYalet  extends AbstractAgregatorYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
    	if (req.getParameter("flag")!=null && req.getParameter("flag").equals("1")){
        	Database.connectToMainBase();
        	System.out.println("!!!!!!!!!!!!!!!!!!!!");
        	DBWrapper newRec= new DBWrapper();
        	newRec.setName(req.getParameter("id"));
        	newRec.setType(req.getParameter("type"));
        	Database.add(newRec);

    	}
    }
}
