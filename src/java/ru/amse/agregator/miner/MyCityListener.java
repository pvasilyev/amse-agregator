package ru.amse.agregator.miner;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;

import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.DataBase;


public class MyCityListener implements ScraperRuntimeListener {
	
	
	
	public void onExecutionEnd(Scraper scraper) {
				


	}
	
	private String clearString(Variable incomed){
		
		if(incomed != null){
			if(incomed.toString().equals(""))
				return null;
			String toClear = incomed.toString();
			int a,b;
			while(toClear.indexOf('<') != (-1)){
				a = toClear.indexOf('<');
				b = toClear.indexOf('>');
				if( b > a ){
					toClear = toClear.substring(0,a) + toClear.substring(b,toClear.length());
					toClear = toClear.replaceFirst(">", " ");
				}
			}	
			toClear = toClear.replaceAll("\n", " ");
			toClear = toClear.replaceAll("\t", "");
			toClear = toClear.replaceAll(" {2,}", " ");
			toClear = toClear.replaceAll(" {1,}[.]", ".");
			toClear = toClear.replaceAll(" {1,}[,]", ",");
			return toClear;
		}
		else
			return null;
	}
	
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

	public void onExecutionContinued(Scraper arg0) {
		// TODO Auto-generated method stub

	}
	
	public void onExecutionError(Scraper arg0, Exception arg1) {
		// TODO Auto-generated method stub

	}

	public void onExecutionPaused(Scraper arg0) {
		// TODO Auto-generated method stub

	}

	public void onExecutionStart(Scraper arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public void onProcessorExecutionFinished(Scraper arg0, BaseProcessor arg1,
			Map arg2) {
		// TODO Auto-generated method stub

	}
	
	
	public void onNewProcessorExecution(Scraper scraper, BaseProcessor arg1) {
		
		if(scraper.getRunningProcessor().getElementDef().getShortElementName() == "empty"){
			Variable link = (Variable) scraper.getContext().get("linkToScrap");
			Variable cityName = (Variable) scraper.getContext().get("CityName");
			Variable cityImage  = (Variable) scraper.getContext().get("cityImages");
			Variable cityDescription  = (Variable) scraper.getContext().get("cityDescription");
			Variable cityLatitude  = (Variable) scraper.getContext().get("cityLatitudesOut");
			Variable cityLongitude  = (Variable) scraper.getContext().get("cityLongitudesOut");
			
						
			System.out.println (link);
			
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

			DataBase.add(newCity);
		
		}
		
	}
}
