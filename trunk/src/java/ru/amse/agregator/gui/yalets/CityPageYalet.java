package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import ru.amse.agregator.gui.model.Attraction;
import ru.amse.agregator.gui.model.DescriptionModel;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.gui.model.UniversalModel;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class CityPageYalet implements Yalet{
    Logger log = Logger.getLogger(ShowAttractionsYalet.class);
    
	@Override
	public void process(InternalRequest req, InternalResponse res) {

		Database.connectToMainBase();
		String id = req.getParameter("id");
		ObjectId selectedCity = new ObjectId(id);

		DBWrapper city = Database.getDBObjectByIdAndType(selectedCity, DBWrapper.TYPE_CITY);

		Attraction response = new Attraction();
		response.setName(city.getName());
		response.setDescription(city.getDescriptionArray().get(0));
//		response.setImageArray(city.getImagesArray());

		ArrayList<String> list = Database.getAllTypesOfObjectByCity(selectedCity);

		ArrayList<MenuItem> links = new ArrayList<MenuItem>();

		for(String tmp : list){
            ArrayList<DBWrapper> array = Database.getAllObjectOfSelectedTypeInCity(new ObjectId(id), tmp);
            for (DBWrapper dbwr: array) {
                MenuItem attraction = new MenuItem(dbwr.getName(), dbwr.getId().toString());
                attraction.setType(dbwr.getType());
                links.add(attraction);
            }
//			MenuItem newCity = new MenuItem(tmp , id);
//            newCity.setType(tmp);
//			links.add(newCity);
		}
//        log.error(links);
		response.setAttractionList(links);
		res.add(response);
	}
}
