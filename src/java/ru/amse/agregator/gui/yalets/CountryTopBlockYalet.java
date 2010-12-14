package ru.amse.agregator.gui.yalets;

import java.util.ArrayList;

import ru.amse.agregator.gui.model.Record;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class CountryTopBlockYalet implements Yalet {

	public void process(InternalRequest req, InternalResponse res) {
		
		Database.connectToMainBase();	
		ArrayList<DBWrapper> countries = Database.getTopNWithType(5, DBWrapper.TYPE_COUNTRY);
		ArrayList<Record> webRes = new ArrayList<Record>(); 
		
		for (DBWrapper country : countries) {
			
			ArrayList<DBWrapper> cities = Database.getTopNWithKeyValue(3,DBWrapper.TYPE_CITY,DBWrapper.FIELD_COUNTRY_ID, country.getId());
			
			Record countryRec = new Record();
			countryRec.addCell("name", country.getName());
			countryRec.addCell("id", country.getId().toString());
			//System.out.println(country.getName());
			ArrayList<Record> citiesRecArray = new ArrayList<Record>();
			for(DBWrapper city : cities){
				Record cityRecord = new Record();
				cityRecord.addCell("name", city.getName());
				//System.out.println(city.getName());
				cityRecord.addCell("id", city.getId().toString());
				citiesRecArray.add(cityRecord);
			}
			countryRec.addCell("cities", citiesRecArray);
			webRes.add(countryRec);
		}
		res.add(webRes);

	}

}
