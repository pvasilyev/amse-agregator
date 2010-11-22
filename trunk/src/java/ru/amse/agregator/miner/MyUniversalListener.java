package ru.amse.agregator.miner;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;

import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;


public class MyUniversalListener implements ScraperRuntimeListener {
		
	@Override
	public void onNewProcessorExecution(Scraper scraper, BaseProcessor arg1) {
			
			/*Variable link = (Variable) scraper.getContext().get("countryLink");
			Variable cityName = (Variable) scraper.getContext().get("CityName");
			Variable cityImage  = (Variable) scraper.getContext().get("cityImages");
			Variable cityDescription  = (Variable) scraper.getContext().get("cityDescription");
			Variable cityLatitude  = (Variable) scraper.getContext().get("cityLatitudesOut");
			Variable cityLongitude  = (Variable) scraper.getContext().get("cityLongitudesOut");
			
			System.out.println ("link");			
			
			String sCityName = clearString(cityName);
			String sCityImage = clearString(cityImage);
			String sCityLatitude = clearString(cityLatitude);
			String sCityLongitude = clearString(cityLongitude);	
			String sCityDescription = clearString(cityDescription);
		
			DBWrapper newCity = new DBWrapper();
			newCity.setType(DBWrapper.TYPE_CITY);
			newCity.setName(sCityName);
			newCity.setPhoto(sCityImage);
			newCity.setDescription(sCityDescription);
		
			if(sCityLatitude != null && sCityLongitude != null){
				newCity.setCoordinates(createCoord(sCityLongitude,sCityLatitude));
			}
		
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add(sCityName);
			newCity.setKeyWordsArray(keywords);

			Database.add(newCity);*/
		
		
	}

	@Override
	public void onExecutionContinued(Scraper arg0) {
		// TODO Auto-generated method stub

	}	
	@Override
	public void onExecutionError(Scraper arg0, Exception arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onExecutionPaused(Scraper arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onExecutionStart(Scraper arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onExecutionEnd(Scraper scraper) {
				

	}

	
	@Override
	public void onProcessorExecutionFinished(Scraper scr, BaseProcessor bp, @SuppressWarnings("rawtypes") Map arg2) {
			
		if (bp.getElementDef().getShortElementName().equalsIgnoreCase("var-def") && scr.getContext().getVar("addToDB").toInt() == 1){
			
			scr.pauseExecution();
			Variable incomeObject = (Variable) scr.getContext().get("returnValue");
			scr.getContext().setVar("addToDB", 0);
			scr.continueExecution();
			
			@SuppressWarnings("rawtypes")
			List myList = incomeObject.toList();
			System.out.println(myList.toString());
			
			DBWrapper newEntry = new DBWrapper();
			for(int i=0; i<myList.size(); i+=2){
				
				if(myList.get(i) != null ){
					
				
				    if(myList.get(i).toString().equals(DBWrapper.FIELD_IMAGES)){
					    
				    	newEntry.setImagesArray(createImagesArray(clearString(myList.get(i+1).toString())));
					    
				    } else if(myList.get(i).toString().equals(DBWrapper.FIELD_KEYWORDS)){
					
					    newEntry.setKeyWordsArray(createImagesArray(clearString(myList.get(i+1).toString())));
					
				    }else if(myList.get(i).toString().equals(DBWrapper.FIELD_COORDS)){
		
					    ArrayList<Point2D.Double> coords = new ArrayList<Point2D.Double>();
					    String tmp = myList.get(i+1).toString();
					    double lon, lat;
					    lon = Double.parseDouble(tmp.substring(0, tmp.indexOf(';')));
					    lat = Double.parseDouble(tmp.substring(tmp.indexOf(';') +1 ));
					    coords.add(new Point2D.Double(lon, lat));
					    newEntry.setCoordsArray(coords);
				    }
				    else {
					    newEntry.setKeyValue(myList.get(i).toString(),clearString(myList.get(i+1).toString()));
				    }
				}
			}
			Database.add(newEntry);

		}
	}
	
	private ArrayList<String> createImagesArray(String input){
		
		if(input == null || input.equals("")){
			return null;
		}
		
		ArrayList<String> images = new ArrayList<String>();
		
		if(input.indexOf(';') == -1){
			images.add(input);
		}
		else{
			int fromIndex = 0;
			int toIndex = input.indexOf(';',fromIndex);
			while(toIndex != -1){
				images.add(input.substring(fromIndex, toIndex));
				fromIndex = toIndex + 1;
				if(fromIndex < input.length()){
					toIndex = input.indexOf(';',fromIndex);
				}
				else {
					break;
				}
				
			}
			if(fromIndex < input.length()){
				images.add(input.substring(fromIndex));
			}
			
			
		}
		return images;
	}
	
	//This method clears string from tags
	private String clearString(String incomed){
		
		if(incomed != null && !incomed.isEmpty()){
			int a,b;
			while(incomed.indexOf('<') != (-1) && incomed.indexOf('>') != (-1)){
				a = incomed.indexOf('<');
				b = incomed.indexOf('>', a);
				if( b > a ){
					incomed = incomed.substring(0,a) + incomed.substring(b);
					incomed = incomed.replaceFirst(">", " ");
				}
				else {
					break;
				}
			}	
			incomed = incomed.replaceAll("\n", " ");
			incomed = incomed.replaceAll("\t", " ");
			incomed = incomed.replaceAll(" {2,}", " ");
			incomed = incomed.replaceAll(" {1,}[.]", ".");
			incomed = incomed.replaceAll(" {1,}[,]", ",");
			return incomed.trim();
		}
		else
			return null;
	}
	
	//Create coordinates point from string
	@SuppressWarnings("unused")
	private Point2D.Double createCoord(String lon, String lat){
		double doubleLat, doubleLon;
		
		doubleLon = java.lang.Double.parseDouble(lon.substring(0, lon.indexOf('°')));
		doubleLon += java.lang.Double.parseDouble(lon.substring(lon.indexOf('°')+1,lon.indexOf('′'))) / 60;
		
		doubleLat = java.lang.Double.parseDouble(lat.substring(0, lat.indexOf('°')));
		doubleLat += java.lang.Double.parseDouble(lat.substring(lat.indexOf('°')+1,lat.indexOf('′'))) / 60;
		
		if(lon.indexOf('с') == -1)
			doubleLon *= -1;
		
		if(lat.indexOf('в') == -1)
			doubleLat *= -1;
		
		return new Point2D.Double(doubleLon, doubleLat);
	}
}
