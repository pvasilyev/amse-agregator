package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class GetTopYalet implements Yalet{

	private String type;
	private int count;
	
    public void setType(final String type) { this.type = type; }
    public void setCount(int count){ this.count = count; }
	
	public void process(InternalRequest req, InternalResponse res) {
			
		Database.connectToMainBase();
		ArrayList<DBWrapper> dbRes = Database.getTopNWithType(count, type);
		ArrayList<Record> webRes = new ArrayList<Record>(); 
		
		for (DBWrapper tmp : dbRes) {
			 Record newRecord = new Record();
			 newRecord.addCell("Name", tmp.getName());
			 newRecord.addCell("ID", tmp.getId());
			 newRecord.addCell("imageLink", tmp.getImagesArray().get(0));
			 newRecord.addCell("upName", tmp.getStaticCountryName());
			 newRecord.addCell("upId", tmp.getCountryId());
			 webRes.add(newRecord);
		}
		res.add(webRes);		
	}
}