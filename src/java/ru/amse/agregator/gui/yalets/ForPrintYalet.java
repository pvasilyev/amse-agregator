package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import org.bson.types.ObjectId;
import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.StorageObject;
import ru.amse.agregator.storage.Tour;

public class ForPrintYalet extends AbstractAgregatorYalet {
	public void process(InternalRequest req, InternalResponse res) {

		Database.connectToMainBase();
		ArrayList<Record> webRes = new ArrayList<Record>();
		// ObjectId selectedItem = new ObjectId(req.getParameter("id"));
		String type = req.getParameter("type");
		Tour tour = new Tour();
		DBWrapper attr = new DBWrapper();
		attr.setName("FFFFFFFFFFF");
		attr.setKeyValue(DBWrapper.FIELD_INFO, "info");
		attr.setType(Database.COLLECTION_ATTRACTIONS);
		Database.add(attr);
		tour.addAttraction(attr.getId());
		Database.addTour(tour);
		ArrayList<ObjectId> allArch = tour.getAttraction();
		for (ObjectId id : allArch) {
			DBWrapper atch = new DBWrapper(Database
					.findInCollection(Database.COLLECTION_ATTRACTIONS,
							StorageObject.FIELD_ID, id));
			Record newRecord = new Record();
			newRecord.addCell("name", atch.getName());
			System.out.println(atch.getName());
			if (atch.getCoordsArray() != null
					&& atch.getCoordsArray().size() > 0) {
				if (atch.getCoordsArray().get(0) != null) {
					System.out.println(atch.getCoordsArray().get(0).x);
					newRecord.addCell("lng", atch.getCoordsArray().get(0).x);
					newRecord.addCell("y", atch.getCoordsArray().get(0).y);
				} else {
					newRecord.addCell("lng", " ");
					newRecord.addCell("y", " ");
				}
			} else {
				newRecord.addCell("lng", " ");
				newRecord.addCell("y", " ");
			}
			if (atch.getInfo() != null) {
				newRecord.addCell("info", atch.getInfo());
			} else {
				newRecord.addCell("info", " ");
			}
			newRecord.addCell("id_attr", id.toString());
			newRecord.addCell("id_user", req.getParameter("user"));
			System.out.println(id.toString());
			webRes.add(newRecord);

		}
		res.add(webRes);
	}
}