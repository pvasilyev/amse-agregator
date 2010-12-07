package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class AttractionTopBlockYalet implements Yalet {

	public void process(InternalRequest req, InternalResponse res) {
		
		Database.connectToMainBase();	
		ArrayList<DBWrapper> attractions = Database.getTopNWithType(5, DBWrapper.TYPE_ATTRACTION);
		ArrayList<Record> webRes = new ArrayList<Record>(); 
		
		for (DBWrapper attraction : attractions) {		
			Record attrRec = new Record();
			attrRec.addCell("name", attraction.getName());
			attrRec.addCell("id", attraction.getId().toString());
			ArrayList<String> desc = attraction.getDescriptionArray();
			if(desc.size() != 0){
				attrRec.addCell("mini-desc", desc.get(0).substring(0, 150));
			} else {
				attrRec.addCell("mini-desc", "...");
			}
			ArrayList<String> images = attraction.getImagesArray();
			if(images.size() != 0){
				attrRec.addCell("image", images.get(0));
			} else {
				attrRec.addCell("image", "images/not_image.gif");
			}
			
			webRes.add(attrRec);
		}
		res.add(webRes);

	}

}
