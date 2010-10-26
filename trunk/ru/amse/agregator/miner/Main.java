package ru.amse.agregator.miner;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.Variable;



public class Main {

	public static void main(String[] args) throws IOException {
	
		String mainFile;
		
		if(args.length == 1){
			mainFile = args[0].toString();
		}
		else{
			System.out.println("Please specify an input file.");
			return;
		}
		
		FileReader fr = new FileReader(mainFile);
		BufferedReader br = new BufferedReader(fr);
		String s;
		
		while((s = br.readLine()) != null){
			
			String linksFile = s.substring(0, s.indexOf(';'));
			String configFile = s.substring(s.indexOf(';')+1);
			
			//configure Web-Harvester scraper 
			ScraperConfiguration config = new ScraperConfiguration(configFile);
			Scraper scraper = new Scraper(config, "resources/");
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
			processIncomeData(cityNames);
			processIncomeData(cityImages);
			processIncomeData(cityLatitudes);
			processIncomeData(cityLongitudes);
			processIncomeData(cityDescriptions);
			
			System.out.println("\n--- Links from: " + linksFile  + " mine complete! ---\n");

		}
		
		fr.close();

	}
	
	//function where we will add data to DB, by now here we display mined data
	private static void processIncomeData(Variable data){
		
		if(data != null){
			Object dataArray[] = data.toArray();
			for(int i=0; i < dataArray.length; i++){
				dataArray[i] = (Object) clearString(dataArray[i].toString());
				System.out.println(dataArray[i]);
			}
		}			
		
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

}
