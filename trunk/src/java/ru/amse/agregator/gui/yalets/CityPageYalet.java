package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import ru.amse.agregator.gui.model.DescriptionModel;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.gui.model.UniversalModel;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class CityPageYalet implements Yalet{

	@Override
	public void process(InternalRequest req, InternalResponse res) {
	
		Database.connectToDirtyBase();
		String id = req.getParameter("id");
		ObjectId selectedCity = new ObjectId(id);
		
		DBWrapper city = Database.getDBObjectByIdAndType(selectedCity, DBWrapper.TYPE_CITY);
		
		DescriptionModel response = new DescriptionModel();
		response.setName(city.getName());
		response.setDescription(city.getDescriptionArray().get(0));
		response.setImages(city.getImagesArray());
		
		ArrayList<String> list = Database.getAllTypesOfObjectByCity(selectedCity);
		
		ArrayList<MenuItem> links = new ArrayList<MenuItem>();
		
		for(String tmp : list){
			MenuItem newCity = new MenuItem(tmp , id);
			links.add(newCity);
		}
		response.setCities(links);
		res.add(response);
	}
}
