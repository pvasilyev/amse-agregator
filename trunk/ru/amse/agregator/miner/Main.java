package ru.amse.agregator.miner;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;


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
		
		FileReader fr =new FileReader(mainFile);
		BufferedReader br = new BufferedReader(fr);
		String s;
		
		while((s = br.readLine()) != null){
			
			String linksFile = s.substring(0, s.indexOf(';'));
			String configFile = s.substring(s.indexOf(';')+1);
			
			ScraperConfiguration config = new ScraperConfiguration(configFile);
			Scraper scraper = new Scraper(config, "resources/");
			scraper.addVariableToContext("inputFile", linksFile);
			scraper.execute();
			
			String cityNames  =	      scraper.getContext().get("cityNamesOut").toString().concat("\n");
			String cityImages  =	  scraper.getContext().get("cityImagesOut").toString().concat("\n");
			String cityLatitudes =    scraper.getContext().get("cityLatitudesOut").toString().concat("\n");
			String cityLongitudes =   scraper.getContext().get("cityLongitudesOut").toString().concat("\n");
			String cityDescriptions = scraper.getContext().get("cityDescriptionsOut").toString().concat("\n");
			
			
			String namesArray[] = createArray(cityNames);
			String imagesArray[] = createArray(cityImages);
			String latArray[] = createArray(cityLatitudes);
			String lonArray[] = createArray(cityLongitudes);
			String desArray[] = createArray(cityDescriptions);
			
			for(int i=0; i<namesArray.length; i++){
				namesArray[i] = removeTags(namesArray[i]);
				System.out.println(namesArray[i]);
				
				try{
				imagesArray[i] = imagesArray[i].substring(imagesArray[i].indexOf("src=")+5); 
				imagesArray[i] = imagesArray[i].substring(0, imagesArray[i].indexOf("\""));
				System.out.println(imagesArray[i]);
				}
				catch(StringIndexOutOfBoundsException e){
					System.out.println("emptyLink");
				}
				

				latArray[i*2] = removeTags(latArray[i*2]);
				System.out.println(latArray[i*2]);
				
				lonArray[i*2] = removeTags(lonArray[i*2]);
				System.out.println(lonArray[i*2]);

				int j = i*2;
				desArray[j] = desArray[j] + desArray[j + 1]; 
				desArray[j].replace('\n', ' ');
				desArray[j] = removeTags(desArray[j]);
				desArray[j] = desArray[j].replaceAll("\t", "");
				desArray[j] = desArray[j].replaceAll(" {2,}", " ");
				desArray[j] = desArray[j].replaceAll(" {1,}[.]", ".");
				desArray[j] = desArray[j].replaceAll(" {1,}[,]", ",");
				System.out.println(desArray[j]);
				
			}
			
		}
		fr.close();
		
	}
	
	
	private static String removeTags(String toClear){
		int a,b;
		while(toClear.indexOf('<') != (-1)){
			a = toClear.indexOf('<');
			b = toClear.indexOf('>');
			if( b > a ){
				toClear = toClear.substring(0,a) + toClear.substring(b,toClear.length());
				toClear = toClear.replaceFirst(">", " ");
			}
		}
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
				Array[i] = "empty!";
			}
			input = input.substring(input.indexOf('\n')+1);
		}
		return Array;
		
	}

}
