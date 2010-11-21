package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import ru.amse.agregator.gui.model.MenuItem;
import ru.amse.agregator.storage.*;

public class SimpleMenuYalet implements Yalet{

	public void process(InternalRequest req, InternalResponse res) {
		
		DataBase.connectToDirtyBase();
		ArrayList<DBWrapper> continents = DataBase.getAllContinents();
		
		for(DBWrapper continent : continents){
			ArrayList<DBWrapper> countrytmp = DataBase.getAllCountriesByContinent(continent.getId());
			
			ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
			menuItems.add(new MenuItem(continent.getName(),null));
			for(DBWrapper country : countrytmp){
		        	menuItems.add(new MenuItem(country.getName(), country.getId().toString()));
		     }
			 
			 res.add(menuItems);
			 
		}
	}
}