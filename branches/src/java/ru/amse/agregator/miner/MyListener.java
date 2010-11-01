package ru.amse.agregator.miner;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;

import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.DataBase;


public class MyListener implements ScraperRuntimeListener {
	
	private int i;
	private int max;
	
	public MyListener(int max) {
		this.max = max;
		i = 1;
	}

	public void onExecutionContinued(Scraper arg0) {
		// TODO Auto-generated method stub

	}

	public void onExecutionEnd(Scraper scraper) {
		scraper.exitExecution(null);
		i+=50;
		if(i < max){
			ScraperConfiguration config;
			try {
				config = new ScraperConfiguration(Main.path + Main.configFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			scraper = new Scraper(config, Main.path);
			scraper.addVariableToContext("inputFile", Main.linksFile);
			if(i+50 > max)
				scraper.addVariableToContext("filter", i + "-"+ max);
			else
				scraper.addVariableToContext("filter", i + "-"+ (i+50));
			
			scraper.addRuntimeListener(this);
			scraper.getHttpClientManager().setHttpProxy("192.168.0.2", 3128);
			scraper.execute();
		}
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

	
	public void onProcessorExecutionFinished(Scraper arg0, BaseProcessor arg1,
			Map arg2) {
		// TODO Auto-generated method stub

	}
	
	
	public void onNewProcessorExecution(Scraper scraper, BaseProcessor arg1) {

		if(scraper.getRunningProcessor().getElementDef().getShortElementName() == "empty"){
			
			scraper.pauseExecution();
			
			String link = scraper.getContext().get("link").toString();
			String cityName =  scraper.getContext().get("cityNamesOut").toString();
			String cityImage  =	scraper.getContext().get("cityImagesOut").toString();
			String cityLatitude  =	 scraper.getContext().get("cityLatitudesOut").toString();
			String cityLongitude  =	scraper.getContext().get("cityLongitudesOut").toString();
			String cityDescription  =	 scraper.getContext().get("cityDescriptionsOut").toString();
						
			scraper.continueExecution();
			
			System.out.println (i+link);
			i++;
			
			cityName = clearString(cityName);
			cityImage = clearString(cityImage);
			cityLatitude = clearString(cityLatitude);
			cityLongitude = clearString(cityLongitude);	
			cityDescription = clearString(cityDescription);
			
			DBWrapper newCity = new DBWrapper();
			newCity.setType(DBWrapper.TYPE_CITY);
			newCity.setName(cityName);
			newCity.setPhoto(cityImage);
			newCity.setDescription(cityDescription);
			
			if(cityLatitude != null && cityLongitude != null){
				newCity.setCoordinates(createCoord(cityLongitude,cityLatitude));
			}
			
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add(cityName);
			newCity.setKeyWordsArray(keywords);

			DataBase.add(newCity);
			
		}
	}
	
	private static String clearString(String toClear){
		if(toClear != null && !toClear.equals("")){
			int a,b;
			while(toClear.indexOf('<') != (-1)){
				a = toClear.indexOf('<');
				b = toClear.indexOf('>');
				if( b > a ){
					toClear = toClear.substring(0,a) + toClear.substring(b,toClear.length());
					toClear = toClear.replaceFirst(">", " ");
				}
			}	
			toClear = toClear.replaceAll("\t", "");
			toClear = toClear.replaceAll(" {2,}", " ");
			toClear = toClear.replaceAll(" {1,}[.]", ".");
			toClear = toClear.replaceAll(" {1,}[,]", ",");
		}
		else{
			toClear = null;
		}
		return toClear;
	}
	
	private static Point2D.Double createCoord(String lon, String lat){
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
	
	public int getCount(){
		return i;
	}

}
