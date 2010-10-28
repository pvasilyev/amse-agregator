package ru.amse.agregator.miner;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.Variable;

import ru.amse.agregator.storage.City;
import ru.amse.agregator.storage.DataBase;

public class Main {
	
	private static final String path = "amse-agregator/ru/amse/agregator/miner/resources/";
	private static String namesArray[];
	private static String imagesArray[];
	private static String descripArray[];
	private static String lonArray[];
	private static String latArray[];
	
	public static void main(String[] args) throws IOException {
	
		String mainFile;
		
		if(args.length == 1){
			mainFile = args[0].toString();
		}
		else{
			System.out.println("Please specify an input file.");
			return;
		}
		
		DataBase.connectToDirtyBase();	
		//DataBase.removeCollection("city");
		//DataBase.printAll();
		//System.in.read();
		
		FileReader fr = new FileReader(mainFile);
		BufferedReader br = new BufferedReader(fr);
		String s;
		
		while((s = br.readLine()) != null){
			
			String linksFile = s.substring(0, s.indexOf(';'));
			String configFile = s.substring(s.indexOf(';')+1);
			
			//configure Web-Harvester scraper 
			ScraperConfiguration config = new ScraperConfiguration(path + configFile);
			Scraper scraper = new Scraper(config, path);
			scraper.addVariableToContext("inputFile", linksFile);
			
			System.out.println("Processing links from: " + linksFile);
			System.out.println("Using config file: " + configFile + "\n");
			
			//Let's mine!
			scraper.execute();
			
			//get data from Web-Harvester
			Variable cityNames = (Variable) scraper.getContext().get("cityNamesOut");
			Variable cityImages  =	(Variable) scraper.getContext().get("cityImagesOut");
			Variable cityLatitudes  =	(Variable) scraper.getContext().get("cityLatitudesOut");
			Variable cityLongitudes  =	(Variable) scraper.getContext().get("cityLongitudesOut");
			Variable cityDescriptions  =	(Variable) scraper.getContext().get("cityDescriptionsOut");
			
			//clear and show mined data
			namesArray = processIncomeData(cityNames);
			imagesArray = processIncomeData(cityImages);
			lonArray = processIncomeData(cityLatitudes);
			latArray = processIncomeData(cityLongitudes);
			descripArray = processIncomeData(cityDescriptions);
			
			for(int i=0; i< namesArray.length; i++){
				
				//create new City object and fill it fields
				City newCity = new City();
				newCity.setName(namesArray[i].toString());
				newCity.setPhoto(imagesArray[i].toString());
				newCity.setDescription(descripArray[i].toString());
				
				if(latArray != null && lonArray != null){
					newCity.setCoordinates(createCoord(lonArray[i],latArray[i]));
				}
				
				ArrayList<String> keywords = new ArrayList<String>();
				keywords.add(namesArray[i].toString());
				newCity.setKeyWordsArray(keywords);
				
				DataBase.add(newCity);
				
			}
			
			System.out.println("\n--- Links from: " + linksFile  + " mine complete! ---\n");

		}
		
		fr.close();

	}
	
	//function where we will add data to DB, by now here we display mined data
	private static String[] processIncomeData(Variable data){
		String dataArray[] = null;
		if(data != null){
			dataArray = createArray(data.toString().concat("\n"));
			for(int i=0; i < dataArray.length; i++){
				dataArray[i] = clearString(dataArray[i].toString());
			//	System.out.println(dataArray[i]);
			}
		}	
		return dataArray;
	}
	
	//function that creates gps coordinates
	private static Point2D.Double createCoord(String lon, String lat){
		double doubleLat, doubleLon;
		Point2D.Double myPoint;
		
		doubleLon = java.lang.Double.parseDouble(lon.substring(0, lon.indexOf('°')));
		doubleLon += java.lang.Double.parseDouble(lon.substring(lon.indexOf('°')+1,lon.indexOf('′'))) / 60;
		
		doubleLat = java.lang.Double.parseDouble(lat.substring(0, lat.indexOf('°')));
		doubleLat += java.lang.Double.parseDouble(lat.substring(lat.indexOf('°')+1,lat.indexOf('′'))) / 60;
		
		if(lon.indexOf('с') == -1)
			doubleLon *= -1;
		
		if(lat.indexOf('в') == -1)
			doubleLat *= -1;
		
		myPoint = new Point2D.Double(doubleLon, doubleLat);
		return myPoint;
	}
	
	//function to clear the data from tags, unnecessary spaces, etc...
	private static String clearString(String toClear){
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
		return toClear;
	}
	
    private static String[] createArray(String input){
        int linesCounter = 0, current = 0;
        current = input.indexOf('\n');
        while(current != -1){
                ++current;
                current = input.indexOf('\n', current);
                linesCounter++;
        }
        
        String Array[] = new String[linesCounter];
        for(int i = 0; i< linesCounter; i++){
                Array[i] = input.substring(0, input.indexOf('\n'));
                if(Array[i].length() == 0){
                        Array[i] = "null";
                }
                input = input.substring(input.indexOf('\n')+1);
        }
        return Array;
    }


}